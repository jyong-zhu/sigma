package com.zone.auth.application.service.command.transfer;

import com.zone.auth.application.service.command.cmd.RoleCreateCommand;
import com.zone.auth.domain.agg.RoleAgg;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/29 11:11 下午
 * @Description:
 */
public class RoleAggTransfer {

  public static RoleAgg getRoleAgg(RoleCreateCommand createCommand) {
    if (createCommand == null) {
      return null;
    }
    RoleAgg roleAgg = new RoleAgg();
    roleAgg.setRoleName(createCommand.getRoleName());
    roleAgg.setResourceIdList(createCommand.getResourceIdList());
    roleAgg.setStatus(createCommand.getStatus());
    return roleAgg;
  }
}
