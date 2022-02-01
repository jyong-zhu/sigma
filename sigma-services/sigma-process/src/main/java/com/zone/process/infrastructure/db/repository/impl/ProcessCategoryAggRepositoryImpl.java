package com.zone.process.infrastructure.db.repository.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zone.process.domain.agg.ProcessCategoryAgg;
import com.zone.process.domain.repository.ProcessCategoryAggRepository;
import com.zone.process.infrastructure.db.adapter.ProcessCategoryAggAdapter;
import com.zone.process.infrastructure.db.dataobject.ProcessCategoryDO;
import com.zone.process.infrastructure.db.mapper.ProcessCategoryMapper;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:08 下午
 * @Description:
 */
@Slf4j
@Repository
public class ProcessCategoryAggRepositoryImpl implements ProcessCategoryAggRepository {

  @Resource
  private ProcessCategoryMapper categoryMapper;

  @Override
  public ProcessCategoryAgg queryById(Long categoryId) {
    ProcessCategoryDO categoryDO = categoryMapper.selectById(categoryId);
    return ProcessCategoryAggAdapter.getProcessCategoryAgg(categoryDO);
  }

  @Override
  public Long save(ProcessCategoryAgg categoryAgg) {
    ProcessCategoryDO categoryDO = ProcessCategoryAggAdapter.getProcessCategoryDO(categoryAgg);

    if (categoryDO == null) {
      return null;
    }
    categoryMapper.insert(categoryDO);

    return categoryDO.getId();
  }

  @Override
  public Long update(ProcessCategoryAgg categoryAgg) {
    ProcessCategoryDO categoryDO = BeanUtil.copyProperties(categoryAgg, ProcessCategoryDO.class);
    int num = categoryMapper.updateById(categoryDO);
    if (num == 0) {
      log.warn("【乐观锁】更新流程分类失败，categoryAgg=[{}]", categoryAgg);
      return null;
    }
    return categoryDO.getId();
  }
}
