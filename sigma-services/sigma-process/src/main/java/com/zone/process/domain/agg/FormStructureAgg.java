package com.zone.process.domain.agg;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:00 下午
 * @Description: 表单的聚合
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FormStructureAgg {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "表单名称")
    private String name;

    @ApiModelProperty(value = "表单的key")
    private String formKey;

    @ApiModelProperty(value = "表单的版本")
    private Integer formVersion;

    @ApiModelProperty(value = "表单的json串")
    private String formJson;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "数据版本")
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "user_id")
    private Long createBy;

    @ApiModelProperty(value = "user_name")
    private String createName;

    /**
     * 初始化id和版本号
     */
    public void init(FormStructureAgg oldFormAgg) {
        this.setFormVersion(oldFormAgg == null ? 0 : oldFormAgg.getFormVersion() + 1);
    }
}
