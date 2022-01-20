package com.zone.auth.application.service.command;

import com.zone.auth.application.service.command.cmd.RoleCreateCommand;
import com.zone.auth.application.service.command.cmd.RoleUpdateCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 8:19 下午
 * @Description:
 */
@Slf4j
@Service
public class RoleCmdService {

  /**
   * 创建角色
   */
  public Long create(RoleCreateCommand createCommand) {
    return null;
  }

  /**
   * 更新角色
   */
  public Long update(RoleUpdateCommand updateCommand) {
    return null;
  }

  /**
   * 删除角色
   */
  public Boolean delete(Long roleId) {
    return null;
  }

}
