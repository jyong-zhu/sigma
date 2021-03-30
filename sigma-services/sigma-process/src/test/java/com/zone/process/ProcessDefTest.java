package com.zone.process;

import cn.hutool.json.JSONUtil;
import com.zone.process.application.service.command.cmd.DefDeployCommand;
import com.zone.process.application.service.command.cmd.DefNodeCommand;
import com.zone.process.application.service.command.cmd.DefNodePropertyCommand;
import com.zone.process.application.service.command.cmd.DefNodeVariableCommand;
import com.zone.process.shared.enums.NodePeopleTypeEnum;
import org.apache.commons.compress.utils.Lists;
import org.junit.Test;

import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/30 4:00 下午
 * @Description:
 */
public class ProcessDefTest {

    private final static String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<bpmn:definitions xmlns:bpmn=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:camunda=\"http://camunda.org/schema/1.0/bpmn\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" id=\"Definitions_049bjpo\" targetNamespace=\"http://bpmn.io/schema/bpmn\" exporter=\"Camunda Modeler\" exporterVersion=\"4.2.0\">\n" +
            "  <bpmn:process id=\"Process_1e60ptt\" isExecutable=\"true\">\n" +
            "    <bpmn:startEvent id=\"StartEvent_1\" name=\"发起节点\">\n" +
            "      <bpmn:outgoing>Flow_0xcri3l</bpmn:outgoing>\n" +
            "    </bpmn:startEvent>\n" +
            "    <bpmn:sequenceFlow id=\"Flow_0xcri3l\" sourceRef=\"StartEvent_1\" targetRef=\"Activity_0g1jh2y\" />\n" +
            "    <bpmn:userTask id=\"Activity_0g1jh2y\" name=\"处理节点\" camunda:candidateGroups=\"${userIds}\">\n" +
            "      <bpmn:incoming>Flow_0xcri3l</bpmn:incoming>\n" +
            "      <bpmn:outgoing>Flow_0zg12iz</bpmn:outgoing>\n" +
            "    </bpmn:userTask>\n" +
            "    <bpmn:sequenceFlow id=\"Flow_0zg12iz\" sourceRef=\"Activity_0g1jh2y\" targetRef=\"Event_0vydz1a\" />\n" +
            "    <bpmn:endEvent id=\"Event_0vydz1a\" name=\"结束节点\">\n" +
            "      <bpmn:incoming>Flow_0zg12iz</bpmn:incoming>\n" +
            "    </bpmn:endEvent>\n" +
            "  </bpmn:process>\n" +
            "  <bpmndi:BPMNDiagram id=\"BPMNDiagram_1\">\n" +
            "    <bpmndi:BPMNPlane id=\"BPMNPlane_1\" bpmnElement=\"Process_1e60ptt\">\n" +
            "      <bpmndi:BPMNEdge id=\"Flow_0zg12iz_di\" bpmnElement=\"Flow_0zg12iz\">\n" +
            "        <di:waypoint x=\"400\" y=\"120\" />\n" +
            "        <di:waypoint x=\"482\" y=\"120\" />\n" +
            "      </bpmndi:BPMNEdge>\n" +
            "      <bpmndi:BPMNEdge id=\"Flow_0xcri3l_di\" bpmnElement=\"Flow_0xcri3l\">\n" +
            "        <di:waypoint x=\"198\" y=\"120\" />\n" +
            "        <di:waypoint x=\"300\" y=\"120\" />\n" +
            "      </bpmndi:BPMNEdge>\n" +
            "      <bpmndi:BPMNShape id=\"_BPMNShape_StartEvent_2\" bpmnElement=\"StartEvent_1\">\n" +
            "        <dc:Bounds x=\"162\" y=\"102\" width=\"36\" height=\"36\" />\n" +
            "        <bpmndi:BPMNLabel>\n" +
            "          <dc:Bounds x=\"158\" y=\"145\" width=\"44\" height=\"14\" />\n" +
            "        </bpmndi:BPMNLabel>\n" +
            "      </bpmndi:BPMNShape>\n" +
            "      <bpmndi:BPMNShape id=\"Activity_15am6lt_di\" bpmnElement=\"Activity_0g1jh2y\">\n" +
            "        <dc:Bounds x=\"300\" y=\"80\" width=\"100\" height=\"80\" />\n" +
            "      </bpmndi:BPMNShape>\n" +
            "      <bpmndi:BPMNShape id=\"Event_0vydz1a_di\" bpmnElement=\"Event_0vydz1a\">\n" +
            "        <dc:Bounds x=\"482\" y=\"102\" width=\"36\" height=\"36\" />\n" +
            "        <bpmndi:BPMNLabel>\n" +
            "          <dc:Bounds x=\"478\" y=\"145\" width=\"44\" height=\"14\" />\n" +
            "        </bpmndi:BPMNLabel>\n" +
            "      </bpmndi:BPMNShape>\n" +
            "    </bpmndi:BPMNPlane>\n" +
            "  </bpmndi:BPMNDiagram>\n" +
            "</bpmn:definitions>";

    @Test
    public void deploy() {
        DefDeployCommand deployCommand = new DefDeployCommand()
                .setBpmnXml(xml)
                .setCategoryId(1L)
                .setFormIds("1,2,3,4")
                .setName("测试流程定义");
        List<DefNodeCommand> nodeCommandList = Lists.newArrayList();

        // 开始节点的信息
        List<DefNodeVariableCommand> variableCommandList = Lists.newArrayList();
        variableCommandList.add(new DefNodeVariableCommand()
                .setVariableName("userIds")
                .setJavaType("java.lang.String")
                .setFormId("1")
                .setFieldId("user")
                .setDefaultValue(""));
        DefNodeCommand startNode = new DefNodeCommand()
                .setBpmnNodeId("StartEvent_1")
                .setDisplayFormIds("")
                .setInputFormIds("1,2")
                .setVariableCommandList(variableCommandList);
        nodeCommandList.add(startNode);

        // 处理节点的信息
        List<DefNodePropertyCommand> propertyCommandList = Lists.newArrayList();
        propertyCommandList.add(new DefNodePropertyCommand()
                .setPropertyName("button")
                .setPropertyValue("[{\"roleId\":\"12345\",\"buttonList\":[\"保存\",\"关闭\",\"转派\",\"完结\"]},{\"roleId\":\"67890\",\"buttonList\":[\"转派\",\"提交\"]}]\n")
                .setDesc("按钮相关"));
        DefNodeCommand userNode = new DefNodeCommand()
                .setBpmnNodeId("Activity_0g1jh2y")
                .setNodePeopleType(NodePeopleTypeEnum.PARAM.getCode())
                .setNodePeopleValue("userIds")
                .setDisplayFormIds("1,2")
                .setInputFormIds("3,4")
                .setPropertyCommandList(propertyCommandList);
        nodeCommandList.add(userNode);

        deployCommand.setNodeList(nodeCommandList);

        System.out.println(JSONUtil.toJsonStr(deployCommand));
    }
}
