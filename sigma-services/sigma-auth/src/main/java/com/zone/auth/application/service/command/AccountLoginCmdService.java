package com.zone.auth.application.service.command;

import com.google.common.base.Preconditions;
import com.zone.auth.application.service.command.cmd.AccountChangeCommand;
import com.zone.auth.application.service.command.cmd.AccountLoginCommand;
import com.zone.auth.domain.agg.AccountAgg;
import com.zone.auth.domain.repository.AccountAggRepository;
import com.zone.commons.entity.LoginUser;
import com.zone.commons.util.JWTUtil;
import com.zone.commons.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
   * 用户登陆 关于密码的加密与解密：
   * <p>1. 首先用 RSA 算法：前端公钥加密，后端拿到后用私钥解密。
   * <p>2. 将解密出来的密码通过不可逆加密算法计算后与数据库中的值进行比对。
   * <p>  【公钥加密，私钥解密】存在数据被篡改的问题
   * <p>
   * <p>
   * <p>
   * 关于 JWT 的签名与验签：
   * <p>1. 用私钥签名，说明消息是我发送的，别人发送不了
   * <p>2. 用公钥验签，所有持有公钥的人都能获取用私钥签名的消息
   * <p>   JWT 由 header.payload.signature 组成
   * <p>   header 与 payload 是不加密的
   * <p>   signature 由私钥签，用于验证 header 与 payload 是否被修改
   * <p>  【私钥签名，公钥验签】存在数据泄露的问题
   */
  public String login(AccountLoginCommand loginCommand) {

    // 0. 校验用户是否存在
    String decryptPhone = SecurityUtil.rsaDecrypt(loginCommand.getPhone());
    AccountAgg accountAgg = accountAggRepository.queryByPhone(decryptPhone);
    Preconditions.checkNotNull(accountAgg, "用户名或者密码错误");

    // 1. 比对密码
    String decryptPwd = SecurityUtil.rsaDecrypt(loginCommand.getPassword());
    String sha1Pwd = SecurityUtil.digestSha1(decryptPwd);
    Preconditions.checkState(accountAgg.getPassword().equals(sha1Pwd), "用户名或者密码错误");

    // 2. 登陆成功返回 JWT
    return JWTUtil.createToken(new LoginUser()
        .setUserName(accountAgg.getName())
        .setUserId(accountAgg.getId()));
  }

  /**
   * 登陆用户改个人信息
   */
  public Long change(AccountChangeCommand changeCommand, LoginUser loginUser) {

    // 0. 校验用户信息
    AccountAgg accountAgg = accountAggRepository.queryById(loginUser.getUserId());
    Preconditions.checkNotNull(accountAgg, "用户名不存在");

    // 1. 修改个人信息
    accountAgg.change(changeCommand, loginUser);

    // 2. 更新个人信息
    return accountAggRepository.update(accountAgg);
  }

}
