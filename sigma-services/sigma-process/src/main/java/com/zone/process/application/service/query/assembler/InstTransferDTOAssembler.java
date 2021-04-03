package com.zone.process.application.service.query.assembler;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.zone.process.application.service.query.dto.InstTransferDTO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstOperationDO;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/3 11:32 下午
 * @Description:
 */
public class InstTransferDTOAssembler {

    public static InstTransferDTO getInstTransferDTO(ProcessInstOperationDO operationDO) {
        if (operationDO != null) {
            InstTransferDTO transferDTO = BeanUtil.copyProperties(operationDO, InstTransferDTO.class, "createTime");
            transferDTO.setCreateTime(LocalDateTimeUtil.toEpochMilli(operationDO.getCreateTime()));
        }
        return null;
    }
}
