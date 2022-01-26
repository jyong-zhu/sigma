package com.zone.gateway;

import com.zone.feign.annotation.SigmaFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/1/15 4:55 下午
 * @Description:
 */
@SigmaFeignClient
@SpringBootApplication
public class SigmaGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(SigmaGatewayApplication.class, args);
  }
}
