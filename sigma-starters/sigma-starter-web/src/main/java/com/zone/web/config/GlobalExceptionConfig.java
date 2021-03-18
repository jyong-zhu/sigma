package com.zone.web.config;

import com.zone.web.interceptor.GlobalExceptionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/17 10:40 下午
 * @Description:
 */
@Configuration
public class GlobalExceptionConfig {

    @Bean
    public GlobalExceptionInterceptor getGlobalExceptionInterceptor() {
        return new GlobalExceptionInterceptor();
    }
}
