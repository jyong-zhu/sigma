package com.zone.process.infrastructure.db.repository.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zone.process.domain.agg.ProcessCategoryAgg;
import com.zone.process.domain.repository.ProcessCategoryAggRepository;
import com.zone.process.infrastructure.db.dataobject.ProcessCategoryDO;
import com.zone.process.infrastructure.db.mapper.ProcessCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:08 下午
 * @Description:
 */
@Service
public class ProcessCategoryAggRepositoryImpl implements ProcessCategoryAggRepository {

    @Autowired
    private ProcessCategoryMapper categoryMapper;

    @Override
    public ProcessCategoryAgg queryById(Long categoryId) {
        ProcessCategoryDO categoryDO = categoryMapper.selectById(categoryId);
        return categoryDO == null ? null : BeanUtil.copyProperties(categoryDO, ProcessCategoryAgg.class);
    }

    @Override
    public void save(ProcessCategoryAgg categoryAgg) {
        ProcessCategoryDO categoryDO = BeanUtil.copyProperties(categoryAgg, ProcessCategoryDO.class);
        categoryMapper.insert(categoryDO);
    }

    @Override
    public void update(ProcessCategoryAgg categoryAgg) {
        ProcessCategoryDO categoryDO = BeanUtil.copyProperties(categoryAgg, ProcessCategoryDO.class);
        categoryMapper.updateById(categoryDO);
    }
}
