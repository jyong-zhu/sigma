package com.zone.auth.application.service.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zone.auth.application.service.query.dto.AccountDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 8:45 下午
 * @Description:
 */
@Slf4j
@Service
public class AccountQueryService {

  /**
   * 获取账号详情
   */
  public AccountDetailDTO detail(Long accountId) {
    return null;
  }

  /**
   * 分页查询账号列表
   */
  public IPage<AccountDetailDTO> page(String name, String email, String phone, Integer pageNo, Integer pageSize) {
    return null;
  }
}
