package com.zone.auth.application.service.query.assembler;

import com.zone.auth.application.service.query.dto.AccountDetailDTO;
import com.zone.auth.application.service.query.dto.RoleDetailDTO;
import com.zone.auth.infrastructure.db.dataobject.AuthAccountDO;
import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/23 3:15 下午
 * @Description:
 */
public class AccountDetailDTOAssembler {

  public static AccountDetailDTO toAccountDetailDTO(AuthAccountDO accountDO, List<RoleDetailDTO> roleDTOList) {
    if (accountDO == null) {
      return null;
    }
    AccountDetailDTO accountDetailDTO = new AccountDetailDTO();
    accountDetailDTO.setId(accountDO.getId());
    accountDetailDTO.setName(accountDO.getName());
    accountDetailDTO.setPhone(accountDO.getPhone());
    accountDetailDTO.setAccountType(accountDO.getAccountType());
    accountDetailDTO.setEmail(accountDO.getEmail());
    accountDetailDTO.setStatus(accountDO.getStatus());
    accountDetailDTO.setRoleList(roleDTOList);
    accountDetailDTO.setCreateTime(accountDO.getCreateTime());
    accountDetailDTO.setCreateName(accountDO.getCreateName());
    accountDetailDTO.setUpdateTime(accountDO.getUpdateTime());
    accountDetailDTO.setUpdateName(accountDO.getUpdateName());
    return accountDetailDTO;
  }
}
