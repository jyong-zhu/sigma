package com.zone.auth.infrastructure.db.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 账号角色关联表
 * </p>
 *
 * @author Jone
 * @since 2022-01-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("auth_account_role")
@ApiModel(value="AuthAccountRoleDO对象", description="账号角色关联表")
public class AuthAccountRoleDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "账号id")
    private Long accountId;

    @ApiModelProperty(value = "角色id")
    private Long roleId;


}
