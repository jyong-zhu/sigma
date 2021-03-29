package com.zone.process.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/8 3:50 下午
 * @Description: 节点上的人员类型，这些值都填在 candidateGroups 中
 */
@Getter
@AllArgsConstructor
public enum NodePeopleTypeEnum {
    ID("ID", "当前节点的人员为确定的用户id或者角色id"),
    PARAM("PARAM", "当前节点的人员为参数类型，或签，该参数由流程流转过程中填入上下文"),
    PAR_MULTI_INSTANCE("PAR_MULTI_INSTANCE", "当前节点的人员为参数，并行会签"),
    SEQ_MULTI_INSTANCE("SEQ_MULTI_INSTANCE", "当前节点的人员为参数，串行会签"),
    ;

    private String code;
    private String desc;

    public static NodePeopleTypeEnum getByCode(String code) {
        for (NodePeopleTypeEnum tmp : NodePeopleTypeEnum.values()) {
            if (tmp.getCode().equals(code)) {
                return tmp;
            }
        }
        return null;
    }

}
