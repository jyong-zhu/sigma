package com.zone.process.shared.process;

import com.zone.process.shared.process.valueobject.ProcessInstanceVO;
import com.zone.process.shared.process.valueobject.TaskVO;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:53 下午
 * @Description:
 */
public interface ProcessEngineQueryAPI {

    ProcessInstanceVO syncInstance(String procInstId);

    TaskVO queryTaskById(String taskId);
}
