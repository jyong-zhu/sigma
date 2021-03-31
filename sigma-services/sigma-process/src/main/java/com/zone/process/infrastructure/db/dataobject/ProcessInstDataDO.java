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
 * 流程实例的表单数据
 * </p>
 *
 * @author Jone
 * @since 2021-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("process_inst_data")
@ApiModel(value = "ProcessInstDataDO对象", description = "流程实例的表单数据")
public class ProcessInstDataDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "流程实例id")
    private Long instanceId;

    @ApiModelProperty(value = "当前录入表单所处的节点id")
    private String bpmnNodeId;

    @ApiModelProperty(value = "所属表单id")
    private Long formId;

    @ApiModelProperty(value = "流程实例在表单中对应的数据")
    private String formData;

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
