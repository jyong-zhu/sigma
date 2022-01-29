package com.zone.process.infrastructure.db.adapter;

import com.zone.process.domain.agg.ProcessDefAgg;
import com.zone.process.domain.valueobject.DefNodePropertyVO;
import com.zone.process.domain.valueobject.DefNodeVO;
import com.zone.process.domain.valueobject.DefNodeVariableVO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodePropertyDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeVariableDO;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:34 下午
 * @Description:
 */
public class ProcessDefAggAdapter {

  public static ProcessDefNodeDO getProcessDefNodeDO(DefNodeVO nodeVO, Long defId, Long id) {
    ProcessDefNodeDO processDefNodeDO = new ProcessDefNodeDO();
    processDefNodeDO.setId(id);
    processDefNodeDO.setDefId(defId);
    processDefNodeDO.setName(nodeVO.getName());
    processDefNodeDO.setBpmnNodeType(nodeVO.getBpmnNodeType());
    processDefNodeDO.setBpmnNodeId(nodeVO.getBpmnNodeId());
    processDefNodeDO.setParentBpmnNodeId(nodeVO.getParentBpmnNodeId());
    processDefNodeDO.setNodePeopleType(nodeVO.getNodePeopleType());
    processDefNodeDO.setNodePeopleValue(nodeVO.getNodePeopleValue());
    processDefNodeDO.setInputFormIds(nodeVO.getInputFormIds());
    processDefNodeDO.setDisplayFormIds(nodeVO.getDisplayFormIds());
    return processDefNodeDO;
  }

  public static ProcessDefNodePropertyDO getProcessDefNodePropertyDO(DefNodePropertyVO propertyVO, Long nodeId, String bpmnNodeId) {
    ProcessDefNodePropertyDO processDefNodePropertyDO = new ProcessDefNodePropertyDO();
    processDefNodePropertyDO.setNodeId(nodeId);
    processDefNodePropertyDO.setBpmnNodeId(bpmnNodeId);
    processDefNodePropertyDO.setPropertyName(propertyVO.getPropertyName());
    processDefNodePropertyDO.setPropertyValue(propertyVO.getPropertyValue());
    processDefNodePropertyDO.setDescription(propertyVO.getDesc());
    return processDefNodePropertyDO;
  }

  public static ProcessDefNodeVariableDO getProcessDefNodeVariableDO(DefNodeVariableVO variableVO, Long nodeId, String bpmnNodeId) {
    ProcessDefNodeVariableDO processDefNodeVariableDO = new ProcessDefNodeVariableDO();
    processDefNodeVariableDO.setNodeId(nodeId);
    processDefNodeVariableDO.setBpmnNodeId(bpmnNodeId);
    processDefNodeVariableDO.setVariableName(variableVO.getVariableName());
    processDefNodeVariableDO.setJavaType(variableVO.getJavaType());
    processDefNodeVariableDO.setFormId(variableVO.getFormId());
    processDefNodeVariableDO.setFieldId(variableVO.getFieldId());
    processDefNodeVariableDO.setDefaultValue(variableVO.getDefaultValue());
    return processDefNodeVariableDO;
  }

  public static ProcessDefDO getProcessDefDO(ProcessDefAgg processDefAgg) {
    if (processDefAgg == null) {
      return null;
    }
    ProcessDefDO processDefDO = new ProcessDefDO();
    processDefDO.setId(processDefAgg.getId());
    processDefDO.setCategoryId(processDefAgg.getCategoryId());
    processDefDO.setProcDefId(processDefAgg.getProcDefId());
    processDefDO.setProcDefKey(processDefAgg.getProcDefKey());
    processDefDO.setProcDefVersion(processDefAgg.getProcDefVersion());
    processDefDO.setName(processDefAgg.getName());
    processDefDO.setIsLatest(true);
    processDefDO.setStatus(processDefAgg.getStatus());
    processDefDO.setBpmnXml(processDefAgg.getBpmnXml());
    processDefDO.setStartBpmnNodeId(processDefAgg.getStartBpmnNodeId());
    processDefDO.setFormIds(processDefAgg.getFormIds());
    processDefDO.setIconUrl(processDefAgg.getIconUrl());
    return processDefDO;
  }
}
