package com.zone.auth.infrastructure.inbound.web.controller;


import com.zone.auth.application.service.command.AccountCmdService;
import com.zone.auth.application.service.command.cmd.AccountCreateCommand;
import com.zone.auth.application.service.command.cmd.AccountUpdateCommand;
import com.zone.auth.application.service.query.AccountQueryService;
import com.zone.auth.application.service.query.dto.AccountDetailDTO;
import com.zone.commons.context.CurrentContext;
import com.zone.commons.entity.LoginUser;
import com.zone.commons.entity.ResponseData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 账号表 前端控制器
 * </p>
 *
 * @author Jone
 * @since 2022-01-20
 */
@RestController
@RequestMapping("/account")
public class AccountManageController {

  @Autowired
  private AccountCmdService accountCmdService;

  @Autowired
  private AccountQueryService accountQueryService;

  @ApiOperation(value = "创建账号", notes = "返回账号id")
  @PostMapping("/create")
  public ResponseData<Long> create(@Valid @RequestBody AccountCreateCommand createCommand) {
    LoginUser loginUser = CurrentContext.getUser();
    return ResponseData.ok(accountCmdService.create(createCommand, loginUser));
  }

  @ApiOperation(value = "更新账号", notes = "返回账号id")
  @PostMapping("/update")
  public ResponseData<Long> update(@Valid @RequestBody AccountUpdateCommand updateCommand) {
    LoginUser loginUser = CurrentContext.getUser();
    return ResponseData.ok(accountCmdService.update(updateCommand, loginUser));
  }

  @ApiOperation(value = "删除账号", notes = "返回账号id")
  @GetMapping("/delete")
  public ResponseData<Long> delete(
      @ApiParam(value = "账号id") @RequestParam(value = "accountId") Long accountId) {
    LoginUser loginUser = CurrentContext.getUser();
    return ResponseData.ok(accountCmdService.delete(accountId, loginUser));
  }

  @ApiOperation(value = "获取账号详情", notes = "返回详情")
  @GetMapping("/detail")
  public ResponseData<AccountDetailDTO> detail(
      @ApiParam(value = "账号id") @RequestParam(value = "accountId") Long accountId) {
    return ResponseData.ok(accountQueryService.detail(accountId));
  }

  // todo 分页查询


}
