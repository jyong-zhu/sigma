package com.zone.auth.application.service.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zone.auth.application.service.query.dto.RoleDetailDTO;
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

  /**
   * 获取角色详情
   */
  public RoleDetailDTO detail(Long roleId) {
    return null;
  }

  /**
   * 分页查询角色
   */
  public IPage<RoleDetailDTO> page(String name, String resourceUrl, Integer pageNo, Integer pageSize) {
    return null;
  }
}
