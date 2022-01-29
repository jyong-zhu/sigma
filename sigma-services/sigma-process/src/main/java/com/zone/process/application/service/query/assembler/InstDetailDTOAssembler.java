package com.zone.process.application.service.query.assembler;

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
    if (instDO == null) {
      return null;
    }
    InstDetailDTO instDetailDTO = new InstDetailDTO();
    instDetailDTO.setInstanceId(instDO.getId());
    instDetailDTO.setDefId(instDO.getDefId());
    instDetailDTO.setDefName(instDO.getDefName());
    instDetailDTO.setName(instDO.getName());
    instDetailDTO.setStatus(instDO.getStatus());
    instDetailDTO.setCurNodeId(instDO.getCurNodeId());
    instDetailDTO.setCurNodeName(instDO.getCurNodeName());
    instDetailDTO.setDueTime(instDO.getDueTime() == null ? null : LocalDateTimeUtil.toEpochMilli(instDO.getDueTime()));
    instDetailDTO.setSubmitTime(instDO.getSubmitTime() == null ? null : LocalDateTimeUtil.toEpochMilli(instDO.getSubmitTime()));
    instDetailDTO.setSubmitBy(instDO.getSubmitBy());
    instDetailDTO.setSubmitName(instDO.getSubmitName());
    instDetailDTO.setComment(instDO.getComment());
    instDetailDTO.setDescription(instDO.getDescription());

    // 当前处理人的id
    List<Long> handlerIdList = Arrays.asList(instDO.getCurHandlerId().split(","))
        .stream().filter(StrUtil::isNotBlank)
        .map(Long::valueOf).collect(Collectors.toList());
    instDetailDTO.setCurHandlerIdList(handlerIdList);

    return instDetailDTO;
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
