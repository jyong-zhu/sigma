package com.zone.process.infrastructure.process.impl;

import com.google.common.collect.Lists;
import com.zone.process.infrastructure.process.adapter.ProcessInstanceAdapter;
import com.zone.process.shared.process.ProcessEngineQueryAPI;
import com.zone.process.shared.process.valueobject.ProcessInstanceVO;
import com.zone.process.shared.process.valueobject.TaskVO;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 5:27 下午
 * @Description:
 */
@Service
public class CamundaQueryService implements ProcessEngineQueryAPI {

    @Autowired
    private RuntimeService runtimeService;


    @Override
    public ProcessInstanceVO syncInstance(String procInstId) {

        ProcessInstance processInstance = queryInstanceById(procInstId);
        List<String> activityList = processInstance == null ? Lists.newArrayList() :
                runtimeService.getActiveActivityIds(processInstance.getId());
        List<String> curHandlerList = Lists.newArrayList();
        // todo 添加mapper

        return ProcessInstanceAdapter.getProcessInstanceVO(procInstId, processInstance,
                activityList, curHandlerList);
    }

    @Override
    public TaskVO queryTaskById(String taskId) {
        return null;
    }

    private ProcessInstance queryInstanceById(String procInstId) {
        return runtimeService.createProcessInstanceQuery()
                .processInstanceId(procInstId)
                .singleResult();
    }
}
