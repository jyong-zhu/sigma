package com.zone.auth.application.service.query.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 8:42 下午
 * @Description:
 */
@Data
@Accessors(chain = true)
public class ResourceDetailDTO {

  @ApiModelProperty(value = "主键")
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @ApiModelProperty(value = "权限类型 1-菜单权限 2-功能权限")
  private Integer type;

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

  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty(value = "user_name")
  private String createName;

  @ApiModelProperty(value = "更新时间")
  private LocalDateTime updateTime;

  @ApiModelProperty(value = "user_name")
  private String updateName;
}
