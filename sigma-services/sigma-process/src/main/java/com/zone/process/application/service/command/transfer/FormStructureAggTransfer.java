package com.zone.process.application.service.command.transfer;

import com.zone.process.application.service.command.cmd.FormCommand;
import com.zone.process.domain.agg.FormStructureAgg;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 4:12 下午
 * @Description:
 */
public class FormStructureAggTransfer {

  public static FormStructureAgg getFormStructureAgg(FormCommand categoryCommand) {
    FormStructureAgg formStructureAgg = new FormStructureAgg();
    formStructureAgg.setName(categoryCommand.getName());
    formStructureAgg.setFormKey(categoryCommand.getFormKey());
    formStructureAgg.setFormJson(categoryCommand.getFormJson());
    formStructureAgg.setDescription(categoryCommand.getDescription());
    return formStructureAgg;
  }
}
