package com.zone.web.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zone.commons.consts.GatewayConstants;
import com.zone.commons.context.CurrentContext;
import com.zone.commons.entity.LoginUser;
import com.zone.commons.entity.ResponseData;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    String path = request.getRequestURI();
    if (GatewayConstants.whiteList.contains(path)) {
      log.info("请求url位于白名单中，放行");
      return true;
    }

    String accountId = URLDecoder.decode(request.getHeader(GatewayConstants.ACCOUNT_ID), GatewayConstants.UTF_8);
    String accountName = URLDecoder.decode(request.getHeader(GatewayConstants.ACCOUNT_NAME), GatewayConstants.UTF_8);
    String accountType = URLDecoder.decode(request.getHeader(GatewayConstants.ACCOUNT_TYPE), GatewayConstants.UTF_8);
    String roleIdList = URLDecoder.decode(request.getHeader(GatewayConstants.ROLE_ID_LIST), GatewayConstants.UTF_8);
    String phone = URLDecoder.decode(request.getHeader(GatewayConstants.PHONE), GatewayConstants.UTF_8);

    // 如果request中存在这些header，则从这些header中封装loginUser
    if (StrUtil.isNotBlank(accountId) && StrUtil.isNotBlank(accountName) && StrUtil.isNotBlank(accountType)
        && StrUtil.isNotBlank(roleIdList) && StrUtil.isNotBlank(phone)) {
      // 上下文中写入用户信息，并放行
      CurrentContext.setUser(new LoginUser()
          .setAccountId(Long.valueOf(accountId))
          .setAccountName(accountName)
          .setAccountType(Integer.valueOf(accountType))
          .setRoleIdList(JSONUtil.toList(JSONUtil.parseArray(roleIdList), Long.class))
          .setPhone(phone));
      return true;
    }

    // 写回错误信息，不放行
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setContentType(GatewayConstants.JSON_CHARSET_UTF_8);
    response.getWriter().write(JSONUtil.toJsonStr(ResponseData.error("系统错误")));
    response.getWriter().flush();

    return false;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    // 清除 ThreadLocal 中的 loginUser 信息
    CurrentContext.remove();
    log.info("=================拦截器清除ThreadLocal<LoginUser>=================");
  }
}
