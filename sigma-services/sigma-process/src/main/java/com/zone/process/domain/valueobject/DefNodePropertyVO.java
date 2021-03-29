package com.zone.process.domain.valueobject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 5:55 下午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DefNodePropertyVO {

    @ApiModelProperty(value = "流程节点中的属性名称")
    private String propertyName;

    @ApiModelProperty(value = "属性值")
    private String propertyValue;

    @ApiModelProperty(value = "属性作用描述")
    private String desc;

    @ApiModelProperty(value = "扩展字段")
    private String ext;
}
