package com.zone.process.application.service.query.assembler;

import cn.hutool.core.bean.BeanUtil;
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

    public static List<DefNodeDetailDTO> getDefNodeDetailDTOList(List<ProcessDefNodeDO> nodeList,
                                                                 List<ProcessDefNodeVariableDO> variableList,
                                                                 List<ProcessDefNodePropertyDO> propertyList) {
        if (CollectionUtil.isEmpty(nodeList)) {
            return Lists.newArrayList();
        }
        List<DefNodeDetailDTO> list = Lists.newArrayList();
        Map<Long, List<ProcessDefNodeVariableDO>> variableMap = variableList.stream()
                .collect(Collectors.groupingBy(ProcessDefNodeVariableDO::getNodeId));
        Map<Long, List<ProcessDefNodePropertyDO>> propertyMap = propertyList.stream()
                .collect(Collectors.groupingBy(ProcessDefNodePropertyDO::getNodeId));

        nodeList.forEach(node -> {
            DefNodeDetailDTO nodeDetail = BeanUtil.copyProperties(node, DefNodeDetailDTO.class);
            nodeDetail.setPropertyList(DefNodePropertyDTOAssembler.getDefNodePropertyList(propertyMap.getOrDefault(node.getId(), Lists.newArrayList())));
            nodeDetail.setVariableList(DefNodeVariableDTOAssembler.getDefNodeVariableList(variableMap.getOrDefault(node.getId(), Lists.newArrayList())));
            list.add(nodeDetail);
        });

        return list;
    }
}
