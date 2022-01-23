package com.zone.auth.application.service.query.assembler;

import com.zone.auth.application.service.query.dto.RoleDetailDTO;
import com.zone.auth.infrastructure.db.dataobject.AuthResourceDO;
import com.zone.auth.infrastructure.db.dataobject.AuthRoleDO;
import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/23 2:58 下午
 * @Description:
 */
public class RoleDetailDTOAssembler {

  public static RoleDetailDTO toRoleDetailDTO(AuthRoleDO roleDO, List<AuthResourceDO> resourceList) {
    if (roleDO == null) {
      return null;
    }
    RoleDetailDTO roleDetailDTO = new RoleDetailDTO();
    roleDetailDTO.setId(roleDO.getId());
    roleDetailDTO.setRoleName(roleDO.getRoleName());
    roleDetailDTO.setResourceList(ResourceDetailDTOAssembler.toResourceDetailDTOList(resourceList));
    roleDetailDTO.setStatus(roleDO.getStatus());
    roleDetailDTO.setCreateTime(roleDO.getCreateTime());
    roleDetailDTO.setCreateName(roleDO.getCreateName());
    roleDetailDTO.setUpdateTime(roleDO.getUpdateTime());
    roleDetailDTO.setUpdateName(roleDO.getUpdateName());
    return roleDetailDTO;
  }
}
