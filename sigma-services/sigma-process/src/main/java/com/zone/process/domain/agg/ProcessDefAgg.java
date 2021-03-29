package com.zone.process.domain.agg;

import com.zone.process.domain.valueobject.DefNodeVO;
import com.zone.process.shared.process.valueobject.ProcessDefinitionVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 2:59 下午
 * @Description: 流程定义的聚合
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProcessDefAgg {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty("bpmn2.0 xml")
    private String xml;

    @ApiModelProperty("流程定义名称")
    private String name;

    @ApiModelProperty("当前流程定义下所关联的全部表单id, 用,隔开")
    private String formIds;

    @ApiModelProperty("分类展示图标")
    private String iconUrl;

    @ApiModelProperty("流程定义中节点的信息")
    private List<DefNodeVO> nodeList;

    /**
     * 初始化流程定义的数据，包括聚合根的id、版本信息等
     */
    public void init(Long generateId, ProcessDefinitionVO definitionVO) {

    }
}
