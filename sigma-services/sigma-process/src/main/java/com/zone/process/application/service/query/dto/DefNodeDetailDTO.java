package com.zone.process.application.service.query.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 11:35 上午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DefNodeDetailDTO {

    @ApiModelProperty("流程定义id")
    private Long defId;

    @ApiModelProperty("节点id")
    private Long nodeId;

    @ApiModelProperty("节点名称")
    private String nodeName;

    @ApiModelProperty("节点类型")
    private String bpmnNodeType;

    @ApiModelProperty("节点的bpmn中的id")
    private String bpmnNodeId;

    @ApiModelProperty("用户任务的人员类型")
    private String nodePeopleType;

    @ApiModelProperty("用户任务的人员值")
    private String nodePeopleValue;

    @ApiModelProperty("节点属性列表")
    private List<DefNodePropertyDTO> propertyList;

    @ApiModelProperty("节点变量列表")
    private List<DefNodeVariableDTO> variableList;
}
