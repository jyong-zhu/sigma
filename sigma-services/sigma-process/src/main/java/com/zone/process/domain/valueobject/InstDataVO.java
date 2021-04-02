package com.zone.process.domain.valueobject;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

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

    /**
     * 获取表单数据
     */
    public static List<InstDataVO> generateDataVOList(String nodeId, Map<Long, Map<String, String>> formDataMap, List<Long> formIdList) {
        List<InstDataVO> instDataVOList = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(formDataMap)) {
            formDataMap.forEach((key, value) -> {
                if (formIdList.contains(key)) {
                    Map<String, String> dataMap = CollectionUtil.isNotEmpty(value) ? value : Maps.newHashMap();
                    instDataVOList.add(new InstDataVO().setBpmnNodeId(nodeId)
                            .setFormData(JSONUtil.toJsonStr(dataMap))
                            .setFormId(key));
                }
            });
        }
        return instDataVOList;
    }
}
