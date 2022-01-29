package com.zone.auth.infrastructure.db.query;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zone.auth.infrastructure.db.dataobject.AuthResourceDO;
import com.zone.auth.infrastructure.db.mapper.AuthResourceMapper;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/29 11:20 下午
 * @Description:
 */
@Service
public class ResourceQuery {

  @Resource
  private AuthResourceMapper authResourceMapper;

  /**
   * 查询指定resourceIdList下是否存在指定url的资源
   */
  public List<AuthResourceDO> queryByUrlInIdList(List<Long> resourceIdList, String url) {
    if (CollectionUtil.isEmpty(resourceIdList)) {
      return Lists.newArrayList();
    }
    QueryWrapper<AuthResourceDO> resourceWrapper = new QueryWrapper<>();
    resourceWrapper.lambda().eq(AuthResourceDO::getStatus, true)
        .eq(AuthResourceDO::getResourceUrl, url)
        .in(AuthResourceDO::getId, resourceIdList);
    return authResourceMapper.selectList(resourceWrapper);
  }

  /**
   * 根据主键id查询资源
   */
  public AuthResourceDO queryById(Long resourceId) {
    return authResourceMapper.selectById(resourceId);
  }

  /**
   * 分页查询资源点
   */
  public Page<AuthResourceDO> page(String name, String resourceUrl, Boolean visible, Integer pageNo, Integer pageSize) {

    QueryWrapper<AuthResourceDO> wrapper = new QueryWrapper<>();

    if (StrUtil.isNotBlank(name)) {
      wrapper.lambda().like(AuthResourceDO::getName, name);
    }

    if (StrUtil.isNotBlank(resourceUrl)) {
      wrapper.lambda().eq(AuthResourceDO::getResourceUrl, resourceUrl);
    }

    if (visible != null) {
      wrapper.lambda().eq(AuthResourceDO::getVisible, visible);
    }

    return authResourceMapper.selectPage(new Page<>(pageNo, pageSize), wrapper);
  }

  /**
   * 查询全部资源
   */
  public List<AuthResourceDO> queryAll() {
    return authResourceMapper.selectList(new QueryWrapper<>());
  }

  /**
   * 根据idList查询资源
   */
  public List<AuthResourceDO> queryInIdList(List<Long> resourceIdList) {
    if (CollectionUtil.isEmpty(resourceIdList)) {
      return Lists.newArrayList();
    }
    QueryWrapper<AuthResourceDO> wrapper = new QueryWrapper<>();
    wrapper.lambda().in(AuthResourceDO::getId, resourceIdList);
    return authResourceMapper.selectList(wrapper);
  }
}
