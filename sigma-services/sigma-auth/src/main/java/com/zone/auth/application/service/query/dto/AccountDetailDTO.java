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
 * @Date: 2022/1/20 8:45 下午
 * @Description:
 */
@Data
@Accessors(chain = true)
public class AccountDetailDTO {

  @ApiModelProperty(value = "主键")
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @ApiModelProperty(value = "姓名")
  private String name;

  @ApiModelProperty(value = "手机号")
  private String phone;

  @ApiModelProperty(value = "1-超管 2-非超管")
  private Integer accountType;

  @ApiModelProperty(value = "邮箱")
  private String email;

  @ApiModelProperty(value = "0-停用 1-正常")
  private Boolean status;

  @ApiModelProperty(value = "角色列表")
  private List<RoleDetailDTO> roleList;

  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty(value = "user_name")
  private String createName;

  @ApiModelProperty(value = "更新时间")
  private LocalDateTime updateTime;

  @ApiModelProperty(value = "user_name")
  private String updateName;
}
