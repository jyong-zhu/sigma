package com.zone.process.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/8 4:08 下午
 * @Description: 流程实例相关的操作类型
 */
@Getter
@AllArgsConstructor
public enum InstanceOperationTypeEnum {

    START("start", "发起流程"),
    STOP("stop", "中止流程"),
    ;

    private String code;
    private String desc;

    public static InstanceOperationTypeEnum getByCode(String code) {
        for (InstanceOperationTypeEnum tmp : InstanceOperationTypeEnum.values()) {
            if (tmp.getCode().equals(code)) {
                return tmp;
            }
        }
        return null;
    }
}
