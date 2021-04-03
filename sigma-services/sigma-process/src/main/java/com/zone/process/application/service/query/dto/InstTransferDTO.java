package com.zone.process.application.service.query.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/3 11:22 下午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InstTransferDTO {

    @ApiModelProperty(value = "操作类型（start/complete/update/stop）")
    private String operationType;

    @ApiModelProperty(value = "操作所处的节点id")
    private String bpmnNodeId;

    @ApiModelProperty(value = "操作的任务id")
    private String taskId;

    @ApiModelProperty(value = "操作备注")
    private String comment;

    @ApiModelProperty(value = "操作人的user_id")
    private String operateBy;

    @ApiModelProperty(value = "操作人的姓名")
    private String operateName;

    @ApiModelProperty(value = "扩展数据")
    private String ext;

    @ApiModelProperty(value = "操作时间")
    private Long createTime;
}
