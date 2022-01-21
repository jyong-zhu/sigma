package com.zone.auth.domain.repository;

import com.zone.auth.domain.agg.RoleAgg;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/21 4:41 下午
 * @Description:
 */
public interface RoleAggRepository {

  /**
   * 保存角色
   */
  Long save(RoleAgg roleAgg);

  /**
   * 更新角色
   */
  Long update(RoleAgg roleAgg);

  /**
   * 删除角色数据
   */
  Long delete(Long roleId);

  /**
   * 获取角色聚合根
   */
  RoleAgg queryById(Long id);

}
