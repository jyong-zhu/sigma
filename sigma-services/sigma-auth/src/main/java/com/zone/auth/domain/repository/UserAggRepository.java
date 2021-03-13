package com.zone.auth.domain.repository;

import com.zone.auth.domain.agg.User;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/11 12:07 上午
 * @Description:
 */
public interface UserAggRepository {

    void save(User user);

    User queryById(Long id);

}
