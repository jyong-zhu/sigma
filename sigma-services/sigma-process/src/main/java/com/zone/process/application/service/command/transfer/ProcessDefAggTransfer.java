package com.zone.process.application.service.command.transfer;

import cn.hutool.core.bean.BeanUtil;
import com.zone.process.application.service.command.cmd.DefDeployCommand;
import com.zone.process.domain.agg.ProcessDefAgg;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 4:11 下午
 * @Description:
 * domain 中不应该出现 command
 * transfer 用于将 command 转化为 domain
 */
public class ProcessDefAggTransfer {

    public static ProcessDefAgg getProcessDefAgg(DefDeployCommand deployCommand) {
        ProcessDefAgg processDefAgg = new ProcessDefAgg();
        BeanUtil.copyProperties(deployCommand, processDefAgg);
        return processDefAgg;
    }
}
