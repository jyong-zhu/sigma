package com.zone.auth.domain.repository;

import com.zone.auth.domain.agg.ResourceAgg;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/21 4:41 下午
 * @Description:
 */
public interface ResourceAggRepository {

  /**
   * 保存资源点
   */
  Long save(ResourceAgg resourceAgg);

  /**
   * 更新资源点
   */
  Long update(ResourceAgg resourceAgg);

  /**
   * 删除资源点
   */
  Long delete(Long resourceId);

  /**
   * 查询资源点详情
   */
  ResourceAgg queryById(Long id);

}
