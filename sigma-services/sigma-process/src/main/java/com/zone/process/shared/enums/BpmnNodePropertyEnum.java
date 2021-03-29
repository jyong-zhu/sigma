package com.zone.process.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/9 10:39 上午
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum BpmnNodePropertyEnum {
    NODE_ID("id", "节点id"),
    NODE_NAME("name", "自定义的节点名称"),
    ;

    private String code;
    private String desc;

    public static BpmnNodePropertyEnum getByCode(String code) {
        for (BpmnNodePropertyEnum tmp : BpmnNodePropertyEnum.values()) {
            if (tmp.getCode().equals(code)) {
                return tmp;
            }
        }
        return null;
    }
}
