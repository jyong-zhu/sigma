package com.zone.process.domain.valueobject;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.XmlUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zone.process.shared.enums.BpmnNodePropertyEnum;
import com.zone.process.shared.enums.BpmnNodeTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 5:54 下午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DefNodeVO {

  @ApiModelProperty(value = "流程节点名称")
  private String name;

  @ApiModelProperty(value = "bpmn2.0中节点的类型, userTask/startEvent/endEvent")
  private String bpmnNodeType;

  @ApiModelProperty("节点id")
  private String bpmnNodeId;

  @ApiModelProperty(value = "当前节点所属的父节点的id")
  private String parentBpmnNodeId;

  @ApiModelProperty("当前节点处理人的类型，ID/PARAM/PAR_MULTI_INSTANCE/SEQ_MULTI_INSTANCE")
  private String nodePeopleType;

  @ApiModelProperty("当前节点处理人的值，具体值或变量名")
  private String nodePeopleValue;

  @ApiModelProperty("挂靠在当前节点用于输入的表单， 用,隔开")
  private String inputFormIds;

  @ApiModelProperty("挂靠在当前节点用于展示的表单， 用,隔开")
  private String displayFormIds;

  @ApiModelProperty("节点的扩展属性")
  private List<DefNodePropertyVO> propertyVOList;

  @ApiModelProperty("节点的变量信息")
  private List<DefNodeVariableVO> variableVOList;

  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty(value = "user_id")
  private Long createBy;

  @ApiModelProperty(value = "user_name")
  private String createName;

  /**
   * 解析 xml 文件，并封装节点的信息
   */
  public static List<DefNodeVO> parseNodeList(String bpmnXml, List<DefNodeVO> nodeVOList) {

    Map<String, DefNodeVO> nodeVOMap = CollectionUtil.isEmpty(nodeVOList) ? Maps.newHashMap() : nodeVOList.stream()
        .collect(Collectors.toMap(key -> key.getBpmnNodeId(), value -> value));
    Document document = XmlUtil.parseXml(bpmnXml);
    Element element = document.getDocumentElement();
    return travelElementTree(element, nodeVOMap, "", "");
  }

  /**
   * 遍历 xml 上的节点
   */
  private static List<DefNodeVO> travelElementTree(Element element, Map<String, DefNodeVO> nodeVOMap,
      String parentBpmnNodeId, String parentBpmnNodeType) {

    List<DefNodeVO> nodeList = Lists.newArrayList();
    if (element == null) {
      return nodeList;
    }

    // 获取节点的属性
    String nodeType = element.getNodeName();
    String bpmnNodeId = element.getAttribute(BpmnNodePropertyEnum.NODE_ID.getCode());
    String bpmnNodeName = element.getAttribute(BpmnNodePropertyEnum.NODE_NAME.getCode());

    // 只对指定类型的节点信息进行保存
    if (BpmnNodeTypeEnum.getByCode(nodeType) != null) {
      // 将父节点为 bpmn:process 类型的节点id改为 ""
      parentBpmnNodeId = "bpmn:process".equals(parentBpmnNodeType) ? "" : parentBpmnNodeId;
      DefNodeVO node = nodeVOMap.getOrDefault(bpmnNodeId, new DefNodeVO());

      node.setBpmnNodeId(bpmnNodeId)
          .setBpmnNodeType(nodeType)
          .setParentBpmnNodeId(parentBpmnNodeId)
          .setName(bpmnNodeName);

      nodeList.add(node);
    }

    // 遍历子节点
    List<Element> childElements = XmlUtil.transElements(element.getChildNodes());
    childElements.forEach(child -> {
      List<DefNodeVO> childNodeList = travelElementTree(child, nodeVOMap, bpmnNodeId, nodeType);
      nodeList.addAll(childNodeList);
    });

    return nodeList;

  }

  /**
   * 根据类型获取节点
   */
  public static DefNodeVO getNodeByType(List<DefNodeVO> nodeVOList, BpmnNodeTypeEnum type, String parentBpmnNodeId) {
    if (CollectionUtil.isEmpty(nodeVOList)) {
      return null;
    }
    return nodeVOList.stream().filter(node -> node != null)
        .filter(node -> type.getCode().equals(node.getBpmnNodeType())
            && parentBpmnNodeId.equals(node.getParentBpmnNodeId()))
        .findFirst().orElse(null);
  }
}
