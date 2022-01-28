package com.zone.auth.application.service.query;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.zone.auth.infrastructure.db.dataobject.AuthAccountDO;
import com.zone.auth.infrastructure.db.dataobject.AuthAccountRoleDO;
import com.zone.auth.infrastructure.db.dataobject.AuthResourceDO;
import com.zone.auth.infrastructure.db.dataobject.AuthRoleDO;
import com.zone.auth.infrastructure.db.mapper.AuthAccountMapper;
import com.zone.auth.infrastructure.db.mapper.AuthAccountRoleMapper;
import com.zone.auth.infrastructure.db.mapper.AuthResourceMapper;
import com.zone.auth.infrastructure.db.mapper.AuthRoleMapper;
import com.zone.auth.shared.enums.AccountTypeEnum;
import com.zone.rpc.dto.auth.AccountCheckDTO;
import com.zone.rpc.req.auth.AccountCheckReq;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/22 2:54 下午
 * @Description:
 */
@Slf4j
@Service
public class AccountLoginQueryService {

  @Resource
  private AuthAccountMapper authAccountMapper;

  @Resource
  private AuthAccountRoleMapper authAccountRoleMapper;

  @Resource
  private AuthRoleMapper authRoleMapper;

  @Resource
  private AuthResourceMapper authResourceMapper;

  public AccountCheckDTO check(AccountCheckReq checkReq) {
    log.info("开始鉴权，请求参数为AccountCheckReq=[{}]", checkReq);

    // 1. 获取账户详情
    AuthAccountDO accountDO = authAccountMapper.selectById(checkReq.getAccountId());
    if (accountDO == null || !accountDO.getStatus()) {
      log.warn("鉴权失败，账号不存在或被禁用：accountDO=[{}]", accountDO);
      return null;
    }

    // 2. 判断是否为超级管理员
    if (AccountTypeEnum.isAdmin(accountDO.getAccountType())) {
      return new AccountCheckDTO()
          .setAccountId(accountDO.getId())
          .setAccountName(accountDO.getName())
          .setAccountType(accountDO.getAccountType())
          .setPhone(accountDO.getPhone())
          .setRoleIdList(Lists.newArrayList());
    }

    // 2. 获取角色列表
    QueryWrapper<AuthAccountRoleDO> accountRoleWrapper = new QueryWrapper<>();
    accountRoleWrapper.lambda().eq(AuthAccountRoleDO::getAccountId, accountDO.getId());
    List<AuthAccountRoleDO> accountRoleList = authAccountRoleMapper.selectList(accountRoleWrapper);
    List<Long> roleIdList = accountRoleList.stream().map(AuthAccountRoleDO::getRoleId).collect(Collectors.toList());
    if (CollectionUtil.isEmpty(roleIdList)) {
      log.warn("鉴权失败，角色为空：accountDO=[{}]", accountDO);
      return null;
    }

    // 3. 获取角色详情
    QueryWrapper<AuthRoleDO> roleWrapper = new QueryWrapper<>();
    roleWrapper.lambda().in(AuthRoleDO::getId, roleIdList).eq(AuthRoleDO::getStatus, true);
    List<AuthRoleDO> roleList = authRoleMapper.selectList(roleWrapper);
    List<Long> resourceIdList = roleList.stream()
        .flatMap(tmp -> Arrays.stream(tmp.getResources().split(",")))
        .map(Long::valueOf)
        .distinct().collect(Collectors.toList());
    if (CollectionUtil.isEmpty(resourceIdList)) {
      log.warn("鉴权失败，资源为空：accountDO=[{}], roleIdList=[{}]", accountDO, roleIdList);
      return null;
    }

    // 4. 判断url是否存在
    QueryWrapper<AuthResourceDO> resourceWrapper = new QueryWrapper<>();
    resourceWrapper.lambda().eq(AuthResourceDO::getStatus, true)
        .eq(AuthResourceDO::getResourceUrl, checkReq.getUrl())
        .in(AuthResourceDO::getId, resourceIdList);
    List<AuthResourceDO> resourceList = authResourceMapper.selectList(resourceWrapper);

    if (CollectionUtil.isEmpty(resourceList)) {
      log.warn("鉴权失败，资源为空：accountDO=[{}], roleIdList=[{}]", accountDO, roleIdList);
      return null;
    }

    return new AccountCheckDTO()
        .setAccountId(accountDO.getId())
        .setAccountName(accountDO.getName())
        .setAccountType(accountDO.getAccountType())
        .setPhone(accountDO.getPhone())
        .setRoleIdList(roleIdList);
  }
}
