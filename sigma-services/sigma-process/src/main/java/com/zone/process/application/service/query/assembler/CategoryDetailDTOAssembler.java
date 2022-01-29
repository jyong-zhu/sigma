package com.zone.process.application.service.query.assembler;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.zone.process.application.service.query.dto.CategoryDetailDTO;
import com.zone.process.infrastructure.db.dataobject.ProcessCategoryDO;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/7 10:45 上午
 * @Description:
 */
public class CategoryDetailDTOAssembler {


  public static CategoryDetailDTO getCategoryDetailDTO(ProcessCategoryDO categoryDO) {
    if (categoryDO == null) {
      return null;
    }
    CategoryDetailDTO categoryDetailDTO = new CategoryDetailDTO();
    categoryDetailDTO.setId(categoryDO.getId());
    categoryDetailDTO.setName(categoryDO.getName());
    categoryDetailDTO.setIconUrl(categoryDO.getIconUrl());
    categoryDetailDTO.setCreateTime(LocalDateTimeUtil.toEpochMilli(categoryDO.getCreateTime()));
    categoryDetailDTO.setCreateBy(categoryDO.getCreateBy());
    categoryDetailDTO.setCreateName(categoryDO.getCreateName());
    categoryDetailDTO.setUpdateTime(LocalDateTimeUtil.toEpochMilli(categoryDO.getUpdateTime()));
    categoryDetailDTO.setUpdateBy(categoryDO.getUpdateBy());
    categoryDetailDTO.setUpdateName(categoryDO.getUpdateName());
    return categoryDetailDTO;
  }
}
