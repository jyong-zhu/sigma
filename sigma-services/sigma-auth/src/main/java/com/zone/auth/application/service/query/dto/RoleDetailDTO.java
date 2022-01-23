package com.zone.auth.application.service.query.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 8:37 下午
 * @Description:
 */
@Data
@Accessors(chain = true)
public class RoleDetailDTO {

  @ApiModelProperty(value = "主键")
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @ApiModelProperty(value = "角色名称")
  private String roleName;

  @ApiModelProperty(value = "角色拥有的资源id，多个用,隔开")
  private List<ResourceDetailDTO> resourceList;

  @ApiModelProperty(value = "0-停用 1-正常")
  private Boolean status;

  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty(value = "user_name")
  private String createName;

  @ApiModelProperty(value = "更新时间")
  private LocalDateTime updateTime;

  @ApiModelProperty(value = "user_name")
  private String updateName;
}
