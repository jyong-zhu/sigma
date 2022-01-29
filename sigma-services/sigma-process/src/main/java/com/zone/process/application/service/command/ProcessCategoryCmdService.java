package com.zone.process.application.service.command;

import com.google.common.base.Preconditions;
import com.zone.process.application.service.command.cmd.CategoryCommand;
import com.zone.process.application.service.command.transfer.ProcessCategoryAggTransfer;
import com.zone.process.domain.agg.ProcessCategoryAgg;
import com.zone.process.domain.repository.ProcessCategoryAggRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 10:44 上午
 * @Description:
 */
@Slf4j
@Service
public class ProcessCategoryCmdService {

  @Autowired
  private ProcessCategoryAggRepository categoryAggRepository;

  /**
   * 新增流程分类
   */
  @Transactional(rollbackFor = Exception.class)
  public Long save(CategoryCommand categoryCommand) {

    ProcessCategoryAgg categoryAgg = ProcessCategoryAggTransfer.getProcessCategoryAgg(categoryCommand);

    Long id = categoryAggRepository.save(categoryAgg);
    Preconditions.checkNotNull(id, "新增流程分类失败");

    return id;
  }

  /**
   * 编辑流程分类
   */
  @Transactional(rollbackFor = Exception.class)
  public Long edit(CategoryCommand categoryCommand) {

    ProcessCategoryAgg categoryAgg = categoryAggRepository.queryById(categoryCommand.getId());
    Preconditions.checkNotNull(categoryAgg, "流程分类不存在");

    categoryAgg.edit(categoryCommand.getName(), categoryCommand.getIconUrl());

    Long id = categoryAggRepository.update(categoryAgg);
    Preconditions.checkNotNull(id, "流程分类更新失败");

    return id;
  }
}
