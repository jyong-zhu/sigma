package com.zone.auth.application.service.query.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/21 8:31 下午
 * @Description:
 */
@Data
@Accessors(chain = true)
public class ResourceTreeDTO {

  @ApiModelProperty("当前节点")
  private ResourceDetailDTO curNode;

  @ApiModelProperty("下一层节点")
  private List<ResourceTreeDTO> nextNodeList;
}
