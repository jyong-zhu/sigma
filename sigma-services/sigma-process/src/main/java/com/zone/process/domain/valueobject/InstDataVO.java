package com.zone.process.domain.valueobject;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/31 5:45 上午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InstDataVO {

    @ApiModelProperty(value = "当前录入表单所处的节点id")
    private String bpmnNodeId;

    @ApiModelProperty(value = "所属表单id")
    private Long formId;

    @ApiModelProperty(value = "流程实例在表单中对应的数据")
    private String formData;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "user_id")
    private Long createBy;

    @ApiModelProperty(value = "user_name")
    private String createName;

    /**
     * 获取表单数据
     */
    public static List<InstDataVO> generateDataVOList(String nodeId, Map<Long, Map<String, String>> formDataMap, List<InstDataVO> oldFormDataList, List<Long> formIdList) {
        // 同一个表单只保存最新的数据
        Map<Long, InstDataVO> curDataMap = oldFormDataList.stream()
                .collect(Collectors.toMap(key -> key.getFormId(), value -> value));
        if (CollectionUtil.isNotEmpty(formDataMap)) {
            formDataMap.forEach((key, value) -> {
                if (formIdList.contains(key)) {
                    // 如果表单id相同，则覆盖成最新的表单数据
                    Map<String, String> dataMap = CollectionUtil.isNotEmpty(value) ? value : Maps.newHashMap();
                    curDataMap.put(key, new InstDataVO().setBpmnNodeId(nodeId)
                            .setFormData(JSONUtil.toJsonStr(dataMap))
                            .setFormId(key));
                }
            });
        }
        return Lists.newArrayList(curDataMap.values());
    }
}
