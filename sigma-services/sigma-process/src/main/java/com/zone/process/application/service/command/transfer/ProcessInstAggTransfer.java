package com.zone.process.application.service.command.transfer;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.zone.process.application.service.command.cmd.InstStartCommand;
import com.zone.process.domain.agg.ProcessInstAgg;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 4:11 下午
 * @Description:
 */
public class ProcessInstAggTransfer {

    public static ProcessInstAgg getProcessInstAgg(InstStartCommand startCommand) {
        ProcessInstAgg processInstAgg = new ProcessInstAgg();
        BeanUtil.copyProperties(startCommand, processInstAgg, "dueTime");
        if (startCommand.getDueTime() != null) {
            processInstAgg.setDueTime(LocalDateTimeUtil.of(startCommand.getDueTime()));
        }
        return processInstAgg;
    }
}
