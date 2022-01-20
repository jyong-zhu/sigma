package com.zone.auth.application.service.command.cmd;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 7:53 下午
 * @Description:
 */
@Data
@Accessors(chain = true)
public class AccountChangeCommand {

  @ApiModelProperty("账户名")
  @NotBlank(message = "账户名不能为空")
  private String name;

  @ApiModelProperty("邮箱")
  @NotBlank(message = "邮箱不能为空")
  private String email;

  @ApiModelProperty("旧密码")
  @NotBlank(message = "旧密码不能为空")
  private String oldPwd;

  @ApiModelProperty("新密码")
  @NotBlank(message = "新密码不能为空")
  private String newPwd;
}
