package com.zone.process.application.service.command.transfer;

import cn.hutool.core.bean.BeanUtil;
import com.zone.process.application.service.command.cmd.CategoryCommand;
import com.zone.process.domain.agg.ProcessCategoryAgg;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 4:12 下午
 * @Description:
 */
public class ProcessCategoryAggTransfer {

    public static ProcessCategoryAgg getProcessCategoryAgg(CategoryCommand categoryCommand) {
        return BeanUtil.copyProperties(categoryCommand, ProcessCategoryAgg.class);
    }
}
