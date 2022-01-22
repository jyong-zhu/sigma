package com.zone.rpc.fallback.auth;

import com.zone.commons.entity.ResponseData;
import com.zone.rpc.dto.auth.AccountCheckDTO;
import com.zone.rpc.feign.auth.AuthFeignClient;
import com.zone.rpc.req.auth.AccountCheckReq;
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
public class AuthFeignClientFallback implements FallbackFactory<AuthFeignClient> {

    @Override
    public AuthFeignClient create(Throwable throwable) {
        return new AuthFeignClient() {

            @Override
            public ResponseData<AccountCheckDTO> check(AccountCheckReq checkReq) {
                log.error("调用check接口降级");
                return ResponseData.error("降级处理");
            }
        };
    }
}
