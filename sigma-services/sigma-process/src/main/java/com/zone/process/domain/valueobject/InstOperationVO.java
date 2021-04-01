package com.zone.process.domain.valueobject;

import com.zone.commons.entity.LoginUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/31 5:46 上午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InstOperationVO {

    @ApiModelProperty(value = "操作所处的节点id")
    private String bpmnNodeId;

    @ApiModelProperty(value = "操作备注")
    private String comment;

    @ApiModelProperty(value = "操作类型")
    private String operationType;

    @ApiModelProperty(value = "操作人的user_id")
    private Long operateBy;

    @ApiModelProperty(value = "操作人的姓名")
    private String operateName;

    @ApiModelProperty(value = "扩展字段")
    private String ext;

    public static InstOperationVO generateOperationVO(String nodeId, String operationType, String comment, LoginUser loginUser, String ext) {
        return new InstOperationVO()
                .setBpmnNodeId(nodeId)
                .setComment(comment)
                .setOperationType(operationType)
                .setOperateBy(loginUser.getUserId())
                .setOperateName(loginUser.getUserName())
                .setExt(ext);
    }
}
