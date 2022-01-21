package com.zone.auth.infrastructure.db.adapter;

import com.zone.auth.domain.agg.AccountAgg;
import com.zone.auth.infrastructure.db.dataobject.AuthAccountDO;
import com.zone.auth.infrastructure.db.dataobject.AuthAccountRoleDO;
import com.zone.auth.shared.enums.AccountTypeEnum;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/21 3:27 下午
 * @Description:
 */
public class AccountAggAdapter {

  public static AuthAccountDO getAccountDO(AccountAgg accountAgg) {
    if (accountAgg == null) {
      return null;
    }
    AuthAccountDO authAccountDO = new AuthAccountDO();
    authAccountDO.setId(accountAgg.getId());
    authAccountDO.setName(accountAgg.getName());
    authAccountDO.setPhone(accountAgg.getPhone());
    authAccountDO.setPassword(accountAgg.getPassword());
    authAccountDO.setAccountType(accountAgg.getAccountType().getCode());
    authAccountDO.setEmail(accountAgg.getEmail());
    authAccountDO.setStatus(accountAgg.getStatus());
    authAccountDO.setCreateTime(accountAgg.getCreateTime());
    authAccountDO.setCreateBy(accountAgg.getCreateBy());
    authAccountDO.setCreateName(accountAgg.getCreateName());
    authAccountDO.setUpdateTime(accountAgg.getUpdateTime());
    authAccountDO.setUpdateBy(accountAgg.getUpdateBy());
    authAccountDO.setUpdateName(accountAgg.getUpdateName());
    return authAccountDO;
  }

  public static List<AuthAccountRoleDO> getAccountRoleDOList(List<Long> roleIdList, Long accountId) {
    return roleIdList.stream().map(roleId -> {
      AuthAccountRoleDO authAccountRoleDO = new AuthAccountRoleDO();
      authAccountRoleDO.setRoleId(roleId);
      authAccountRoleDO.setAccountId(accountId);
      return authAccountRoleDO;
    }).collect(Collectors.toList());
  }

  public static AccountAgg getAccountAgg(AuthAccountDO authAccountDO, List<AuthAccountRoleDO> accountRoleDOList) {
    AccountAgg accountAgg = new AccountAgg();
    accountAgg.setId(authAccountDO.getId());
    accountAgg.setName(authAccountDO.getName());
    accountAgg.setPhone(authAccountDO.getPhone());
    accountAgg.setPassword(authAccountDO.getPassword());
    accountAgg.setAccountType(AccountTypeEnum.getByCode(authAccountDO.getAccountType()));
    accountAgg.setEmail(authAccountDO.getEmail());
    accountAgg.setRoleIdList(accountRoleDOList.stream().map(tmp -> tmp.getRoleId()).collect(Collectors.toList()));
    accountAgg.setStatus(authAccountDO.getStatus());
    accountAgg.setCreateTime(authAccountDO.getCreateTime());
    accountAgg.setCreateBy(authAccountDO.getCreateBy());
    accountAgg.setCreateName(authAccountDO.getCreateName());
    accountAgg.setUpdateTime(authAccountDO.getUpdateTime());
    accountAgg.setUpdateBy(authAccountDO.getUpdateBy());
    accountAgg.setUpdateName(authAccountDO.getUpdateName());
    return accountAgg;
  }
}
