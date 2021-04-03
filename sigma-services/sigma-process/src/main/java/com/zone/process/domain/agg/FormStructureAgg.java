package com.zone.process.domain.agg;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:00 下午
 * @Description: 表单的聚合
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FormStructureAgg {

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "user_id")
    private Long createBy;

    @ApiModelProperty(value = "user_name")
    private String createName;
}
