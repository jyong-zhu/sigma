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
 * 流程定义中节点上的属性信息
 * </p>
 *
 * @author Jone
 * @since 2021-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("process_def_node_property")
@ApiModel(value="ProcessDefNodePropertyDO对象", description="流程定义中节点上的属性信息")
public class ProcessDefNodePropertyDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所属流程节点id")
    private String nodeId;

    @ApiModelProperty(value = "流程节点中的属性名称")
    private String propertyName;

    @ApiModelProperty(value = "属性值")
    private String propertyValue;

    @ApiModelProperty(value = "属性作用描述")
    private String desc;

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
