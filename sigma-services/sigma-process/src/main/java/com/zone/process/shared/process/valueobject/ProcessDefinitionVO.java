package com.zone.process.shared.process.valueobject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:46 下午
 * @Description: 包含流程定义相关属性的VO，
 * 根据六边形架构的规则，在 application.service 或者 domain 中不能够出现 camunda 中的任何类或方法，
 * 所以需要用这个 valueobject 包下的所有值对象将 camunda 中的数据进行封装
 * 封装后的类可以出现在 application.service 或者 domain 中
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProcessDefinitionVO {

    @ApiModelProperty("流程定义id")
    private String procDefId;

    @ApiModelProperty("流程定义的标识key")
    private String procDefKey;

    @ApiModelProperty("流程定义的版本")
    private Integer procDefVersion;
}
