package com.zone.auth.application.service.command;

import com.google.common.base.Preconditions;
import com.zone.auth.application.service.command.cmd.AccountCreateCommand;
import com.zone.auth.application.service.command.cmd.AccountUpdateCommand;
import com.zone.auth.application.service.command.transfer.AccountAggTransfer;
import com.zone.auth.domain.agg.AccountAgg;
import com.zone.auth.domain.repository.AccountAggRepository;
import com.zone.auth.shared.enums.AccountTypeEnum;
import com.zone.commons.entity.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 8:10 下午
 * @Description:
 */
@Slf4j
@Service
public class AccountCmdService {

  @Autowired
  private AccountAggRepository accountAggRepository;

  /**
   * 创建账号
   */
  @Transactional(rollbackFor = Exception.class)
  public Long create(AccountCreateCommand createCommand, LoginUser loginUser) {

    // 0. 校验账号类型
    Preconditions.checkState(AccountTypeEnum.isAdmin(loginUser.getAccountType()), "非管理员不能创建账号");

    // 1. 创建账号聚合根
    AccountAgg accountAgg = AccountAggTransfer.getAccountAgg(createCommand);
    accountAgg.init();

    // 2. 落地账号数据
    return accountAggRepository.save(accountAgg);
  }

  /**
   * 更新账号
   */
  @Transactional(rollbackFor = Exception.class)
  public Long update(AccountUpdateCommand updateCommand, LoginUser loginUser) {

    // 0. 校验账号类型
    Preconditions.checkState(AccountTypeEnum.isAdmin(loginUser.getAccountType()), "非管理员不能更新账号");

    // 1. 获取账号信息
    AccountAgg oldAccount = accountAggRepository.queryById(updateCommand.getAccountId());
    Preconditions.checkNotNull(oldAccount, "账号不存在");

    // 2. 更新账号
    oldAccount.update(updateCommand);

    // 3. 落地数据
    Long accountId = accountAggRepository.update(oldAccount);
    Preconditions.checkNotNull(accountId, "账号更新失败，请重试");

    return accountId;

  }

  /**
   * 删除账号
   */
  @Transactional(rollbackFor = Exception.class)
  public Long delete(Long accountId, LoginUser loginUser) {

    // 0. 校验账号类型
    Preconditions.checkState(AccountTypeEnum.isAdmin(loginUser.getAccountType()), "非管理员不能删除账号");

    // 1. 删除账号
    return accountAggRepository.delete(accountId);
  }
}
