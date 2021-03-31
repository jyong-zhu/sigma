package com.zone.process.domain.valueobject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 5:55 下午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DefNodeVariableVO {

    @ApiModelProperty(value = "流程节点的变量名称")
    private String variableName;

    @ApiModelProperty(value = "当前节点上挂靠的表单id")
    private Long formId;

    @ApiModelProperty(value = "该表单中的字段id")
    private String fieldId;

    @ApiModelProperty("变量值在Java中的类型")
    private String javaType;

    @ApiModelProperty(value = "默认的参数值")
    private String defaultValue;

    @ApiModelProperty(value = "扩展字段")
    private String ext;
}
