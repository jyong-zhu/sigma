package com.zone.process.shared.enums;

import com.zone.process.shared.type.BooleanTypeHandler;
import com.zone.process.shared.type.ListTypeHandler;
import com.zone.process.shared.type.LongTypeHandler;
import com.zone.process.shared.type.StringTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/2 1:35 下午
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum VariableTypeEnum {

    STRING("String", StringTypeHandler.class),
    BOOLEAN("Boolean", BooleanTypeHandler.class),
    List("List", ListTypeHandler.class),
    Long("Long", LongTypeHandler.class),
    ;

    private String code;
    private Class handlerClass;

    public static VariableTypeEnum getByCode(String code) {
        for (VariableTypeEnum tmp : VariableTypeEnum.values()) {
            if (tmp.getCode().equals(code)) {
                return tmp;
            }
        }
        return null;
    }
}
