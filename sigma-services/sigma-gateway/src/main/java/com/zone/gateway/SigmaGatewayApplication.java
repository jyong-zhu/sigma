package com.zone.gateway;

import com.zone.feign.annotation.SigmaFeignClient;
import com.zone.web.annotation.SigmaSpringBootApplication;
import org.springframework.boot.SpringApplication;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/1/15 4:55 下午
 * @Description:
 */
@SigmaFeignClient
@SigmaSpringBootApplication
public class SigmaGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(SigmaGatewayApplication.class, args);
  }
}
