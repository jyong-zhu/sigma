package com.zone.process.application.service.query.assembler;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.zone.process.application.service.query.dto.DefNodePropertyDTO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodePropertyDO;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/6 5:30 下午
 * @Description:
 */
public class DefNodePropertyDTOAssembler {

  public static DefNodePropertyDTO getDefNodePropertyDTO(ProcessDefNodePropertyDO propertyDO) {
    if (propertyDO == null) {
      return null;
    }
    DefNodePropertyDTO defNodePropertyDTO = new DefNodePropertyDTO();
    defNodePropertyDTO.setId(propertyDO.getId());
    defNodePropertyDTO.setNodeId(propertyDO.getNodeId());
    defNodePropertyDTO.setBpmnNodeId(propertyDO.getBpmnNodeId());
    defNodePropertyDTO.setPropertyName(propertyDO.getPropertyName());
    defNodePropertyDTO.setPropertyValue(propertyDO.getPropertyValue());
    defNodePropertyDTO.setDescription(propertyDO.getDescription());
    return defNodePropertyDTO;

  }

  public static List<DefNodePropertyDTO> getDefNodePropertyDTOList(List<ProcessDefNodePropertyDO> propertyDOList) {
    if (CollectionUtil.isEmpty(propertyDOList)) {
      return Lists.newArrayList();
    }
    return propertyDOList.stream()
        .filter(propertyDO -> propertyDO != null)
        .map(propertyDO -> getDefNodePropertyDTO(propertyDO))
        .collect(Collectors.toList());
  }
}
