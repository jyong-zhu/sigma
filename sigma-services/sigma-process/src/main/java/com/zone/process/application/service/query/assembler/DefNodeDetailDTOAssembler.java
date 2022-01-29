package com.zone.process.application.service.query.assembler;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.zone.process.application.service.query.dto.DefNodeDetailDTO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodePropertyDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeVariableDO;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/6 5:18 下午
 * @Description:
 */
public class DefNodeDetailDTOAssembler {

  public static DefNodeDetailDTO getDefNodeDetailDTO(ProcessDefNodeDO defNodeDO) {
    if (defNodeDO == null) {
      return null;
    }
    DefNodeDetailDTO defNodeDetailDTO = new DefNodeDetailDTO();
    defNodeDetailDTO.setId(defNodeDO.getId());
    defNodeDetailDTO.setDefId(defNodeDO.getDefId());
    defNodeDetailDTO.setName(defNodeDO.getName());
    defNodeDetailDTO.setBpmnNodeType(defNodeDO.getBpmnNodeType());
    defNodeDetailDTO.setBpmnNodeId(defNodeDO.getBpmnNodeId());
    defNodeDetailDTO.setNodePeopleType(defNodeDO.getNodePeopleType());
    defNodeDetailDTO.setNodePeopleValue(defNodeDO.getNodePeopleValue());
    defNodeDetailDTO.setInputFormIds(defNodeDO.getInputFormIds());
    defNodeDetailDTO.setDisplayFormIds(defNodeDO.getDisplayFormIds());
    return defNodeDetailDTO;

  }

  public static List<DefNodeDetailDTO> getDefNodeDetailDTOList(List<ProcessDefNodeDO> nodeList,
      List<ProcessDefNodeVariableDO> variableList, List<ProcessDefNodePropertyDO> propertyList) {

    if (CollectionUtil.isEmpty(nodeList)) {
      return Lists.newArrayList();
    }
    List<DefNodeDetailDTO> list = Lists.newArrayList();

    Map<Long, List<ProcessDefNodeVariableDO>> variableMap = variableList.stream()
        .collect(Collectors.groupingBy(ProcessDefNodeVariableDO::getNodeId));

    Map<Long, List<ProcessDefNodePropertyDO>> propertyMap = propertyList.stream()
        .collect(Collectors.groupingBy(ProcessDefNodePropertyDO::getNodeId));

    nodeList.stream().filter(node -> node != null).forEach(node -> {
      DefNodeDetailDTO nodeDetail = getDefNodeDetailDTO(node);
      nodeDetail.setPropertyList(DefNodePropertyDTOAssembler.getDefNodePropertyDTOList(propertyMap.getOrDefault(node.getId(), Lists.newArrayList())));
      nodeDetail.setVariableList(DefNodeVariableDTOAssembler.getDefNodeVariableDTOList(variableMap.getOrDefault(node.getId(), Lists.newArrayList())));
      list.add(nodeDetail);
    });

    return list;
  }

  public static List<DefNodeDetailDTO> getDefNodeDetailDTOList(List<ProcessDefNodeDO> nodeList) {

    if (CollectionUtil.isEmpty(nodeList)) {
      return Lists.newArrayList();
    }

    return nodeList.stream().filter(node -> node != null)
        .map(DefNodeDetailDTOAssembler::getDefNodeDetailDTO)
        .collect(Collectors.toList());
  }
}
