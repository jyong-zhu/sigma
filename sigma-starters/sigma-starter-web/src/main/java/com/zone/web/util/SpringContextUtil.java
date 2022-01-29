package com.zone.web.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/29 11:16 上午
 * @Description: spring context 工具类
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    SpringContextUtil.applicationContext = applicationContext;
  }

  /**
   * 获取ApplicationContext
   */
  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  /**
   * 获取对象
   */
  public static Object getBean(String beanName) throws BeansException {
    return applicationContext.getBean(beanName);
  }

  /**
   * 获取对象
   */
  public static <T> T getBean(Class<T> clazz) throws BeansException {
    return applicationContext.getBean(clazz);
  }

  /**
   * 判断对象是否存在
   */
  public static boolean containsBean(String beanName) throws BeansException {
    return applicationContext.containsBean(beanName);
  }
}
