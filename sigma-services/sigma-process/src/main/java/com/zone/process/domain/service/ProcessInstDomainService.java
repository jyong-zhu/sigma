package com.zone.process.domain.service;

import com.google.common.collect.Maps;
import com.zone.commons.util.IdWorkerUtil;
import com.zone.process.domain.agg.ProcessDefAgg;
import com.zone.process.domain.agg.ProcessInstAgg;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 5:04 下午
 * @Description:
 */
@Service
public class ProcessInstDomainService {

    public static Long generateId() {
        return IdWorkerUtil.nextId();
    }

    /**
     * 生成传入上下文的参数，涉及 instAgg 与 defAgg 这两个聚合根
     */
    public Map<String, Object> generateParamMap(ProcessInstAgg instAgg, ProcessDefAgg defAgg) {
        return Maps.newHashMap();
    }
}
