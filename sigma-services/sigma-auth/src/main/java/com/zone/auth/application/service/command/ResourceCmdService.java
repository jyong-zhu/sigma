package com.zone.auth.application.service.command;

import com.zone.auth.application.service.command.cmd.ResourceCreateCommand;
import com.zone.auth.application.service.command.cmd.ResourceUpdateCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 8:19 下午
 * @Description:
 */
@Slf4j
@Service
public class ResourceCmdService {

  /**
   * 创建资源点
   */
  public Long create(ResourceCreateCommand createCommand) {
    return null;
  }

  /**
   * 更新资源点
   */
  public Long update(ResourceUpdateCommand updateCommand) {
    return null;
  }

  /**
   * 删除资源点
   */
  public Boolean delete(Long resourceId) {
    return null;
  }
}
