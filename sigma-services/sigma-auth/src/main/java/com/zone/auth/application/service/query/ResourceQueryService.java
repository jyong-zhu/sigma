package com.zone.auth.application.service.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zone.auth.application.service.query.dto.ResourceDetailDTO;
import com.zone.auth.application.service.query.dto.ResourceTreeDTO;
import java.util.List;
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

  /**
   * 获取资源点详情
   */
  public ResourceDetailDTO detail(Long resourceId) {
    return null;
  }

  /**
   * 分页查询资源点
   */
  public IPage<ResourceDetailDTO> page(String name, String resourceUrl, Boolean visible, Integer pageNo, Integer pageSize) {
    return null;
  }

  /**
   * 获取资源树
   */
  public List<ResourceTreeDTO> tree() {
    return null;
  }
}
