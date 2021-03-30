package com.zone.process.domain.service;

import com.google.common.collect.Maps;
import com.zone.process.domain.agg.ProcessDefAgg;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 5:04 下午
 * @Description: 流程实例参数相关的领域服务
 */
@Service
public class InstanceParamDomainService {

    /**
     * 生成传入上下文的参数
     * formDataMap 属于的是 ProcessInstAgg 这个聚合根
     * 所以这里涉及两个聚合根，故放到领域服务中
     */
    public Map<String, Object> generateParamMap(Map<Long, Map<String, Object>> formDataMap, String curNodeId, ProcessDefAgg defAgg) {
        return Maps.newHashMap();
    }
}
