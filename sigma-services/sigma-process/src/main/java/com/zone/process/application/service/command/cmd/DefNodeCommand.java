package com.zone.process.application.service.command.cmd;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 5:54 下午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DefNodeCommand {

    @ApiModelProperty("节点id")
    @NotBlank(message = "节点id不能为空")
    private String bpmnNodeId;

    @ApiModelProperty("当前节点处理人的类型，ID/PARAM/PAR_MULTI_INSTANCE/SEQ_MULTI_INSTANCE")
    private String nodePeopleType;

    @ApiModelProperty("当前节点处理人的值，具体值或变量名")
    private String nodePeopleValue;

    @ApiModelProperty("挂靠在当前节点用于输入的表单， 用,隔开")
    private String inputFormIds;

    @ApiModelProperty("挂靠在当前节点用于展示的表单， 用,隔开")
    private String displayFormIds;

    @ApiModelProperty("节点的扩展属性")
    @Valid
    private List<DefNodePropertyCommand> propertyCommandList;

    @ApiModelProperty("节点的变量信息")
    @Valid
    private List<DefNodeVariableCommand> variableCommandList;
}
