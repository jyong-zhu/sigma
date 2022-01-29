package com.zone.auth.application.service.command.transfer;

import com.zone.auth.application.service.command.cmd.AccountCreateCommand;
import com.zone.auth.domain.agg.AccountAgg;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/29 10:53 下午
 * @Description:
 */
public class AccountAggTransfer {

  public static AccountAgg getAccountAgg(AccountCreateCommand createCommand) {
    if (createCommand == null) {
      return null;
    }
    AccountAgg accountAgg = new AccountAgg();
    accountAgg.setName(createCommand.getName());
    accountAgg.setPhone(createCommand.getPhone());
    accountAgg.setEmail(createCommand.getEmail());
    accountAgg.setRoleIdList(createCommand.getRoleIdList());
    return accountAgg;
  }
}
