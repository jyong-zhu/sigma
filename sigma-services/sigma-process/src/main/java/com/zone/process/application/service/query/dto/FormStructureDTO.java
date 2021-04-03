package com.zone.process.application.service.query.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/3 11:43 上午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FormStructureDTO {

    @ApiModelProperty("表单id")
    private Long formId;

    @ApiModelProperty("表单结构")
    private String formJson;

    @ApiModelProperty(value = "表单名称")
    private String formName;

    @ApiModelProperty(value = "表单的key")
    private String formKey;

    @ApiModelProperty(value = "表单的版本")
    private Integer formVersion;

    @ApiModelProperty(value = "描述")
    private String description;
}
