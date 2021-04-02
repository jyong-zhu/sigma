package com.zone.process.shared.type;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/2 1:23 下午
 * @Description:
 */
public interface VariableTypeHandler<T> {

    /**
     * 将从前端接收到的字符串的变量值转为对应类型的变量值
     */
    T transform(String value);
}
