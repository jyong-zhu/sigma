package com.zone.process.application.service.command.cmd;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 11:12 上午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DefDeployCommand {


    @ApiModelProperty("分类id")
    @NotNull(message = "分类id不能为空")
    private Long categoryId;

    @ApiModelProperty("bpmn2.0 xml")
    @NotBlank(message = "bpmn2.0 xml不能为空")
    private String bpmnXml;

    @ApiModelProperty("流程定义名称")
    @NotBlank(message = "流程定义名称不能为空")
    private String name;

    @ApiModelProperty("当前流程定义下所关联的全部表单id, 用,隔开")
    private String formIds;

    @ApiModelProperty("分类展示图标")
    private String iconUrl;

    @ApiModelProperty("流程定义中节点上的扩展信息")
    @Valid
    private List<DefNodeCommand> nodeList;

}
