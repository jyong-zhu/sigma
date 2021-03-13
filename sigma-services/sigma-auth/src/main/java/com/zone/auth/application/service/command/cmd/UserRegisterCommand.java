package com.zone.auth.application.service.command.cmd;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/10 11:29 下午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserRegisterCommand {

    @ApiModelProperty("账户名")
    @NotBlank(message = "账户名不能为空")
    private String accountName;

    @ApiModelProperty("用户姓名")
    @NotBlank(message = "用户姓名不能为空")
    private String userName;

    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("额外的扩展信息")
    @Valid
    private List<UserExtCommand> userExtCommandList;

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class UserExtCommand {

        @ApiModelProperty("字段id")
        @NotBlank(message = "字段id不能为空")
        private String field;

        @ApiModelProperty("字段类型")
        @NotBlank(message = "字段类型不能为空")
        private String fieldType;

        @ApiModelProperty("字段值")
        @NotBlank(message = "字段值不能为空")
        private String value;
    }
}
