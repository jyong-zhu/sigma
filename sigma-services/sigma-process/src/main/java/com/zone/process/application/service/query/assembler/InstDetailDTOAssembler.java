package com.zone.process.application.service.query.assembler;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.zone.process.application.service.query.dto.InstDataDTO;
import com.zone.process.application.service.query.dto.InstDetailDTO;
import com.zone.process.infrastructure.db.dataobject.FormStructureDO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstDO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstDataDO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/3 2:00 下午
 * @Description:
 */
public class InstDetailDTOAssembler {

    /**
     * 不含流程实例上的表单数据，即不对 List<InstDataDTO> dataList 赋值
     */
    public static InstDetailDTO getInstDetailDTO(ProcessInstDO instDO) {
        if (instDO != null) {
            InstDetailDTO detailDTO = BeanUtil.copyProperties(instDO, InstDetailDTO.class, "dueTime", "submitTime");
            detailDTO.setInstanceId(instDO.getId());
            detailDTO.setDueTime(instDO.getDueTime() == null ? null : LocalDateTimeUtil.toEpochMilli(instDO.getDueTime()));
            detailDTO.setSubmitTime(LocalDateTimeUtil.toEpochMilli(instDO.getSubmitTime()));
            List<Long> handlerIdList = Arrays.asList(instDO.getCurHandlerId().split(","))
                    .stream().filter(tmp -> StrUtil.isNotBlank(tmp))
                    .map(tmp -> Long.valueOf(tmp)).collect(Collectors.toList());
            detailDTO.setCurHandlerIdList(handlerIdList);
            return detailDTO;
        }
        return null;
    }

    /**
     * 包含流程实例上的表单数据
     */
    public static InstDetailDTO getInstDetailDTO(ProcessInstDO instDO, List<ProcessInstDataDO> instDataDOList, List<FormStructureDO> formList) {
        InstDetailDTO detailDTO = getInstDetailDTO(instDO);
        if (instDO != null) {
            List<InstDataDTO> dataList = InstDataDTOAssembler.getInstDataDTOList(instDataDOList, formList);
            detailDTO.setDataList(dataList);
        }
        return detailDTO;
    }
}
