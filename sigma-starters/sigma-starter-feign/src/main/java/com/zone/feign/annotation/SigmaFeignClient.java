package com.zone.feign.annotation;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/21 4:33 下午
 * @Description: 合并 feign client 相关的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@EnableFeignClients(basePackages = "com.zone.rpc.feign.*")
public @interface SigmaFeignClient {
    @AliasFor(
            annotation = EnableFeignClients.class
    )
    String[] value() default {};
}
