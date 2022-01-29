package com.zone.process.infrastructure.db.query;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zone.process.infrastructure.db.dataobject.ProcessInstDO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstDataDO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstOperationDO;
import com.zone.process.infrastructure.db.mapper.ProcessInstDataMapper;
import com.zone.process.infrastructure.db.mapper.ProcessInstMapper;
import com.zone.process.infrastructure.db.mapper.ProcessInstOperationMapper;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/3 1:50 下午
 * @Description: 查询没有领域的概念，这里返回的均为 DO
 */
@Service
public class ProcessInstQuery {

  @Resource
  private ProcessInstMapper instMapper;

  @Resource
  private ProcessInstOperationMapper instOperationMapper;

  @Resource
  private ProcessInstDataMapper instDataMapper;

  /**
   * 查询与 userId 相关的流程实例ID
   */
  public List<Long> queryRelateInstIdList(Long userId) {

    List<ProcessInstOperationDO> operationDOList = instOperationMapper.selectList(
        new QueryWrapper<ProcessInstOperationDO>().lambda()
            .eq(ProcessInstOperationDO::getOperateBy, userId)
            .select(ProcessInstOperationDO::getInstanceId));

    return operationDOList.stream().map(tmp -> tmp.getInstanceId()).collect(Collectors.toList());
  }

  /**
   * 根据主键id进行分页查询
   */
  public IPage<ProcessInstDO> pageInIdList(List<Long> idList, String name, String defName, Long startTime, Long endTime, Long curHandlerId, Long submitBy,
      String status, Integer pageNo, Integer pageSize) {
    if (CollectionUtil.isEmpty(idList)) {
      return new Page<>();
    }

    QueryWrapper<ProcessInstDO> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().in(ProcessInstDO::getId, idList).orderByDesc(ProcessInstDO::getId);

    assembleConditions(queryWrapper, name, defName, startTime, endTime, curHandlerId, submitBy, status);

    return instMapper.selectPage(new Page<>(pageNo, pageSize), queryWrapper);
  }

  /**
   * 根据procInstId进行分页查询
   */
  public List<ProcessInstDO> queryInProcInstIdList(List<String> procInstIdList, String name, Long startTime, Long endTime, Long submitBy) {

    if (CollectionUtil.isEmpty(procInstIdList)) {
      return Lists.newArrayList();
    }

    QueryWrapper<ProcessInstDO> queryWrapper = new QueryWrapper<ProcessInstDO>()
        .in("proc_inst_id", procInstIdList);

    assembleConditions(queryWrapper, name, null, startTime, endTime, null, submitBy, "");

    return instMapper.selectList(queryWrapper);
  }

  /**
   * 查询流程实例
   */
  public ProcessInstDO queryInstById(Long instId) {
    return instMapper.selectById(instId);
  }


  /**
   * 查询流程实例
   */
  public ProcessInstDO queryInstByProcInstId(String procInstId) {
    return instMapper.selectOne(new QueryWrapper<ProcessInstDO>()
        .eq("proc_inst_id", procInstId));
  }

  /**
   * 查询userId作为操作人是否操作过instId这个流程实例
   */
  public ProcessInstOperationDO queryRelateOperation(Long instId, Long userId) {
    return instOperationMapper.selectOne(
        new QueryWrapper<ProcessInstOperationDO>().lambda()
            .eq(ProcessInstOperationDO::getInstanceId, instId)
            .eq(ProcessInstOperationDO::getOperateBy, userId)
            .last(" limit 1 "));
  }

  /**
   * 获取流程实例上指定表单的数据
   */
  public List<ProcessInstDataDO> queryDataByFormIds(Long instId, String inputFormIds) {
    List<ProcessInstDataDO> result = Lists.newArrayList();
    if (StrUtil.isNotBlank(inputFormIds)) {
      List<Long> formIdList = Arrays.asList(inputFormIds.split(","))
          .stream().filter(StrUtil::isNotBlank)
          .map(Long::valueOf).collect(Collectors.toList());
      if (CollectionUtil.isNotEmpty(formIdList)) {
        return instDataMapper.selectList(new QueryWrapper<ProcessInstDataDO>().lambda()
            .eq(ProcessInstDataDO::getInstanceId, instId)
            .in(ProcessInstDataDO::getFormId, formIdList));
      }
    }
    return result;
  }

  private void assembleConditions(QueryWrapper<ProcessInstDO> queryWrapper, String name, String defName, Long startTime, Long endTime,
      Long curHandlerId, Long submitBy, String status) {

    if (StrUtil.isNotBlank(name)) {
      queryWrapper.lambda().like(ProcessInstDO::getName, name);
    }

    if (StrUtil.isNotBlank(defName)) {
      queryWrapper.lambda().like(ProcessInstDO::getDefName, name);
    }

    if (startTime != null) {
      queryWrapper.lambda().ge(ProcessInstDO::getSubmitTime, LocalDateTimeUtil.of(startTime));
    }

    if (endTime != null) {
      queryWrapper.lambda().le(ProcessInstDO::getSubmitTime, LocalDateTimeUtil.of(endTime));
    }

    if (curHandlerId != null) {
      // 虽然 cur_handler_id 中存的既可能是 userId 也可能是 roleId
      // 但这里只根据 userId 去查，不根据 roleId 去查
      queryWrapper.lambda().like(ProcessInstDO::getCurHandlerId, curHandlerId);
    }

    if (submitBy != null) {
      queryWrapper.lambda().eq(ProcessInstDO::getSubmitBy, submitBy);
    }

    if (StrUtil.isNotBlank(status)) {
      queryWrapper.lambda().eq(ProcessInstDO::getStatus, status);
    }
  }

  public List<ProcessInstOperationDO> queryOperationList(Long instId) {
    return instOperationMapper.selectList(
        new QueryWrapper<ProcessInstOperationDO>().lambda()
            .eq(ProcessInstOperationDO::getInstanceId, instId)
            .orderByDesc(ProcessInstOperationDO::getId));
  }
}
