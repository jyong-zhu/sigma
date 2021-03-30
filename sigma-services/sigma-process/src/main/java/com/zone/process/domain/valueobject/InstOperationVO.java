package com.zone.process.domain.valueobject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/31 5:46 上午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InstOperationVO {

    @ApiModelProperty(value = "操作所处的节点id")
    private String bpmnNodeId;

    @ApiModelProperty(value = "操作备注")
    private String comment;

    @ApiModelProperty(value = "操作人的user_id")
    private String operateBy;

    @ApiModelProperty(value = "操作人的姓名")
    private String operateName;

    @ApiModelProperty(value = "扩展数据")
    private String ext;
}
