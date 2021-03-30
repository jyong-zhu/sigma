package com.zone.process.application.service.command.cmd;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 11:49 上午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InstStopCommand {

    @ApiModelProperty("流程实例id")
    @NotNull(message = "流程实例id不能为空")
    private Long id;

    @ApiModelProperty("评论")
    private String comment;
}
