package com.zone.process.infrastructure.process.adapter;

import com.zone.process.shared.process.valueobject.ProcessInstanceVO;
import org.camunda.bpm.engine.runtime.ProcessInstance;

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
                .setCurHandlerId(curHandlerList.stream().collect(Collectors.joining(",")))
                .setProcInstId(procInstId)
                .setCurNodeId(activityList.stream().collect(Collectors.joining(",")));
    }
}
