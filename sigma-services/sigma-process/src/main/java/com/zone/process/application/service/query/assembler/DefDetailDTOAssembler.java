package com.zone.process.application.service.query.assembler;

import cn.hutool.core.bean.BeanUtil;
import com.zone.process.application.service.query.dto.DefDetailDTO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodePropertyDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeVariableDO;

import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/6 5:14 下午
 * @Description:
 */
public class DefDetailDTOAssembler {

    public static DefDetailDTO getDefDetailDTO(ProcessDefDO def, List<ProcessDefNodeDO> nodeList,
                                               List<ProcessDefNodeVariableDO> variableList,
                                               List<ProcessDefNodePropertyDO> propertyList) {
        if (def == null) {
            return null;
        }
        DefDetailDTO defDTO = BeanUtil.copyProperties(def, DefDetailDTO.class);

        defDTO.setNodeList(DefNodeDetailDTOAssembler.getDefNodeDetailDTOList(nodeList, variableList, propertyList));

        return defDTO;
    }
}
