package com.zone.process.infrastructure.db.repository.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.zone.process.domain.agg.ProcessInstAgg;
import com.zone.process.domain.repository.ProcessInstAggRepository;
import com.zone.process.infrastructure.db.adapter.ProcessInstAggAdapter;
import com.zone.process.infrastructure.db.dataobject.ProcessInstDO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstDataDO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstOperationDO;
import com.zone.process.infrastructure.db.mapper.ProcessInstDataMapper;
import com.zone.process.infrastructure.db.mapper.ProcessInstMapper;
import com.zone.process.infrastructure.db.mapper.ProcessInstOperationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:07 下午
 * @Description:
 */
@Service
public class ProcessInstAggRepositoryImpl implements ProcessInstAggRepository {

    @Autowired
    private ProcessInstMapper instMapper;

    @Autowired
    private ProcessInstDataMapper instDataMapper;

    @Autowired
    private ProcessInstOperationMapper instOperationMapper;

    @Override
    public void save(ProcessInstAgg instAgg) {
        ProcessInstDO instDO = BeanUtil.copyProperties(instAgg, ProcessInstDO.class);
        instDO.setVersion(0);
        instMapper.insert(instDO);

        List<ProcessInstDataDO> dataDOList = ProcessInstAggAdapter.getInstDataDOList(instAgg.getDataVOList(), instDO.getId());
        List<ProcessInstOperationDO> operationDOList = ProcessInstAggAdapter.getInstOperationDOList(instAgg.getOperationVOList(), instDO.getId());


        if (CollectionUtil.isNotEmpty(dataDOList)) {
            instDataMapper.insertBatchSomeColumn(dataDOList);
        }

        if (CollectionUtil.isNotEmpty(operationDOList)) {
            instOperationMapper.insertBatchSomeColumn(operationDOList);
        }
    }

    @Override
    public ProcessInstAgg queryById(Long id) {
        return null;
    }

    @Override
    public Boolean update(ProcessInstAgg instAgg) {
        return null;
    }

    @Override
    public ProcessInstAgg queryByInstId(String procInstId) {
        return null;
    }
}
