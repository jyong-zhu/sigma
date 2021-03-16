package com.zone.commons.util;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;
import com.zone.commons.entity.LoginUser;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/13 1:19 下午
 * @Description:
 */
@Slf4j
public final class JWTUtil {

    // 过期时间15min
    private static final long EXPIRE_TIME = 15 * 60 * 1000;

    // JWT中的 header 与 payload 是不加密的，用 signature 来验证前面两者是否被修改。
    // signature 的生成是用以下这个 Secret 来生成的
    private static final String TOKEN_SECRET = "sigma";

    /**
     * 自定义过期时间
     */
    public static String createToken(LoginUser loginUser, Long expireDate) {

        Date date = new Date(expireDate);
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

        Map<String, Object> headerMap = Maps.newHashMap();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> claimMap = Maps.newHashMap();
        claimMap.put("userId", loginUser.getUserId());
        claimMap.put("userName", loginUser.getUserName());
        claimMap.put("roleId", loginUser.getRoleId());

        return JWT.create()
                .withHeader(headerMap)
                .withClaim("userInfo", claimMap)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 默认15分钟过期
     */
    public static String createToken(LoginUser loginUser) {
        return createToken(loginUser, System.currentTimeMillis() + EXPIRE_TIME);
    }

    /**
     * 验证 JWT token
     * 过期或者验签失败都会返回 null
     */
    public static LoginUser verifyToken(String token) {
        if (StrUtil.isBlank(token)) {
            return null;
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Object> claimMap = jwt.getClaim("userInfo").asMap();
            LoginUser loginUser = new LoginUser()
                    .setUserId(claimMap.get("userId") == null ? null : Long.valueOf(claimMap.get("userId").toString()))
                    .setUserName(claimMap.getOrDefault("userName", "").toString())
                    .setRoleId(claimMap.get("roleId") == null ? null : Long.valueOf(claimMap.get("roleId").toString()));
            return loginUser.getUserId() == null ? null : loginUser;
        } catch (Exception e) {
            log.error("验证 " + token + " 时出错");
            return null;
        }
    }

//    会报错java.lang.ClassNotFoundException: org.slf4j.LoggerFactory
//    因为 slf4j 是 provided 声明的依赖
//    public static void main(String[] args) {
//        LoginUser loginUser = new LoginUser()
//                .setUserId(1l)
//                .setUserName("admin")
//                .setRoleId(1l);
//        String jwtToken = createToken(loginUser);
//        System.out.println(jwtToken);
//
//        LoginUser verifyUser = verifyToken(jwtToken);
//        System.out.println(verifyUser);
//    }
}
