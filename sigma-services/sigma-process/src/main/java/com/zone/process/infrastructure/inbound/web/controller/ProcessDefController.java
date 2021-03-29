package com.zone.process.infrastructure.inbound.web.controller;


import com.zone.commons.context.CurrentContext;
import com.zone.commons.entity.LoginUser;
import com.zone.commons.entity.Page;
import com.zone.commons.entity.ResponseData;
import com.zone.process.application.service.command.ProcessDefCmdService;
import com.zone.process.application.service.command.cmd.DefDeployCommand;
import com.zone.process.application.service.query.ProcessDefQueryService;
import com.zone.process.application.service.query.dto.DefDetailDTO;
import com.zone.process.application.service.query.dto.DefNodeDetailDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 流程定义 前端控制器
 * </p>
 *
 * @author Jone
 * @since 2021-03-27
 */
@RestController
@RequestMapping("/def")
public class ProcessDefController {

    @Autowired
    private ProcessDefQueryService queryService;

    @Autowired
    private ProcessDefCmdService cmdService;

    @ApiOperation(value = "部署流程定义", notes = "返回defId")
    @PostMapping("/deploy")
    public ResponseData<Long> deploy(@Valid @RequestBody DefDeployCommand deployCommand) {
        LoginUser loginUser = CurrentContext.getUser();
        return ResponseData.ok(cmdService.deploy(deployCommand, loginUser));
    }

    @ApiOperation(value = "分页查询流程定义列表", notes = "列表返回的流程定义均为最新版本的数据")
    @GetMapping("/page")
    public ResponseData<Page<DefDetailDTO>> page(
            @ApiParam(value = "流程分类id", required = true) @RequestParam("categoryId") Long categoryId,
            @ApiParam(value = "流程定义的名称") @RequestParam(value = "name", required = false) String name,
            @ApiParam(name = "pageNo") @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(name = "pageSize") @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        LoginUser loginUser = CurrentContext.getUser();
        return ResponseData.ok(null);
    }

    @ApiOperation(value = "查询流程定义的详情", notes = "若有多个版本，则返回最新版本")
    @GetMapping("/detail-by-key")
    public ResponseData<DefDetailDTO> queryDetailByKey(
            @ApiParam(value = "流程定义的key", required = true) @RequestParam("procDefKey") String procDefKey) {
        LoginUser loginUser = CurrentContext.getUser();
        return ResponseData.ok(null);
    }

    @ApiOperation(value = "查询流程定义的详情", notes = "根据defId查询特定的流程定义，可以是老版本")
    @GetMapping("/detail-by-id")
    public ResponseData<DefDetailDTO> queryDetailById(
            @ApiParam(value = "流程定义的id", required = true) @RequestParam("defId") Long defId) {
        LoginUser loginUser = CurrentContext.getUser();
        return ResponseData.ok(null);
    }

    @ApiOperation(value = "查询流程定义的节点详情", notes = "涉及历史版本的节点信息，故用defId+bpmnNodeId来查询")
    @GetMapping("/node-detail")
    public ResponseData<DefNodeDetailDTO> queryNodeDetail(
            @ApiParam(value = "流程定义的id", required = true) @RequestParam("defId") Long defId,
            @ApiParam(value = "bpmn中的节点id", required = true) @RequestParam("bpmnNodeId") String bpmnNodeId) {
        LoginUser loginUser = CurrentContext.getUser();
        return ResponseData.ok(null);
    }
}
