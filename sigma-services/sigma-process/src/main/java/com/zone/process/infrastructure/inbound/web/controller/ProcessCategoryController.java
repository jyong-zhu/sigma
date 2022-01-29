package com.zone.process.infrastructure.inbound.web.controller;


import com.zone.commons.entity.Page;
import com.zone.commons.entity.ResponseData;
import com.zone.process.application.service.command.ProcessCategoryCmdService;
import com.zone.process.application.service.command.cmd.CategoryCommand;
import com.zone.process.application.service.query.ProcessCategoryQueryService;
import com.zone.process.application.service.query.dto.CategoryDetailDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 流程分类 前端控制器
 * </p>
 *
 * @author Jone
 * @since 2021-03-27
 */
@RestController
@RequestMapping("/category")
public class ProcessCategoryController {

  @Autowired
  private ProcessCategoryQueryService queryService;

  @Autowired
  private ProcessCategoryCmdService cmdService;

  @ApiOperation(value = "新增流程分类", notes = "返回分类id")
  @PostMapping("/add")
  public ResponseData<Long> save(@Valid @RequestBody CategoryCommand categoryCommand) {
    return ResponseData.ok(cmdService.save(categoryCommand));
  }

  @ApiOperation(value = "编辑流程分类", notes = "返回分类id")
  @PostMapping("/edit")
  public ResponseData<Long> edit(@Valid @RequestBody CategoryCommand categoryCommand) {
    return ResponseData.ok(cmdService.edit(categoryCommand));
  }

  @ApiOperation(value = "分页查询流程分类", notes = "返回分类列表")
  @PostMapping("/page")
  public ResponseData<Page<CategoryDetailDTO>> page(
      @ApiParam(value = "流程分类的名称") @RequestParam(value = "name", required = false) String name,
      @ApiParam(name = "pageNo") @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @ApiParam(name = "pageSize") @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
    return ResponseData.ok(queryService.page(name, pageNo, pageSize));
  }

}
