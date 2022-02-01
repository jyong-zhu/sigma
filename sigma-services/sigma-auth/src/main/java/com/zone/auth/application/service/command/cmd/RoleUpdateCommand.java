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
 * @Date: 2022/1/20 8:33 下午
 * @Description:
 */
@Data
@Accessors(chain = true)
public class RoleUpdateCommand {

  @ApiModelProperty(value = "主键")
  @NotNull(message = "主键id不能为空")
  private Long roleId;

  @ApiModelProperty(value = "角色名称")
  @NotBlank(message = "角色名称不能为空")
  private String roleName;

  @ApiModelProperty(value = "角色拥有的资源列表")
  @NotNull(message = "角色的资源列表不能为空")
  @Size(min = 1, message = "资源列表不能为空")
  @Size(max = 300, message = "最多分配300个资源")
  private List<Long> resourceIdList;

  @ApiModelProperty(value = "角色状态")
  @NotNull(message = "状态不能为空")
  private Boolean status;

}
