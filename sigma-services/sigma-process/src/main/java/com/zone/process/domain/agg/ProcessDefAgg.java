package com.zone.process.domain.agg;

import com.google.common.base.Preconditions;
import com.zone.process.domain.valueobject.DefNodeVO;
import com.zone.process.shared.enums.BpmnNodeTypeEnum;
import com.zone.process.shared.process.valueobject.ProcessDefinitionVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 2:59 下午
 * @Description: 流程定义的聚合
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProcessDefAgg {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty(value = "camunda中的流程定义id，关联到ACT_RE_PROCDEF")
    private String procDefId;

    @ApiModelProperty(value = "camunda中的流程定义key，关联到ACT_RE_PROCDEF的key")
    private String procDefKey;

    @ApiModelProperty(value = "camunda中的流程定义版本，关联到ACT_RE_PROCDEF的version")
    private Integer procDefVersion;

    @ApiModelProperty(value = "流程定义名称")
    private String name;

    @ApiModelProperty(value = "流程定义的状态，0-禁用 1-启用")
    private Boolean status;

    @ApiModelProperty("bpmn2.0 xml")
    private String bpmnXml;

    @ApiModelProperty(value = "开始节点的id")
    private String startBpmnNodeId;

    @ApiModelProperty("当前流程定义下所关联的全部表单id, 用,隔开")
    private String formIds;

    @ApiModelProperty("分类展示图标")
    private String iconUrl;

    @ApiModelProperty("流程定义中节点的信息")
    private List<DefNodeVO> nodeVOList;

    /**
     * 初始化流程定义的数据，包括聚合根的id、版本信息等
     */
    public void init(Long id, ProcessDefinitionVO definitionVO) {
        this.setId(id);
        this.setProcDefId(definitionVO.getProcDefId());
        this.setProcDefKey(definitionVO.getProcDefKey());
        this.setProcDefVersion(definitionVO.getProcDefVersion());
        this.setStatus(true);

        // 解析 xml 文件，填充节点的信息
        this.nodeVOList = DefNodeVO.parseNodeList(this.bpmnXml, this.nodeVOList);

        // 获取开始节点的信息
        DefNodeVO startNode = DefNodeVO.getNodeByType(this.nodeVOList, BpmnNodeTypeEnum.START_EVENT, "");
        DefNodeVO endNode = DefNodeVO.getNodeByType(this.nodeVOList, BpmnNodeTypeEnum.START_EVENT, "");
        Preconditions.checkState(startNode != null && endNode != null, "xml格式错误");
        this.setStartBpmnNodeId(startNode.getBpmnNodeId());
    }

    /**
     * 获取指定节点
     */
    public DefNodeVO getNodeByNodeId(String curNodeId) {
        for (DefNodeVO nodeVO : this.getNodeVOList()) {
            if (nodeVO.getBpmnNodeId().equals(curNodeId)) {
                return nodeVO;
            }
        }
        return null;
    }
}
