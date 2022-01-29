package com.zone.auth.application.service.command.transfer;

import com.zone.auth.application.service.command.cmd.ResourceCreateCommand;
import com.zone.auth.domain.agg.ResourceAgg;
import com.zone.auth.shared.enums.ResourceTypeEnum;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/29 11:07 下午
 * @Description:
 */
public class ResourceAggTransfer {

  public static ResourceAgg getResourceAgg(ResourceCreateCommand createCommand) {
    if (createCommand == null) {
      return null;
    }
    ResourceAgg resourceAgg = new ResourceAgg();
    resourceAgg.setType(ResourceTypeEnum.getByCode(createCommand.getType()));
    resourceAgg.setKey(createCommand.getKey());
    resourceAgg.setName(createCommand.getName());
    resourceAgg.setResourceUrl(createCommand.getResourceUrl());
    resourceAgg.setIconUrl(createCommand.getIconUrl());
    resourceAgg.setParentId(createCommand.getParentId());
    resourceAgg.setVisible(createCommand.getVisible());
    resourceAgg.setSortNum(createCommand.getSortNum());
    resourceAgg.setStatus(true);
    return resourceAgg;
  }
}
