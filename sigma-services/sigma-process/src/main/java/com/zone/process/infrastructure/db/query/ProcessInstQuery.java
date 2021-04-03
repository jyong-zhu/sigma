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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/3 1:50 下午
 * @Description: 查询没有领域的概念，这里返回的均为 DO
 */
@Service
public class ProcessInstQuery {

    @Autowired
    private ProcessInstMapper instMapper;

    @Autowired
    private ProcessInstOperationMapper instOperationMapper;

    @Autowired
    private ProcessInstDataMapper instDataMapper;

    /**
     * 查询与 userId 相关的流程实例ID
     */
    public List<Long> queryRelateInstIdList(Long userId) {
        List<ProcessInstOperationDO> operationDOList = instOperationMapper.selectList(
                new QueryWrapper<ProcessInstOperationDO>().eq("operate_by", userId).select("instance_id"));

        return operationDOList.stream().map(tmp -> tmp.getInstanceId()).collect(Collectors.toList());
    }

    /**
     * 根据主键id进行分页查询
     */
    public IPage<ProcessInstDO> pageInIdList(List<Long> idList, String name, Long startTime, Long endTime, Long curHandlerId, Long submitBy,
                                             String status, Integer pageNo, Integer pageSize) {
        if (CollectionUtil.isEmpty(idList)) {
            return new Page<>();
        }

        QueryWrapper<ProcessInstDO> queryWrapper = new QueryWrapper<ProcessInstDO>()
                .in("id", idList).orderByDesc("submit_time");

        assembleConditions(queryWrapper, name, startTime, endTime, curHandlerId, submitBy, status);

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

        assembleConditions(queryWrapper, name, startTime, endTime, null, submitBy, "");

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
                new QueryWrapper<ProcessInstOperationDO>().eq("instance_id", instId)
                        .eq("operate_by", userId).last(" limit 1 "));
    }

    /**
     * 获取流程实例上指定表单的数据
     */
    public List<ProcessInstDataDO> queryDataByFormIds(Long instId, String inputFormIds) {
        List<ProcessInstDataDO> result = Lists.newArrayList();
        if (StrUtil.isNotBlank(inputFormIds)) {
            List<Long> formIdList = Arrays.asList(inputFormIds.split(","))
                    .stream().filter(tmp -> StrUtil.isNotBlank(tmp))
                    .map(tmp -> Long.valueOf(tmp)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(formIdList)) {
                return instDataMapper.selectList(new QueryWrapper<ProcessInstDataDO>()
                        .eq("instance_id", instId)
                        .in("form_id", formIdList));
            }
        }
        return result;
    }

    private void assembleConditions(QueryWrapper<ProcessInstDO> queryWrapper, String name, Long startTime, Long endTime,
                                    Long curHandlerId, Long submitBy, String status) {

        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }

        if (startTime != null) {
            queryWrapper.ge("submit_time", LocalDateTimeUtil.of(startTime));
        }

        if (endTime != null) {
            queryWrapper.le("end_time", LocalDateTimeUtil.of(endTime));
        }

        if (curHandlerId != null) {
            // 虽然 cur_handler_id 中存的既可能是 userId 也可能是 roleId
            // 但这里只根据 userId 去查，不根据 roleId 去查
            queryWrapper.like("cur_handler_id", curHandlerId);
        }

        if (submitBy != null) {
            queryWrapper.eq("submit_by", submitBy);
        }

        if (StrUtil.isNotBlank(status)) {
            queryWrapper.eq("status", status);
        }
    }

    public List<ProcessInstOperationDO> queryOperationList(Long instId) {
        return instOperationMapper.selectList(
                new QueryWrapper<ProcessInstOperationDO>()
                        .eq("instance_id", instId)
                        .orderByDesc("create_time"));
    }
}
