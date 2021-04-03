package com.zone.process.application.service.query.dto;

import com.zone.rpc.dto.auth.UserDetailDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 2:54 下午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TaskDetailDTO {

    @ApiModelProperty("taskId")
    private String taskId;

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("流程实例id")
    private Long instanceId;

    @ApiModelProperty("流程定义id")
    private Long defId;

    @ApiModelProperty("流程定义名称")
    private String defName;

    @ApiModelProperty("流程实例名称")
    private String name;

    @ApiModelProperty("流程实例状态")
    private String status;

    @ApiModelProperty("流程实例当前停留节点id")
    private String curNodeId;

    @ApiModelProperty("流程实例当前停留节点名称")
    private String curNodeName;

    @ApiModelProperty("当前处理人id")
    private List<Long> curHandlerIdList;

    @ApiModelProperty("当前处理人信息")
    private List<UserDetailDTO> curHandlerList;

    @ApiModelProperty("要求时间")
    private Long dueTime;

    @ApiModelProperty("提交时间")
    private Long submitTime;

    @ApiModelProperty("提交人id")
    private Long submitBy;

    @ApiModelProperty("提交人姓名")
    private String submitName;

    @ApiModelProperty("评论")
    private String comment;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("当前节点挂靠的所有表单信息，有数据的要展示数据")
    private List<InstDataDTO> dataList;

    @ApiModelProperty("用于输入的表单结构信息")
    private List<Long> inputFormIdList;
}
