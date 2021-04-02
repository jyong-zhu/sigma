package com.zone.process;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zone.process.application.service.command.cmd.TaskOperateCommand;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/2 10:20 上午
 * @Description:
 */
public class ProcessTaskTest {

    /**
     * 将任务从处理节点1提交至处理节点2
     */
    @Test
    public void operate1() {
        String taskId = "";
        String operationType = "complete";
        List<String> identityList = Lists.newArrayList();
        Map<Long, Map<String, Object>> formDataMap = Maps.newHashMap();
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("user", "5,6,7");
        dataMap.put("complete", false);
        formDataMap.put(2L, dataMap);
        TaskOperateCommand operateCommand = new TaskOperateCommand()
                .setComment("处理节点1 ===> 处理节点2")
                .setFormDataMap(formDataMap)
                .setIdentityList(identityList)
                .setOperationType(operationType)
                .setTaskId(taskId);

        System.out.println(JSONUtil.toJsonStr(operateCommand));
    }

    /**
     * 将任务从处理节点2提交至处理节点1
     */
    @Test
    public void operate2() {
        String taskId = "";
        String operationType = "complete";
        List<String> identityList = Lists.newArrayList();
        Map<Long, Map<String, Object>> formDataMap = Maps.newHashMap();
        TaskOperateCommand operateCommand = new TaskOperateCommand()
                .setComment("处理节点2 ===> 处理节点1")
                .setFormDataMap(formDataMap)
                .setIdentityList(identityList)
                .setOperationType(operationType)
                .setTaskId(taskId);

        System.out.println(JSONUtil.toJsonStr(operateCommand));
    }

    /**
     * 转派任务
     */
    @Test
    public void operate3() {
        String taskId = "";
        String operationType = "update";
        List<String> identityList = Lists.newArrayList("77", "88", "99");
        Map<Long, Map<String, Object>> formDataMap = Maps.newHashMap();
        TaskOperateCommand operateCommand = new TaskOperateCommand()
                .setComment("处理节点转派任务")
                .setFormDataMap(formDataMap)
                .setIdentityList(identityList)
                .setOperationType(operationType)
                .setTaskId(taskId);

        System.out.println(JSONUtil.toJsonStr(operateCommand));
    }

    /**
     * 将任务从处理节点1提交至结束节点
     */
    @Test
    public void operate4() {
        String taskId = "";
        String operationType = "complete";
        List<String> identityList = Lists.newArrayList();
        Map<Long, Map<String, Object>> formDataMap = Maps.newHashMap();
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("complete", true);
        formDataMap.put(2L, dataMap);
        TaskOperateCommand operateCommand = new TaskOperateCommand()
                .setComment("处理节点1 ===> 结束节点")
                .setFormDataMap(formDataMap)
                .setIdentityList(identityList)
                .setOperationType(operationType)
                .setTaskId(taskId);

        System.out.println(JSONUtil.toJsonStr(operateCommand));
    }
}
