package com.zone.process.application.service.query.assembler;

import cn.hutool.core.bean.BeanUtil;
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

    public static List<DefNodePropertyDTO> getDefNodePropertyList(List<ProcessDefNodePropertyDO> propertyDOList) {
        if (CollectionUtil.isEmpty(propertyDOList)) {
            return Lists.newArrayList();
        }
        return propertyDOList.stream()
                .map(propertyDO -> BeanUtil.copyProperties(propertyDO, DefNodePropertyDTO.class))
                .collect(Collectors.toList());
    }
}
