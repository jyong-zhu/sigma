package com.zone.process.application.service.command.cmd;

import com.zone.commons.annotation.ValueValid;
import com.zone.process.shared.enums.VariableTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 5:55 下午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DefNodeVariableCommand {

    @ApiModelProperty(value = "流程节点的变量名称")
    @NotBlank(message = "变量名称不能为空")
    private String variableName;

    @ApiModelProperty(value = "当前节点上挂靠的表单id")
    @NotBlank(message = "表单id不能为空")
    private Long formId;

    @ApiModelProperty(value = "该表单中的字段id")
    @NotBlank(message = "字段id不能为空")
    private String fieldId;

    @ApiModelProperty("变量值在Java中的类型")
    @NotBlank(message = "变量值的类型不能为空")
    @ValueValid(checkClass = VariableTypeEnum.class, message = "变量值的类型出错")
    private String javaType;

    @ApiModelProperty(value = "默认的参数值")
    private String defaultValue;
}
