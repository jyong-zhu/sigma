package com.zone.auth.application.service.query.converter;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zone.auth.application.service.query.dto.UserBasicDTO;
import com.zone.auth.infrastructure.db.dataobject.UserBasicDO;
import com.zone.auth.infrastructure.db.dataobject.UserExtDO;
import com.zone.auth.infrastructure.db.mapper.UserExtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/13 11:07 上午
 * @Description:
 */
@Component
public class UserBasicConverter implements Function<UserBasicDO, UserBasicDTO> {

    @Autowired
    private UserExtMapper userExtMapper;

    @Autowired
    private UserExtConverter converter;

    @Override
    public UserBasicDTO apply(UserBasicDO userBasicDO) {
        UserBasicDTO userBasicDTO = new UserBasicDTO();
        BeanUtil.copyProperties(userBasicDO, userBasicDTO);
        // 对于需要补充的额外数据，在这里查询
        List<UserExtDO> userExtDOList = userExtMapper.selectList(
                new QueryWrapper<UserExtDO>().eq("user_id", userBasicDO.getId()));
        userBasicDTO.setUserExtDTOList(userExtDOList.stream()
                .map(converter::apply).collect(Collectors.toList()));
        return userBasicDTO;
    }
}
