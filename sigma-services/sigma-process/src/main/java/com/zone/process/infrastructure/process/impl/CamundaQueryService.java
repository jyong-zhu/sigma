package com.zone.process.infrastructure.process.impl;

import com.zone.process.shared.process.ProcessEngineQueryAPI;
import com.zone.process.shared.process.valueobject.ProcessInstanceVO;
import com.zone.process.shared.process.valueobject.TaskVO;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 5:27 下午
 * @Description:
 */
@Service
public class CamundaQueryService implements ProcessEngineQueryAPI {

    @Override
    public ProcessInstanceVO syncInstance(String procInstId) {
        return null;
    }

    @Override
    public TaskVO queryTaskById(String taskId) {
        return null;
    }
}
