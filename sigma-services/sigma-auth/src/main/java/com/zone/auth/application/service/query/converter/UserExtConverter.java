package com.zone.auth.application.service.query.converter;

import cn.hutool.core.bean.BeanUtil;
import com.zone.auth.application.service.query.dto.UserExtDTO;
import com.zone.auth.infrastructure.db.dataobject.UserExtDO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/13 11:51 上午
 * @Description:
 */
@Component
public class UserExtConverter implements Function<UserExtDO, UserExtDTO> {

    @Override
    public UserExtDTO apply(UserExtDO userExtDO) {
        UserExtDTO dto = new UserExtDTO();
        BeanUtil.copyProperties(userExtDO, dto);
        return dto;
    }
}
