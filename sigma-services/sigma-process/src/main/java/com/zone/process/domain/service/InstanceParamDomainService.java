package com.zone.process.domain.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.zone.process.domain.agg.ProcessDefAgg;
import com.zone.process.domain.valueobject.DefNodeVO;
import com.zone.process.shared.enums.VariableTypeEnum;
import com.zone.process.shared.type.VariableTypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 5:04 下午
 * @Description: 流程实例参数相关的领域服务
 */
@Slf4j
@Service
public class InstanceParamDomainService {

    /**
     * 生成传入上下文的参数
     * formDataMap 属于的是 ProcessInstAgg 这个聚合根
     * 所以这里涉及两个聚合根，故放到领域服务中
     */
    public Map<String, Object> generateParamMap(Map<Long, Map<String, String>> formDataMap, String curNodeId, ProcessDefAgg defAgg) {
        DefNodeVO nodeVO = defAgg.getNodeByNodeId(curNodeId);
        Preconditions.checkNotNull(nodeVO, "节点不存在");

        Map<String, Object> paramMap = Maps.newHashMap();
        nodeVO.getVariableVOList().forEach(variable -> {

            try {
                // 获取变量类型
                VariableTypeEnum typeEnum = VariableTypeEnum.getByCode(variable.getJavaType());
                VariableTypeHandler typeHandler = (VariableTypeHandler) typeEnum.getHandlerClass().newInstance();

                // 表单数据
                Map<String, String> formData = formDataMap.getOrDefault(variable.getFormId(), Maps.newHashMap());
                String value = formData.getOrDefault(variable.getFieldId(), variable.getDefaultValue());

                // 填入转换后的表单数据
                paramMap.put(variable.getVariableName(), typeHandler.transform(value));

            } catch (Exception e) {
                log.error("填充流程实例的变量值出错: [{}]", e.getMessage());
            }
        });

        return paramMap;
    }
}
