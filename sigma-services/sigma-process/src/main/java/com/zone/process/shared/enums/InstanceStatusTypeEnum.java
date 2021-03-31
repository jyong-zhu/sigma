package com.zone.process.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/11 11:11 上午
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum InstanceStatusTypeEnum {
    ACTIVE("active", "进行中"),
    FINISHED("finished", "已结束"),
    ;

    private String code;
    private String desc;

    public static InstanceStatusTypeEnum getByCode(String code) {
        for (InstanceStatusTypeEnum tmp : InstanceStatusTypeEnum.values()) {
            if (tmp.getCode().equals(code)) {
                return tmp;
            }
        }
        return null;
    }
}
