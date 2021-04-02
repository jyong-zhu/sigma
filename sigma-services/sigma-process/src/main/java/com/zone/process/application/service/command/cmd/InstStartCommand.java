package com.zone.process.application.service.command.cmd;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 11:46 上午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InstStartCommand {

    @ApiModelProperty("流程定义id")
    @NotNull(message = "流程定义id不能为空")
    private Long defId;

    @ApiModelProperty("流程实例名称")
    @NotBlank(message = "流程实例名称不能为空")
    private String name;

    @ApiModelProperty("要求时间")
    private Long dueTime;

    @ApiModelProperty("评论")
    private String comment;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("表单数据")
    @NotNull(message = "表单数据不能为空")
    private Map<Long, Map<String, String>> formDataMap;
}
