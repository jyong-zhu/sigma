package com.zone.process.shared.process;

import com.zone.process.shared.process.valueobject.ProcessDefinitionVO;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/28 9:46 上午
 * @Description:
 */
public interface ProcessEngineCommandAPI {

    ProcessDefinitionVO deploy(String xml, String name);
}
