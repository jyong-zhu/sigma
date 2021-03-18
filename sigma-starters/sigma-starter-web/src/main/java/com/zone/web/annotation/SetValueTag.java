package com.zone.web.annotation;

import java.lang.annotation.*;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/18 11:27 上午
 * @Description: 用于标记，作用在方法和类上
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SetValueTag {

    String value() default "";
}
