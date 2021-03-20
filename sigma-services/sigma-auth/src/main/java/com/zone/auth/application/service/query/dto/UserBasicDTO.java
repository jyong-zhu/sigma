package com.zone.auth.application.service.query.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/10 11:35 下午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserBasicDTO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("账户名")
    private String accountName;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("额外的扩展信息")
    private List<UserExtDTO> userExtDTOList;

}
