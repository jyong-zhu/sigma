package com.zone.process.infrastructure.process.adapter;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.zone.process.shared.process.valueobject.ProcessInstanceVO;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import java.util.List;

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
                .setCurNodeIdList(CollectionUtil.isNotEmpty(activityList) ? curHandlerList : Lists.newArrayList());
    }
}
