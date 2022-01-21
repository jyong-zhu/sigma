package com.zone.auth.infrastructure.db.repository.impl;

import com.zone.auth.domain.agg.ResourceAgg;
import com.zone.auth.domain.repository.ResourceAggRepository;
import com.zone.auth.infrastructure.db.adapter.ResourceAggAdapter;
import com.zone.auth.infrastructure.db.dataobject.AuthResourceDO;
import com.zone.auth.infrastructure.db.mapper.AuthResourceMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/21 4:42 下午
 * @Description:
 */
@Repository
public class ResourceAggRepositoryImpl implements ResourceAggRepository {

  @Resource
  private AuthResourceMapper authResourceMapper;

  @Override
  public Long save(ResourceAgg resourceAgg) {
    AuthResourceDO resourceDO = ResourceAggAdapter.getResourceDO(resourceAgg);
    if (resourceDO == null) {
      return null;
    }
    authResourceMapper.insert(resourceDO);

    return resourceDO.getId();
  }

  @Override
  public Long update(ResourceAgg resourceAgg) {
    AuthResourceDO resourceDO = ResourceAggAdapter.getResourceDO(resourceAgg);
    if (resourceDO == null) {
      return null;
    }

    authResourceMapper.updateById(resourceDO);

    return resourceDO.getId();
  }

  @Override
  public Long delete(Long resourceId) {

    authResourceMapper.deleteById(resourceId);

    return resourceId;
  }

  @Override
  public ResourceAgg queryById(Long id) {
    AuthResourceDO resourceDO = authResourceMapper.selectById(id);
    return ResourceAggAdapter.getResourceAgg(resourceDO);
  }
}
