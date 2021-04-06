package com.zone.process.application.service.query.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 11:17 上午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DefDetailDTO {

    @ApiModelProperty("流程定义id")
    private Long id;

    @ApiModelProperty("流程定义所属的分类id")
    private Long categoryId;

    @ApiModelProperty("xml文件")
    private String bpmnXml;

    @ApiModelProperty(value = "camunda中的流程定义id，关联到ACT_RE_PROCDEF")
    private String procDefId;

    @ApiModelProperty(value = "camunda中的流程定义key，关联到ACT_RE_PROCDEF的key")
    private String procDefKey;

    @ApiModelProperty(value = "camunda中的流程定义版本，关联到ACT_RE_PROCDEF的version")
    private Integer procDefVersion;

    @ApiModelProperty("流程定义名称")
    private String name;

    @ApiModelProperty("流程定义拥有的表单id")
    private String formIds;

    @ApiModelProperty("图标")
    private String iconUrl;

    @ApiModelProperty("节点列表")
    private List<DefNodeDetailDTO> nodeList;

}
