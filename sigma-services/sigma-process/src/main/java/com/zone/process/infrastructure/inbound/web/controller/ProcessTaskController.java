package com.zone.process.infrastructure.inbound.web.controller;

import com.zone.commons.context.CurrentContext;
import com.zone.commons.entity.LoginUser;
import com.zone.commons.entity.Page;
import com.zone.commons.entity.ResponseData;
import com.zone.process.application.service.command.ProcessTaskCmdService;
import com.zone.process.application.service.command.cmd.TaskOperateCommand;
import com.zone.process.application.service.query.ProcessTaskQueryService;
import com.zone.process.application.service.query.dto.TaskDetailDTO;
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
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 10:43 上午
 * @Description:
 */
@RestController
@RequestMapping("/task")
public class ProcessTaskController {

  @Autowired
  private ProcessTaskQueryService queryService;

  @Autowired
  private ProcessTaskCmdService cmdService;

  @ApiOperation(value = "操作任务", notes = "返回任务所属的流程实例id")
  @PostMapping("/operate")
  public ResponseData<Long> operate(@Valid @RequestBody TaskOperateCommand operateCommand) {
    LoginUser loginUser = CurrentContext.getUser();
    return ResponseData.ok(cmdService.operate(operateCommand, loginUser));
  }

  @ApiOperation(value = "分页查询任务", notes = "返回分配给当前用户的任务")
  @GetMapping("/page")
  public ResponseData<Page<TaskDetailDTO>> page(
      @ApiParam(value = "流程实例的名称") @RequestParam(value = "name", required = false) String name,
      @ApiParam(value = "创建区间的开始时间") @RequestParam(value = "startTime", required = false) Long startTime,
      @ApiParam(value = "创建区间的结束时间") @RequestParam(value = "endTime", required = false) Long endTime,
      @ApiParam(value = "发起人userId") @RequestParam(value = "submitBy", required = false) Long submitBy,
      @ApiParam(name = "pageNo") @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @ApiParam(name = "pageSize") @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
    LoginUser loginUser = CurrentContext.getUser();
    return ResponseData.ok(queryService.page(name, startTime, endTime, submitBy, pageNo, pageSize, loginUser));
  }

  @ApiOperation(value = "查看任务详情", notes = "返回任务详情，做权限校验")
  @GetMapping("/detail")
  public ResponseData<TaskDetailDTO> detail(
      @ApiParam(value = "任务id") @RequestParam(value = "taskId", required = false) String taskId) {
    LoginUser loginUser = CurrentContext.getUser();
    return ResponseData.ok(queryService.detail(taskId, loginUser));
  }

}
