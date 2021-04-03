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
 * 流程实例操作信息
 * </p>
 *
 * @author Jone
 * @since 2021-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("process_inst_operation")
@ApiModel(value = "ProcessInstOperationDO对象", description = "流程实例操作信息")
public class ProcessInstOperationDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "流程实例id")
    private Long instanceId;

    @ApiModelProperty(value = "操作类型（start/complete/update/stop）")
    private String operationType;

    @ApiModelProperty(value = "操作所处的节点id")
    private String bpmnNodeId;

    @ApiModelProperty(value = "操作的任务id")
    private String taskId;

    @ApiModelProperty(value = "操作备注")
    private String comment;

    @ApiModelProperty(value = "操作人的user_id")
    private String operateBy;

    @ApiModelProperty(value = "操作人的姓名")
    private String operateName;

    @ApiModelProperty(value = "操作时提交的表单数据")
    private String formData;

    @ApiModelProperty(value = "扩展数据")
    private String ext;

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
