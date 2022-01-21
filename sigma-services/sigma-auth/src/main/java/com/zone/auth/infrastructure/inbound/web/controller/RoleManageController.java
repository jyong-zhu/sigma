package com.zone.auth.infrastructure.inbound.web.controller;


import com.zone.auth.application.service.command.RoleCmdService;
import com.zone.auth.application.service.command.cmd.RoleCreateCommand;
import com.zone.auth.application.service.command.cmd.RoleUpdateCommand;
import com.zone.auth.application.service.query.RoleQueryService;
import com.zone.auth.application.service.query.dto.RoleDetailDTO;
import com.zone.commons.context.CurrentContext;
import com.zone.commons.entity.LoginUser;
import com.zone.commons.entity.ResponseData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author Jone
 * @since 2022-01-20
 */
@RestController
@RequestMapping("/role")
public class RoleManageController {

  @Autowired
  private RoleCmdService roleCmdService;

  @Autowired
  private RoleQueryService roleQueryService;

  @ApiOperation(value = "创建角色", notes = "返回角色id")
  @PostMapping("/create")
  public ResponseData<Long> create(@Valid @RequestBody RoleCreateCommand createCommand) {
    LoginUser loginUser = CurrentContext.getUser();
    return ResponseData.ok(roleCmdService.create(createCommand, loginUser));
  }

  @ApiOperation(value = "更新角色", notes = "返回角色id")
  @PostMapping("/update")
  public ResponseData<Long> update(@Valid @RequestBody RoleUpdateCommand updateCommand) {
    LoginUser loginUser = CurrentContext.getUser();
    return ResponseData.ok(roleCmdService.update(updateCommand, loginUser));
  }

  @ApiOperation(value = "删除角色", notes = "返回角色id")
  @GetMapping("/delete")
  public ResponseData<Long> delete(
      @ApiParam(value = "角色id") @RequestParam(value = "roleId") Long roleId) {
    LoginUser loginUser = CurrentContext.getUser();
    return ResponseData.ok(roleCmdService.delete(roleId, loginUser));
  }

  @ApiOperation(value = "获取角色详情", notes = "返回详情")
  @GetMapping("/detail")
  public ResponseData<RoleDetailDTO> detail(
      @ApiParam(value = "角色id") @RequestParam(value = "roleId") Long roleId) {
    return ResponseData.ok(roleQueryService.detail(roleId));
  }

}
