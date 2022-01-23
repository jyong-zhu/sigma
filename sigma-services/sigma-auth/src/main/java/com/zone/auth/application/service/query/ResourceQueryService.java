package com.zone.auth.application.service.query;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zone.auth.application.service.query.assembler.ResourceDetailDTOAssembler;
import com.zone.auth.application.service.query.dto.ResourceDetailDTO;
import com.zone.auth.application.service.query.dto.ResourceTreeDTO;
import com.zone.auth.infrastructure.db.dataobject.AuthResourceDO;
import com.zone.auth.infrastructure.db.mapper.AuthResourceMapper;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 8:43 下午
 * @Description:
 */
@Slf4j
@Service
public class ResourceQueryService {

  @Resource
  private AuthResourceMapper authResourceMapper;

  private static final Long FINAL_PARENT_ID = 0L;

  /**
   * 获取资源点详情
   */
  public ResourceDetailDTO detail(Long resourceId) {

    AuthResourceDO resourceDO = authResourceMapper.selectById(resourceId);
    Preconditions.checkNotNull(resourceDO, "资源点不存在");

    return ResourceDetailDTOAssembler.getResourceDetailDTO(resourceDO);
  }

  /**
   * 分页查询资源点
   */
  public IPage<ResourceDetailDTO> page(String name, String resourceUrl, Boolean visible, Integer pageNo, Integer pageSize) {

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

    Page<AuthResourceDO> authResourceDOPage = authResourceMapper.selectPage(new Page<>(pageNo, pageSize), wrapper);

    return authResourceDOPage.convert(ResourceDetailDTOAssembler::getResourceDetailDTO);
  }

  /**
   * 获取资源树
   */
  public List<ResourceTreeDTO> tree() {

    // 获取全部的资源
    List<AuthResourceDO> resourceList = authResourceMapper.selectList(new QueryWrapper<>());

    // 按 parentID 进行聚合
    Map<Long, List<AuthResourceDO>> resourceMap = resourceList.stream().collect(Collectors.groupingBy(AuthResourceDO::getParentId));

    return generateTree(resourceMap, FINAL_PARENT_ID);
  }

  private List<ResourceTreeDTO> generateTree(Map<Long, List<AuthResourceDO>> resourceMap, Long finalParentId) {
    List<ResourceTreeDTO> result = Lists.newArrayList();
    List<AuthResourceDO> resourceDOList = resourceMap.getOrDefault(finalParentId, Lists.newArrayList());

    resourceDOList.forEach(resourceDO -> {
      ResourceDetailDTO detailDTO = ResourceDetailDTOAssembler.getResourceDetailDTO(resourceDO);
      List<ResourceTreeDTO> nextList = generateTree(resourceMap, resourceDO.getId());
      result.add(new ResourceTreeDTO()
          .setCurNode(detailDTO)
          .setNextNodeList(nextList));
    });

    return result;
  }
}
