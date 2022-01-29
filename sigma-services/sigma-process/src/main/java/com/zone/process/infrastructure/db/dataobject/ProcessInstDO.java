package com.zone.process.infrastructure.db.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 流程实例信息
 * </p>
 *
 * @author Jone
 * @since 2021-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("process_inst")
@ApiModel(value = "ProcessInstDO对象", description = "流程实例信息")
public class ProcessInstDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "camunda中的流程实例id")
    private String procInstId;

    @ApiModelProperty(value = "流程定义id")
    private Long defId;

    @ApiModelProperty(value = "流程定义名称")
    private String defName;

    @ApiModelProperty(value = "流程定义key")
    private String procDefKey;

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
