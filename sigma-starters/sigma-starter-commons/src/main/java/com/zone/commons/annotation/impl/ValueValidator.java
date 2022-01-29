package com.zone.commons.annotation.impl;

import com.zone.commons.annotation.ValueValid;
import java.lang.reflect.Method;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/2 8:17 上午
 * @Description:
 */
@Slf4j
public class ValueValidator implements ConstraintValidator<ValueValid, Object> {

  private Class checkClass;

  private String methodName;

  private boolean notNull;

  @Override
  public void initialize(ValueValid valueValid) {
    this.checkClass = valueValid.checkClass();
    this.methodName = valueValid.methodName();
    this.notNull = valueValid.notNull();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    if (value == null) {
      return !notNull;
    }
    try {
      Method method = checkClass.getMethod(methodName, value.getClass());
      Object checkObject = checkClass.isEnum() ? checkClass.getEnumConstants()[0] : checkClass.newInstance();
      Object result = method.invoke(checkObject, value);
      return result != null;
    } catch (Exception e) {
      log.error("ValueValid解析失败: [{}]", e.getMessage(), e);
      return false;
    }
  }
}
