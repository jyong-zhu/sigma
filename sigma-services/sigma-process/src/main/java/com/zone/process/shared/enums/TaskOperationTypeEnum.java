package com.zone.process.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/8 3:59 下午
 * @Description: 任务维度的操作类型
 */
@Getter
@AllArgsConstructor
public enum TaskOperationTypeEnum {

    COMPLETE("complete", "完成一个任务，使流程流转到下一个节点"),
    UPDATE("update", "只更新流程的上下文参数，不流转流程"),
    STOP("stop", "中止一个流程实例"),
    INSERT("insert", "只插入操作记录，不做其余任何操作"),
    ;

    private String code;
    private String desc;

    public static TaskOperationTypeEnum getByCode(String code) {
        for (TaskOperationTypeEnum tmp : TaskOperationTypeEnum.values()) {
            if (tmp.getCode().equals(code)) {
                return tmp;
            }
        }
        return null;
    }

}
