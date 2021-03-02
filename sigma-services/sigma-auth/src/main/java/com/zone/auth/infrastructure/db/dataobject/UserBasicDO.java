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
 * 用户基础信息表
 * </p>
 *
 * @author Jone
 * @since 2021-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_basic")
@ApiModel(value="UserBasicDO对象", description="用户基础信息表")
public class UserBasicDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "账户名")
    private String accountName;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "密码，采用哈希算法进行单向加密")
    private String password;

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
