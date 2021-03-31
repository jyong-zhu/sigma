package com.zone.process.infrastructure.process.impl;

import com.zone.process.infrastructure.process.adapter.ProcessDefinitionAdapter;
import com.zone.process.shared.process.ProcessEngineCommandAPI;
import com.zone.process.shared.process.valueobject.ProcessDefinitionVO;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.xml.ModelValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

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
            log.error("xml文件解析出错[{}]", e.getMessage());
        } catch (IOException e) {
            log.error("流程部署出错[{}]", e.getMessage());
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
            log.error("发起流程实例出错：[{}]", e.getMessage());
        }
        return null;
    }

    @Override
    public void stopInstance(String procInstId, String comment) {

    }

    @Override
    public void operateTask(String taskId, Map<String, Object> paramMap, String operationType) {

    }
}
