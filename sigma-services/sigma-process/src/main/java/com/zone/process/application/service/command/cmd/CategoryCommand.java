package com.zone.process.application.service.command.cmd;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/7 10:15 上午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CategoryCommand {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "分类名称")
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @ApiModelProperty(value = "分类展示图标")
    private String iconUrl;


}
