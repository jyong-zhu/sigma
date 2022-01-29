package com.zone.process.infrastructure.inbound.web.controller;


import com.zone.commons.entity.Page;
import com.zone.commons.entity.ResponseData;
import com.zone.process.application.service.command.FormStructureCmdService;
import com.zone.process.application.service.command.cmd.FormCommand;
import com.zone.process.application.service.query.FormStructureQueryService;
import com.zone.process.application.service.query.dto.FormStructureDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 表单结构信息 前端控制器
 * </p>
 *
 * @author Jone
 * @since 2021-03-27
 */
@RestController
@RequestMapping("/form")
public class FormStructureController {

  @Autowired
  private FormStructureQueryService queryService;

  @Autowired
  private FormStructureCmdService cmdService;

  @ApiOperation(value = "保存表单", notes = "根据key来保存，相同的key则更新版本号再保存，返回表单id")
  @PostMapping("/save")
  public ResponseData<Long> save(@Valid @RequestBody FormCommand categoryCommand) {
    return ResponseData.ok(cmdService.save(categoryCommand));
  }

  @ApiOperation(value = "查询表单详情", notes = "返回表单详情")
  @PostMapping("/detail")
  public ResponseData<FormStructureDTO> detail(
      @ApiParam(value = "表单key") @RequestParam(value = "formKey", required = false) String formKey) {
    return ResponseData.ok(queryService.detail(formKey));
  }

  @ApiOperation(value = "分页查询表单列表", notes = "返回分页表单列表")
  @PostMapping("/page")
  public ResponseData<Page<FormStructureDTO>> page(
      @ApiParam(value = "表单名称") @RequestParam(value = "name", required = false) String name,
      @ApiParam(name = "pageNo") @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @ApiParam(name = "pageSize") @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
    return ResponseData.ok(queryService.page(name, pageNo, pageSize));
  }

  @ApiOperation(value = "查询表单列表", notes = "返回表单列表")
  @PostMapping("/list")
  public ResponseData<List<FormStructureDTO>> list(
      @ApiParam(value = "表单名称") @RequestParam(value = "name", required = false) String name) {
    return ResponseData.ok(queryService.list(name));
  }

}
