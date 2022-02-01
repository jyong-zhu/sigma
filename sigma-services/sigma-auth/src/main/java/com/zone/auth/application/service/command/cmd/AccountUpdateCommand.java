package com.zone.auth.application.service.command.cmd;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 7:53 下午
 * @Description:
 */
@Data
@Accessors(chain = true)
public class AccountUpdateCommand {

  @ApiModelProperty("账号id")
  @NotNull(message = "账号id不能为空")
  private Long accountId;

  @ApiModelProperty("账户名")
  @NotBlank(message = "账户名不能为空")
  private String name;

  @ApiModelProperty("账号状态")
  @NotNull(message = "账号状态不能为空")
  private Boolean status;

  @ApiModelProperty("邮箱")
  @NotBlank(message = "邮箱不能为空")
  private String email;

  @ApiModelProperty("角色列表")
  @NotNull(message = "角色列表不能为空")
  @Size(min = 1, message = "角色不能为空")
  @Size(max = 5, message = "最多指定5个角色")
  private List<Long> roleIdList;
}
