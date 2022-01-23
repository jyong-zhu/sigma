package com.zone.auth.application.service.query.assembler;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.zone.auth.application.service.query.dto.ResourceDetailDTO;
import com.zone.auth.infrastructure.db.dataobject.AuthResourceDO;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/23 9:41 上午
 * @Description:
 */
public class ResourceDetailDTOAssembler {

  public static ResourceDetailDTO toResourceDetailDTO(AuthResourceDO resourceDO) {
    if (resourceDO == null) {
      return null;
    }
    ResourceDetailDTO resourceDetailDTO = new ResourceDetailDTO();
    resourceDetailDTO.setId(resourceDO.getId());
    resourceDetailDTO.setType(resourceDO.getType());
    resourceDetailDTO.setKey(resourceDO.getKey());
    resourceDetailDTO.setName(resourceDO.getName());
    resourceDetailDTO.setResourceUrl(resourceDO.getResourceUrl());
    resourceDetailDTO.setIconUrl(resourceDO.getIconUrl());
    resourceDetailDTO.setParentId(resourceDO.getParentId());
    resourceDetailDTO.setVisible(resourceDO.getVisible());
    resourceDetailDTO.setStatus(resourceDO.getStatus());
    resourceDetailDTO.setSortNum(resourceDO.getSortNum());
    resourceDetailDTO.setCreateTime(resourceDO.getCreateTime());
    resourceDetailDTO.setCreateName(resourceDO.getCreateName());
    resourceDetailDTO.setUpdateTime(resourceDO.getUpdateTime());
    resourceDetailDTO.setUpdateName(resourceDO.getUpdateName());
    return resourceDetailDTO;
  }

  public static List<ResourceDetailDTO> toResourceDetailDTOList(List<AuthResourceDO> resourceList) {
    if (CollectionUtil.isEmpty(resourceList)) {
      return Lists.newArrayList();
    }
    return resourceList.stream()
        .filter(tmp -> tmp != null)
        .map(ResourceDetailDTOAssembler::toResourceDetailDTO)
        .collect(Collectors.toList());
  }
}
