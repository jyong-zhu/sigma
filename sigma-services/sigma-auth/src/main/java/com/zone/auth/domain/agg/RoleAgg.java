package com.zone.auth.domain.agg;

import com.zone.auth.application.service.command.cmd.RoleUpdateCommand;
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

  @ApiModelProperty(value = "数据版本")
  private Integer version;

  @ApiModelProperty(value = "user_id")
  private Long createBy;

  @ApiModelProperty(value = "user_name")
  private String createName;

  @ApiModelProperty(value = "user_id")
  private Long updateBy;

  @ApiModelProperty(value = "user_name")
  private String updateName;

  /**
   * 更新角色
   */
  public void update(RoleUpdateCommand updateCommand) {
    this.setRoleName(updateCommand.getRoleName());
    this.setResourceIdList(updateCommand.getResourceIdList());
    this.setStatus(updateCommand.getStatus());
  }
}
