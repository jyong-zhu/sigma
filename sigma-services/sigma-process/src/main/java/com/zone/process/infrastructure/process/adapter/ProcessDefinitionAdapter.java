package com.zone.process.infrastructure.process.adapter;

import com.zone.process.shared.process.valueobject.ProcessDefinitionVO;
import org.camunda.bpm.engine.repository.ProcessDefinition;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 5:31 下午
 * @Description:
 */
public class ProcessDefinitionAdapter {

  public static ProcessDefinitionVO getProcessDefinitionVO(ProcessDefinition processDefinition) {
    if (processDefinition == null) {
      return null;
    }
    return new ProcessDefinitionVO()
        .setProcDefId(processDefinition.getId())
        .setProcDefKey(processDefinition.getKey())
        .setProcDefVersion(processDefinition.getVersion());
  }
}
