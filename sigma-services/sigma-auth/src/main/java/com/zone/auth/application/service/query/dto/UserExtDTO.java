package com.zone.auth.application.service.query.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/13 11:51 上午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserExtDTO {

    @ApiModelProperty("字段id")
    private String field;

    @ApiModelProperty("字段类型")
    private String fieldType;

    @ApiModelProperty("字段值")
    private String value;
}
