package com.zone.auth.application.service.command;

import com.google.common.base.Preconditions;
import com.zone.auth.application.service.command.cmd.ResourceCreateCommand;
import com.zone.auth.application.service.command.cmd.ResourceUpdateCommand;
import com.zone.auth.application.service.command.transfer.ResourceAggTransfer;
import com.zone.auth.domain.agg.ResourceAgg;
import com.zone.auth.domain.repository.ResourceAggRepository;
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
public class ResourceCmdService {

  @Autowired
  private ResourceAggRepository resourceAggRepository;

  /**
   * 创建资源点
   */
  @Transactional(rollbackFor = Exception.class)
  public Long create(ResourceCreateCommand createCommand, LoginUser loginUser) {

    // 0. 校验账号类型
    Preconditions.checkState(AccountTypeEnum.isAdmin(loginUser.getAccountType()), "非管理员不能创建资源点");

    // 1. 创建资源点聚合根
    ResourceAgg resourceAgg = ResourceAggTransfer.getResourceAgg(createCommand);

    // 2. 落地资源点数据
    return resourceAggRepository.save(resourceAgg);
  }

  /**
   * 更新资源点
   */
  @Transactional(rollbackFor = Exception.class)
  public Long update(ResourceUpdateCommand updateCommand, LoginUser loginUser) {

    // 0. 校验账号类型
    Preconditions.checkState(AccountTypeEnum.isAdmin(loginUser.getAccountType()), "非管理员不能更新资源点");

    // 1. 获取资源点
    ResourceAgg resourceAgg = resourceAggRepository.queryById(updateCommand.getId());
    Preconditions.checkNotNull(resourceAgg, "资源点不存在");

    // 2. 更新资源点
    resourceAgg.update(updateCommand);

    // 3. 落地资源点数据
    return resourceAggRepository.update(resourceAgg);
  }

  /**
   * 删除资源点
   */
  @Transactional(rollbackFor = Exception.class)
  public Long delete(Long resourceId, LoginUser loginUser) {

    // 0. 校验账号类型
    Preconditions.checkState(AccountTypeEnum.isAdmin(loginUser.getAccountType()), "非管理员不能更新资源点");

    // 1. 删除资源点
    return resourceAggRepository.delete(resourceId);
  }
}
