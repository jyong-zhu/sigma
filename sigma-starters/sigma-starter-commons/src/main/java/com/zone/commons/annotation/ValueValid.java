package com.zone.commons.annotation;

import com.zone.commons.annotation.impl.ValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/2 8:11 上午
 * @Description:
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValueValidator.class)
public @interface ValueValid {

    String message();

    Class checkClass();

    String methodName() default "getByCode";

    boolean notNull() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
