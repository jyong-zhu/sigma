package com.zone.process.application.service.query.assembler;

import cn.hutool.core.bean.BeanUtil;
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
    public static TaskDetailDTO getTaskDetailDTO(ProcessInstDO instDO, String taskId) {
        if (instDO != null) {
            TaskDetailDTO detailDTO = BeanUtil.copyProperties(instDO, TaskDetailDTO.class, "dueTime", "submitTime");
            detailDTO.setInstanceId(instDO.getId());
            detailDTO.setDueTime(instDO.getDueTime() == null ? null : LocalDateTimeUtil.toEpochMilli(instDO.getDueTime()));
            detailDTO.setSubmitTime(LocalDateTimeUtil.toEpochMilli(instDO.getSubmitTime()));
            detailDTO.setTaskId(taskId);
            List<Long> handlerIdList = Arrays.asList(instDO.getCurHandlerId().split(","))
                    .stream().filter(tmp -> StrUtil.isNotBlank(tmp))
                    .map(tmp -> Long.valueOf(tmp)).collect(Collectors.toList());
            detailDTO.setCurHandlerIdList(handlerIdList);
            return detailDTO;
        }
        return null;
    }

    /**
     * 获取任务详情，包含表单数据
     */
    public static TaskDetailDTO getTaskDetailDTO(ProcessInstDO instDO, String taskId, List<FormStructureDO> formList,
                                                 List<ProcessInstDataDO> instDataDOList, String inputFormIds) {
        TaskDetailDTO detailDTO = getTaskDetailDTO(instDO, taskId);
        if (detailDTO != null) {
            detailDTO.setDataList(InstDataDTOAssembler.getInstDataDTOList(instDataDOList, formList));
            List<Long> formIdList = StrUtil.isBlank(inputFormIds) ? Lists.newArrayList() :
                    Arrays.asList(inputFormIds.split(",")).stream()
                            .filter(tmp -> StrUtil.isNotBlank(tmp))
                            .map(tmp -> Long.valueOf(tmp)).collect(Collectors.toList());
            detailDTO.setInputFormIdList(formIdList);
        }
        return detailDTO;
    }
}
