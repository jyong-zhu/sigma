package com.zone.process.shared.process;

import com.zone.process.shared.process.valueobject.ProcessDefinitionVO;

import java.util.List;
import java.util.Map;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/28 9:46 上午
 * @Description:
 */
public interface ProcessEngineCommandAPI {

    ProcessDefinitionVO deploy(String xml, String name);

    String startInstance(String processDefKey, Map<String, Object> paramMap);

    void stopInstance(String procInstId, String comment);

    void operateTask(String taskId, String procInstId, Map<String, Object> paramMap, List<String> identityList, String operationType);
}
