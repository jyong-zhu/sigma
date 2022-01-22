package com.zone.rpc.req.auth;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/22 2:41 下午
 * @Description: 鉴权的请求参数
 */
@Data
@Accessors(chain = true)
public class AccountCheckReq {

  @ApiModelProperty("账号id")
  @NotNull(message = "账号id不能为空")
  private Long accountId;

  @ApiModelProperty("账号名称")
  @NotBlank(message = "账号名称不能为空")
  private String name;

  @ApiModelProperty("请求url")
  @NotBlank(message = "请求url不能为空")
  private String url;
}
