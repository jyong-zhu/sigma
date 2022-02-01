package com.zone.auth.application.service.command.cmd;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 8:24 下午
 * @Description:
 */
@Data
@Accessors(chain = true)
public class ResourceUpdateCommand {

  @ApiModelProperty(value = "主键")
  @NotNull(message = "主键id不能为空")
  private Long resourceId;

  @ApiModelProperty(value = "权限类型 1-菜单权限 2-功能权限")
  @NotNull(message = "权限类型不能为空")
  private Integer type;

  @ApiModelProperty(value = "key")
  @NotBlank(message = "key不能为空")
  private String key;

  @ApiModelProperty(value = "资源点名称")
  @NotBlank(message = "资源点名称不能为空")
  private String name;

  @ApiModelProperty(value = "资源点对应的url")
  @NotBlank(message = "资源点url不能为空")
  private String resourceUrl;

  @ApiModelProperty(value = "icon图标地址")
  private String iconUrl;

  @ApiModelProperty(value = "功能权限对应菜单的id")
  @NotNull(message = "菜单id不能为空")
  private Long parentId;

  @ApiModelProperty(value = "是否可见 0-隐藏 1-可见")
  @NotNull(message = "是否可见不能为空")
  private Boolean visible;

  @ApiModelProperty(value = "0-停用 1-正常")
  @NotNull(message = "状态不能为空")
  private Boolean status;

  @ApiModelProperty(value = "排序值")
  @NotNull(message = "排序值不能为空")
  private Integer sortNum;

}
