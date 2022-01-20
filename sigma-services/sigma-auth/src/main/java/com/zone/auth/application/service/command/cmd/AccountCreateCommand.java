package com.zone.auth.application.service.command.cmd;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 7:53 下午
 * @Description:
 */
@Data
@Accessors(chain = true)
public class AccountCreateCommand {

  @ApiModelProperty("账户名")
  @NotBlank(message = "账户名不能为空")
  private String name;

  @ApiModelProperty("手机号")
  @NotBlank(message = "手机号不能为空")
  private String phone;

  @ApiModelProperty("邮箱")
  @NotBlank(message = "邮箱不能为空")
  private String email;

  @ApiModelProperty("角色列表")
  @NotNull(message = "角色列表不能为空")
  private List<Long> roleIdList;
}
