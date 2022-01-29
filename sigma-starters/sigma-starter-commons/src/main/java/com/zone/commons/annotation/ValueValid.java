package com.zone.commons.annotation;

import com.zone.commons.annotation.impl.ValueValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

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
