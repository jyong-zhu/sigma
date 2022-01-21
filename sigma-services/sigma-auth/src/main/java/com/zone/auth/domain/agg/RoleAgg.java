package com.zone.auth.domain.agg;

import com.zone.auth.application.service.command.cmd.RoleCreateCommand;
import com.zone.auth.application.service.command.cmd.RoleUpdateCommand;
import com.zone.commons.entity.LoginUser;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/21 11:49 上午
 * @Description: 角色聚合根
 */
@Data
@Accessors(chain = true)
public class RoleAgg {

  @ApiModelProperty(value = "主键")
  private Long id;

  @ApiModelProperty(value = "角色名称")
  private String roleName;

  @ApiModelProperty(value = "角色的资源列表")
  private List<Long> resourceIdList;

  @ApiModelProperty(value = "0-停用 1-正常")
  private Boolean status;

  @ApiModelProperty(value = "user_id")
  private Long createBy;

  @ApiModelProperty(value = "user_name")
  private String createName;

  @ApiModelProperty(value = "user_id")
  private Long updateBy;

  @ApiModelProperty(value = "user_name")
  private String updateName;

  /**
   * 创建角色
   */
  public static RoleAgg create(RoleCreateCommand createCommand, LoginUser loginUser) {
    return new RoleAgg()
        .setRoleName(createCommand.getRoleName())
        .setResourceIdList(createCommand.getResourceIdList())
        .setStatus(createCommand.getStatus())
        .setCreateBy(loginUser.getUserId())
        .setCreateName(loginUser.getUserName())
        .setUpdateBy(loginUser.getUserId())
        .setUpdateName(loginUser.getUserName());
  }

  /**
   * 更新角色
   */
  public void update(RoleUpdateCommand updateCommand, LoginUser loginUser) {
    this.setRoleName(updateCommand.getRoleName());
    this.setResourceIdList(updateCommand.getResourceIdList());
    this.setStatus(updateCommand.getStatus());
    this.setUpdateBy(loginUser.getUserId());
    this.setUpdateName(loginUser.getUserName());
  }
}
