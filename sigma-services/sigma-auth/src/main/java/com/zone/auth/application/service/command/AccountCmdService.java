package com.zone.auth.application.service.command;

import com.zone.auth.application.service.command.cmd.AccountCreateCommand;
import com.zone.auth.application.service.command.cmd.AccountUpdateCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 8:10 下午
 * @Description:
 */
@Slf4j
@Service
public class AccountCmdService {

  /**
   * 创建账号
   */
  public Long create(AccountCreateCommand createCommand) {
    return null;
  }

  /**
   * 更新账号
   */
  public Long update(AccountUpdateCommand updateCommand) {
    return null;
  }

  /**
   * 禁用/启用账号
   */
  public Boolean enable(Long accountId) {
    return null;
  }

  /**
   * 删除账号
   */
  public Boolean delete(Long accountId) {
    return null;
  }
}
