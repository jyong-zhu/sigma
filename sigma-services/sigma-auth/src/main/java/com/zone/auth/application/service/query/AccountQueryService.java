package com.zone.auth.application.service.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zone.auth.application.service.query.assembler.AccountDetailDTOAssembler;
import com.zone.auth.application.service.query.assembler.RoleDetailDTOAssembler;
import com.zone.auth.application.service.query.dto.AccountDetailDTO;
import com.zone.auth.application.service.query.dto.RoleDetailDTO;
import com.zone.auth.infrastructure.db.dataobject.AuthAccountDO;
import com.zone.auth.infrastructure.db.dataobject.AuthRoleDO;
import com.zone.auth.infrastructure.db.query.AccountQuery;
import com.zone.auth.infrastructure.db.query.RoleQuery;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 8:45 下午
 * @Description:
 */
@Slf4j
@Service
public class AccountQueryService {

  @Autowired
  private AccountQuery accountQuery;

  @Autowired
  private RoleQuery roleQuery;

  /**
   * 获取账号详情
   */
  public AccountDetailDTO detail(Long accountId) {

    // 1. 查询账号信息
    AuthAccountDO accountDO = accountQuery.queryById(accountId);
    Preconditions.checkNotNull(accountDO, "账号不存在");

    // 2. 查询账号对应的角色信息
    List<RoleDetailDTO> roleDTOList = queryRoleDTOList(accountDO.getId());

    return AccountDetailDTOAssembler.toAccountDetailDTO(accountDO, roleDTOList);
  }

  /**
   * 分页查询账号列表
   */
  public IPage<AccountDetailDTO> page(String name, String email, String phone, Integer pageNo, Integer pageSize) {

    Page<AuthAccountDO> authAccountDOPage = accountQuery.page(name, email, phone, pageNo, pageSize);

    return authAccountDOPage.convert(accountDO -> AccountDetailDTOAssembler.toAccountDetailDTO(accountDO, Lists.newArrayList()));
  }

  /**
   * 查询角色信息
   */
  private List<RoleDetailDTO> queryRoleDTOList(Long accountId) {

    // 1. 查询角色id列表
    List<Long> roleIdList = roleQuery.queryRoleIdList(accountId);

    // 2. 查询角色列表
    List<AuthRoleDO> roleList = roleQuery.queryInIdList(roleIdList);

    return roleList.stream()
        .map(roleDO -> RoleDetailDTOAssembler.toRoleDetailDTO(roleDO, Lists.newArrayList()))
        .collect(Collectors.toList());
  }
}
