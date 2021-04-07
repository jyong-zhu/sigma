package com.zone.process.application.service.command.cmd;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/7 10:57 上午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FormCommand {

    @ApiModelProperty(value = "表单名称")
    @NotBlank(message = "表单名称不能为空")
    private String name;

    @ApiModelProperty(value = "表单的key")
    @NotBlank(message = "表单key不能为空")
    private String formKey;

    @ApiModelProperty(value = "表单的json串")
    @NotBlank(message = "表单结构不能为空")
    private String formJson;

    @ApiModelProperty(value = "描述")
    private String description;
}
