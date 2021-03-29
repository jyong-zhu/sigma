package com.zone.process.domain.repository;

import com.zone.process.domain.agg.ProcessCategoryAgg;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:06 下午
 * @Description:
 */
public interface ProcessCategoryAggRepository {

    ProcessCategoryAgg queryById(Long categoryId);
}
