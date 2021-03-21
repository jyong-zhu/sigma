package com.zone.feign.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

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
            if (requestAttributes != null) {
                HttpServletRequest servletRequest = requestAttributes.getRequest();
                // 塞入当前请求的上下文的信息
                Enumeration<String> headerNames = servletRequest.getHeaderNames();
                if (headerNames != null) {
                    String headerName;
                    while (headerNames.hasMoreElements()) {
                        headerName = headerNames.nextElement();
                        String value = servletRequest.getHeader(headerName);
                        if (headerName.equalsIgnoreCase(HttpHeaders.CONTENT_TYPE)) {
                            continue;
                        }
                        requestTemplate.header(headerName, value);
                    }
                }
            }
        };
    }
}
