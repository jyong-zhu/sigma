package com.zone.process.infrastructure.process.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.zone.process.infrastructure.process.adapter.ProcessInstanceAdapter;
import com.zone.process.infrastructure.process.dataobject.ActRuIdentitylinkDO;
import com.zone.process.infrastructure.process.mapper.ActRuIdentitylinkMapper;
import com.zone.process.shared.process.ProcessEngineQueryAPI;
import com.zone.process.shared.process.valueobject.ProcessInstanceVO;
import com.zone.process.shared.process.valueobject.TaskVO;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 5:27 下午
 * @Description:
 */
@Service
public class CamundaQueryService implements ProcessEngineQueryAPI {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ActRuIdentitylinkMapper identityLinkMapper;


    @Override
    public ProcessInstanceVO syncInstance(String procInstId) {

        ProcessInstance processInstance = queryInstanceById(procInstId);
        List<String> activityList = processInstance == null ? Lists.newArrayList() :
                runtimeService.getActiveActivityIds(processInstance.getId());
        List<String> curHandlerList = queryIdentityOfInstance(procInstId);

        return ProcessInstanceAdapter.getProcessInstanceVO(procInstId, processInstance,
                activityList, curHandlerList);
    }

    @Override
    public TaskVO queryTaskById(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        return ProcessInstanceAdapter.getTaskVO(task);
    }

    private ProcessInstance queryInstanceById(String procInstId) {
        return runtimeService.createProcessInstanceQuery()
                .processInstanceId(procInstId)
                .singleResult();
    }

    /**
     * 查询流程实例的当前处理人
     */
    private List<String> queryIdentityOfInstance(String procInstId) {
        List<Task> taskList = taskService.createTaskQuery()
                .processInstanceId(procInstId)
                .list();
        if (CollectionUtil.isNotEmpty(taskList)) {
            List<ActRuIdentitylinkDO> identityLinkDOList = identityLinkMapper.selectList(new QueryWrapper<ActRuIdentitylinkDO>().in("TASK_ID_",
                    taskList.stream().map(tmp -> tmp.getId()).collect(Collectors.toList())));
            return identityLinkDOList.stream().map(tmp -> tmp.getGroupId()).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }
}
