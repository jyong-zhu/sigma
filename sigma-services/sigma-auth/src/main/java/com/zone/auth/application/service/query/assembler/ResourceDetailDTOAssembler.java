package com.zone.auth.application.service.query.assembler;

import com.zone.auth.application.service.query.dto.ResourceDetailDTO;
import com.zone.auth.infrastructure.db.dataobject.AuthResourceDO;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/23 9:41 上午
 * @Description:
 */
public class ResourceDetailDTOAssembler {

  public static ResourceDetailDTO getResourceDetailDTO(AuthResourceDO resourceDO) {
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
}
