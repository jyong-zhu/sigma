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
 * 流程实例信息
 * </p>
 *
 * @author Jone
 * @since 2021-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("process_instance")
@ApiModel(value="ProcessInstanceDO对象", description="流程实例信息")
public class ProcessInstanceDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "camunda中的流程实例id")
    private String procInstId;

    @ApiModelProperty(value = "流程定义id")
    private String defId;

    @ApiModelProperty(value = "流程实例名称")
    private String name;

    @ApiModelProperty(value = "流程实例的状态，处理中/已完结")
    private String status;

    @ApiModelProperty(value = "流程实例发起时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "流程实例要求时间")
    private LocalDateTime dueTime;

    @ApiModelProperty(value = "流程实例提交人的user_id")
    private String submitBy;

    @ApiModelProperty(value = "流程实例提交人的姓名")
    private String submitName;

    @ApiModelProperty(value = "描述信息")
    private String desc;

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
