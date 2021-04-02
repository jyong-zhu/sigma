package com.zone.process.application.service.command.cmd;

import com.zone.commons.annotation.ValueValid;
import com.zone.process.shared.enums.TaskOperationTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 2:51 下午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TaskOperateCommand {

    @ApiModelProperty("任务id")
    @NotBlank(message = "任务id不能为空")
    private String taskId;

    @ApiModelProperty("操作类型")
    @NotBlank(message = "操作类型不能为空")
    @ValueValid(checkClass = TaskOperationTypeEnum.class, message = "操作类型出错")
    private String operationType;

    @ApiModelProperty("评论")
    private String comment;

    @ApiModelProperty("任务所属人，用于转派时指定人")
    private List<String> identityList;

    @ApiModelProperty("表单数据")
    @NotNull(message = "表单数据不能为空")
    private Map<Long, Map<String, String>> formDataMap;
}
