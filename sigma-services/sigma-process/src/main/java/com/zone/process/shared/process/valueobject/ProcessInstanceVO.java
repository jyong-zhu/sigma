package com.zone.process.shared.process.valueobject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/31 6:03 上午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProcessInstanceVO {

    @ApiModelProperty("流程实例id")
    private String procInstId;

    @ApiModelProperty("流程实例当前所处的节点id")
    private String curNodeId;

    @ApiModelProperty("流程实例当前处理人的id")
    private String curHandlerId;

    @ApiModelProperty("流程实例是否已经结束")
    private Boolean isFinished;
}
