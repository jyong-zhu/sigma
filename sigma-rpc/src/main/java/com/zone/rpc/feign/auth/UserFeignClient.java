package com.zone.rpc.feign.auth;

import com.zone.commons.entity.ResponseData;
import com.zone.rpc.dto.auth.UserDetailDTO;
import com.zone.rpc.fallback.auth.UserFeignClientFallback;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/18 11:17 下午
 * @Description:
 */
@FeignClient(value = "sigma-auth", fallbackFactory = UserFeignClientFallback.class)
public interface UserFeignClient {

    @ApiOperation("用户登陆接口")
    @GetMapping("/rpc/user")
    ResponseData<UserDetailDTO> queryUserInfo(@ApiParam(value = "id", required = true) @RequestParam(name = "id") Long id);
}
