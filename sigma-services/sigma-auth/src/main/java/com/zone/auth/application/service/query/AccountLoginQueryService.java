package com.zone.auth.application.service.query;

import com.zone.rpc.dto.auth.AccountCheckDTO;
import com.zone.rpc.req.auth.AccountCheckReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/22 2:54 下午
 * @Description:
 */
@Slf4j
@Service
public class AccountLoginQueryService {

  public AccountCheckDTO check(AccountCheckReq checkReq) {
    log.info("开始鉴权，请求参数为AccountCheckReq=[{}]", checkReq);
    return null;
  }
}
