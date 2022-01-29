package com.zone.auth.infrastructure.db.repository.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zone.auth.domain.agg.AccountAgg;
import com.zone.auth.domain.repository.AccountAggRepository;
import com.zone.auth.infrastructure.db.adapter.AccountAggAdapter;
import com.zone.auth.infrastructure.db.dataobject.AuthAccountDO;
import com.zone.auth.infrastructure.db.dataobject.AuthAccountRoleDO;
import com.zone.auth.infrastructure.db.mapper.AuthAccountMapper;
import com.zone.auth.infrastructure.db.mapper.AuthAccountRoleMapper;
import com.zone.commons.util.IdWorkerUtil;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/21 1:43 下午
 * @Description:
 */
@Slf4j
@Repository
public class AccountAggRepositoryImpl implements AccountAggRepository {

  @Resource
  private AuthAccountMapper authAccountMapper;

  @Resource
  private AuthAccountRoleMapper authAccountRoleMapper;

  @Override
  public Long save(AccountAgg accountAgg) {

    // 插入账号信息
    AuthAccountDO accountDO = AccountAggAdapter.getAccountDO(accountAgg);
    if (accountDO == null) {
      return null;
    }

    // 用户id采用雪花算法生成，角色id采用自增主键
    // 这样用于区分流程实例中当前处理人是角色的情况
    Long accountId = IdWorkerUtil.nextId();
    accountDO.setId(accountId);
    authAccountMapper.insert(accountDO);

    // 插入账号-角色表信息
    List<AuthAccountRoleDO> accountRoleList = AccountAggAdapter.getAccountRoleDOList(accountAgg.getRoleIdList(), accountDO.getId());
    if (CollectionUtil.isNotEmpty(accountRoleList)) {
      authAccountRoleMapper.insertBatch(accountRoleList);
    }

    return accountDO.getId();
  }

  @Override
  public Long update(AccountAgg accountAgg) {

    AuthAccountDO accountDO = AccountAggAdapter.getAccountDO(accountAgg);
    if (accountDO == null) {
      return null;
    }

    int num = authAccountMapper.updateById(accountDO);

    List<AuthAccountRoleDO> accountRoleDOList = AccountAggAdapter.getAccountRoleDOList(accountAgg.getRoleIdList(), accountDO.getId());
    if (num != 0 && CollectionUtil.isNotEmpty(accountRoleDOList)) {

      // 删除原有关联角色
      QueryWrapper<AuthAccountRoleDO> wrapper = new QueryWrapper<>();
      wrapper.lambda().eq(AuthAccountRoleDO::getAccountId, accountDO.getId());
      authAccountRoleMapper.delete(wrapper);

      // 新增角色
      authAccountRoleMapper.insertBatch(accountRoleDOList);
    }

    return accountDO.getId();
  }

  @Override
  public Long delete(Long accountId) {

    authAccountMapper.deleteById(accountId);

    QueryWrapper<AuthAccountRoleDO> wrapper = new QueryWrapper<>();
    wrapper.lambda().eq(AuthAccountRoleDO::getAccountId, accountId);
    authAccountRoleMapper.delete(wrapper);

    return accountId;
  }

  @Override
  public AccountAgg queryById(Long accountId) {
    AuthAccountDO authAccountDO = authAccountMapper.selectById(accountId);

    QueryWrapper<AuthAccountRoleDO> wrapper = new QueryWrapper<>();
    wrapper.lambda().eq(AuthAccountRoleDO::getAccountId, accountId);
    List<AuthAccountRoleDO> accountRoleDOList = authAccountRoleMapper.selectList(wrapper);

    return AccountAggAdapter.getAccountAgg(authAccountDO, accountRoleDOList);
  }

  @Override
  public AccountAgg queryByPhone(String phone) {
    QueryWrapper<AuthAccountDO> accountWrapper = new QueryWrapper<>();
    accountWrapper.lambda().eq(AuthAccountDO::getPhone, phone);
    AuthAccountDO authAccountDO = authAccountMapper.selectOne(accountWrapper);
    if (authAccountDO == null) {
      return null;
    }

    QueryWrapper<AuthAccountRoleDO> wrapper = new QueryWrapper<>();
    wrapper.lambda().eq(AuthAccountRoleDO::getAccountId, authAccountDO.getId());
    List<AuthAccountRoleDO> accountRoleDOList = authAccountRoleMapper.selectList(wrapper);

    return AccountAggAdapter.getAccountAgg(authAccountDO, accountRoleDOList);
  }
}
