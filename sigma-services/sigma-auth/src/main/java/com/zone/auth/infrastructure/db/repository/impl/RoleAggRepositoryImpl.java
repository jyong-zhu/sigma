package com.zone.auth.infrastructure.db.repository.impl;

import com.zone.auth.domain.agg.RoleAgg;
import com.zone.auth.domain.repository.RoleAggRepository;
import com.zone.auth.infrastructure.db.adapter.RoleAggAdapter;
import com.zone.auth.infrastructure.db.dataobject.AuthRoleDO;
import com.zone.auth.infrastructure.db.mapper.AuthRoleMapper;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/21 4:42 下午
 * @Description:
 */
@Slf4j
@Repository
public class RoleAggRepositoryImpl implements RoleAggRepository {

  @Resource
  private AuthRoleMapper authRoleMapper;

  @Override
  public Long save(RoleAgg roleAgg) {

    AuthRoleDO roleDO = RoleAggAdapter.getAuthRoleDO(roleAgg);
    if (roleDO == null) {
      return null;
    }

    authRoleMapper.insert(roleDO);

    return roleDO.getId();
  }

  @Override
  public Long update(RoleAgg roleAgg) {
    AuthRoleDO roleDO = RoleAggAdapter.getAuthRoleDO(roleAgg);
    if (roleDO == null) {
      return null;
    }

    int num = authRoleMapper.updateById(roleDO);
    if (num == 0) {
      log.warn("【乐观锁】更新角色失败，roleAgg=[{}]", roleAgg);
      return null;
    }

    return roleDO.getId();
  }

  @Override
  public Long delete(Long roleId) {
    authRoleMapper.deleteById(roleId);
    return roleId;
  }

  @Override
  public RoleAgg queryById(Long id) {
    AuthRoleDO roleDO = authRoleMapper.selectById(id);
    return RoleAggAdapter.getRoleAgg(roleDO);
  }
}
