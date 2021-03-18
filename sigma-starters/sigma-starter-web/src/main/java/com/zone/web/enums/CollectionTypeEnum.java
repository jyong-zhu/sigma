package com.zone.web.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/18 10:10 下午
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum CollectionTypeEnum {

    LIST("List"),
    SET("Set"),
    MAP("Map"),
    PAGE("com.zone.commons.entity.Page"),
    ;

    private String name;

    public static CollectionTypeEnum getByName(String name) {
        for (CollectionTypeEnum type : CollectionTypeEnum.values()) {
            if (StrUtil.isNotBlank(name) && name.contains(type.getName())) {
                return type;
            }
        }
        return null;
    }
}
