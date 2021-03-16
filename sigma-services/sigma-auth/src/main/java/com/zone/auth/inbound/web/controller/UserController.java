package com.zone.auth.inbound.web.controller;


import com.zone.auth.application.service.command.UserCmdService;
import com.zone.auth.application.service.command.cmd.UserLoginCommand;
import com.zone.auth.application.service.command.cmd.UserRegisterCommand;
import com.zone.auth.application.service.query.UserQueryService;
import com.zone.auth.application.service.query.dto.UserBasicDTO;
import com.zone.commons.entity.ResponseData;
import com.zone.commons.util.SecurityUtil;
import com.zone.mybatisplus.util.Page;
import com.zone.mybatisplus.util.PlusPageConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 用户基础信息表 前端控制器
 * </p>
 *
 * @author Jone
 * @since 2021-03-10
 */
@Api(tags = "用户接口信息")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserCmdService userCmdService;

    @Autowired
    private UserQueryService userQueryService;

    @ApiOperation("用户登陆接口")
    @PostMapping("/login")
    public ResponseData<String> login(@Valid @RequestBody UserLoginCommand loginCommand) {
        return ResponseData.ok(userCmdService.login(loginCommand));
    }

    @ApiOperation("用户注册信息")
    @PostMapping("/register")
    public ResponseData<Boolean> register(@Valid @RequestBody UserRegisterCommand registerCommand) {
        return ResponseData.ok(userCmdService.register(registerCommand, 0L, "admin"));
    }

    @ApiOperation("用户列表")
    @GetMapping("/page")
    public ResponseData<Page<UserBasicDTO>> page(
            @ApiParam("账户名") @RequestParam(value = "accountName", required = false) String accountName,
            @ApiParam("用户名") @RequestParam(value = "userName", required = false) String userName,
            @ApiParam("邮箱") @RequestParam(value = "email", required = false) String email,
            @ApiParam(name = "pageNo") @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(name = "pageSize") @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return ResponseData.ok(PlusPageConverter.convert(userQueryService.page(accountName, userName, email, pageNo, pageSize)));
    }

    @ApiOperation("用户登陆接口")
    @PostMapping("/public-key")
    public ResponseData<String> queryPublicKey() {
        return ResponseData.ok(SecurityUtil.getPublicKey());
    }
}
