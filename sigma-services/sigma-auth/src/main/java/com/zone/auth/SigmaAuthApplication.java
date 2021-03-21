package com.zone.auth;

import com.zone.feign.annotation.SigmaFeignClient;
import com.zone.web.annotation.SigmaSpringBootApplication;
import org.springframework.boot.SpringApplication;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/1/17 6:56 下午
 * @Description:
 */
@SigmaFeignClient
@SigmaSpringBootApplication
public class SigmaAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigmaAuthApplication.class, args);
    }
}
