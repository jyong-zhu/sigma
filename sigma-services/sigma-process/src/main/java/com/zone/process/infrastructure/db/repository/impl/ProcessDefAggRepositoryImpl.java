package com.zone.process.infrastructure.db.repository.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.zone.commons.util.IdWorkerUtil;
import com.zone.process.domain.agg.ProcessDefAgg;
import com.zone.process.domain.repository.ProcessDefAggRepository;
import com.zone.process.domain.valueobject.DefNodeVO;
import com.zone.process.infrastructure.db.adapter.ProcessDefAggAdapter;
import com.zone.process.infrastructure.db.dataobject.ProcessDefDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodePropertyDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeVariableDO;
import com.zone.process.infrastructure.db.mapper.ProcessDefMapper;
import com.zone.process.infrastructure.db.mapper.ProcessDefNodeMapper;
import com.zone.process.infrastructure.db.mapper.ProcessDefNodePropertyMapper;
import com.zone.process.infrastructure.db.mapper.ProcessDefNodeVariableMapper;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:07 下午
 * @Description:
 */
@Slf4j
@Service
public class ProcessDefAggRepositoryImpl implements ProcessDefAggRepository {

  @Resource
  private ProcessDefMapper defMapper;

  @Resource
  private ProcessDefNodeMapper defNodeMapper;

  @Resource
  private ProcessDefNodePropertyMapper nodePropertyMapper;

  @Resource
  private ProcessDefNodeVariableMapper nodeVariableMapper;

  @Override
  public Long save(ProcessDefAgg processDefAgg) {

    // 0. 保存流程定义的时候，要把老版本的流程定义的 isLatest 字段更新掉
    QueryWrapper<ProcessDefDO> wrapper = new QueryWrapper<>();
    wrapper.lambda().eq(ProcessDefDO::getProcDefKey, processDefAgg.getProcDefKey())
        .eq(ProcessDefDO::getIsLatest, true);
    ProcessDefDO oldDefDO = defMapper.selectOne(wrapper);

    // 1. 使用乐观锁更新旧流程定义的isLatest字段
    if (oldDefDO != null) {
      oldDefDO.setIsLatest(false);
      int num = defMapper.updateById(oldDefDO);
      if (num == 0) {
        log.error("更新旧版本流程实例失败, processDefAgg=[{}]", processDefAgg);
        return null;
      }
    }

    // 2. 落地流程定义数据
    ProcessDefDO defDO = ProcessDefAggAdapter.getProcessDefDO(processDefAgg);
    int num = defMapper.insert(defDO);
    if (num == 0) {
      log.error("插入流程定义失败, processDefAgg=[{}]", processDefAgg);
      return null;
    }

    // 3. 落地节点数据
    if (CollectionUtil.isNotEmpty(processDefAgg.getNodeVOList())) {
      List<ProcessDefNodeDO> nodeDOList = Lists.newArrayList();
      List<ProcessDefNodePropertyDO> propertyList = Lists.newArrayList();
      List<ProcessDefNodeVariableDO> variableList = Lists.newArrayList();

      processDefAgg.getNodeVOList().forEach(nodeVO -> {
        // nodeId需要手动生成，给property和variable进行关联
        Long nodeId = IdWorkerUtil.nextId();
        String bpmnNodeId = nodeVO.getBpmnNodeId();
        ProcessDefNodeDO nodeDO = ProcessDefAggAdapter.getProcessDefNodeDO(nodeVO, defDO.getId(), nodeId);
        nodeDO.setVersion(0);
        nodeDOList.add(nodeDO);

        if (CollectionUtil.isNotEmpty(nodeVO.getPropertyVOList())) {
          nodeVO.getPropertyVOList().forEach(propertyVO -> {
            ProcessDefNodePropertyDO propertyDO = ProcessDefAggAdapter.getProcessDefNodePropertyDO(propertyVO, nodeId, bpmnNodeId);
            propertyList.add(propertyDO);
          });
        }

        if (CollectionUtil.isNotEmpty(nodeVO.getVariableVOList())) {
          nodeVO.getVariableVOList().forEach(variableVO -> {
            ProcessDefNodeVariableDO variableDO = ProcessDefAggAdapter.getProcessDefNodeVariableDO(variableVO, nodeId, bpmnNodeId);
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
    return defDO.getId();
  }

  @Override
  public ProcessDefAgg queryById(Long defId) {

    ProcessDefDO processDefDO = defMapper.selectById(defId);

    return ProcessDefAggAdapter.getProcessDefAgg(processDefDO, queryNodeList(processDefDO.getId()));
  }

  @Override
  public ProcessDefAgg queryByKey(String defKey) {
    QueryWrapper<ProcessDefDO> wrapper = new QueryWrapper<>();
    wrapper.lambda().eq(ProcessDefDO::getProcDefKey, defKey)
        .eq(ProcessDefDO::getIsLatest, true);
    ProcessDefDO processDefDO = defMapper.selectOne(wrapper);

    return ProcessDefAggAdapter.getProcessDefAgg(processDefDO, queryNodeList(processDefDO.getId()));

  }

  private List<DefNodeVO> queryNodeList(Long defId) {
    List<DefNodeVO> result = Lists.newArrayList();

    // 1. 查询指定defId下的节点
    List<ProcessDefNodeDO> nodeDOList = defNodeMapper.selectList(
        new LambdaQueryWrapper<ProcessDefNodeDO>().eq(ProcessDefNodeDO::getDefId, defId));

    // 2. 节点属性配置
    if (CollectionUtil.isNotEmpty(nodeDOList)) {
      // 只查一次，将节点参数与属性查出来
      List<Long> nodeIdList = nodeDOList.stream().map(node -> node.getId()).collect(Collectors.toList());
      List<ProcessDefNodePropertyDO> propertyDOList = nodePropertyMapper.selectList(
          new LambdaQueryWrapper<ProcessDefNodePropertyDO>().in(ProcessDefNodePropertyDO::getNodeId, nodeIdList));
      List<ProcessDefNodeVariableDO> variableDOList = nodeVariableMapper.selectList(
          new LambdaQueryWrapper<ProcessDefNodeVariableDO>().in(ProcessDefNodeVariableDO::getNodeId, nodeIdList));

      Map<Long, List<ProcessDefNodePropertyDO>> propertyMap = propertyDOList.stream()
          .collect(Collectors.groupingBy(ProcessDefNodePropertyDO::getNodeId));
      Map<Long, List<ProcessDefNodeVariableDO>> variableMap = variableDOList.stream()
          .collect(Collectors.groupingBy(ProcessDefNodeVariableDO::getNodeId));

      nodeDOList.forEach(node -> {
        DefNodeVO nodeVO = BeanUtil.copyProperties(node, DefNodeVO.class);
        nodeVO.setVariableVOList(ProcessDefAggAdapter.getDefNodeVariableVOList(variableMap.getOrDefault(node.getId(), Lists.newArrayList())));
        nodeVO.setPropertyVOList(ProcessDefAggAdapter.getDefNodePropertyVOList(propertyMap.getOrDefault(node.getId(), Lists.newArrayList())));
        result.add(nodeVO);
      });
    }
    return result;
  }

}
