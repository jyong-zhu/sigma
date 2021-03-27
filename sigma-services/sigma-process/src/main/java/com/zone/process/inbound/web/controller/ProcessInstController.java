package com.zone.process.inbound.web.controller;


import com.zone.commons.context.CurrentContext;
import com.zone.commons.entity.LoginUser;
import com.zone.commons.entity.Page;
import com.zone.commons.entity.ResponseData;
import com.zone.process.application.service.command.ProcessInstCmdService;
import com.zone.process.application.service.command.cmd.InstStartCommand;
import com.zone.process.application.service.command.cmd.InstStopCommand;
import com.zone.process.application.service.query.ProcessInstQueryService;
import com.zone.process.application.service.query.dto.InstDetailDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 流程实例信息 前端控制器
 * </p>
 *
 * @author Jone
 * @since 2021-03-27
 */
@RestController
@RequestMapping("/inst")
public class ProcessInstController {

    @Autowired
    private ProcessInstQueryService queryService;

    @Autowired
    private ProcessInstCmdService cmdService;

    @ApiOperation(value = "发起流程实例", notes = "返回流程实例id")
    @PostMapping("/start")
    public ResponseData<Long> start(@Valid @RequestBody InstStartCommand startCommand) {
        LoginUser loginUser = CurrentContext.getUser();
        return ResponseData.ok(null);
    }

    @ApiOperation(value = "中止流程实例", notes = "返回流程实例id")
    @PostMapping("stop")
    public ResponseData<Long> stop(@Valid @RequestBody InstStopCommand instStopCommand) {
        LoginUser loginUser = CurrentContext.getUser();
        return ResponseData.ok(null);
    }

    @ApiOperation(value = "流程实例列表", notes = "返回当前用户操作过或者发起的流程实例列表")
    @GetMapping("/page")
    public ResponseData<Page<InstDetailDTO>> page(
            @ApiParam(value = "流程实例的名称") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "创建区间的开始时间") @RequestParam(value = "startTime", required = false) Long startTime,
            @ApiParam(value = "创建区间的结束时间") @RequestParam(value = "endTime", required = false) Long endTime,
            @ApiParam(value = "处理人userId") @RequestParam(value = "userId", required = false) Long curHandlerId,
            @ApiParam(value = "状态，进行中-active，已结束-finished") @RequestParam(value = "status", required = false) String status,
            @ApiParam(name = "pageNo") @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(name = "pageSize") @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        LoginUser loginUser = CurrentContext.getUser();
        return ResponseData.ok(null);
    }

    @ApiOperation(value = "流程实例详情")
    @GetMapping("/detail")
    public ResponseData<InstDetailDTO> detail(
            @ApiParam(value = "流程实例id") @RequestParam(value = "instId") Long instId) {
        LoginUser loginUser = CurrentContext.getUser();
        return ResponseData.ok(null);
    }

}
