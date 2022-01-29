package com.zone.process.application.service.query.assembler;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.zone.process.application.service.query.dto.TaskDetailDTO;
import com.zone.process.infrastructure.db.dataobject.FormStructureDO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstDO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstDataDO;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/3 4:57 下午
 * @Description:
 */
public class TaskDetailDTOAssembler {


  /**
   * 不含表单数据
   */
  public static TaskDetailDTO getTaskDetailDTO(ProcessInstDO instDO, String taskId, String taskName) {
    if (instDO == null) {
      return null;
    }
    TaskDetailDTO taskDetailDTO = new TaskDetailDTO();
    taskDetailDTO.setTaskId(taskId);
    taskDetailDTO.setTaskName(taskName);
    taskDetailDTO.setInstanceId(instDO.getId());
    taskDetailDTO.setDefId(instDO.getDefId());
    taskDetailDTO.setDefName(instDO.getDefName());
    taskDetailDTO.setName(instDO.getName());
    taskDetailDTO.setStatus(instDO.getStatus());
    taskDetailDTO.setCurNodeId(instDO.getCurNodeId());
    taskDetailDTO.setCurNodeName(instDO.getCurNodeName());
    taskDetailDTO.setDueTime(instDO.getDueTime() == null ? null : LocalDateTimeUtil.toEpochMilli(instDO.getDueTime()));
    taskDetailDTO.setSubmitTime(instDO.getSubmitTime() == null ? null : LocalDateTimeUtil.toEpochMilli(instDO.getSubmitTime()));
    taskDetailDTO.setSubmitBy(instDO.getSubmitBy());
    taskDetailDTO.setSubmitName(instDO.getSubmitName());
    taskDetailDTO.setComment(instDO.getComment());
    taskDetailDTO.setDescription(instDO.getDescription());

    // 设置当前处理人id
    List<Long> handlerIdList = Arrays.asList(instDO.getCurHandlerId().split(","))
        .stream().filter(tmp -> StrUtil.isNotBlank(tmp))
        .map(tmp -> Long.valueOf(tmp)).collect(Collectors.toList());
    taskDetailDTO.setCurHandlerIdList(handlerIdList);

    return taskDetailDTO;
  }

  /**
   * 获取任务详情，包含表单数据
   */
  public static TaskDetailDTO getTaskDetailDTO(ProcessInstDO instDO, String taskId, String taskName, List<FormStructureDO> formList,
      List<ProcessInstDataDO> instDataDOList, String inputFormIds) {

    TaskDetailDTO detailDTO = getTaskDetailDTO(instDO, taskId, taskName);

    if (detailDTO != null) {
      // 设置表单数据
      detailDTO.setDataList(InstDataDTOAssembler.getInstDataDTOList(instDataDOList, formList));

      // 设置可输入表单的id
      List<Long> formIdList = StrUtil.isBlank(inputFormIds) ? Lists.newArrayList() :
          Arrays.asList(inputFormIds.split(",")).stream()
              .filter(tmp -> StrUtil.isNotBlank(tmp))
              .map(tmp -> Long.valueOf(tmp)).collect(Collectors.toList());
      detailDTO.setInputFormIdList(formIdList);
    }
    return detailDTO;
  }
}
