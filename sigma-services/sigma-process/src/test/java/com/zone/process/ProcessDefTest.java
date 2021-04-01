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
            "    <bpmn:userTask id=\"Activity_0g1jh2y\" name=\"处理节点1\" camunda:candidateGroups=\"${user1}\">\n" +
            "      <bpmn:incoming>Flow_0xcri3l</bpmn:incoming>\n" +
            "      <bpmn:incoming>Flow_0az6g18</bpmn:incoming>\n" +
            "      <bpmn:outgoing>Flow_082pyfo</bpmn:outgoing>\n" +
            "    </bpmn:userTask>\n" +
            "    <bpmn:endEvent id=\"Event_186b38v\" name=\"结束节点\">\n" +
            "      <bpmn:incoming>Flow_08equrw</bpmn:incoming>\n" +
            "    </bpmn:endEvent>\n" +
            "    <bpmn:userTask id=\"Activity_035v77d\" name=\"处理节点2\" camunda:candidateGroups=\"${user2}\">\n" +
            "      <bpmn:incoming>Flow_0y1689k</bpmn:incoming>\n" +
            "      <bpmn:outgoing>Flow_0az6g18</bpmn:outgoing>\n" +
            "    </bpmn:userTask>\n" +
            "    <bpmn:sequenceFlow id=\"Flow_0az6g18\" sourceRef=\"Activity_035v77d\" targetRef=\"Activity_0g1jh2y\" />\n" +
            "    <bpmn:exclusiveGateway id=\"Gateway_0z0h3v3\" name=\"排他网关\">\n" +
            "      <bpmn:incoming>Flow_082pyfo</bpmn:incoming>\n" +
            "      <bpmn:outgoing>Flow_08equrw</bpmn:outgoing>\n" +
            "      <bpmn:outgoing>Flow_0y1689k</bpmn:outgoing>\n" +
            "    </bpmn:exclusiveGateway>\n" +
            "    <bpmn:sequenceFlow id=\"Flow_08equrw\" sourceRef=\"Gateway_0z0h3v3\" targetRef=\"Event_186b38v\">\n" +
            "      <bpmn:conditionExpression xsi:type=\"bpmn:tFormalExpression\">${isComplete}</bpmn:conditionExpression>\n" +
            "    </bpmn:sequenceFlow>\n" +
            "    <bpmn:sequenceFlow id=\"Flow_0y1689k\" sourceRef=\"Gateway_0z0h3v3\" targetRef=\"Activity_035v77d\">\n" +
            "      <bpmn:conditionExpression xsi:type=\"bpmn:tFormalExpression\">${!isComplete}</bpmn:conditionExpression>\n" +
            "    </bpmn:sequenceFlow>\n" +
            "    <bpmn:sequenceFlow id=\"Flow_082pyfo\" sourceRef=\"Activity_0g1jh2y\" targetRef=\"Gateway_0z0h3v3\" />\n" +
            "  </bpmn:process>\n" +
            "  <bpmndi:BPMNDiagram id=\"BPMNDiagram_1\">\n" +
            "    <bpmndi:BPMNPlane id=\"BPMNPlane_1\" bpmnElement=\"Process_1e60ptt\">\n" +
            "      <bpmndi:BPMNEdge id=\"Flow_082pyfo_di\" bpmnElement=\"Flow_082pyfo\">\n" +
            "        <di:waypoint x=\"400\" y=\"230\" />\n" +
            "        <di:waypoint x=\"505\" y=\"230\" />\n" +
            "      </bpmndi:BPMNEdge>\n" +
            "      <bpmndi:BPMNEdge id=\"Flow_0y1689k_di\" bpmnElement=\"Flow_0y1689k\">\n" +
            "        <di:waypoint x=\"530\" y=\"205\" />\n" +
            "        <di:waypoint x=\"530\" y=\"160\" />\n" +
            "      </bpmndi:BPMNEdge>\n" +
            "      <bpmndi:BPMNEdge id=\"Flow_08equrw_di\" bpmnElement=\"Flow_08equrw\">\n" +
            "        <di:waypoint x=\"555\" y=\"230\" />\n" +
            "        <di:waypoint x=\"652\" y=\"230\" />\n" +
            "      </bpmndi:BPMNEdge>\n" +
            "      <bpmndi:BPMNEdge id=\"Flow_0az6g18_di\" bpmnElement=\"Flow_0az6g18\">\n" +
            "        <di:waypoint x=\"480\" y=\"120\" />\n" +
            "        <di:waypoint x=\"350\" y=\"120\" />\n" +
            "        <di:waypoint x=\"350\" y=\"190\" />\n" +
            "      </bpmndi:BPMNEdge>\n" +
            "      <bpmndi:BPMNEdge id=\"Flow_0xcri3l_di\" bpmnElement=\"Flow_0xcri3l\">\n" +
            "        <di:waypoint x=\"228\" y=\"230\" />\n" +
            "        <di:waypoint x=\"300\" y=\"230\" />\n" +
            "      </bpmndi:BPMNEdge>\n" +
            "      <bpmndi:BPMNShape id=\"Activity_15am6lt_di\" bpmnElement=\"Activity_0g1jh2y\">\n" +
            "        <dc:Bounds x=\"300\" y=\"190\" width=\"100\" height=\"80\" />\n" +
            "      </bpmndi:BPMNShape>\n" +
            "      <bpmndi:BPMNShape id=\"Event_186b38v_di\" bpmnElement=\"Event_186b38v\">\n" +
            "        <dc:Bounds x=\"652\" y=\"212\" width=\"36\" height=\"36\" />\n" +
            "        <bpmndi:BPMNLabel>\n" +
            "          <dc:Bounds x=\"648\" y=\"255\" width=\"44\" height=\"14\" />\n" +
            "        </bpmndi:BPMNLabel>\n" +
            "      </bpmndi:BPMNShape>\n" +
            "      <bpmndi:BPMNShape id=\"Activity_0hzbm9n_di\" bpmnElement=\"Activity_035v77d\">\n" +
            "        <dc:Bounds x=\"480\" y=\"80\" width=\"100\" height=\"80\" />\n" +
            "      </bpmndi:BPMNShape>\n" +
            "      <bpmndi:BPMNShape id=\"Gateway_0z0h3v3_di\" bpmnElement=\"Gateway_0z0h3v3\" isMarkerVisible=\"true\">\n" +
            "        <dc:Bounds x=\"505\" y=\"205\" width=\"50\" height=\"50\" />\n" +
            "        <bpmndi:BPMNLabel>\n" +
            "          <dc:Bounds x=\"508\" y=\"262\" width=\"44\" height=\"14\" />\n" +
            "        </bpmndi:BPMNLabel>\n" +
            "      </bpmndi:BPMNShape>\n" +
            "      <bpmndi:BPMNShape id=\"_BPMNShape_StartEvent_2\" bpmnElement=\"StartEvent_1\">\n" +
            "        <dc:Bounds x=\"192\" y=\"212\" width=\"36\" height=\"36\" />\n" +
            "        <bpmndi:BPMNLabel>\n" +
            "          <dc:Bounds x=\"188\" y=\"255\" width=\"44\" height=\"14\" />\n" +
            "        </bpmndi:BPMNLabel>\n" +
            "      </bpmndi:BPMNShape>\n" +
            "    </bpmndi:BPMNPlane>\n" +
            "  </bpmndi:BPMNDiagram>\n" +
            "</bpmn:definitions>\n";

    @Test
    public void deploy() {
        DefDeployCommand deployCommand = new DefDeployCommand()
                .setBpmnXml(xml)
                .setCategoryId(1L)
                .setFormIds("1,2")
                .setName("报批流程定义");
        List<DefNodeCommand> nodeCommandList = Lists.newArrayList();

        // 开始节点的信息
        List<DefNodeVariableCommand> variableCommandList = Lists.newArrayList();
        variableCommandList.add(new DefNodeVariableCommand()
                .setVariableName("user1")
                .setJavaType("java.lang.String")
                .setFormId("1")
                .setFieldId("user")
                .setDefaultValue(""));
        DefNodeCommand startNode = new DefNodeCommand()
                .setBpmnNodeId("StartEvent_1")
                .setDisplayFormIds("")
                .setInputFormIds("1")
                .setVariableCommandList(variableCommandList);
        nodeCommandList.add(startNode);

        // 处理节点1的信息
        variableCommandList = Lists.newArrayList();
        variableCommandList.add(new DefNodeVariableCommand()
                .setVariableName("user2")
                .setJavaType("java.lang.String")
                .setFormId("2")
                .setFieldId("user")
                .setDefaultValue(""));
        variableCommandList.add(new DefNodeVariableCommand()
                .setVariableName("isComplete")
                .setJavaType("java.lang.Boolean")
                .setFormId("2")
                .setFieldId("complete")
                .setDefaultValue("true"));
        DefNodeCommand node1 = new DefNodeCommand()
                .setBpmnNodeId("Activity_0g1jh2y")
                .setNodePeopleType(NodePeopleTypeEnum.PARAM.getCode())
                .setNodePeopleValue("user1")
                .setDisplayFormIds("1")
                .setInputFormIds("2")
                .setVariableCommandList(variableCommandList);
        nodeCommandList.add(node1);

        // 处理节点2的信息
        List<DefNodePropertyCommand> propertyCommandList = Lists.newArrayList();
        propertyCommandList.add(new DefNodePropertyCommand()
                .setPropertyName("属性名")
                .setPropertyValue("属性值")
                .setDesc("这是节点属性的配置信息"));
        DefNodeCommand userNode = new DefNodeCommand()
                .setBpmnNodeId("Activity_035v77d")
                .setNodePeopleType(NodePeopleTypeEnum.PARAM.getCode())
                .setNodePeopleValue("user2")
                .setDisplayFormIds("1,2")
                .setInputFormIds("")
                .setPropertyCommandList(propertyCommandList);
        nodeCommandList.add(userNode);
        deployCommand.setNodeList(nodeCommandList);

        System.out.println(JSONUtil.toJsonStr(deployCommand));
    }
}
