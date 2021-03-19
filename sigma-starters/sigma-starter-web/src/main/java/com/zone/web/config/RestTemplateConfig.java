package com.zone.web.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/19 12:20 上午
 * @Description: RestTemplate需要通过RestTemplateBuilder进行注入
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced // 添加该注解，可以直接通过服务名找到对应的IP地址
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}