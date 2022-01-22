package com.zone.web.interceptor;

import com.zone.commons.consts.GatewayConstants;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
        headers.add(GatewayConstants.ACCOUNT_ID, servletRequest.getHeader(GatewayConstants.ACCOUNT_ID));
        headers.add(GatewayConstants.ACCOUNT_TYPE, servletRequest.getHeader(GatewayConstants.ACCOUNT_TYPE));
        headers.add(GatewayConstants.ROLE_ID_LIST, servletRequest.getHeader(GatewayConstants.ROLE_ID_LIST));
        headers.add(GatewayConstants.PHONE, servletRequest.getHeader(GatewayConstants.PHONE));

        // 执行RestTemplate请求
        return execution.execute(httpRequest, bytes);
    }
}
