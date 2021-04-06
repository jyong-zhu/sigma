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
public class DefNodePropertyDTO {

    @ApiModelProperty("属性id")
    private Long id;

    @ApiModelProperty("节点id")
    private Long nodeId;

    @ApiModelProperty("bpmn节点id")
    private String bpmnNodeId;

    @ApiModelProperty("属性名称")
    private String propertyName;

    @ApiModelProperty("属性值")
    private String propertyValue;

    @ApiModelProperty("描述")
    private String description;

}
