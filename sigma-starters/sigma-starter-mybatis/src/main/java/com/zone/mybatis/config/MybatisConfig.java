package com.zone.mybatis.config;

import com.zone.mybatis.interceptor.SqlLogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/21 12:33 上午
 * @Description:
 */
@Configuration
public class MybatisConfig {

    @Bean
    public SqlLogInterceptor getSqlLogInterceptor() {
        return new SqlLogInterceptor();
    }

}
