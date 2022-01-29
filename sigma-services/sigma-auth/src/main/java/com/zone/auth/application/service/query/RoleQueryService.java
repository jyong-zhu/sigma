package com.zone.auth.application.service.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zone.auth.application.service.query.assembler.RoleDetailDTOAssembler;
import com.zone.auth.application.service.query.dto.RoleDetailDTO;
import com.zone.auth.infrastructure.db.dataobject.AuthResourceDO;
import com.zone.auth.infrastructure.db.dataobject.AuthRoleDO;
import com.zone.auth.infrastructure.db.query.ResourceQuery;
import com.zone.auth.infrastructure.db.query.RoleQuery;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 8:39 下午
 * @Description:
 */
@Slf4j
@Service
public class RoleQueryService {

  @Autowired
  private RoleQuery roleQuery;

  @Autowired
  private ResourceQuery resourceQuery;

  /**
   * 获取角色详情
   */
  public RoleDetailDTO detail(Long roleId) {

    // 查询角色
    AuthRoleDO roleDO = roleQuery.queryById(roleId);
    Preconditions.checkNotNull(roleDO, "角色不存在");

    // 查询资源
    List<Long> resourceIdList = Arrays.stream(roleDO.getResources().split(","))
        .map(Long::valueOf).collect(Collectors.toList());
    List<AuthResourceDO> resourceList = resourceQuery.queryInIdList(resourceIdList);

    return RoleDetailDTOAssembler.toRoleDetailDTO(roleDO, resourceList);
  }

  /**
   * 分页查询角色
   */
  public IPage<RoleDetailDTO> page(String name, Integer pageNo, Integer pageSize) {

    Page<AuthRoleDO> authRoleDOPage = roleQuery.page(name, pageNo, pageSize);

    return authRoleDOPage.convert(roleDO -> RoleDetailDTOAssembler.toRoleDetailDTO(roleDO, Lists.newArrayList()));
  }
}
