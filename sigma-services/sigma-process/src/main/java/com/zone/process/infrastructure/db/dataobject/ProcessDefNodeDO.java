package com.zone.process.infrastructure.db.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
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
 * @since 2021-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("process_def_node")
@ApiModel(value="ProcessDefNodeDO对象", description="流程定义中的节点信息")
public class ProcessDefNodeDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "流程定义id")
    private Long defId;

    @ApiModelProperty(value = "流程节点名称")
    private String name;

    @ApiModelProperty(value = "bpmn2.0中节点的类型, userTask/startEvent/endEvent")
    private String bpmnNodeType;

    @ApiModelProperty(value = "bpmn2.0中节点的id")
    private String bpmnNodeId;

    @ApiModelProperty(value = "当前节点所属的父节点的id")
    private String parentBpmnNodeId;

    @ApiModelProperty(value = "当前流程的处理人类型，ID/PARAM/PAR_MULTI_INSTANCE/SEQ_MULTI_INSTANCE")
    private String nodePeopleType;

    @ApiModelProperty(value = "当前节点处理人的值，可以是具体值，也可以是变量名")
    private String nodePeopleValue;

    @ApiModelProperty(value = "挂靠在当前节点用于输入的表单， 用,隔开")
    private String inputFormIds;

    @ApiModelProperty(value = "挂靠在当前节点用于展示的表单， 用,隔开")
    private String displayFormIds;

    @ApiModelProperty(value = "乐观锁版本")
    @Version
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "user_id")
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @ApiModelProperty(value = "user_name")
    @TableField(fill = FieldFill.INSERT)
    private String createName;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "user_id")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @ApiModelProperty(value = "user_name")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateName;


}
