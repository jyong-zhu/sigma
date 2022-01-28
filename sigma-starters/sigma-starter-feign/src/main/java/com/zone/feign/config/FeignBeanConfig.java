package com.zone.feign.config;

import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/26 4:23 下午
 * @Description: https://github.com/spring-cloud/spring-cloud-openfeign/issues/235
 */
@Configuration
public class FeignBeanConfig {


  private ObjectFactory<HttpMessageConverters> messageConverters = HttpMessageConverters::new;

  @Bean
  Encoder feignEncoder() {
    return new SpringEncoder(messageConverters);
  }

  @Bean
  Decoder feignDecoder() {
    return new SpringDecoder(messageConverters);
  }

}
