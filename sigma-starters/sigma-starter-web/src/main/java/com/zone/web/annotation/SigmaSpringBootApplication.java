package com.zone.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/21 4:25 下午
 * @Description: 合并 spring boot 和 mybatis mapper 的相关注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@ComponentScan(basePackages = {"com.zone.*"})
@MapperScan(basePackages = "com.zone.*.infrastructure")
@EnableOpenApi
@SpringBootApplication
public @interface SigmaSpringBootApplication {

  String value() default "";
}
