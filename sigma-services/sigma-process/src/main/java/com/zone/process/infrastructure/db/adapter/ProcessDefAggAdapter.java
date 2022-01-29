package com.zone.process.infrastructure.db.adapter;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.zone.process.domain.agg.ProcessDefAgg;
import com.zone.process.domain.valueobject.DefNodePropertyVO;
import com.zone.process.domain.valueobject.DefNodeVO;
import com.zone.process.domain.valueobject.DefNodeVariableVO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodePropertyDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeVariableDO;
import java.util.List;
import java.util.stream.Collectors;

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
    processDefNodePropertyDO.setDescription(propertyVO.getDescription());
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

  public static ProcessDefAgg getProcessDefAgg(ProcessDefDO processDefDO, List<DefNodeVO> nodeVOList) {
    if (processDefDO == null) {
      return null;
    }
    ProcessDefAgg processDefAgg = new ProcessDefAgg();
    processDefAgg.setId(processDefDO.getId());
    processDefAgg.setCategoryId(processDefDO.getCategoryId());
    processDefAgg.setProcDefId(processDefDO.getProcDefId());
    processDefAgg.setProcDefKey(processDefDO.getProcDefKey());
    processDefAgg.setProcDefVersion(processDefDO.getProcDefVersion());
    processDefAgg.setName(processDefDO.getName());
    processDefAgg.setStatus(processDefDO.getStatus());
    processDefAgg.setBpmnXml(processDefDO.getBpmnXml());
    processDefAgg.setStartBpmnNodeId(processDefDO.getStartBpmnNodeId());
    processDefAgg.setFormIds(processDefDO.getFormIds());
    processDefAgg.setIconUrl(processDefDO.getIconUrl());
    processDefAgg.setVersion(processDefDO.getVersion());
    processDefAgg.setNodeVOList(nodeVOList);
    processDefAgg.setCreateTime(processDefDO.getCreateTime());
    processDefAgg.setCreateBy(processDefDO.getCreateBy());
    processDefAgg.setCreateName(processDefDO.getCreateName());
    return processDefAgg;
  }

  public static List<DefNodeVariableVO> getDefNodeVariableVOList(List<ProcessDefNodeVariableDO> variableDOList) {
    if (CollectionUtil.isEmpty(variableDOList)) {
      return Lists.newArrayList();
    }
    return variableDOList.stream().filter(variableDO -> variableDO != null)
        .map(variableDO -> getDefNodeVariableVO(variableDO))
        .collect(Collectors.toList());
  }

  public static List<DefNodePropertyVO> getDefNodePropertyVOList(List<ProcessDefNodePropertyDO> propertyDOList) {
    if (CollectionUtil.isEmpty(propertyDOList)) {
      return Lists.newArrayList();
    }
    return propertyDOList.stream().filter(propertyDO -> propertyDO != null)
        .map(propertyDO -> getDefNodePropertyVO(propertyDO))
        .collect(Collectors.toList());
  }

  public static DefNodeVariableVO getDefNodeVariableVO(ProcessDefNodeVariableDO variableDO) {
    if (variableDO == null) {
      return null;
    }
    DefNodeVariableVO defNodeVariableVO = new DefNodeVariableVO();
    defNodeVariableVO.setVariableName(variableDO.getVariableName());
    defNodeVariableVO.setFormId(variableDO.getFormId());
    defNodeVariableVO.setFieldId(variableDO.getFieldId());
    defNodeVariableVO.setJavaType(variableDO.getJavaType());
    defNodeVariableVO.setDefaultValue(variableDO.getDefaultValue());
    defNodeVariableVO.setCreateTime(variableDO.getCreateTime());
    defNodeVariableVO.setCreateBy(variableDO.getCreateBy());
    defNodeVariableVO.setCreateName(variableDO.getCreateName());
    return defNodeVariableVO;

  }

  public static DefNodePropertyVO getDefNodePropertyVO(ProcessDefNodePropertyDO propertyDO) {
    if (propertyDO == null) {
      return null;
    }
    DefNodePropertyVO defNodePropertyVO = new DefNodePropertyVO();
    defNodePropertyVO.setPropertyName(propertyDO.getPropertyName());
    defNodePropertyVO.setPropertyValue(propertyDO.getPropertyValue());
    defNodePropertyVO.setDescription(propertyDO.getDescription());
    defNodePropertyVO.setCreateTime(propertyDO.getCreateTime());
    defNodePropertyVO.setCreateBy(propertyDO.getCreateBy());
    defNodePropertyVO.setCreateName(propertyDO.getCreateName());
    return defNodePropertyVO;

  }
}
