package com.zone.auth.application.service.command;

import com.google.common.base.Preconditions;
import com.zone.auth.application.service.command.cmd.RoleCreateCommand;
import com.zone.auth.application.service.command.cmd.RoleUpdateCommand;
import com.zone.auth.application.service.command.transfer.RoleAggTransfer;
import com.zone.auth.domain.agg.RoleAgg;
import com.zone.auth.domain.repository.RoleAggRepository;
import com.zone.auth.shared.enums.AccountTypeEnum;
import com.zone.commons.entity.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 8:19 下午
 * @Description:
 */
@Slf4j
@Service
public class RoleCmdService {

  @Autowired
  private RoleAggRepository roleAggRepository;

  /**
   * 创建角色
   */
  @Transactional(rollbackFor = Exception.class)
  public Long create(RoleCreateCommand createCommand, LoginUser loginUser) {

    // 0. 校验账号类型
    Preconditions.checkState(AccountTypeEnum.isAdmin(loginUser.getAccountType()), "非管理员不能创建角色");

    // 1. 获取角色聚合根
    RoleAgg roleAgg = RoleAggTransfer.getRoleAgg(createCommand);

    // 2. 落地角色数据
    return roleAggRepository.save(roleAgg);
  }

  /**
   * 更新角色
   */
  @Transactional(rollbackFor = Exception.class)
  public Long update(RoleUpdateCommand updateCommand, LoginUser loginUser) {

    // 0. 校验账号类型
    Preconditions.checkState(AccountTypeEnum.isAdmin(loginUser.getAccountType()), "非管理员不能更新角色");

    // 1. 获取角色详情
    RoleAgg roleAgg = roleAggRepository.queryById(updateCommand.getRoleId());
    Preconditions.checkNotNull(roleAgg, "角色不存在");

    // 2. 更新角色数据
    roleAgg.update(updateCommand);

    // 3. 落地角色数据
    Long roleId = roleAggRepository.update(roleAgg);
    Preconditions.checkNotNull(roleId, "角色更新失败，请重试");

    return roleId;
  }

  /**
   * 删除角色
   */
  @Transactional(rollbackFor = Exception.class)
  public Long delete(Long roleId, LoginUser loginUser) {

    // 0. 校验账号类型
    Preconditions.checkState(AccountTypeEnum.isAdmin(loginUser.getAccountType()), "非管理员不能删除角色");

    // 1. 删除角色数据
    return roleAggRepository.delete(roleId);
  }

}
