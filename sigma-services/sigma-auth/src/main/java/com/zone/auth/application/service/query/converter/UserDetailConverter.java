package com.zone.auth.application.service.query.converter;

import cn.hutool.core.bean.BeanUtil;
import com.zone.auth.infrastructure.db.dataobject.UserBasicDO;
import com.zone.rpc.dto.auth.UserDetailDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/20 11:57 上午
 * @Description:
 */
@Component
public class UserDetailConverter implements Function<UserBasicDO, UserDetailDTO> {

    @Override
    public UserDetailDTO apply(UserBasicDO userBasicDO) {
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        BeanUtil.copyProperties(userBasicDO, userDetailDTO);
        return userDetailDTO;
    }
}
