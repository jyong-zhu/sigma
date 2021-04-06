package com.zone.process.infrastructure.db.repository.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zone.commons.util.IdWorkerUtil;
import com.zone.process.domain.agg.ProcessDefAgg;
import com.zone.process.domain.repository.ProcessDefAggRepository;
import com.zone.process.domain.valueobject.DefNodePropertyVO;
import com.zone.process.domain.valueobject.DefNodeVO;
import com.zone.process.domain.valueobject.DefNodeVariableVO;
import com.zone.process.infrastructure.db.adapter.ProcessDefAggAdapter;
import com.zone.process.infrastructure.db.dataobject.ProcessDefDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodePropertyDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeVariableDO;
import com.zone.process.infrastructure.db.mapper.ProcessDefMapper;
import com.zone.process.infrastructure.db.mapper.ProcessDefNodeMapper;
import com.zone.process.infrastructure.db.mapper.ProcessDefNodePropertyMapper;
import com.zone.process.infrastructure.db.mapper.ProcessDefNodeVariableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:07 下午
 * @Description:
 */
@Service
public class ProcessDefAggRepositoryImpl implements ProcessDefAggRepository {

    @Autowired
    private ProcessDefMapper defMapper;

    @Autowired
    private ProcessDefNodeMapper defNodeMapper;

    @Autowired
    private ProcessDefNodePropertyMapper nodePropertyMapper;

    @Autowired
    private ProcessDefNodeVariableMapper nodeVariableMapper;


    @Override
    public void save(ProcessDefAgg processDefAgg) {

        // 保存流程定义的时候，要把老版本的流程定义的 isLatest 字段更新掉
        List<ProcessDefDO> oldDefDOList = defMapper.selectList(new QueryWrapper<ProcessDefDO>()
                .eq("proc_def_key", processDefAgg.getProcDefKey()));
        int total = 0;
        // 这里使用乐观锁，考虑到数据量也不大，所以用 for 循环去调用
        for (ProcessDefDO tmp : oldDefDOList) {
            tmp.setIsLatest(false);
            total += defMapper.updateById(tmp);
        }
        Preconditions.checkState(total == oldDefDOList.size(), "更新旧版本流程实例失败");

        ProcessDefDO defDO = BeanUtil.copyProperties(processDefAgg, ProcessDefDO.class);
        defMapper.insert(defDO);

        if (CollectionUtil.isNotEmpty(processDefAgg.getNodeVOList())) {
            List<ProcessDefNodeDO> nodeDOList = Lists.newArrayList();
            List<ProcessDefNodePropertyDO> propertyList = Lists.newArrayList();
            List<ProcessDefNodeVariableDO> variableList = Lists.newArrayList();

            processDefAgg.getNodeVOList().forEach(nodeVO -> {
                Long nodeId = IdWorkerUtil.nextId();
                ProcessDefNodeDO nodeDO = ProcessDefAggAdapter.getProcessDefNodeDO(nodeVO, defDO.getId(), nodeId);
                nodeDO.setVersion(0);
                nodeDOList.add(nodeDO);

                if (CollectionUtil.isNotEmpty(nodeVO.getPropertyVOList())) {
                    nodeVO.getPropertyVOList().forEach(propertyVO -> {
                        Long id = IdWorkerUtil.nextId();
                        ProcessDefNodePropertyDO propertyDO = ProcessDefAggAdapter.getProcessDefNodePropertyDO(propertyVO, nodeId, nodeVO.getBpmnNodeId(), id);
                        propertyDO.setVersion(0);
                        propertyList.add(propertyDO);
                    });
                }

                if (CollectionUtil.isNotEmpty(nodeVO.getVariableVOList())) {
                    nodeVO.getVariableVOList().forEach(variableVO -> {
                        Long id = IdWorkerUtil.nextId();
                        ProcessDefNodeVariableDO variableDO = ProcessDefAggAdapter.getProcessDefNodeVariableDO(variableVO, nodeId, nodeVO.getBpmnNodeId(), id);
                        variableDO.setVersion(0);
                        variableList.add(variableDO);
                    });
                }
            });

            defNodeMapper.insertBatchSomeColumn(nodeDOList);

            if (CollectionUtil.isNotEmpty(propertyList)) {
                nodePropertyMapper.insertBatchSomeColumn(propertyList);
            }

            if (CollectionUtil.isNotEmpty(variableList)) {
                nodeVariableMapper.insertBatchSomeColumn(variableList);
            }
        }
    }

    @Override
    public ProcessDefAgg queryById(Long defId) {

        ProcessDefDO processDefDO = defMapper.selectById(defId);
        if (processDefDO != null) {
            ProcessDefAgg defAgg = BeanUtil.copyProperties(processDefDO, ProcessDefAgg.class);
            defAgg.setNodeVOList(queryNodeList(defId));
            return defAgg;
        }
        return null;
    }

    @Override
    public ProcessDefAgg queryByKey(String defKey) {
        ProcessDefDO processDefDO = defMapper.selectOne(
                new QueryWrapper<ProcessDefDO>().eq("proc_def_key", defKey)
                        .eq("is_latest", true));
        if (processDefDO != null) {
            ProcessDefAgg defAgg = BeanUtil.copyProperties(processDefDO, ProcessDefAgg.class);
            defAgg.setNodeVOList(queryNodeList(processDefDO.getId()));
            return defAgg;
        }
        return null;
    }

    private List<DefNodeVO> queryNodeList(Long defId) {
        List<DefNodeVO> result = Lists.newArrayList();
        List<ProcessDefNodeDO> nodeDOList = defNodeMapper.selectList(new QueryWrapper<ProcessDefNodeDO>()
                .eq("def_id", defId));
        if (CollectionUtil.isNotEmpty(nodeDOList)) {
            // 只查一次，将节点参数与属性查出来
            List<Long> nodeIdList = nodeDOList.stream().map(node -> node.getId()).collect(Collectors.toList());
            List<ProcessDefNodePropertyDO> propertyDOList = nodePropertyMapper.selectList(
                    new QueryWrapper<ProcessDefNodePropertyDO>().in("node_id", nodeIdList));
            List<ProcessDefNodeVariableDO> variableDOList = nodeVariableMapper.selectList(
                    new QueryWrapper<ProcessDefNodeVariableDO>().in("node_id", nodeIdList));

            Map<Long, List<ProcessDefNodePropertyDO>> propertyMap = Maps.newHashMap();
            Map<Long, List<ProcessDefNodeVariableDO>> variableMap = Maps.newHashMap();
            propertyDOList.forEach(tmp -> {
                List<ProcessDefNodePropertyDO> list = propertyMap.getOrDefault(tmp.getNodeId(), Lists.newArrayList());
                list.add(tmp);
                propertyMap.put(tmp.getNodeId(), list);
            });
            variableDOList.forEach(tmp -> {
                List<ProcessDefNodeVariableDO> list = variableMap.getOrDefault(tmp.getNodeId(), Lists.newArrayList());
                list.add(tmp);
                variableMap.put(tmp.getNodeId(), list);
            });


            nodeDOList.forEach(node -> {
                DefNodeVO nodeVO = BeanUtil.copyProperties(node, DefNodeVO.class);
                nodeVO.setVariableVOList(getVariableVOList(variableMap.getOrDefault(node.getId(), Lists.newArrayList())));
                nodeVO.setPropertyVOList(getPropertyVOList(propertyMap.getOrDefault(node.getId(), Lists.newArrayList())));
                result.add(nodeVO);
            });
        }
        return result;
    }

    private List<DefNodePropertyVO> getPropertyVOList(List<ProcessDefNodePropertyDO> propertyDOList) {
        return CollectionUtil.isEmpty(propertyDOList) ? Lists.newArrayList() :
                propertyDOList.stream().map(property -> BeanUtil.copyProperties(property, DefNodePropertyVO.class)).collect(Collectors.toList());
    }

    private List<DefNodeVariableVO> getVariableVOList(List<ProcessDefNodeVariableDO> variableDOList) {
        return CollectionUtil.isEmpty(variableDOList) ? Lists.newArrayList() :
                variableDOList.stream().map(variable -> BeanUtil.copyProperties(variable, DefNodeVariableVO.class)).collect(Collectors.toList());
    }
}
