package com.zone.web.interceptor;

import com.zone.commons.consts.GatewayConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/19 1:54 下午
 * @Description: 对出去的请求进行拦截，即在 RestTemplate 的请求之前进行拦截，填入上下文的信息
 */
@Component
public class RestClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {


    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {

        // 获取当前请求
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest servletRequest = requestAttributes.getRequest();

        // 获取RestTemplate要发出去的请求头
        HttpHeaders headers = httpRequest.getHeaders();

        // 塞入当前请求的上下文的信息
        headers.add(GatewayConstants.AUTHORIZATION, servletRequest.getHeader(GatewayConstants.AUTHORIZATION));
        headers.add(GatewayConstants.ACCOUNT_NAME, servletRequest.getHeader(GatewayConstants.ACCOUNT_NAME));
        headers.add(GatewayConstants.USER_NAME, servletRequest.getHeader(GatewayConstants.USER_NAME));
        headers.add(GatewayConstants.ROLE_ID, servletRequest.getHeader(GatewayConstants.ROLE_ID));
        headers.add(GatewayConstants.USER_ID, servletRequest.getHeader(GatewayConstants.USER_ID));

        // 执行RestTemplate请求
        return execution.execute(httpRequest, bytes);
    }
}
