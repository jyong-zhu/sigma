package com.zone.process.domain.valueobject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/31 5:45 上午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InstDataVO {

    @ApiModelProperty(value = "当前录入表单所处的节点id")
    private String bpmnNodeId;

    @ApiModelProperty(value = "所属表单id")
    private Long formId;

    @ApiModelProperty(value = "流程实例在表单中对应的数据")
    private String formData;

    @ApiModelProperty(value = "扩展信息")
    private String ext;
}
