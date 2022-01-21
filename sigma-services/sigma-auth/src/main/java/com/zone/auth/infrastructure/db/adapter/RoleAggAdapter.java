package com.zone.auth.infrastructure.db.adapter;

import com.zone.auth.domain.agg.RoleAgg;
import com.zone.auth.infrastructure.db.dataobject.AuthRoleDO;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.bouncycastle.util.Strings;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/21 5:32 下午
 * @Description:
 */
public class RoleAggAdapter {

  public static AuthRoleDO getAuthRoleDO(RoleAgg roleAgg) {
    if (roleAgg == null) {
      return null;
    }
    AuthRoleDO authRoleDO = new AuthRoleDO();
    authRoleDO.setId(roleAgg.getId());
    authRoleDO.setRoleName(roleAgg.getRoleName());
    authRoleDO.setResources(roleAgg.getResourceIdList().stream()
        .map(String::valueOf)
        .collect(Collectors.joining(",")));
    authRoleDO.setStatus(roleAgg.getStatus());
    authRoleDO.setCreateBy(roleAgg.getCreateBy());
    authRoleDO.setCreateName(roleAgg.getCreateName());
    authRoleDO.setUpdateBy(roleAgg.getUpdateBy());
    authRoleDO.setUpdateName(roleAgg.getUpdateName());
    return authRoleDO;
  }

  public static RoleAgg getRoleAgg(AuthRoleDO roleDO) {
    if (roleDO == null) {
      return null;
    }
    RoleAgg roleAgg = new RoleAgg();
    roleAgg.setId(roleDO.getId());
    roleAgg.setRoleName(roleDO.getRoleName());
    roleAgg.setResourceIdList(Arrays.stream(Strings.split(roleDO.getResources(), ','))
        .map(Long::valueOf)
        .collect(Collectors.toList()));
    roleAgg.setStatus(roleDO.getStatus());
    roleAgg.setCreateBy(roleDO.getCreateBy());
    roleAgg.setCreateName(roleDO.getCreateName());
    roleAgg.setUpdateBy(roleDO.getUpdateBy());
    roleAgg.setUpdateName(roleDO.getUpdateName());
    return roleAgg;
  }
}
