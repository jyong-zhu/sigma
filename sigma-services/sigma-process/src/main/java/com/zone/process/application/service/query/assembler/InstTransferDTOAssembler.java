package com.zone.process.application.service.query.assembler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.zone.process.application.service.query.dto.InstTransferDTO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstOperationDO;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/3 11:32 下午
 * @Description:
 */
public class InstTransferDTOAssembler {

  public static InstTransferDTO getInstTransferDTO(ProcessInstOperationDO operationDO) {
    if (operationDO == null) {
      return null;
    }
    InstTransferDTO instTransferDTO = new InstTransferDTO();
    instTransferDTO.setOperationType(operationDO.getOperationType());
    instTransferDTO.setBpmnNodeId(operationDO.getBpmnNodeId());
    instTransferDTO.setTaskId(operationDO.getTaskId());
    instTransferDTO.setComment(operationDO.getComment());
    instTransferDTO.setOperateBy(String.valueOf(operationDO.getOperateBy()));
    instTransferDTO.setOperateName(operationDO.getOperateName());
    instTransferDTO.setExt(operationDO.getExt());
    instTransferDTO.setCreateTime(LocalDateTimeUtil.toEpochMilli(operationDO.getCreateTime()));
    return instTransferDTO;
  }

  public static List<InstTransferDTO> getInstTransferDTOList(List<ProcessInstOperationDO> operationDOList) {
    if (CollectionUtil.isEmpty(operationDOList)) {
      return null;
    }
    return operationDOList.stream().filter(tmp -> tmp != null)
        .map(InstTransferDTOAssembler::getInstTransferDTO)
        .collect(Collectors.toList());
  }
}
