package com.zone.auth.application.service.query;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zone.auth.application.service.query.converter.UserBasicConverter;
import com.zone.auth.application.service.query.dto.UserBasicDTO;
import com.zone.auth.infrastructure.db.dataobject.UserBasicDO;
import com.zone.auth.infrastructure.db.mapper.UserBasicMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/10 11:16 下午
 * @Description:
 */
@Slf4j
@Service
public class UserQueryService {

    @Autowired
    private UserBasicMapper userBasicMapper;

    @Autowired
    private UserBasicConverter converter;

    public IPage<UserBasicDTO> page(String accountName, String userName, String email, Integer pageNo, Integer pageSize) {
        QueryWrapper<UserBasicDO> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(accountName)) {
            queryWrapper.like("account_name", accountName);
        }
        if (StrUtil.isNotBlank(userName)) {
            queryWrapper.like("user_name", userName);
        }
        if (StrUtil.isNotBlank(email)) {
            queryWrapper.like("email", email);
        }
        IPage<UserBasicDO> page = userBasicMapper.selectPage(new Page<>(pageNo, pageSize), queryWrapper);
        return page.convert(converter::apply);
    }

    public String queryUserName(Long userId) {
        UserBasicDO userBasicDO = userBasicMapper.selectById(userId);
        return userBasicDO == null ? "" : userBasicDO.getUserName();
    }
}
