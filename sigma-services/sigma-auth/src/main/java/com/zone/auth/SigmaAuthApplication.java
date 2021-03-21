package com.zone.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/1/17 6:56 下午
 * @Description:
 */
@EnableFeignClients
@MapperScan(basePackages = "com.zone.*.infrastructure.db.mapper")
@SpringBootApplication
public class SigmaAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigmaAuthApplication.class, args);
    }
}
