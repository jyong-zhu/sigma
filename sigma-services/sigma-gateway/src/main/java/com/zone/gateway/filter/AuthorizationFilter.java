package com.zone.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zone.commons.consts.GatewayConstants;
import com.zone.commons.entity.LoginUser;
import com.zone.commons.entity.ResponseData;
import com.zone.commons.util.JWTUtil;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/1/15 5:47 下午
 * @Description: global filter 对所有的请求进行过滤
 */
@Component
public class AuthorizationFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 对白名单中的请求直接转发
        String path = exchange.getRequest().getURI().getPath();

        path = removeServiceName(path);

        if (GatewayConstants.whiteList.contains(path)) {
            return chain.filter(exchange);
        }

        // 校验 token
        String token = exchange.getRequest().getHeaders().getFirst(GatewayConstants.AUTHORIZATION);
        if (StrUtil.isBlank(token)) {
            return unAuthorized(exchange.getResponse(), "请求未携带token");
        }

        // 将 user 的信息放入 HttpHeader 中
        try {

            LoginUser loginUser = JWTUtil.verifyToken(token);
            if (loginUser == null) {
                return unAuthorized(exchange.getResponse(), "token未通过验证");
            }

            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header(GatewayConstants.ACCOUNT_NAME, URLEncoder.encode(loginUser.getAccountName(), "UTF-8"))
                    .header(GatewayConstants.USER_NAME, URLEncoder.encode(loginUser.getUserName(), "UTF-8"))
                    .header(GatewayConstants.USER_ID, URLEncoder.encode(String.valueOf(loginUser.getUserId()), "UTF-8"))
                    .header(GatewayConstants.ROLE_ID, URLEncoder.encode(String.valueOf(loginUser.getRoleId()), "UTF-8"))
                    .build();
            return chain.filter(exchange.mutate().request(request).build());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return chain.filter(exchange);
    }

    private static String removeServiceName(String path) {
        if (StrUtil.isBlank(path)) {
            return "";
        }
        return path.substring(path.indexOf("/", 1));
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
