package com.zone.auth.application.service.query;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zone.auth.application.service.query.assembler.AccountDetailDTOAssembler;
import com.zone.auth.application.service.query.assembler.RoleDetailDTOAssembler;
import com.zone.auth.application.service.query.dto.AccountDetailDTO;
import com.zone.auth.application.service.query.dto.RoleDetailDTO;
import com.zone.auth.infrastructure.db.dataobject.AuthAccountDO;
import com.zone.auth.infrastructure.db.dataobject.AuthAccountRoleDO;
import com.zone.auth.infrastructure.db.dataobject.AuthRoleDO;
import com.zone.auth.infrastructure.db.mapper.AuthAccountMapper;
import com.zone.auth.infrastructure.db.mapper.AuthAccountRoleMapper;
import com.zone.auth.infrastructure.db.mapper.AuthRoleMapper;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 8:45 下午
 * @Description:
 */
@Slf4j
@Service
public class AccountQueryService {

  @Resource
  private AuthAccountMapper authAccountMapper;

  @Resource
  private AuthAccountRoleMapper authAccountRoleMapper;

  @Resource
  private AuthRoleMapper authRoleMapper;

  /**
   * 获取账号详情
   */
  public AccountDetailDTO detail(Long accountId) {

    // 1. 查询账号信息
    AuthAccountDO accountDO = authAccountMapper.selectById(accountId);
    Preconditions.checkNotNull(accountDO, "账号不存在");

    // 2. 查询账号对应的角色信息
    List<RoleDetailDTO> roleDTOList = queryRoleDTOList(accountDO.getId());

    return AccountDetailDTOAssembler.toAccountDetailDTO(accountDO, roleDTOList);
  }

  /**
   * 分页查询账号列表
   */
  public IPage<AccountDetailDTO> page(String name, String email, String phone, Integer pageNo, Integer pageSize) {

    QueryWrapper<AuthAccountDO> wrapper = new QueryWrapper<>();

    if (StrUtil.isNotBlank(name)) {
      wrapper.lambda().like(AuthAccountDO::getName, name);
    }

    if (StrUtil.isNotBlank(email)) {
      wrapper.lambda().eq(AuthAccountDO::getEmail, email);
    }

    if (StrUtil.isNotBlank(phone)) {
      wrapper.lambda().eq(AuthAccountDO::getPhone, phone);
    }

    Page<AuthAccountDO> authAccountDOPage = authAccountMapper.selectPage(new Page<>(pageNo, pageSize), wrapper);

    return authAccountDOPage.convert(accountDO -> AccountDetailDTOAssembler.toAccountDetailDTO(accountDO, Lists.newArrayList()));
  }

  /**
   * 查询角色信息
   */
  private List<RoleDetailDTO> queryRoleDTOList(Long accountId) {

    // 1. 查询角色id
    QueryWrapper<AuthAccountRoleDO> accountRoleWrapper = new QueryWrapper<>();
    accountRoleWrapper.lambda().eq(AuthAccountRoleDO::getAccountId, accountId);
    List<AuthAccountRoleDO> accountRoleList = authAccountRoleMapper.selectList(accountRoleWrapper);
    List<Long> roleIdList = accountRoleList.stream().map(AuthAccountRoleDO::getRoleId).collect(Collectors.toList());

    // 2. 查询角色列表
    List<AuthRoleDO> roleList = Lists.newArrayList();
    if (CollectionUtil.isNotEmpty(roleIdList)) {
      QueryWrapper<AuthRoleDO> wrapper = new QueryWrapper<>();
      wrapper.lambda().in(AuthRoleDO::getId, roleIdList);
      roleList = authRoleMapper.selectList(wrapper);
    }

    return roleList.stream()
        .map(roleDO -> RoleDetailDTOAssembler.toRoleDetailDTO(roleDO, Lists.newArrayList()))
        .collect(Collectors.toList());
  }
}
