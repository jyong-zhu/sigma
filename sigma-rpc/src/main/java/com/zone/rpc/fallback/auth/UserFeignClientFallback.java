package com.zone.rpc.fallback.auth;

import com.zone.commons.entity.ResponseData;
import com.zone.rpc.dto.auth.UserDetailDTO;
import com.zone.rpc.feign.auth.UserFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/21 3:27 下午
 * @Description:
 */
@Slf4j
@Component
public class UserFeignClientFallback implements FallbackFactory<UserFeignClient> {

    @Override
    public UserFeignClient create(Throwable throwable) {
        return new UserFeignClient() {
            @Override
            public ResponseData<UserDetailDTO> queryUserInfo(Long id) {
                log.error(throwable.getMessage() + "熔断降级");
                return ResponseData.error(new UserDetailDTO(), throwable.getMessage() + "熔断降级");
            }
        };
    }
}
