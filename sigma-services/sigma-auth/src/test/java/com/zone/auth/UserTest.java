package com.zone.auth;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.zone.auth.application.service.command.UserCmdService;
import com.zone.auth.application.service.command.cmd.AccountLoginCommand;
import com.zone.auth.application.service.command.cmd.UserRegisterCommand;
import com.zone.auth.application.service.query.UserQueryService;
import com.zone.auth.application.service.query.dto.UserBasicDTO;
import com.zone.auth.infrastructure.db.mapper.UserBasicMapper;
import com.zone.auth.infrastructure.db.mapper.UserExtMapper;
import com.zone.commons.util.SecurityUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/13 8:44 下午
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserCmdService userCmdService;

    @Autowired
    private UserQueryService userQueryService;

    @Autowired
    private UserBasicMapper userBasicMapper;

    @Autowired
    private UserExtMapper userExtMapper;

    private static final String userName = "admin";

    private static final Long userId = 1l;

    @Before
    public void init() {
        // 删除所有数据
        userBasicMapper.delete(new QueryWrapper<>());
        userExtMapper.delete(new QueryWrapper<>());
    }

    @Test
    public void registerUser() {
        UserRegisterCommand registerCommand = new UserRegisterCommand()
                .setUserExtCommandList(Lists.newArrayList(new UserRegisterCommand
                        .UserExtCommand().setField("sex").setFieldType("integer").setValue("1")))
                .setAccountName("jone")
                .setEmail("jyong_zhu@163.com")
                .setPassword(SecurityUtil.rsaEncrypt("123456"))
                .setPhone("15900000000")
                .setRoleId(1l)
                .setUserName("jone");
        Boolean res = userCmdService.register(registerCommand, userId, userName);
        Assert.assertTrue(res);
    }

    @Test
    public void login() {
        registerUser();
        AccountLoginCommand loginCommand = new AccountLoginCommand()
                .setPhone("jone")
                .setPassword(SecurityUtil.rsaEncrypt("123456"));
        String jwtToken = userCmdService.login(loginCommand);
        Assert.assertTrue(StrUtil.isNotBlank(jwtToken));
    }

    @Test
    public void page() {
        registerUser();
        IPage<UserBasicDTO> page = userQueryService.page("jone", "", "", 1, 10);
        Assert.assertTrue(page.getRecords().size() == 1);
    }
}
