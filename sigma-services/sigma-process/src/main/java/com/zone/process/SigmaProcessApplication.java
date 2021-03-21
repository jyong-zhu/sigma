package com.zone.process;

import com.zone.feign.annotation.SigmaFeignClient;
import com.zone.web.annotation.SigmaSpringBootApplication;
import org.springframework.boot.SpringApplication;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/21 2:52 下午
 * @Description:
 */
@SigmaFeignClient
@SigmaSpringBootApplication
public class SigmaProcessApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigmaProcessApplication.class, args);
    }
}
