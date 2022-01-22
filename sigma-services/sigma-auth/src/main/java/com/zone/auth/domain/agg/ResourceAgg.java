package com.zone.auth.domain.agg;

import com.zone.auth.application.service.command.cmd.ResourceCreateCommand;
import com.zone.auth.application.service.command.cmd.ResourceUpdateCommand;
import com.zone.auth.shared.enums.ResourceTypeEnum;
import com.zone.commons.entity.LoginUser;
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
public class ResourceAgg {

  @ApiModelProperty(value = "主键")
  private Long id;

  @ApiModelProperty(value = "权限类型 1-菜单权限 2-功能权限")
  private ResourceTypeEnum type;

  @ApiModelProperty(value = "key")
  private String key;

  @ApiModelProperty(value = "资源点名称")
  private String name;

  @ApiModelProperty(value = "资源点对应的url")
  private String resourceUrl;

  @ApiModelProperty(value = "icon图标地址")
  private String iconUrl;

  @ApiModelProperty(value = "功能权限对应菜单的id")
  private Long parentId;

  @ApiModelProperty(value = "是否可见 0-隐藏 1-可见")
  private Boolean visible;

  @ApiModelProperty(value = "0-停用 1-正常")
  private Boolean status;

  @ApiModelProperty(value = "排序值")
  private Integer sortNum;

  @ApiModelProperty(value = "user_id")
  private Long createBy;

  @ApiModelProperty(value = "user_name")
  private String createName;

  @ApiModelProperty(value = "user_id")
  private Long updateBy;

  @ApiModelProperty(value = "user_name")
  private String updateName;


  /**
   * 创建资源点
   */
  public static ResourceAgg create(ResourceCreateCommand createCommand, LoginUser loginUser) {
    ResourceAgg resourceAgg = new ResourceAgg();
    resourceAgg.setType(ResourceTypeEnum.getByCode(createCommand.getType()));
    resourceAgg.setKey(createCommand.getKey());
    resourceAgg.setName(createCommand.getName());
    resourceAgg.setResourceUrl(createCommand.getResourceUrl());
    resourceAgg.setIconUrl(createCommand.getIconUrl());
    resourceAgg.setParentId(createCommand.getParentId());
    resourceAgg.setVisible(createCommand.getVisible());
    resourceAgg.setStatus(true);
    resourceAgg.setSortNum(createCommand.getSortNum());
    resourceAgg.setCreateBy(loginUser.getAccountId());
    resourceAgg.setCreateName(loginUser.getAccountName());
    resourceAgg.setUpdateBy(loginUser.getAccountId());
    resourceAgg.setUpdateName(loginUser.getAccountName());
    return resourceAgg;
  }

  /**
   * 更新资源点
   */
  public void update(ResourceUpdateCommand updateCommand, LoginUser loginUser) {
    this.setType(ResourceTypeEnum.getByCode(updateCommand.getType()));
    this.setKey(updateCommand.getKey());
    this.setName(updateCommand.getName());
    this.setResourceUrl(updateCommand.getResourceUrl());
    this.setIconUrl(updateCommand.getIconUrl());
    this.setParentId(updateCommand.getParentId());
    this.setVisible(updateCommand.getVisible());
    this.setStatus(updateCommand.getStatus());
    this.setSortNum(updateCommand.getSortNum());
    this.setUpdateBy(loginUser.getAccountId());
    this.setUpdateName(loginUser.getAccountName());
  }
}
