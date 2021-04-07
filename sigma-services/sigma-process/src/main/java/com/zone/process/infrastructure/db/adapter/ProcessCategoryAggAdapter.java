package com.zone.process.infrastructure.db.adapter;

import cn.hutool.core.bean.BeanUtil;
import com.zone.process.domain.agg.ProcessCategoryAgg;
import com.zone.process.infrastructure.db.dataobject.ProcessCategoryDO;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:37 下午
 * @Description:
 */
public class ProcessCategoryAggAdapter {


    public static ProcessCategoryAgg getCategoryAgg(ProcessCategoryDO categoryDO) {
        return BeanUtil.copyProperties(categoryDO, ProcessCategoryAgg.class);
    }

    public static ProcessCategoryDO getCategoryDO(ProcessCategoryAgg categoryAgg) {
        return BeanUtil.copyProperties(categoryAgg, ProcessCategoryDO.class);
    }
}
