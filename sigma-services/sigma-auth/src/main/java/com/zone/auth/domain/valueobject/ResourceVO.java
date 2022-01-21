package com.zone.auth.domain.valueobject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/21 11:50 上午
 * @Description: 资源值对象
 */
@Data
@Accessors(chain = true)
public class ResourceVO {

  @ApiModelProperty(value = "权限类型 1-菜单权限 2-功能权限")
  private Integer type;

  @ApiModelProperty(value = "key")
  private String name;

  @ApiModelProperty(value = "菜单名称")
  private String nameZh;

  @ApiModelProperty(value = "资源点对应的url")
  private String resourceUrl;

  @ApiModelProperty(value = "icon图标地址")
  private String iconUrl;

  @ApiModelProperty(value = "功能权限对应菜单的id")
  private Long parentId;

  @ApiModelProperty(value = "排序值")
  private Integer sortNum;
}
