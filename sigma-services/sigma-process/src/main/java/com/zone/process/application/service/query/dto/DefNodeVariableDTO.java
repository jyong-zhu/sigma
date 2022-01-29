package com.zone.process.application.service.query.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/6 2:37 下午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DefNodeVariableDTO {

    @ApiModelProperty("变量id")
    private Long id;

    @ApiModelProperty("节点id")
    private Long nodeId;

    @ApiModelProperty("bpmn节点id")
    private String bpmnNodeId;

    @ApiModelProperty("变量名称")
    private String variableName;

    @ApiModelProperty("变量对应的java类型")
    private String javaType;

    @ApiModelProperty("表单id")
    private Long formId;

    @ApiModelProperty("表单下的字段")
    private String fieldId;

    @ApiModelProperty("默认值")
    private String defaultValue;
}
