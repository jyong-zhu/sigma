package com.zone.auth.application.service.command;

import com.zone.auth.application.service.command.cmd.AccountChangeCommand;
import com.zone.auth.application.service.command.cmd.AccountLoginCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 10:08 下午
 * @Description: 登陆相关的service
 */
@Slf4j
@Service
public class AccountLoginCmdService {

  /**
   * 用户登陆
   */
  public String login(AccountLoginCommand loginCommand) {
    return null;
  }

  /**
   * 登陆用户改个人信息
   */
  public Boolean change(AccountChangeCommand changeCommand) {
    return null;
  }

}
