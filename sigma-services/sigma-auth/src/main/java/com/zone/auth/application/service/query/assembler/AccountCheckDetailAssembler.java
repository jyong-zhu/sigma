package com.zone.auth.application.service.query.assembler;

import com.zone.auth.infrastructure.db.dataobject.AuthAccountDO;
import com.zone.rpc.dto.auth.AccountCheckDTO;
import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/29 11:24 下午
 * @Description:
 */
public class AccountCheckDetailAssembler {

  public static AccountCheckDTO toAccountCheckDTO(AuthAccountDO accountDO, List<Long> roleIdList) {
    if (accountDO == null) {
      return null;
    }
    AccountCheckDTO accountCheckDTO = new AccountCheckDTO();
    accountCheckDTO.setAccountId(accountDO.getId());
    accountCheckDTO.setAccountName(accountDO.getName());
    accountCheckDTO.setAccountType(accountDO.getAccountType());
    accountCheckDTO.setRoleIdList(roleIdList);
    accountCheckDTO.setPhone(accountDO.getPhone());
    return accountCheckDTO;
  }
}
