package com.zone.auth.infrastructure.inbound.web.controller;

import com.zone.auth.application.service.command.AccountLoginCmdService;
import com.zone.auth.application.service.command.cmd.AccountChangeCommand;
import com.zone.auth.application.service.command.cmd.AccountLoginCommand;
import com.zone.commons.context.CurrentContext;
import com.zone.commons.entity.LoginUser;
import com.zone.commons.entity.ResponseData;
import com.zone.commons.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 5:53 下午
 * @Description: 登陆用户相关的controller
 */
@Api("登陆用户相关")
@RestController
@RequestMapping("/account")
public class AccountLoginController {

  @Autowired
  private AccountLoginCmdService accountLoginCmdService;

  @ApiOperation(value = "获取用户密码加密公钥", notes = "返回公钥")
  @PostMapping("/public-key")
  public ResponseData<String> queryPublicKey() {
    return ResponseData.ok(SecurityUtil.getPublicKey());
  }

  @ApiOperation(value = "用户登陆",notes = "返回JWT")
  @PostMapping("/login")
  public ResponseData<String> login(@Valid @RequestBody AccountLoginCommand loginCommand) {
    return ResponseData.ok(accountLoginCmdService.login(loginCommand));
  }

  @ApiOperation(value = "登陆用户更改个人信息",notes = "返回boolean")
  @PostMapping("/change")
  public ResponseData<Long> change(@Valid @RequestBody AccountChangeCommand changeCommand) {
    LoginUser loginUser = CurrentContext.getUser();
    return ResponseData.ok(accountLoginCmdService.change(changeCommand, loginUser));
  }

  // todo 鉴权接口


}
