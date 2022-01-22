package com.zone.auth.infrastructure.inbound.web.controller;

import com.zone.auth.application.service.command.ResourceCmdService;
import com.zone.auth.application.service.command.cmd.ResourceCreateCommand;
import com.zone.auth.application.service.command.cmd.ResourceUpdateCommand;
import com.zone.auth.application.service.query.ResourceQueryService;
import com.zone.auth.application.service.query.dto.ResourceDetailDTO;
import com.zone.auth.application.service.query.dto.ResourceTreeDTO;
import com.zone.commons.context.CurrentContext;
import com.zone.commons.entity.LoginUser;
import com.zone.commons.entity.Page;
import com.zone.commons.entity.ResponseData;
import com.zone.mybatis.util.PlusPageConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/20 7:38 下午
 * @Description: 资源相关的 controller
 */
@Api("资源点相关")
@RestController
@RequestMapping("/resource")
public class ResourceManageController {

  @Autowired
  private ResourceCmdService resourceCmdService;

  @Autowired
  private ResourceQueryService resourceQueryService;

  @ApiOperation(value = "创建资源点", notes = "返回资源点id")
  @PostMapping("/create")
  public ResponseData<Long> create(@Valid @RequestBody ResourceCreateCommand createCommand) {
    LoginUser loginUser = CurrentContext.getUser();
    return ResponseData.ok(resourceCmdService.create(createCommand, loginUser));
  }

  @ApiOperation(value = "更新资源点", notes = "返回资源点id")
  @PostMapping("/update")
  public ResponseData<Long> update(@Valid @RequestBody ResourceUpdateCommand updateCommand) {
    LoginUser loginUser = CurrentContext.getUser();
    return ResponseData.ok(resourceCmdService.update(updateCommand, loginUser));
  }

  @ApiOperation(value = "删除资源点", notes = "返回资源点id")
  @GetMapping("/delete")
  public ResponseData<Long> delete(
      @ApiParam(value = "资源id") @RequestParam(value = "resourceId") Long resourceId) {
    LoginUser loginUser = CurrentContext.getUser();
    return ResponseData.ok(resourceCmdService.delete(resourceId, loginUser));
  }

  @ApiOperation(value = "获取资源点详情", notes = "返回详情")
  @GetMapping("/detail")
  public ResponseData<ResourceDetailDTO> detail(
      @ApiParam(value = "资源id") @RequestParam(value = "resourceId") Long resourceId) {
    return ResponseData.ok(resourceQueryService.detail(resourceId));
  }

  @ApiOperation("资源点列表")
  @GetMapping("/page")
  public ResponseData<Page<ResourceDetailDTO>> page(
      @ApiParam("资源点名称") @RequestParam(value = "name", required = false) String name,
      @ApiParam("资源点Url") @RequestParam(value = "resourceUrl", required = false) String resourceUrl,
      @ApiParam("是否可见") @RequestParam(value = "visible", required = false) Boolean visible,
      @ApiParam("pageNo") @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @ApiParam("pageSize") @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
    return ResponseData.ok(PlusPageConverter.convert(resourceQueryService.page(name, resourceUrl, visible, pageNo, pageSize)));
  }

  @ApiOperation("资源点列表")
  @GetMapping("/page")
  public ResponseData<List<ResourceTreeDTO>> tree() {
    return ResponseData.ok(resourceQueryService.tree());
  }


}
