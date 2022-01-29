package com.zone.process.infrastructure.process.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Preconditions;
import com.zone.process.infrastructure.process.adapter.ProcessDefinitionAdapter;
import com.zone.process.infrastructure.process.dataobject.ActRuIdentitylinkDO;
import com.zone.process.infrastructure.process.mapper.ActRuIdentitylinkMapper;
import com.zone.process.shared.enums.TaskOperationTypeEnum;
import com.zone.process.shared.process.ProcessEngineCommandAPI;
import com.zone.process.shared.process.valueobject.ProcessDefinitionVO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.xml.ModelValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/28 9:47 上午
 * @Description:
 */
@Slf4j
@Service
public class CamundaCommandService implements ProcessEngineCommandAPI {

  @Autowired
  private RepositoryService repositoryService;

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private TaskService taskService;

  @Resource
  private ActRuIdentitylinkMapper identityLinkMapper;

  @Override
  public ProcessDefinitionVO deploy(String xml, String name) {
    try (InputStream validXmlStream = new ByteArrayInputStream(xml.getBytes());
        InputStream deployXmlStream = new ByteArrayInputStream(xml.getBytes())) {

      // 1. 校验 xml 文件
      BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(validXmlStream);
      Bpmn.validateModel(bpmnModelInstance);

      // 2. 部署 xml 并查询 camunda 中的 processDefinition
      Deployment deployment = repositoryService.createDeployment()
          .addInputStream(name + ".bpmn", deployXmlStream).deploy();
      ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
          .deploymentId(deployment.getId()).singleResult();

      return ProcessDefinitionAdapter.getProcessDefinitionVO(processDefinition);

    } catch (ModelValidationException e) {
      log.error("xml文件解析出错[{}]", e.getMessage(), e);
    } catch (IOException e) {
      log.error("流程部署出错[{}]", e.getMessage(), e);
    }

    return null;
  }

  @Override
  public String startInstance(String processDefKey, Map<String, Object> paramMap) {
    try {
      // 通过 defKey 发起流程，默认是最新版本的流程定义
      ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefKey, paramMap);
      return processInstance.getProcessInstanceId();
    } catch (Exception e) {
      log.error("发起流程实例出错: [{}]", e.getMessage(), e);
    }
    return null;
  }

  @Override
  public void stopInstance(String procInstId, String comment) {
    ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
        .processInstanceId(procInstId).singleResult();
    Preconditions.checkNotNull(processInstance, "流程实例已结束");

    try {
      // camunda 将 runtime 中的一些数据删除，并将 ACT_HI_PROCINST 中的流程实例状态改为 INTERNALLY_TERMINATED
      runtimeService.deleteProcessInstance(procInstId, comment);
    } catch (Exception e) {
      log.error("停止流程实例出错: [{}]", e.getMessage());
      Preconditions.checkState(false, "停止流程实例出错");
    }
  }

  @Override
  public void operateTask(String taskId, String procInstId, Map<String, Object> paramMap, List<String> identityList, String operationType) {
    try {
      TaskOperationTypeEnum type = TaskOperationTypeEnum.getByCode(operationType);
      switch (type) {
        case COMPLETE:
          taskService.complete(taskId, paramMap);
          break;
        case UPDATE:
          identityLinkMapper.delete(new QueryWrapper<ActRuIdentitylinkDO>().eq("TASK_ID_", taskId));
          identityList.stream().filter(identity -> StrUtil.isNotBlank(identity))
              .forEach(identity -> taskService.addCandidateGroup(taskId, identity));
          break;
      }
    } catch (Exception e) {
      log.error("操作任务出错: [{}]", e.getMessage());
      Preconditions.checkState(false, "操作任务出错");
    }

  }
}
