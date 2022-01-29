package com.zone.auth.infrastructure.db.query;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zone.auth.infrastructure.db.dataobject.AuthAccountRoleDO;
import com.zone.auth.infrastructure.db.dataobject.AuthRoleDO;
import com.zone.auth.infrastructure.db.mapper.AuthAccountRoleMapper;
import com.zone.auth.infrastructure.db.mapper.AuthRoleMapper;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/29 11:20 下午
 * @Description:
 */
@Service
public class RoleQuery {

  @Resource
  private AuthAccountRoleMapper authAccountRoleMapper;

  @Resource
  private AuthRoleMapper authRoleMapper;

  /**
   * 查询指定账号的所有角色id列表
   */
  public List<Long> queryRoleIdList(Long accountId) {
    QueryWrapper<AuthAccountRoleDO> accountRoleWrapper = new QueryWrapper<>();
    accountRoleWrapper.lambda().eq(AuthAccountRoleDO::getAccountId, accountId);
    List<AuthAccountRoleDO> accountRoleList = authAccountRoleMapper.selectList(accountRoleWrapper);
    return accountRoleList.stream().map(AuthAccountRoleDO::getRoleId).collect(Collectors.toList());
  }

  /**
   * 查询角色列表对应的全部资源id列表
   */
  public List<Long> queryRoleResourceIdList(List<Long> roleIdList) {
    if (CollectionUtil.isEmpty(roleIdList)) {
      return Lists.newArrayList();
    }
    QueryWrapper<AuthRoleDO> roleWrapper = new QueryWrapper<>();
    roleWrapper.lambda().in(AuthRoleDO::getId, roleIdList).eq(AuthRoleDO::getStatus, true);
    List<AuthRoleDO> roleList = authRoleMapper.selectList(roleWrapper);
    return roleList.stream()
        .flatMap(tmp -> Arrays.stream(tmp.getResources().split(",")))
        .map(Long::valueOf)
        .distinct().collect(Collectors.toList());
  }

  /**
   * 查询角色列表
   */
  public List<AuthRoleDO> queryInIdList(List<Long> roleIdList) {
    if (CollectionUtil.isEmpty(roleIdList)) {
      return Lists.newArrayList();
    }
    QueryWrapper<AuthRoleDO> wrapper = new QueryWrapper<>();
    wrapper.lambda().in(AuthRoleDO::getId, roleIdList);
    return authRoleMapper.selectList(wrapper);
  }

  /**
   * 根据id查询详情
   */
  public AuthRoleDO queryById(Long roleId) {
    return authRoleMapper.selectById(roleId);
  }

  /**
   * 分页查询角色
   */
  public Page<AuthRoleDO> page(String name, Integer pageNo, Integer pageSize) {

    QueryWrapper<AuthRoleDO> wrapper = new QueryWrapper<>();

    if (StrUtil.isNotBlank(name)) {
      wrapper.lambda().like(AuthRoleDO::getRoleName, name);
    }

    return authRoleMapper.selectPage(new Page<>(pageNo, pageSize), wrapper);
  }
}
