package com.zone.auth.application.service.query;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zone.auth.application.service.query.assembler.RoleDetailDTOAssembler;
import com.zone.auth.application.service.query.dto.RoleDetailDTO;
import com.zone.auth.infrastructure.db.dataobject.AuthResourceDO;
import com.zone.auth.infrastructure.db.dataobject.AuthRoleDO;
import com.zone.auth.infrastructure.db.mapper.AuthResourceMapper;
import com.zone.auth.infrastructure.db.mapper.AuthRoleMapper;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 8:39 下午
 * @Description:
 */
@Slf4j
@Service
public class RoleQueryService {

  @Resource
  private AuthRoleMapper authRoleMapper;

  @Resource
  private AuthResourceMapper authResourceMapper;

  /**
   * 获取角色详情
   */
  public RoleDetailDTO detail(Long roleId) {

    // 查询角色
    AuthRoleDO roleDO = authRoleMapper.selectById(roleId);
    Preconditions.checkNotNull(roleDO, "角色不存在");

    // 查询资源
    List<Long> resourceIdList = Arrays.stream(roleDO.getResources().split(","))
        .map(Long::valueOf).collect(Collectors.toList());
    List<AuthResourceDO> resourceList = Lists.newArrayList();
    if (CollectionUtil.isNotEmpty(resourceIdList)) {
      QueryWrapper<AuthResourceDO> wrapper = new QueryWrapper<>();
      wrapper.lambda().in(AuthResourceDO::getId, resourceIdList);
      resourceList = authResourceMapper.selectList(wrapper);
    }

    return RoleDetailDTOAssembler.toRoleDetailDTO(roleDO, resourceList);
  }

  /**
   * 分页查询角色
   */
  public IPage<RoleDetailDTO> page(String name, Integer pageNo, Integer pageSize) {

    QueryWrapper<AuthRoleDO> wrapper = new QueryWrapper<>();
    if (StrUtil.isNotBlank(name)) {
      wrapper.lambda().like(AuthRoleDO::getRoleName, name);
    }

    Page<AuthRoleDO> authRoleDOPage = authRoleMapper.selectPage(new Page<>(pageNo, pageSize), wrapper);

    return authRoleDOPage.convert(roleDO -> RoleDetailDTOAssembler.toRoleDetailDTO(roleDO, Lists.newArrayList()));
  }
}
