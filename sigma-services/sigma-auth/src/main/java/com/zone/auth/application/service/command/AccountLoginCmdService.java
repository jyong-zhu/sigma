package com.zone.auth.application.service.command;

import com.google.common.base.Preconditions;
import com.zone.auth.application.service.command.cmd.AccountChangeCommand;
import com.zone.auth.domain.agg.AccountAgg;
import com.zone.auth.domain.repository.AccountAggRepository;
import com.zone.commons.entity.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 10:08 下午
 * @Description: 登陆相关的service
 */
@Slf4j
@Service
public class AccountLoginCmdService {

  @Autowired
  private AccountAggRepository accountAggRepository;

  /**
   * 登陆用户改个人信息
   */
  @Transactional(rollbackFor = Exception.class)
  public Long change(AccountChangeCommand changeCommand, LoginUser loginUser) {

    // 0. 校验用户信息
    AccountAgg accountAgg = accountAggRepository.queryById(loginUser.getAccountId());
    Preconditions.checkNotNull(accountAgg, "用户名不存在");

    // 1. 修改个人信息
    accountAgg.change(changeCommand);

    // 2. 更新个人信息
    return accountAggRepository.update(accountAgg);
  }

}
