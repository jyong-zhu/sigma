package com.zone.process.application.service.query.assembler;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.zone.process.application.service.query.dto.CategoryDetailDTO;
import com.zone.process.infrastructure.db.dataobject.ProcessCategoryDO;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/7 10:45 上午
 * @Description:
 */
public class CategoryDetailDTOAssembler {


    public static CategoryDetailDTO getCategoryDetailDTO(ProcessCategoryDO tmp) {
        CategoryDetailDTO detailDTO = BeanUtil.copyProperties(tmp, CategoryDetailDTO.class, "createTime", "updateTime");
        detailDTO.setCreateTime(LocalDateTimeUtil.toEpochMilli(tmp.getCreateTime()));
        detailDTO.setUpdateTime(LocalDateTimeUtil.toEpochMilli(tmp.getUpdateTime()));
        return detailDTO;
    }
}
