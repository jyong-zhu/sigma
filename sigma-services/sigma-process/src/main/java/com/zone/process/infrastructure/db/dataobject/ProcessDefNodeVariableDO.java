package com.zone.process.infrastructure.db.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 流程定义中节点上的变量信息
 * </p>
 *
 * @author Jone
 * @since 2021-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("process_def_node_variable")
@ApiModel(value="ProcessDefNodeVariableDO对象", description="流程定义中节点上的变量信息")
public class ProcessDefNodeVariableDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所属流程节点id")
    private String nodeId;

    @ApiModelProperty(value = "流程节点的变量名称，这个参数可以在后续的节点中被引用")
    private String variableName;

    @ApiModelProperty(value = "当前节点上挂靠的表单id")
    private String formId;

    @ApiModelProperty(value = "该表单中的字段id")
    private String fieldId;

    @ApiModelProperty(value = "默认的参数值")
    private String defaultValue;

    @ApiModelProperty(value = "扩展字段")
    private String ext;

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