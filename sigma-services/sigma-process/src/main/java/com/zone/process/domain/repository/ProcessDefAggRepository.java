package com.zone.process.domain.repository;

import com.zone.commons.entity.LoginUser;
import com.zone.process.domain.agg.ProcessDefAgg;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:05 下午
 * @Description:
 */
public interface ProcessDefAggRepository {

    void save(ProcessDefAgg processDefAgg, LoginUser loginUser);
}
