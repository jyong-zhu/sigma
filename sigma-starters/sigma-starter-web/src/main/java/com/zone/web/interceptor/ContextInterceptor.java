package com.zone.web.interceptor;

import cn.hutool.core.util.StrUtil;
import com.zone.commons.consts.GatewayConstants;
import com.zone.commons.context.CurrentContext;
import com.zone.commons.entity.LoginUser;
import com.zone.commons.util.JWTUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/17 1:05 上午
 * @Description: 声明拦截器，对进来的请求进行拦截
 */
@Slf4j
@Component
public class ContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("=================拦截器设置ThreadLocal<LoginUser>=================");
        String authorization = request.getHeader(GatewayConstants.AUTHORIZATION);
        String userId = request.getHeader(GatewayConstants.USER_ID);
        String userName = request.getHeader(GatewayConstants.USER_NAME);
        String accountName = request.getHeader(GatewayConstants.ACCOUNT_NAME);
        String roleId = request.getHeader(GatewayConstants.ROLE_ID);

        // 如果request中存在这些header，则从这些header中封装loginUser
        if (StrUtil.isNotBlank(userId) && StrUtil.isNotBlank(userName)
                && StrUtil.isNotBlank(accountName) && StrUtil.isNotBlank(roleId)) {
            CurrentContext.setUser(new LoginUser()
                    .setUserId(Long.valueOf(userId))
                    .setUserName(userName));
            return true;
        }

        // 否则解析JWT
        LoginUser loginUser = JWTUtil.verifyToken(authorization);
        if (loginUser != null) {
            CurrentContext.setUser(loginUser);
        }

        // 默认不放行
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清除 ThreadLocal 中的 loginUser 信息
        CurrentContext.remove();
        log.info("=================拦截器清除ThreadLocal<LoginUser>=================");
    }
}
