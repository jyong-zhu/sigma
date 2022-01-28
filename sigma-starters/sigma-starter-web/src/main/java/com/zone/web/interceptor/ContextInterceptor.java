package com.zone.web.interceptor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zone.commons.consts.GatewayConstants;
import com.zone.commons.context.CurrentContext;
import com.zone.commons.entity.LoginUser;
import com.zone.commons.entity.ResponseData;
import java.net.URLDecoder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
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
    // swagger相关的路径也要放行
    String simplePath = removeServiceName(path);
    if (pathMatch(GatewayConstants.whiteList, path) || pathMatch(GatewayConstants.whiteList, simplePath)) {
      log.info("请求url位于白名单中，放行");
      return true;
    }

    String accountId = request.getHeader(GatewayConstants.ACCOUNT_ID);
    String accountName = request.getHeader(GatewayConstants.ACCOUNT_NAME);
    String accountType = request.getHeader(GatewayConstants.ACCOUNT_TYPE);
    String roleIdList = request.getHeader(GatewayConstants.ROLE_ID_LIST);
    String phone = request.getHeader(GatewayConstants.PHONE);

    // 如果request中存在这些header，则从这些header中封装loginUser
    if (StrUtil.isNotBlank(accountId) && StrUtil.isNotBlank(accountName) && StrUtil.isNotBlank(accountType)
        && StrUtil.isNotBlank(roleIdList) && StrUtil.isNotBlank(phone)) {

      accountId = URLDecoder.decode(request.getHeader(GatewayConstants.ACCOUNT_ID), GatewayConstants.UTF_8);
      accountName = URLDecoder.decode(request.getHeader(GatewayConstants.ACCOUNT_NAME), GatewayConstants.UTF_8);
      accountType = URLDecoder.decode(request.getHeader(GatewayConstants.ACCOUNT_TYPE), GatewayConstants.UTF_8);
      roleIdList = URLDecoder.decode(request.getHeader(GatewayConstants.ROLE_ID_LIST), GatewayConstants.UTF_8);
      phone = URLDecoder.decode(request.getHeader(GatewayConstants.PHONE), GatewayConstants.UTF_8);

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

  /**
   * 移除服务名
   */
  private static String removeServiceName(String path) {
    if (StrUtil.isBlank(path)) {
      return "";
    }
    return path.substring(path.indexOf("/", 1));
  }

  /**
   * 路径匹配
   */
  private boolean pathMatch(List<String> patternList, String path) {
    if (CollectionUtil.isEmpty(patternList)) {
      return false;
    }
    AntPathMatcher matcher = new AntPathMatcher();
    return patternList.stream().anyMatch(pattern -> matcher.match(pattern, path));
  }
}
