package com.zone.process.domain.valueobject;

import cn.hutool.json.JSONUtil;
import com.zone.commons.entity.LoginUser;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.Map;
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

    @ApiModelProperty(value = "操作的任务id")
    private String taskId;

    @ApiModelProperty(value = "操作备注")
    private String comment;

    @ApiModelProperty(value = "操作类型")
    private String operationType;

    @ApiModelProperty(value = "操作人的user_id")
    private Long operateBy;

    @ApiModelProperty(value = "操作人的姓名")
    private String operateName;

    @ApiModelProperty(value = "操作时提交的表单数据")
    private String formData;

    @ApiModelProperty(value = "扩展字段")
    private String ext;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "user_id")
    private Long createBy;

    @ApiModelProperty(value = "user_name")
    private String createName;

    public static InstOperationVO generateOperationVO(String nodeId, String taskId, String operationType, String comment,
                                                      Map<Long, Map<String, String>> formDataMap, LoginUser loginUser, String ext) {
        return new InstOperationVO()
                .setBpmnNodeId(nodeId)
                .setTaskId(taskId)
                .setComment(comment)
                .setOperationType(operationType)
                .setOperateBy(loginUser.getAccountId())
                .setOperateName(loginUser.getAccountName())
                .setFormData(JSONUtil.toJsonStr(formDataMap))
                .setExt(ext);
    }
}
