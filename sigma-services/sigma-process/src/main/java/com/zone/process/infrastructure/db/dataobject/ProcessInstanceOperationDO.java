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
 * 流程实例操作信息
 * </p>
 *
 * @author Jone
 * @since 2021-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("process_instance_operation")
@ApiModel(value="ProcessInstanceOperationDO对象", description="流程实例操作信息")
public class ProcessInstanceOperationDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "流程实例id")
    private String instanceId;

    @ApiModelProperty(value = "操作类型（发起/转派/提交）")
    private String operationType;

    @ApiModelProperty(value = "操作所处的节点id")
    private String bpmnNodeId;

    @ApiModelProperty(value = "操作备注")
    private String comment;

    @ApiModelProperty(value = "操作人的user_id")
    private String operateBy;

    @ApiModelProperty(value = "操作人的姓名")
    private String operateName;

    @ApiModelProperty(value = "扩展数据")
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
