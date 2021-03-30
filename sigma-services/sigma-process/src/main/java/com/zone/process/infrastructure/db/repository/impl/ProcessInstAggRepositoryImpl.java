package com.zone.process.infrastructure.db.repository.impl;

import com.zone.process.domain.agg.ProcessInstAgg;
import com.zone.process.domain.repository.ProcessInstAggRepository;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:07 下午
 * @Description:
 */
@Service
public class ProcessInstAggRepositoryImpl implements ProcessInstAggRepository {

    @Override
    public void save(ProcessInstAgg instAgg) {

    }

    @Override
    public ProcessInstAgg queryById(Long id) {
        return null;
    }

    @Override
    public Boolean update(ProcessInstAgg instAgg) {
        return null;
    }
}
