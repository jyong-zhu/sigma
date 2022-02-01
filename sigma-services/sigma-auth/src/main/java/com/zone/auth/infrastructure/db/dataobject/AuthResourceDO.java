package com.zone.auth.infrastructure.db.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author Jone
 * @since 2022-01-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("auth_resource")
@ApiModel(value="AuthResourceDO对象", description="资源表")
public class AuthResourceDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "权限类型 1-菜单权限 2-功能权限")
    private Integer type;

    @ApiModelProperty(value = "key")
    @TableField(value = "`key`")
    private String key;

    @ApiModelProperty(value = "资源点名称")
    private String name;

    @ApiModelProperty(value = "资源点对应的url")
    private String resourceUrl;

    @ApiModelProperty(value = "icon图标地址")
    private String iconUrl;

    @ApiModelProperty(value = "功能权限对应菜单的id")
    private Long parentId;

    @ApiModelProperty(value = "是否可见 0-隐藏 1-可见")
    private Boolean visible;

    @ApiModelProperty(value = "0-停用 1-正常")
    private Boolean status;

    @ApiModelProperty(value = "排序值")
    private Integer sortNum;

    @ApiModelProperty(value = "乐观锁版本")
    @Version
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "user_id")
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @ApiModelProperty(value = "user_name")
    @TableField(fill = FieldFill.INSERT)
    private String createName;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "user_id")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @ApiModelProperty(value = "user_name")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateName;


}
