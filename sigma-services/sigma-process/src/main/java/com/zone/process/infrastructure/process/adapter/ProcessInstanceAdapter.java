package com.zone.process.infrastructure.process.adapter;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.zone.process.shared.process.valueobject.ProcessInstanceVO;
import com.zone.process.shared.process.valueobject.TaskVO;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/31 2:47 下午
 * @Description:
 */
public class ProcessInstanceAdapter {

    public static ProcessInstanceVO getProcessInstanceVO(String procInstId, ProcessInstance processInstance, List<String> activityList, List<String> curHandlerList) {
        return new ProcessInstanceVO()
                .setIsFinished(processInstance == null)
                .setCurHandlerIdList(CollectionUtil.isNotEmpty(curHandlerList) ? curHandlerList : Lists.newArrayList())
                .setProcInstId(procInstId)
                .setCurNodeIdList(CollectionUtil.isNotEmpty(activityList) ? activityList : Lists.newArrayList());
    }

    public static TaskVO getTaskVO(Task task) {
        if (task != null) {
            TaskVO taskVO = new TaskVO();
            taskVO.setCurNodeId(task.getTaskDefinitionKey());
            taskVO.setCurNodeName(task.getName());
            taskVO.setProcInstId(task.getProcessInstanceId());
            taskVO.setTaskId(task.getId());
            taskVO.setTaskName(task.getName());
            return taskVO;
        }
        return null;
    }

    public static List<TaskVO> getTaskVOList(List<Task> taskList) {
        if (CollectionUtil.isEmpty(taskList)) {
            return Lists.newArrayList();
        }
        return taskList.stream().filter(task -> task != null)
                .map(task -> getTaskVO(task)).collect(Collectors.toList());
    }
}
