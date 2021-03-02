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
 * 流程实例的表单数据
 * </p>
 *
 * @author Jone
 * @since 2021-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("form_data")
@ApiModel(value="FormDataDO对象", description="流程实例的表单数据")
public class FormDataDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "表单id")
    private Long formId;

    @ApiModelProperty(value = "流程实例id")
    private String instanceId;

    @ApiModelProperty(value = "当前录入表单所处的节点id")
    private String bpmnNodeId;

    @ApiModelProperty(value = "表单的json串，冗余字段")
    private String formJson;

    @ApiModelProperty(value = "流程实例在表单中对应的数据")
    private String formData;

    @ApiModelProperty(value = "扩展信息")
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
