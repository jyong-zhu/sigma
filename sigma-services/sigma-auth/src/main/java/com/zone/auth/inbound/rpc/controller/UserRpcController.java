package com.zone.auth.inbound.rpc.controller;

import com.zone.auth.application.service.query.UserQueryService;
import com.zone.commons.entity.ResponseData;
import com.zone.rpc.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/18 11:42 下午
 * @Description:
 */
public class UserRpcController implements UserFeignClient {

    @Autowired
    private UserQueryService userQueryService;

    @Override
    public ResponseData<String> queryUserName(Long userId) {
        return ResponseData.ok(userQueryService.queryUserName(userId));
    }
}
