package com.zone.process.application.service.command.transfer;

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
    if (startCommand == null) {
      return null;
    }
    ProcessInstAgg processInstAgg = new ProcessInstAgg();
    processInstAgg.setProcDefKey(startCommand.getDefKey());
    processInstAgg.setName(startCommand.getName());
    processInstAgg.setDueTime(startCommand.getDueTime() != null ? LocalDateTimeUtil.of(startCommand.getDueTime()) : null);
    processInstAgg.setComment(startCommand.getComment());
    processInstAgg.setDescription(startCommand.getDescription());
    return processInstAgg;
  }

}
