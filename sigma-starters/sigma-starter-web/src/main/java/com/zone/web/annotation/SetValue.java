package com.zone.web.annotation;

import java.lang.annotation.*;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/18 11:27 上午
 * @Description: 只作用在字段上，用于设值
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SetValue {

    /**
     * 请求的接口地址
     */
    String url() default "";

    /**
     * get or post
     */
    String type() default "get";

    /**
     * 请求的参数名
     */
    String param() default "userId";

    /**
     * 请求的参数值在当前对象中的字段名
     */
    String field();

    /**
     * 响应的类
     */
    Class resClass() default String.class;

    /**
     * 响应的字段名，为空则直接返回响应体
     */
    String resField() default "";
}
