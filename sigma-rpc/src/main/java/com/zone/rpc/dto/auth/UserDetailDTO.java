package com.zone.rpc.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/20 11:54 上午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserDetailDTO implements Serializable {

    private static final long serialVersionUID = -4547342494607872087L;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("账户名")
    private String accountName;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("角色id")
    private Long roleId;
}
