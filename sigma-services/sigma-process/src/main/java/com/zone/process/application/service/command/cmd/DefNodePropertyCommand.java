package com.zone.process.application.service.command.cmd;

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
public class DefNodePropertyCommand {

    @ApiModelProperty(value = "流程节点中的属性名称")
    @NotBlank(message = "属性名称不能为空")
    private String propertyName;

    @ApiModelProperty(value = "属性值")
    @NotBlank(message = "属性值不能为空")
    private String propertyValue;

    @ApiModelProperty(value = "属性作用描述")
    private String desc;
}
