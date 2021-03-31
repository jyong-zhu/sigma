package com.zone.process.infrastructure.process.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Jone
 * @since 2021-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ACT_RU_IDENTITYLINK")
@ApiModel(value = "ActRuIdentitylinkDO对象", description = "")
public class ActRuIdentitylinkDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID_")
    private String id;

    @TableField("REV_")
    private Integer rev;

    @TableField("GROUP_ID_")
    private String groupId;

    @TableField("TYPE_")
    private String type;

    @TableField("USER_ID_")
    private String userId;

    @TableField("TASK_ID_")
    private String taskId;

    @TableField("PROC_DEF_ID_")
    private String procDefId;

    @TableField("TENANT_ID_")
    private String tenantId;


}
