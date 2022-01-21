package com.zone.auth.application.service.command;

import com.google.common.base.Preconditions;
import com.zone.auth.application.service.command.cmd.AccountLoginCommand;
import com.zone.auth.application.service.command.cmd.UserRegisterCommand;
import com.zone.auth.domain.agg.User;
import com.zone.auth.domain.repository.UserAggRepository;
import com.zone.commons.entity.LoginUser;
import com.zone.commons.util.JWTUtil;
import com.zone.commons.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/10 11:15 下午
 * @Description:
 */
@Slf4j
@Service
public class UserCmdService {

    @Autowired
    private UserAggRepository userAggRepository;

    /**
     * 用户登陆校验并返回token
     */
    public String login(AccountLoginCommand loginCommand) {
        User user = userAggRepository.queryByAccountName(loginCommand.getPhone());
        Preconditions.checkNotNull(user.getUserBasic(), "用户名或者密码错误");

        // 关于密码的加密与解密：
        // 1. 首先用 RSA 算法：前端公钥加密，后端拿到后用私钥解密。
        // 2. 将解密出来的密码通过不可逆加密算法计算后与数据库中的值进行比对。
        // 【公钥加密，私钥解密】存在数据被篡改的问题
        String decryptPwd = SecurityUtil.rsaDecrypt(loginCommand.getPassword());
        String sha1Pwd = SecurityUtil.digestSha1(decryptPwd);
        Preconditions.checkState(user.getUserBasic()
                .getPassword().equals(sha1Pwd), "用户名或者密码错误");

        // 关于 JWT 的签名与验签：
        // 1. 用私钥签名，说明消息是我发送的，别人发送不了
        // 2. 用公钥验签，所有持有公钥的人都能获取用私钥签名的消息
        // JWT 由 header.payload.signature 组成
        // header 与 payload 是不加密的
        // signature 由私钥签，用于验证 header 与 payload 是否被修改
        // 【私钥签名，公钥验签】存在数据泄露的问题
        String jwtToken = JWTUtil.createToken(
                new LoginUser()
                        .setUserName(user.getUserBasic().getUserName())
                        .setUserId(user.getUserBasic().getId()));
        return jwtToken;
    }

    /**
     * 注册用户信息
     */
    public Boolean register(UserRegisterCommand registerCommand, Long userId, String userName) {
        User user = User.create(registerCommand, userId, userName);
        userAggRepository.save(user);
        return true;
    }
}
