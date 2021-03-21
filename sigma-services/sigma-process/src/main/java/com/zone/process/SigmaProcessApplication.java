package com.zone.process;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/21 2:52 下午
 * @Description:
 */
@ComponentScan(basePackages = {"com.zone.*"})
@EnableFeignClients(basePackages = "com.zone.rpc.feign.*")
@MapperScan(basePackages = "com.zone.*.infrastructure.db.mapper")
@SpringBootApplication
public class SigmaProcessApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigmaProcessApplication.class, args);
    }
}
