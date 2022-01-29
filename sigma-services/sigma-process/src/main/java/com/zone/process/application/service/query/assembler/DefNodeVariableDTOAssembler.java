package com.zone.process.application.service.query.assembler;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.zone.process.application.service.query.dto.DefNodeVariableDTO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeVariableDO;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/6 5:30 下午
 * @Description:
 */
public class DefNodeVariableDTOAssembler {

  public static DefNodeVariableDTO getDefNodeVariableDTO(ProcessDefNodeVariableDO variableDO) {
    if (variableDO == null) {
      return null;
    }
    DefNodeVariableDTO defNodeVariableDTO = new DefNodeVariableDTO();
    defNodeVariableDTO.setId(variableDO.getId());
    defNodeVariableDTO.setNodeId(variableDO.getNodeId());
    defNodeVariableDTO.setBpmnNodeId(variableDO.getBpmnNodeId());
    defNodeVariableDTO.setVariableName(variableDO.getVariableName());
    defNodeVariableDTO.setJavaType(variableDO.getJavaType());
    defNodeVariableDTO.setFormId(variableDO.getFormId());
    defNodeVariableDTO.setFieldId(variableDO.getFieldId());
    defNodeVariableDTO.setDefaultValue(variableDO.getDefaultValue());
    return defNodeVariableDTO;
  }

  public static List<DefNodeVariableDTO> getDefNodeVariableDTOList(List<ProcessDefNodeVariableDO> variableDOList) {
    if (CollectionUtil.isEmpty(variableDOList)) {
      return Lists.newArrayList();
    }
    return variableDOList.stream()
        .filter(variableDO -> variableDO != null)
        .map(variableDO -> getDefNodeVariableDTO(variableDO))
        .collect(Collectors.toList());
  }
}
