package com.zone.auth.domain.agg;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.zone.auth.application.service.command.cmd.UserRegisterCommand;
import com.zone.auth.infrastructure.db.dataobject.UserBasicDO;
import com.zone.auth.infrastructure.db.dataobject.UserExtDO;
import com.zone.commons.util.SecurityUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/10 11:58 下午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User {

    @ApiModelProperty("用户基础信息")
    private UserBasicDO userBasic;

    @ApiModelProperty("用户扩展信息")
    private List<UserExtDO> userExtList;

    /**
     * 创建用户
     */
    public static User create(UserRegisterCommand registerCommand, Long userId, String userName) {
        // rsa 用私钥进行密码的解密
        String decryptPwd = SecurityUtil.rsaDecrypt(registerCommand.getPassword());
        UserBasicDO userBasicDO = new UserBasicDO()
                .setUserName(registerCommand.getAccountName())
                .setAccountName(registerCommand.getAccountName())
                .setEmail(registerCommand.getEmail())
                // sha1 单向加密
                .setPassword(SecurityUtil.digestSha1(decryptPwd))
                .setPhone(registerCommand.getPhone())
                .setRoleId(registerCommand.getRoleId())
                .setCreateName(userName)
                .setCreateBy(userId)
                .setUpdateBy(userId)
                .setUpdateName(userName);

        List<UserExtDO> userExtDOList = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(registerCommand.getUserExtCommandList())) {
            registerCommand.getUserExtCommandList()
                    .forEach(ext -> userExtDOList.add(new UserExtDO()
                            .setFieldType(ext.getFieldType())
                            .setField(ext.getField())
                            .setValue(ext.getValue())
                            .setCreateBy(userId)
                            .setUpdateBy(userId)
                            .setCreateName(userName)
                            .setUpdateName(userName)));
        }

        return new User().setUserBasic(userBasicDO).setUserExtList(userExtDOList);
    }
}
