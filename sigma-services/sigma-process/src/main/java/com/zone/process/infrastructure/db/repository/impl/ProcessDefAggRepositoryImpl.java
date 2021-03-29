package com.zone.process.infrastructure.db.repository.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.zone.commons.util.IdWorkerUtil;
import com.zone.process.domain.agg.ProcessDefAgg;
import com.zone.process.domain.repository.ProcessDefAggRepository;
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

        ProcessDefDO defDO = BeanUtil.copyProperties(processDefAgg, ProcessDefDO.class);
        defMapper.insert(defDO);

        if (CollectionUtil.isNotEmpty(processDefAgg.getNodeVOList())) {
            List<ProcessDefNodeDO> nodeDOList = Lists.newArrayList();
            List<ProcessDefNodePropertyDO> propertyList = Lists.newArrayList();
            List<ProcessDefNodeVariableDO> variableList = Lists.newArrayList();

            processDefAgg.getNodeVOList().forEach(nodeVO -> {
                Long nodeId = IdWorkerUtil.nextId();
                ProcessDefNodeDO nodeDO = ProcessDefAggAdapter.getProcessDefNodeDO(nodeVO, defDO.getId(), nodeId);
                nodeDOList.add(nodeDO);

                if (CollectionUtil.isNotEmpty(nodeVO.getPropertyVOList())) {
                    nodeVO.getPropertyVOList().forEach(propertyVO -> {
                        Long id = IdWorkerUtil.nextId();
                        ProcessDefNodePropertyDO propertyDO = ProcessDefAggAdapter.getProcessDefNodePropertyDO(propertyVO, nodeId, nodeVO.getBpmnNodeId(), id);
                        propertyList.add(propertyDO);
                    });
                }

                if (CollectionUtil.isNotEmpty(nodeVO.getVariableVOList())) {
                    nodeVO.getVariableVOList().forEach(variableVO -> {
                        Long id = IdWorkerUtil.nextId();
                        ProcessDefNodeVariableDO variableDO = ProcessDefAggAdapter.getProcessDefNodeVariableDO(variableVO, nodeId, nodeVO.getBpmnNodeId(), id);
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
}
