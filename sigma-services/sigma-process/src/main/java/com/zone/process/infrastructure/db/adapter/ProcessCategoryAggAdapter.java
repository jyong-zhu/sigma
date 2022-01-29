package com.zone.process.infrastructure.db.adapter;

import com.zone.process.domain.agg.ProcessCategoryAgg;
import com.zone.process.infrastructure.db.dataobject.ProcessCategoryDO;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/29 3:31 下午
 * @Description:
 */
public class ProcessCategoryAggAdapter {

  public static ProcessCategoryDO getProcessCategoryDO(ProcessCategoryAgg categoryAgg) {
    if (categoryAgg == null) {
      return null;
    }
    ProcessCategoryDO processCategoryDO = new ProcessCategoryDO();
    processCategoryDO.setId(categoryAgg.getId());
    processCategoryDO.setName(categoryAgg.getName());
    processCategoryDO.setIconUrl(categoryAgg.getIconUrl());
    processCategoryDO.setVersion(categoryAgg.getVersion());
    processCategoryDO.setCreateTime(categoryAgg.getCreateTime());
    processCategoryDO.setCreateBy(categoryAgg.getCreateBy());
    processCategoryDO.setCreateName(categoryAgg.getCreateName());
    return processCategoryDO;
  }

  public static ProcessCategoryAgg getProcessCategoryAgg(ProcessCategoryDO categoryDO) {
    if (categoryDO == null) {
      return null;
    }
    ProcessCategoryAgg processCategoryAgg = new ProcessCategoryAgg();
    processCategoryAgg.setId(categoryDO.getId());
    processCategoryAgg.setName(categoryDO.getName());
    processCategoryAgg.setIconUrl(categoryDO.getIconUrl());
    processCategoryAgg.setVersion(categoryDO.getVersion());
    processCategoryAgg.setCreateTime(categoryDO.getCreateTime());
    processCategoryAgg.setCreateBy(categoryDO.getCreateBy());
    processCategoryAgg.setCreateName(categoryDO.getCreateName());
    return processCategoryAgg;
  }
}
