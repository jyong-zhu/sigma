package com.zone.auth.application.service.command.cmd;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/10 11:17 下午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserLoginCommand {

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String accountName;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空，非对称加密")
    private String password;
}
