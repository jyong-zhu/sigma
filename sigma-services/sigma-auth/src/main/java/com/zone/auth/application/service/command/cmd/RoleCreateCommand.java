package com.zone.auth.application.service.command.cmd;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 8:33 下午
 * @Description:
 */
@Data
@Accessors(chain = true)
public class RoleCreateCommand {

  @ApiModelProperty(value = "角色名称")
  @NotBlank(message = "角色名称不能为空")
  private String roleName;

  @ApiModelProperty(value = "角色拥有的资源列表")
  @NotNull(message = "角色的资源列表不能为空")
  private List<Long> resourceIdList;

  @ApiModelProperty(value = "角色状态")
  @NotNull(message = "状态不能为空")
  private Boolean status;

}
