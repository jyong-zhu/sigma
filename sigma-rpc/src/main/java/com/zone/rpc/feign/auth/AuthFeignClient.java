package com.zone.rpc.feign.auth;

import com.zone.commons.entity.ResponseData;
import com.zone.rpc.dto.auth.AccountCheckDTO;
import com.zone.rpc.fallback.auth.AuthFeignClientFallback;
import com.zone.rpc.req.auth.AccountCheckReq;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/22 2:23 下午
 * @Description:
 */
@FeignClient(value = "sigma-auth", fallbackFactory = AuthFeignClientFallback.class)
public interface AuthFeignClient {

  @ApiOperation("用户登陆接口")
  @PostMapping("/rpc/auth/check")
  ResponseData<AccountCheckDTO> check(@Valid @RequestBody AccountCheckReq checkReq);
}
