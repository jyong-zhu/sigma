package com.zone.feign.config;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/26 4:23 下午
 * @Description:
 */
@Configuration
public class FeignBeanConfig {

  @Bean
  public Decoder decoder() {
    return new JacksonDecoder();
  }

  @Bean
  public Encoder encoder() {
    return new JacksonEncoder();
  }

}
