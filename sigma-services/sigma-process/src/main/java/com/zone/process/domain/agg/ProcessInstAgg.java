package com.zone.process.domain.agg;

import com.google.common.collect.Lists;
import com.zone.commons.entity.LoginUser;
import com.zone.process.domain.valueobject.DefNodeVO;
import com.zone.process.domain.valueobject.InstDataVO;
import com.zone.process.domain.valueobject.InstOperationVO;
import com.zone.process.shared.enums.InstanceOperationTypeEnum;
import com.zone.process.shared.enums.InstanceStatusTypeEnum;
import com.zone.process.shared.process.valueobject.ProcessInstanceVO;
import com.zone.process.shared.process.valueobject.TaskVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 2:59 下午
 * @Description: 流程实例的聚合
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProcessInstAgg {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "camunda中的流程实例id")
    private String procInstId;

    @ApiModelProperty(value = "流程定义id")
    private Long defId;

    @ApiModelProperty(value = "流程实例名称")
    private String name;

    @ApiModelProperty(value = "流程实例的状态，进行中/已结束")
    private String status;

    @ApiModelProperty(value = "当前流程停留的节点id, 多个用,隔开")
    private String curNodeId;

    @ApiModelProperty(value = "当前流程停留的节点名称, 多个用,隔开")
    private String curNodeName;

    @ApiModelProperty(value = "当前处理人的id，多个用,隔开，支持userId/roleId")
    private String curHandlerId;

    @ApiModelProperty(value = "流程实例要求时间")
    private LocalDateTime dueTime;

    @ApiModelProperty(value = "流程实例发起时间")
    private LocalDateTime submitTime;

    @ApiModelProperty(value = "流程实例提交人的user_id")
    private Long submitBy;

    @ApiModelProperty(value = "流程实例提交人的姓名")
    private String submitName;

    @ApiModelProperty(value = "操作流程实例的备注")
    private String comment;

    @ApiModelProperty(value = "描述信息")
    private String description;

    @ApiModelProperty("流程实例的数据列表")
    private List<InstDataVO> dataVOList;

    @ApiModelProperty("流程实例的操作列表")
    private List<InstOperationVO> operationVOList;

    /**
     * 初始化流程实例
     */
    public void init(Long id, String procInstId, String nodeId, Map<Long, Map<String, Object>> formDataMap, LoginUser loginUser) {
        this.setId(id);
        this.setSubmitBy(loginUser.getUserId());
        this.setSubmitName(loginUser.getUserName());
        this.setSubmitTime(LocalDateTime.now());
        this.setProcInstId(procInstId);
        this.setDataVOList(InstDataVO.generateDataVOList(nodeId, formDataMap));
        this.setOperationVOList(Lists.newArrayList(InstOperationVO.generateOperationVO(nodeId, InstanceOperationTypeEnum.START.getCode(), this.comment, loginUser)));
    }

    /**
     * 中止流程实例
     */
    public void stop(String comment, String curNodeId, LoginUser loginUser) {
        this.setStatus(InstanceStatusTypeEnum.FINISHED.getCode());
        this.getOperationVOList().add(InstOperationVO.generateOperationVO(curNodeId, InstanceOperationTypeEnum.STOP.getCode(), comment, loginUser));
    }

    /**
     * 同步流程实例的当前状态
     */
    public void sync(ProcessInstanceVO processInstanceVO, ProcessDefAgg defAgg) {
        this.setStatus(processInstanceVO.getIsFinished() ? InstanceStatusTypeEnum.FINISHED.getCode() : InstanceStatusTypeEnum.ACTIVE.getCode());
        this.setCurHandlerId(processInstanceVO.getCurHandlerIdList().stream().collect(Collectors.joining(",")));
        this.setCurNodeId(processInstanceVO.getCurNodeIdList().stream().collect(Collectors.joining(",")));
        List<DefNodeVO> defNodeVOList = defAgg.getNodeByIdList(processInstanceVO.getCurNodeIdList());
        this.setCurNodeName(defNodeVOList.stream().map(tmp -> tmp.getName()).collect(Collectors.joining(",")));
    }

    /**
     * 操作任务
     */
    public void operateTask(TaskVO taskVO, String operationType, String comment, Map<Long, Map<String, Object>> formDataMap, LoginUser loginUser) {
        this.getDataVOList().addAll(InstDataVO.generateDataVOList(taskVO.getCurNodeId(), formDataMap));
        this.getOperationVOList().add(InstOperationVO.generateOperationVO(taskVO.getCurNodeId(), operationType, comment, loginUser));
    }
}
