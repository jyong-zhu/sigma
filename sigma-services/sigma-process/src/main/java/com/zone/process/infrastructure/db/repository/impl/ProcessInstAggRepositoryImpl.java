package com.zone.process.infrastructure.db.repository.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zone.process.domain.agg.ProcessInstAgg;
import com.zone.process.domain.repository.ProcessInstAggRepository;
import com.zone.process.domain.valueobject.InstDataVO;
import com.zone.process.domain.valueobject.InstOperationVO;
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
import java.util.stream.Collectors;

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
        ProcessInstDO instDO = instMapper.selectById(id);
        return assemble(instDO);
    }

    @Override
    public Boolean update(ProcessInstAgg instAgg) {
        ProcessInstDO instDO = BeanUtil.copyProperties(instAgg, ProcessInstDO.class);
        int num = instMapper.updateById(instDO);
        if (num > 0) {

            instOperationMapper.delete(new QueryWrapper<ProcessInstOperationDO>().eq("instance_id", instDO.getId()));
            instDataMapper.delete(new QueryWrapper<ProcessInstDataDO>().eq("instance_id", instDO.getId()));

            List<ProcessInstOperationDO> instOperationDOList = ProcessInstAggAdapter.getInstOperationDOList(instAgg.getOperationVOList(), instDO.getId());
            List<ProcessInstDataDO> instDataDOList = ProcessInstAggAdapter.getInstDataDOList(instAgg.getDataVOList(), instDO.getId());
            instOperationMapper.insertBatchSomeColumn(instOperationDOList);
            instDataMapper.insertBatchSomeColumn(instDataDOList);
            return true;
        }
        return false;
    }

    @Override
    public ProcessInstAgg queryByInstId(String procInstId) {
        ProcessInstDO instDO = instMapper.selectOne(new QueryWrapper<ProcessInstDO>()
                .eq("proc_inst_id", procInstId));
        return assemble(instDO);
    }

    private ProcessInstAgg assemble(ProcessInstDO instDO) {
        if (instDO != null) {
            ProcessInstAgg instAgg = BeanUtil.copyProperties(instDO, ProcessInstAgg.class);

            List<ProcessInstOperationDO> operationDOList = instOperationMapper.selectList(
                    new QueryWrapper<ProcessInstOperationDO>().eq("instance_id", instDO.getId()));

            List<ProcessInstDataDO> dataDOList = instDataMapper.selectList(
                    new QueryWrapper<ProcessInstDataDO>().eq("instance_id", instDO.getId()));

            instAgg.setOperationVOList(operationDOList.stream()
                    .map(operation -> BeanUtil.copyProperties(operation, InstOperationVO.class))
                    .collect(Collectors.toList()));

            instAgg.setDataVOList(dataDOList.stream()
                    .map(data -> BeanUtil.copyProperties(data, InstDataVO.class))
                    .collect(Collectors.toList()));

            return instAgg;
        }
        return null;
    }
}
