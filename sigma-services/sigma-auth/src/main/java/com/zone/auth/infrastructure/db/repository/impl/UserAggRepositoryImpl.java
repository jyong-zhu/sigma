package com.zone.auth.infrastructure.db.repository.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.zone.auth.domain.agg.User;
import com.zone.auth.domain.repository.UserAggRepository;
import com.zone.auth.infrastructure.db.dataobject.UserBasicDO;
import com.zone.auth.infrastructure.db.dataobject.UserExtDO;
import com.zone.auth.infrastructure.db.mapper.UserBasicMapper;
import com.zone.auth.infrastructure.db.mapper.UserExtMapper;
import com.zone.commons.util.IdWorkerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/11 8:13 上午
 * @Description:
 */
@Service
public class UserAggRepositoryImpl implements UserAggRepository {

    @Autowired
    private UserBasicMapper userBasicMapper;

    @Autowired
    private UserExtMapper userExtMapper;

    @Override
    public void save(User user) {
        Long userId = IdWorkerUtil.nextId();
        user.getUserBasic().setId(userId);
        userBasicMapper.insert(user.getUserBasic());
        if (CollectionUtil.isNotEmpty(user.getUserExtList())) {
            user.getUserExtList().forEach(ext -> {
                ext.setUserId(userId);
                ext.setId(IdWorkerUtil.nextId());
                userExtMapper.insert(ext);
            });
        }
    }

    @Override
    public User queryById(Long id) {
        User user = new User();
        UserBasicDO userBasicDO = userBasicMapper.selectById(id);
        return user.setUserBasic(userBasicDO)
                .setUserExtList(queryUserExtList(userBasicDO));
    }

    private List<UserExtDO> queryUserExtList(UserBasicDO userBasicDO) {
        if (userBasicDO != null) {
            List<UserExtDO> userExtDOList = userExtMapper.selectList(
                    new QueryWrapper<UserExtDO>().eq("user_id", userBasicDO.getId()));
            return userExtDOList;
        }
        return Lists.newArrayList();
    }
}
