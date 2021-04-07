package com.zone.process.domain.repository;

import com.zone.process.domain.agg.FormStructureAgg;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:06 下午
 * @Description:
 */
public interface FormStructureAggRepository {

    /**
     * 获取最新版本的表单
     */
    FormStructureAgg queryByKey(String formKey);

    /**
     * 保存表单
     */
    void save(FormStructureAgg newFormAgg, FormStructureAgg oldFormAgg);
}
