package com.zone.auth.application.service.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zone.auth.application.service.query.assembler.ResourceDetailDTOAssembler;
import com.zone.auth.application.service.query.dto.ResourceDetailDTO;
import com.zone.auth.application.service.query.dto.ResourceTreeDTO;
import com.zone.auth.infrastructure.db.dataobject.AuthResourceDO;
import com.zone.auth.infrastructure.db.query.ResourceQuery;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 8:43 下午
 * @Description:
 */
@Slf4j
@Service
public class ResourceQueryService {

  @Autowired
  private ResourceQuery resourceQuery;

  private static final Long FINAL_PARENT_ID = 0L;

  /**
   * 获取资源点详情
   */
  public ResourceDetailDTO detail(Long resourceId) {

    AuthResourceDO resourceDO = resourceQuery.queryById(resourceId);

    Preconditions.checkNotNull(resourceDO, "资源点不存在");

    return ResourceDetailDTOAssembler.toResourceDetailDTO(resourceDO);
  }

  /**
   * 分页查询资源点
   */
  public IPage<ResourceDetailDTO> page(String name, String resourceUrl, Boolean visible, Integer pageNo, Integer pageSize) {

    Page<AuthResourceDO> authResourceDOPage = resourceQuery.page(name, resourceUrl, visible, pageNo, pageSize);

    return authResourceDOPage.convert(ResourceDetailDTOAssembler::toResourceDetailDTO);
  }

  /**
   * 获取资源树
   */
  public List<ResourceTreeDTO> tree() {

    // 获取全部的资源
    List<AuthResourceDO> resourceList = resourceQuery.queryAll();

    // 按 parentID 进行聚合
    Map<Long, List<AuthResourceDO>> resourceMap = resourceList.stream().collect(Collectors.groupingBy(AuthResourceDO::getParentId));

    return generateTree(resourceMap, FINAL_PARENT_ID);
  }

  /**
   * 生成树
   */
  private List<ResourceTreeDTO> generateTree(Map<Long, List<AuthResourceDO>> resourceMap, Long finalParentId) {
    List<ResourceTreeDTO> result = Lists.newArrayList();
    List<AuthResourceDO> resourceDOList = resourceMap.getOrDefault(finalParentId, Lists.newArrayList());

    resourceDOList.forEach(resourceDO -> {
      ResourceDetailDTO detailDTO = ResourceDetailDTOAssembler.toResourceDetailDTO(resourceDO);
      List<ResourceTreeDTO> nextList = generateTree(resourceMap, resourceDO.getId());
      result.add(new ResourceTreeDTO()
          .setCurNode(detailDTO)
          .setNextNodeList(nextList));
    });

    return result;
  }
}
