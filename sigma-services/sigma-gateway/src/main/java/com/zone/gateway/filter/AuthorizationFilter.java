package com.zone.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zone.commons.consts.GatewayConstants;
import com.zone.commons.entity.LoginUser;
import com.zone.commons.entity.ResponseData;
import com.zone.commons.util.JWTUtil;
import com.zone.rpc.dto.auth.AccountCheckDTO;
import com.zone.rpc.feign.auth.AuthFeignClient;
import com.zone.rpc.req.auth.AccountCheckReq;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/1/15 5:47 下午
 * @Description: global filter 对所有的请求进行过滤
 */
@Slf4j
@Component
public class AuthorizationFilter implements GlobalFilter, Ordered {

  @Resource
  private AuthFeignClient authFeignClient;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

    // 0. 获取请求地址
    String path = exchange.getRequest().getURI().getPath();

    // 1. 对白名单中的请求直接转发
    if (GatewayConstants.whiteList.contains(path)) {
      return chain.filter(exchange);
    }

    // 2. 校验 token
    String token = exchange.getRequest().getHeaders().getFirst(GatewayConstants.AUTHORIZATION);
    if (StrUtil.isBlank(token)) {
      return unAuthorized(exchange.getResponse(), "请求未携带token");
    }

    try {
      // 校验 JWT
      LoginUser loginUser = JWTUtil.verifyToken(token);
      if (loginUser == null) {
        return unAuthorized(exchange.getResponse(), "token未通过验证");
      }

      // 对接口进行鉴权
      AccountCheckReq checkReq = new AccountCheckReq()
          .setAccountId(loginUser.getAccountId())
          .setName(loginUser.getAccountName())
          .setUrl(path);
      ResponseData<AccountCheckDTO> checkResponse = authFeignClient.check(checkReq);
      if (checkResponse == null || !checkResponse.getSuccess() || checkResponse.getData() == null) {
        log.warn("鉴权无权限, checkReq=[{}], response=[{}]", checkReq, checkResponse);
        return unAuthorized(exchange.getResponse(), "token未通过验证");
      }

      AccountCheckDTO checkDTO = checkResponse.getData();

      // 重写 request，将 user 的信息放入 HttpHeader 中
      ServerHttpRequest request = exchange.getRequest().mutate()
          .header(GatewayConstants.ACCOUNT_NAME, URLEncoder.encode(String.valueOf(checkDTO.getAccountId()), GatewayConstants.UTF_8))
          .header(GatewayConstants.ACCOUNT_ID, URLEncoder.encode(String.valueOf(checkDTO.getAccountId()), GatewayConstants.UTF_8))
          .header(GatewayConstants.ROLE_ID_LIST, URLEncoder.encode(JSONUtil.toJsonStr(checkDTO.getRoleIdList()), GatewayConstants.UTF_8))
          .header(GatewayConstants.PHONE, URLEncoder.encode(checkDTO.getPhone(), GatewayConstants.UTF_8))
          .header(GatewayConstants.ACCOUNT_TYPE, URLEncoder.encode(String.valueOf(checkDTO.getAccountType()), GatewayConstants.UTF_8))
          .build();

      return chain.filter(exchange.mutate().request(request).build());
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return chain.filter(exchange);
  }

  private Mono<Void> unAuthorized(ServerHttpResponse response, String msg) {
    response.setStatusCode(HttpStatus.UNAUTHORIZED);
    response.getHeaders().add(HttpHeaders.CONTENT_TYPE, GatewayConstants.JSON_CHARSET_UTF_8);
    ResponseData<String> data = ResponseData.error(HttpStatus.UNAUTHORIZED.value(), msg);
    DataBuffer dataBuffer = response.bufferFactory().wrap(JSONUtil.toJsonStr(data).getBytes());
    return response.writeWith(Mono.just(dataBuffer));
  }

  @Override
  public int getOrder() {
    // 过滤器的优先级
    // HIGHEST_PRECEDENCE: 拿到的 path 是 /sigma-auth/user/login
    // LOWEST_PRECEDENCE: 拿到的 path 是 /user/login
    // 这说明 gateway 有自带的过滤器将 url 中的服务名给剔除了
    // 同时，gateway 会根据服务名进行转发
    // 这里选择最高的优先级，即获取带上服务名的url
    return Ordered.HIGHEST_PRECEDENCE;
  }
}
