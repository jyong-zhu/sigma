package com.zone.feign.config;

import com.zone.commons.consts.GatewayConstants;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/21 3:09 下午
 * @Description: feign 拦截器，在发起 feign 调用的时候塞入http请求的header
 */
@Configuration
public class FeignInterceptorConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // 获取当前请求
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest servletRequest = requestAttributes.getRequest();

            // 塞入当前请求的上下文的信息
            requestTemplate.header(GatewayConstants.AUTHORIZATION, servletRequest.getHeader(GatewayConstants.AUTHORIZATION));
            requestTemplate.header(GatewayConstants.ACCOUNT_NAME, servletRequest.getHeader(GatewayConstants.ACCOUNT_NAME));
            requestTemplate.header(GatewayConstants.USER_NAME, servletRequest.getHeader(GatewayConstants.USER_NAME));
            requestTemplate.header(GatewayConstants.ROLE_ID, servletRequest.getHeader(GatewayConstants.ROLE_ID));
            requestTemplate.header(GatewayConstants.USER_ID, servletRequest.getHeader(GatewayConstants.USER_ID));
        };
    }
}
