package com.zone.process.application.service.query.assembler;

import cn.hutool.core.bean.BeanUtil;
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

    public static List<DefNodeVariableDTO> getDefNodeVariableList(List<ProcessDefNodeVariableDO> variableDOList) {
        if (CollectionUtil.isEmpty(variableDOList)) {
            return Lists.newArrayList();
        }
        return variableDOList.stream()
                .map(tmp -> BeanUtil.copyProperties(tmp, DefNodeVariableDTO.class))
                .collect(Collectors.toList());
    }
}
