package com.zone.auth.infrastructure.db.adapter;

import com.zone.auth.domain.agg.ResourceAgg;
import com.zone.auth.infrastructure.db.dataobject.AuthResourceDO;
import com.zone.auth.shared.enums.ResourceTypeEnum;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/21 5:09 下午
 * @Description:
 */
public class ResourceAggAdapter {

  public static AuthResourceDO getResourceDO(ResourceAgg resourceAgg) {
    if (resourceAgg == null) {
      return null;
    }
    AuthResourceDO authResourceDO = new AuthResourceDO();
    authResourceDO.setId(resourceAgg.getId());
    authResourceDO.setType(resourceAgg.getType().getCode());
    authResourceDO.setKey(resourceAgg.getKey());
    authResourceDO.setName(resourceAgg.getName());
    authResourceDO.setResourceUrl(resourceAgg.getResourceUrl());
    authResourceDO.setIconUrl(resourceAgg.getIconUrl());
    authResourceDO.setParentId(resourceAgg.getParentId());
    authResourceDO.setVisible(resourceAgg.getVisible());
    authResourceDO.setStatus(resourceAgg.getStatus());
    authResourceDO.setSortNum(resourceAgg.getSortNum());
    authResourceDO.setVersion(resourceAgg.getVersion());
    authResourceDO.setCreateBy(resourceAgg.getCreateBy());
    authResourceDO.setCreateName(resourceAgg.getCreateName());
    authResourceDO.setUpdateBy(resourceAgg.getUpdateBy());
    authResourceDO.setUpdateName(resourceAgg.getUpdateName());
    return authResourceDO;
  }

  public static ResourceAgg getResourceAgg(AuthResourceDO resourceDO) {
    if (resourceDO == null) {
      return null;
    }
    ResourceAgg resourceAgg = new ResourceAgg();
    resourceAgg.setId(resourceDO.getId());
    resourceAgg.setType(ResourceTypeEnum.getByCode(resourceDO.getType()));
    resourceAgg.setKey(resourceDO.getKey());
    resourceAgg.setName(resourceDO.getName());
    resourceAgg.setResourceUrl(resourceDO.getResourceUrl());
    resourceAgg.setIconUrl(resourceDO.getIconUrl());
    resourceAgg.setParentId(resourceDO.getParentId());
    resourceAgg.setVisible(resourceDO.getVisible());
    resourceAgg.setStatus(resourceDO.getStatus());
    resourceAgg.setSortNum(resourceDO.getSortNum());
    resourceAgg.setVersion(resourceDO.getVersion());
    resourceAgg.setCreateBy(resourceDO.getCreateBy());
    resourceAgg.setCreateName(resourceDO.getCreateName());
    resourceAgg.setUpdateBy(resourceDO.getUpdateBy());
    resourceAgg.setUpdateName(resourceDO.getUpdateName());
    return resourceAgg;
  }
}
