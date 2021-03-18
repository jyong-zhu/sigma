package com.zone.web.config;

import com.zone.web.interceptor.ResponseBodyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/18 11:00 上午
 * @Description:
 */
@Configuration
public class ResponseBodyConfig {

    @Bean
    public ResponseBodyInterceptor getResponseBodyInterceptor() {
        return new ResponseBodyInterceptor();
    }
}
