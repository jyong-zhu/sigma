package com.zone.process.application.service.query.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/3 11:51 上午
 * @Description: 获取指定节点的流程实例上的数据
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InstNodeDataDTO {

    @ApiModelProperty("流程实例id")
    private Long instanceId;

    @ApiModelProperty("当前节点id")
    private String bpmnNodeId;

    @ApiModelProperty("用于展示的表单结构+数据")
    private List<InstDataDTO> dataList;

}
