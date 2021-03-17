package com.zone.web.config;

import com.zone.web.interceptor.ContextInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/17 1:26 上午
 * @Description:
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public ContextInterceptor getContextInterceptor() {
        return new ContextInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求
        registry.addInterceptor(getContextInterceptor())
                .addPathPatterns("/**");
    }
}
