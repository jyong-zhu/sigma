package com.zone.auth.infrastructure.db.query;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zone.auth.infrastructure.db.dataobject.AuthAccountDO;
import com.zone.auth.infrastructure.db.mapper.AuthAccountMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/29 11:20 下午
 * @Description:
 */
@Service
public class AccountQuery {

  @Resource
  private AuthAccountMapper authAccountMapper;

  /**
   * 根据手机号查询账号详情
   */
  public AuthAccountDO queryByPhone(String phone) {
    QueryWrapper<AuthAccountDO> accountWrapper = new QueryWrapper<>();
    accountWrapper.lambda().eq(AuthAccountDO::getPhone, phone);
    return authAccountMapper.selectOne(accountWrapper);
  }

  /**
   * 根据id查询账号详情
   */
  public AuthAccountDO queryById(Long accountId) {
    return authAccountMapper.selectById(accountId);
  }

  /**
   * 分页查询账号列表
   */
  public Page<AuthAccountDO> page(String name, String email, String phone, Integer pageNo, Integer pageSize) {
    QueryWrapper<AuthAccountDO> wrapper = new QueryWrapper<>();

    if (StrUtil.isNotBlank(name)) {
      wrapper.lambda().like(AuthAccountDO::getName, name);
    }

    if (StrUtil.isNotBlank(email)) {
      wrapper.lambda().eq(AuthAccountDO::getEmail, email);
    }

    if (StrUtil.isNotBlank(phone)) {
      wrapper.lambda().eq(AuthAccountDO::getPhone, phone);
    }

    return authAccountMapper.selectPage(new Page<>(pageNo, pageSize), wrapper);
  }
}
