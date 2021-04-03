package com.zone.process.application.service.query.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/3 10:48 上午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InstDataDTO {

    @ApiModelProperty("实例数据id")
    private Long id;

    @ApiModelProperty("所属流程实例id")
    private Long instanceId;

    @ApiModelProperty("节点id")
    private String bpmnNodeId;

    @ApiModelProperty("表单id")
    private Long formId;

    @ApiModelProperty("表单结构")
    private String formJson;

    @ApiModelProperty("表单数据")
    private String formData;

    @ApiModelProperty(value = "表单名称")
    private String formName;

    @ApiModelProperty(value = "表单的key")
    private String formKey;

    @ApiModelProperty(value = "表单的版本")
    private Integer formVersion;

    @ApiModelProperty(value = "描述")
    private String description;

}
