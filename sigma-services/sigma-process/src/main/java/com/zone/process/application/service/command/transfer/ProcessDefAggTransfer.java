package com.zone.process.application.service.command.transfer;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.zone.process.application.service.command.cmd.DefDeployCommand;
import com.zone.process.application.service.command.cmd.DefNodeCommand;
import com.zone.process.application.service.command.cmd.DefNodePropertyCommand;
import com.zone.process.application.service.command.cmd.DefNodeVariableCommand;
import com.zone.process.domain.agg.ProcessDefAgg;
import com.zone.process.domain.valueobject.DefNodePropertyVO;
import com.zone.process.domain.valueobject.DefNodeVO;
import com.zone.process.domain.valueobject.DefNodeVariableVO;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 4:11 下午
 * @Description: domain 中不应该出现 command transfer 用于将 command 转化为 domain
 */
public class ProcessDefAggTransfer {

  public static ProcessDefAgg getProcessDefAgg(DefDeployCommand deployCommand) {
    ProcessDefAgg processDefAgg = new ProcessDefAgg();
    processDefAgg.setCategoryId(deployCommand.getCategoryId());
    processDefAgg.setName(deployCommand.getName());
    processDefAgg.setBpmnXml(deployCommand.getBpmnXml());
    processDefAgg.setFormIds(deployCommand.getFormIds());
    processDefAgg.setIconUrl(deployCommand.getIconUrl());
    processDefAgg.setNodeVOList(getNodeVOList(deployCommand.getNodeList()));
    return processDefAgg;
  }

  public static List<DefNodeVO> getNodeVOList(List<DefNodeCommand> nodeList) {
    if (CollectionUtil.isEmpty(nodeList)) {
      return Lists.newArrayList();
    }
    return nodeList.stream()
        .filter(cmd -> cmd != null)
        .map(cmd -> getNodeVO(cmd))
        .collect(Collectors.toList());
  }

  public static DefNodeVO getNodeVO(DefNodeCommand cmd) {
    DefNodeVO defNodeVO = new DefNodeVO();
    defNodeVO.setBpmnNodeId(cmd.getBpmnNodeId());
    defNodeVO.setNodePeopleType(cmd.getNodePeopleType());
    defNodeVO.setNodePeopleValue(cmd.getNodePeopleValue());
    defNodeVO.setInputFormIds(cmd.getInputFormIds());
    defNodeVO.setDisplayFormIds(cmd.getDisplayFormIds());
    defNodeVO.setPropertyVOList(getNodePropertyVOList(cmd.getPropertyCommandList()));
    defNodeVO.setVariableVOList(getNodeVariableVOList(cmd.getVariableCommandList()));
    return defNodeVO;
  }

  public static List<DefNodePropertyVO> getNodePropertyVOList(List<DefNodePropertyCommand> propertyList) {
    if (CollectionUtil.isEmpty(propertyList)) {
      return Lists.newArrayList();
    }
    return propertyList.stream()
        .filter(cmd -> cmd != null)
        .map(cmd -> getNodePropertyVO(cmd))
        .collect(Collectors.toList());
  }

  public static List<DefNodeVariableVO> getNodeVariableVOList(List<DefNodeVariableCommand> variableList) {
    if (CollectionUtil.isEmpty(variableList)) {
      return Lists.newArrayList();
    }
    return variableList.stream()
        .filter(cmd -> cmd != null)
        .map(cmd -> getNodeVariableVO(cmd))
        .collect(Collectors.toList());
  }

  public static DefNodePropertyVO getNodePropertyVO(DefNodePropertyCommand cmd) {
    DefNodePropertyVO defNodePropertyVO = new DefNodePropertyVO();
    defNodePropertyVO.setPropertyName(cmd.getPropertyName());
    defNodePropertyVO.setPropertyValue(cmd.getPropertyValue());
    defNodePropertyVO.setDescription(cmd.getDesc());
    return defNodePropertyVO;
  }

  public static DefNodeVariableVO getNodeVariableVO(DefNodeVariableCommand cmd) {
    DefNodeVariableVO defNodeVariableVO = new DefNodeVariableVO();
    defNodeVariableVO.setVariableName(cmd.getVariableName());
    defNodeVariableVO.setFormId(cmd.getFormId());
    defNodeVariableVO.setFieldId(cmd.getFieldId());
    defNodeVariableVO.setJavaType(cmd.getJavaType());
    defNodeVariableVO.setDefaultValue(cmd.getDefaultValue());
    return defNodeVariableVO;
  }


}
