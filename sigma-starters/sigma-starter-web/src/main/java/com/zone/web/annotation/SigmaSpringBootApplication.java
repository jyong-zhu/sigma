package com.zone.web.annotation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/21 4:25 下午
 * @Description: 合并 spring boot 和 mybatis mapper 的相关注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@ComponentScan(basePackages = {"com.zone.*"})
@MapperScan(basePackages = "com.zone.*.infrastructure.db.mapper")
@SpringBootApplication
public @interface SigmaSpringBootApplication {
    String value() default "";
}
