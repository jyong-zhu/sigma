package com.zone.process.application.service.query.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/4 12:28 上午
 * @Description: 发起流程实例时获取的表单结构
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StartNodeDTO {

    @ApiModelProperty("流程定义id")
    private Long defId;

    @ApiModelProperty("流程定义名称")
    private String defName;

    @ApiModelProperty("开始节点用于输入的表单结构信息")
    private List<FormStructureDTO> inputFormList;
}
