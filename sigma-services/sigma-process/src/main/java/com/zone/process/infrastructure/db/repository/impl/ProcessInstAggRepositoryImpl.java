package com.zone.process.infrastructure.db.repository.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zone.process.domain.agg.ProcessInstAgg;
import com.zone.process.domain.repository.ProcessInstAggRepository;
import com.zone.process.infrastructure.db.adapter.ProcessInstAggAdapter;
import com.zone.process.infrastructure.db.dataobject.ProcessInstDO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstDataDO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstOperationDO;
import com.zone.process.infrastructure.db.mapper.ProcessInstDataMapper;
import com.zone.process.infrastructure.db.mapper.ProcessInstMapper;
import com.zone.process.infrastructure.db.mapper.ProcessInstOperationMapper;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:07 下午
 * @Description:
 */
@Slf4j
@Service
public class ProcessInstAggRepositoryImpl implements ProcessInstAggRepository {

  @Resource
  private ProcessInstMapper instMapper;

  @Resource
  private ProcessInstDataMapper instDataMapper;

  @Resource
  private ProcessInstOperationMapper instOperationMapper;

  @Override
  public Long save(ProcessInstAgg instAgg) {
    ProcessInstDO instDO = BeanUtil.copyProperties(instAgg, ProcessInstDO.class);
    int num = instMapper.insert(instDO);
    if (num == 0) {
      log.error("插入流程实例失败，instAgg=[{}]", instAgg);
      return null;
    }

    List<ProcessInstDataDO> dataDOList = ProcessInstAggAdapter.getInstDataDOList(instAgg.getDataVOList(), instDO.getId());
    List<ProcessInstOperationDO> operationDOList = ProcessInstAggAdapter.getInstOperationDOList(instAgg.getOperationVOList(), instDO.getId());

    if (CollectionUtil.isNotEmpty(dataDOList)) {
      instDataMapper.insertBatchSomeColumn(dataDOList);
    }

    if (CollectionUtil.isNotEmpty(operationDOList)) {
      instOperationMapper.insertBatchSomeColumn(operationDOList);
    }

    return instDO.getId();
  }

  @Override
  public ProcessInstAgg queryById(Long id) {
    ProcessInstDO instDO = instMapper.selectById(id);
    return assemble(instDO);
  }

  @Override
  public Long update(ProcessInstAgg instAgg) {

    // 更新流程实例
    ProcessInstDO instDO = ProcessInstAggAdapter.getInstDO(instAgg);
    int num = instMapper.updateById(instDO);
    if (num == 0) {
      log.error("更新流程失败，instAgg=[{}]", instAgg);
      return null;
    }

    // 删除流程实例子表数据
    instOperationMapper.delete(new QueryWrapper<ProcessInstOperationDO>().lambda()
        .eq(ProcessInstOperationDO::getInstanceId, instDO.getId()));
    instDataMapper.delete(new QueryWrapper<ProcessInstDataDO>().lambda()
        .eq(ProcessInstDataDO::getInstanceId, instDO.getId()));

    // 创建新的流程实例子表数据
    List<ProcessInstOperationDO> instOperationDOList = ProcessInstAggAdapter.getInstOperationDOList(instAgg.getOperationVOList(), instDO.getId());
    List<ProcessInstDataDO> instDataDOList = ProcessInstAggAdapter.getInstDataDOList(instAgg.getDataVOList(), instDO.getId());
    instOperationMapper.insertBatchSomeColumn(instOperationDOList);
    instDataMapper.insertBatchSomeColumn(instDataDOList);

    return instAgg.getId();
  }

  @Override
  public ProcessInstAgg queryByInstId(String procInstId) {
    ProcessInstDO instDO = instMapper.selectOne(new QueryWrapper<ProcessInstDO>().lambda()
        .eq(ProcessInstDO::getProcInstId, procInstId));
    return assemble(instDO);
  }

  private ProcessInstAgg assemble(ProcessInstDO instDO) {

    ProcessInstAgg instAgg = ProcessInstAggAdapter.getProcessInstAgg(instDO);
    if (instAgg == null) {
      return null;
    }

    // 获取操作记录
    List<ProcessInstOperationDO> operationDOList = instOperationMapper.selectList(
        new QueryWrapper<ProcessInstOperationDO>().lambda().eq(ProcessInstOperationDO::getInstanceId, instDO.getId()));
    instAgg.setOperationVOList(ProcessInstAggAdapter.getInstOperationVOList(operationDOList));

    // 获取表单数据
    List<ProcessInstDataDO> dataDOList = instDataMapper.selectList(
        new QueryWrapper<ProcessInstDataDO>().lambda().eq(ProcessInstDataDO::getInstanceId, instDO.getId()));
    instAgg.setDataVOList(ProcessInstAggAdapter.getInstDataVOList(dataDOList));

    return instAgg;
  }
}
