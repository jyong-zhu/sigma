package com.zone.auth.infrastructure.inbound.rpc.controller;

import com.zone.auth.application.service.query.AccountLoginQueryService;
import com.zone.commons.entity.ResponseData;
import com.zone.rpc.dto.auth.AccountCheckDTO;
import com.zone.rpc.feign.auth.AuthFeignClient;
import com.zone.rpc.req.auth.AccountCheckReq;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/22 2:25 下午
 * @Description:
 */
@Api("鉴权相关")
@RestController
public class AuthRpcController implements AuthFeignClient {

  @Autowired
  private AccountLoginQueryService accountLoginQueryService;

  @Override
  public ResponseData<AccountCheckDTO> check(AccountCheckReq checkReq) {
    return ResponseData.ok(accountLoginQueryService.check(checkReq));
  }
}
