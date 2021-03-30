package com.zone.process.shared.process.valueobject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/31 6:50 上午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TaskVO {

    @ApiModelProperty("任务所属的流程实例id")
    private String procInstId;

    @ApiModelProperty("任务id")
    private String taskId;

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("任务当前所处的节点名称")
    private String curNodeName;

    @ApiModelProperty("任务当前所处的节点id")
    private String curNodeId;

}
