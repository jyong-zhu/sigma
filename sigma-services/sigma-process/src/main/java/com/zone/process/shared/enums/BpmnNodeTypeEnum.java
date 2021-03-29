package com.zone.process.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/9 10:34 上午
 * @Description: 以下是本系统支持的需要额外扩展属性的bpmn中的节点类型
 */
@Getter
@AllArgsConstructor
public enum BpmnNodeTypeEnum {

    START_EVENT("bpmn:startEvent", "开始事件"),
    USER_TASK("bpmn:userTask", "用户任务"),
    END_EVENT("bpmn:endEvent", "结束事件"),
    EXCLUSIVE_GATEWAY("bpmn:exclusiveGateway", "排他网关"),
    PARALLEL_GATEWAY("bpmn:parallelGateway", "并行网关"),
    ;

    private String code;
    private String desc;

    public static BpmnNodeTypeEnum getByCode(String code) {
        for (BpmnNodeTypeEnum tmp : BpmnNodeTypeEnum.values()) {
            if (tmp.getCode().equals(code)) {
                return tmp;
            }
        }
        return null;
    }
}
