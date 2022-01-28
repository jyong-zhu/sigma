package com.zone.rpc.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/22 2:34 下午
 * @Description: 用户鉴权返回的详情
 */
@Data
@Accessors(chain = true)
public class AccountCheckDTO implements Serializable {

  private static final long serialVersionUID = -9119281396199298843L;

  @ApiModelProperty("账号id")
  private Long accountId;

  @ApiModelProperty("账号名称")
  private String accountName;

  @ApiModelProperty("账号类型")
  private Integer accountType;

  @ApiModelProperty("角色列表")
  private List<Long> roleIdList;

  @ApiModelProperty("手机号")
  private String phone;

}
