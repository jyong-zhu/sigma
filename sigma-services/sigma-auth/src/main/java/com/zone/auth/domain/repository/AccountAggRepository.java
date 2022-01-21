package com.zone.auth.domain.repository;

import com.zone.auth.domain.agg.AccountAgg;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/21 1:43 下午
 * @Description:
 */
public interface AccountAggRepository {

  /**
   * 保存账号数据
   */
  Long save(AccountAgg accountAgg);

  /**
   * 更新账号数据
   */
  Long update(AccountAgg accountAgg);

  /**
   * 删除账号数据
   */
  Long delete(Long accountId);

  /**
   * 获取账号信息
   */
  AccountAgg queryById(Long accountId);

  /**
   * 根据手机号获取账号
   */
  AccountAgg queryByPhone(String phone);
}
