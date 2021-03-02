package com.zone.auth.infrastructure.db.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户扩展信息表
 * </p>
 *
 * @author Jone
 * @since 2021-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_ext")
@ApiModel(value="UserExtDO对象", description="用户扩展信息表")
public class UserExtDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "逻辑主键id")
    private String userId;

    @ApiModelProperty(value = "扩展字段名称")
    private String field;

    @ApiModelProperty(value = "扩展字段的类型，Integer/Double/Long/String/Object等")
    private String fieldType;

    @ApiModelProperty(value = "扩展字段的值")
    private String value;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "user_id")
    private Long createBy;

    @ApiModelProperty(value = "user_name")
    private String createName;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "user_id")
    private Long updateBy;

    @ApiModelProperty(value = "user_name")
    private String updateName;


}
