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
 * 流程定义
 * </p>
 *
 * @author Jone
 * @since 2021-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("process_def")
@ApiModel(value="ProcessDefDO对象", description="流程定义")
public class ProcessDefDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "流程分类id")
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

    @ApiModelProperty(value = "当前流程的bpmn2.0的xml定义")
    private String bpmnXml;

    @ApiModelProperty(value = "开始节点的id")
    private String startBpmnNodeId;

    @ApiModelProperty(value = "当前流程定义下所关联的全部表单id, 用,隔开")
    private String formIds;

    @ApiModelProperty(value = "流程定义展示图标")
    private String iconUrl;

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
