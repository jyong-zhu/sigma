package com.zone.process.application.service.command;

import com.google.common.base.Preconditions;
import com.zone.process.application.service.command.cmd.CategoryCommand;
import com.zone.process.application.service.command.transfer.ProcessCategoryAggTransfer;
import com.zone.process.domain.agg.ProcessCategoryAgg;
import com.zone.process.domain.repository.ProcessCategoryAggRepository;
import com.zone.process.domain.service.AggIdentityDomainService;
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

    @Autowired
    private AggIdentityDomainService identityDomainService;

    /**
     * 新增流程分类
     */
    @Transactional(rollbackFor = Exception.class)
    public Long add(CategoryCommand categoryCommand) {

        ProcessCategoryAgg categoryAgg = ProcessCategoryAggTransfer.getProcessCategoryAgg(categoryCommand);

        categoryAgg.init(identityDomainService.generateCategoryAggId());

        categoryAggRepository.save(categoryAgg);

        return categoryAgg.getId();
    }

    /**
     * 编辑流程分类
     */
    @Transactional(rollbackFor = Exception.class)
    public Long edit(CategoryCommand categoryCommand) {

        ProcessCategoryAgg categoryAgg = categoryAggRepository.queryById(categoryCommand.getId());
        Preconditions.checkNotNull(categoryAgg, "流程分类不存在");

        categoryAgg.edit(categoryCommand.getName(), categoryCommand.getIconUrl());

        categoryAggRepository.update(categoryAgg);

        return categoryAgg.getId();
    }
}
