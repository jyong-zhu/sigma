package com.zone.process.infrastructure.db.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 流程定义中的节点信息
 * </p>
 *
 * @author Jone
 * @since 2021-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("process_def_node")
@ApiModel(value="ProcessDefNodeDO对象", description="流程定义中的节点信息")
public class ProcessDefNodeDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "流程定义id")
    private String defId;

    @ApiModelProperty(value = "流程节点名称")
    private String name;

    @ApiModelProperty(value = "bpmn2.0中节点的类型, userTask/startEvent/endEvent")
    private String bpmnNodeType;

    @ApiModelProperty(value = "bpmn2.0中节点的id")
    private String bpmnNodeId;

    @ApiModelProperty(value = "当前节点所属的父节点的id")
    private String parentBpmnNodeId;

    @ApiModelProperty(value = "当前流程的处理人类型，指定用户/指定角色/用户参数/角色参数")
    private String nodePeopleType;

    @ApiModelProperty(value = "当前流程的处理人的信息，可以为参数${xxx}, 也可以为具体的值，这部分信息存储在candidateUser中")
    private String nodePeopleValue;

    @ApiModelProperty(value = "挂靠在当前节点用于输入的表单， 用,隔开")
    private String inputFormIds;

    @ApiModelProperty(value = "挂靠在当前节点用于展示的表单， 用,隔开")
    private String displayFormIds;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "user_id")
    private Long createBy;

    @ApiModelProperty(value = "user_name")
    private String createName;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "user_id")
    private Long updateBy;

    @ApiModelProperty(value = "user_name")
    private String updateName;


}
