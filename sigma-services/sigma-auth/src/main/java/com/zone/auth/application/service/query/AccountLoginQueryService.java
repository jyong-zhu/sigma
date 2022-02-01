package com.zone.auth.application.service.query;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zone.auth.application.service.command.cmd.AccountLoginCommand;
import com.zone.auth.application.service.query.assembler.AccountCheckDetailAssembler;
import com.zone.auth.infrastructure.db.dataobject.AuthAccountDO;
import com.zone.auth.infrastructure.db.dataobject.AuthResourceDO;
import com.zone.auth.infrastructure.db.query.AccountQuery;
import com.zone.auth.infrastructure.db.query.ResourceQuery;
import com.zone.auth.infrastructure.db.query.RoleQuery;
import com.zone.auth.shared.enums.AccountTypeEnum;
import com.zone.commons.entity.LoginUser;
import com.zone.commons.util.JWTUtil;
import com.zone.commons.util.SecurityUtil;
import com.zone.rpc.dto.auth.AccountCheckDTO;
import com.zone.rpc.req.auth.AccountCheckReq;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/22 2:54 下午
 * @Description:
 */
@Slf4j
@Service
public class AccountLoginQueryService {

  @Autowired
  private AccountQuery accountQuery;

  @Autowired
  private RoleQuery roleQuery;

  @Autowired
  private ResourceQuery resourceQuery;

  /**
   * 用户登陆 关于密码的加密与解密：
   * <p>1. 首先用 RSA 算法：前端公钥加密，后端拿到后用私钥解密。
   * <p>2. 将解密出来的密码通过不可逆加密算法计算后与数据库中的值进行比对。
   * <p>  【公钥加密，私钥解密】存在数据被篡改的问题
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
    AuthAccountDO accountDO = accountQuery.queryByPhone(loginCommand.getPhone());
    Preconditions.checkState(accountDO != null && accountDO.getStatus(), "用户名或者密码错误");

    // 1. 比对密码
    String decryptPwd = SecurityUtil.rsaDecrypt(loginCommand.getPassword());
    String sha1Pwd = SecurityUtil.digestSha1(decryptPwd);
    Preconditions.checkState(accountDO.getPassword().equals(sha1Pwd), "用户名或者密码错误");

    // 2. 登陆成功返回 JWT
    return JWTUtil.createToken(new LoginUser()
        .setAccountName(accountDO.getName())
        .setAccountId(accountDO.getId()));
  }

  /**
   * 鉴权方法
   */
  public AccountCheckDTO check(AccountCheckReq checkReq) {
    log.info("开始鉴权，请求参数为AccountCheckReq=[{}]", checkReq);

    // 1. 获取账户详情
    AuthAccountDO accountDO = accountQuery.queryById(checkReq.getAccountId());
    if (accountDO == null || !accountDO.getStatus()) {
      log.warn("鉴权失败，账号不存在或被禁用：accountDO=[{}]", accountDO);
      return null;
    }

    // 2. 判断是否为超级管理员
    if (AccountTypeEnum.isAdmin(accountDO.getAccountType())) {
      return AccountCheckDetailAssembler.toAccountCheckDTO(accountDO, Lists.newArrayList());
    }

    // 3. 获取角色列表
    List<Long> roleIdList = roleQuery.queryRoleIdList(accountDO.getId());
    if (CollectionUtil.isEmpty(roleIdList)) {
      log.warn("鉴权失败，角色为空：accountDO=[{}]", accountDO);
      return null;
    }

    // 3. 获取角色对应的资源id列表
    List<Long> resourceIdList = roleQuery.queryRoleResourceIdList(roleIdList);
    if (CollectionUtil.isEmpty(resourceIdList)) {
      log.warn("鉴权失败，资源为空：accountDO=[{}], roleIdList=[{}]", accountDO, roleIdList);
      return null;
    }

    // 4. 判断url是否存在
    List<AuthResourceDO> resourceList = resourceQuery.queryByUrlInIdList(resourceIdList, checkReq.getUrl());
    if (CollectionUtil.isEmpty(resourceList)) {
      log.warn("鉴权失败，资源为空：accountDO=[{}], roleIdList=[{}]", accountDO, roleIdList);
      return null;
    }

    return AccountCheckDetailAssembler.toAccountCheckDTO(accountDO, roleIdList);
  }
}
