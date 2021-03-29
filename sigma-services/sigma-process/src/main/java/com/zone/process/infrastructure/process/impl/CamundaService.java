package com.zone.process.infrastructure.process.impl;

import com.zone.process.shared.process.ProcessEngineCommandAPI;
import com.zone.process.shared.process.valueobject.ProcessDefinitionVO;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/28 9:47 上午
 * @Description:
 */
@Service
public class CamundaService implements ProcessEngineCommandAPI {

    @Override
    public ProcessDefinitionVO deploy(String xml, String name) {
        return null;
    }
}
