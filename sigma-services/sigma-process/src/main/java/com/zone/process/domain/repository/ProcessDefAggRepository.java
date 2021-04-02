package com.zone.process.domain.repository;

import com.zone.process.domain.agg.ProcessDefAgg;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:05 下午
 * @Description:
 */
public interface ProcessDefAggRepository {

    void save(ProcessDefAgg processDefAgg);

    /**
     * repository 中的查询操作都是以聚合根的维度查出来的
     */
    ProcessDefAgg queryById(Long defId);

    /**
     * 根据key获取流程定义（最新的版本）
     */
    ProcessDefAgg queryByKey(String defKey);
}
