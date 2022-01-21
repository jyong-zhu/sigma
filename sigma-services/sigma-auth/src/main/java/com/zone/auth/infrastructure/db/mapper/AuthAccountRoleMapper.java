package com.zone.auth.infrastructure.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zone.auth.infrastructure.db.dataobject.AuthAccountRoleDO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 账号角色关联表 Mapper 接口
 * </p>
 *
 * @author Jone
 * @since 2022-01-20
 */
public interface AuthAccountRoleMapper extends BaseMapper<AuthAccountRoleDO> {

  void insertBatch(@Param("entityList") List<AuthAccountRoleDO> accountRoleList);
}
