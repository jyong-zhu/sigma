package com.zone.process.shared.process.valueobject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

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
    private List<String> curNodeIdList;

    @ApiModelProperty("流程实例当前处理人的id")
    private List<String> curHandlerIdList;

    @ApiModelProperty("流程实例是否已经结束")
    private Boolean isFinished;
}
