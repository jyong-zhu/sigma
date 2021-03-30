package com.zone.process.shared.process;

import com.zone.process.shared.process.valueobject.ProcessDefinitionVO;
import com.zone.process.shared.process.valueobject.ProcessInstanceVO;

import java.util.Map;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/28 9:46 上午
 * @Description:
 */
public interface ProcessEngineCommandAPI {

    ProcessDefinitionVO deploy(String xml, String name);

    String startInstance(String processDefKey, Map<String, Object> paramMap);

    ProcessInstanceVO syncInstance(String procInstId);

    void stopInstance(String procInstId, String comment);
}
