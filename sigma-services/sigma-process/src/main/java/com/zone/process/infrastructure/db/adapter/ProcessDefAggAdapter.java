package com.zone.process.infrastructure.db.adapter;

import cn.hutool.core.bean.BeanUtil;
import com.zone.process.domain.valueobject.DefNodePropertyVO;
import com.zone.process.domain.valueobject.DefNodeVO;
import com.zone.process.domain.valueobject.DefNodeVariableVO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodePropertyDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeVariableDO;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:34 下午
 * @Description:
 */
public class ProcessDefAggAdapter {

    public static ProcessDefNodeDO getProcessDefNodeDO(DefNodeVO nodeVO, Long defId, Long id) {
        ProcessDefNodeDO nodeDO = new ProcessDefNodeDO();
        BeanUtil.copyProperties(nodeVO, nodeDO);
        nodeDO.setId(id);
        nodeDO.setDefId(defId);
        return nodeDO;
    }

    public static ProcessDefNodePropertyDO getProcessDefNodePropertyDO(DefNodePropertyVO propertyVO, Long nodeId, String bpmnNodeId, Long id) {
        ProcessDefNodePropertyDO propertyDO = new ProcessDefNodePropertyDO();
        BeanUtil.copyProperties(propertyVO, propertyDO);
        propertyDO.setId(id);
        propertyDO.setNodeId(nodeId);
        propertyDO.setBpmnNodeId(bpmnNodeId);
        return propertyDO;
    }

    public static ProcessDefNodeVariableDO getProcessDefNodeVariableDO(DefNodeVariableVO variableVO, Long nodeId, String bpmnNodeId, Long id) {
        ProcessDefNodeVariableDO variableDO = new ProcessDefNodeVariableDO();
        BeanUtil.copyProperties(variableVO, variableDO);
        variableDO.setId(id);
        variableDO.setNodeId(nodeId);
        variableDO.setBpmnNodeId(bpmnNodeId);
        return variableDO;
    }
}
