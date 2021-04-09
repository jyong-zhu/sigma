package com.zone.process.infrastructure.process.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.zone.commons.entity.Page;
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
                runtimeService.getActiveActivityIds(processInstance.getId())
                        .stream().distinct().collect(Collectors.toList());
        List<String> curHandlerList = queryIdentityOfInstance(procInstId);

        return ProcessInstanceAdapter.getProcessInstanceVO(procInstId, processInstance,
                activityList, curHandlerList);
    }

    @Override
    public TaskVO queryRelateTaskById(String taskId, Long userId, Long roleId) {
        List<String> identityList = generateIdentityList(userId, roleId);
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .taskCandidateGroupIn(identityList)
                .singleResult();
        return ProcessInstanceAdapter.getTaskVO(task);
    }

    @Override
    public List<TaskVO> queryRelateTaskList(Long userId, Long roleId) {
        List<String> identityList = generateIdentityList(userId, roleId);
        List<Task> taskList = taskService.createTaskQuery()
                .taskCandidateGroupIn(identityList)
                .list();
        return ProcessInstanceAdapter.getTaskVOList(taskList);
    }

    /**
     * 在内存中进行分页
     */
    @Override
    public Page<TaskVO> pageTaskList(List<String> taskIdList, List<String> procInstIdList, Integer pageNo, Integer pageSize) {
        // 所有属于 procInstIdList 的任务
        List<Task> taskList = taskService.createTaskQuery()
                .processInstanceIdIn(procInstIdList.toArray(new String[0]))
                .list();

        // 通过 taskIdList 进行过滤
        taskList = taskList.stream().filter(task -> taskIdList.contains(task.getId())).collect(Collectors.toList());

        Page<TaskVO> page = new Page<>();
        page.setPageNo(Long.valueOf(pageNo));
        page.setPageSize(Long.valueOf(pageSize));
        page.setTotalSize(Long.valueOf(taskList.size()));

        int start = (pageNo - 1) * pageSize;
        if (start >= taskList.size()) {
            page.setData(Lists.newArrayList());
        } else {
            int end = start + pageSize < taskList.size() + 1 ? start + pageSize : taskList.size();
            page.setData(ProcessInstanceAdapter.getTaskVOList(taskList.subList(start, end)));
        }
        return page;
    }

    /**
     * 获取 identityList
     */
    private List<String> generateIdentityList(Long userId, Long roleId) {
        List<String> identityList = Lists.newArrayList();
        if (userId != null) {
            identityList.add(userId.toString());
        }
        if (roleId != null) {
            identityList.add(roleId.toString());
        }
        return identityList;
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
