package com.zone.process.infrastructure.inbound.web.controller;


import com.zone.commons.entity.Page;
import com.zone.commons.entity.ResponseData;
import com.zone.process.application.service.command.ProcessDefCmdService;
import com.zone.process.application.service.command.cmd.DefDeployCommand;
import com.zone.process.application.service.query.ProcessDefQueryService;
import com.zone.process.application.service.query.dto.DefDetailDTO;
import com.zone.process.application.service.query.dto.DefNodeDetailDTO;
import com.zone.process.application.service.query.dto.DefStartNodeDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        return ResponseData.ok(cmdService.deploy(deployCommand));
    }

    @ApiOperation(value = "分页查询流程定义列表", notes = "列表返回的流程定义均为最新版本的数据")
    @GetMapping("/page")
    public ResponseData<Page<DefDetailDTO>> page(
            @ApiParam(value = "流程分类id", required = true) @RequestParam("categoryId") Long categoryId,
            @ApiParam(value = "流程定义的名称") @RequestParam(value = "name", required = false) String name,
            @ApiParam(name = "pageNo") @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam(name = "pageSize") @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return ResponseData.ok(queryService.page(categoryId, name, pageNo, pageSize));
    }

    @ApiOperation(value = "查询流程定义的详情", notes = "若有多个版本，则返回最新版本")
    @GetMapping("/detail-by-key")
    public ResponseData<DefDetailDTO> queryDetailByKey(
            @ApiParam(value = "流程定义的key", required = true) @RequestParam("procDefKey") String procDefKey) {
        return ResponseData.ok(queryService.queryDetailByKey(procDefKey));
    }

    @ApiOperation(value = "查询流程定义的开始节点详情", notes = "用proDefKey来查，是最新的版本, 用于发起流程实例")
    @GetMapping("/start-node")
    public ResponseData<DefStartNodeDTO> queryStartNodeDetail(
            @ApiParam(value = "流程定义的key", required = true) @RequestParam("procDefKey") String procDefKey) {
        return ResponseData.ok(queryService.queryStartNodeDetail(procDefKey));
    }

    @ApiOperation(value = "查询流程定义的节点列表", notes = "用defId来查，指定到某一个版本，用于切换节点")
    @GetMapping("/node-list")
    public ResponseData<List<DefNodeDetailDTO>> queryNodeList(
            @ApiParam(value = "流程定义的id", required = true) @RequestParam("defId") Long defId) {
        return ResponseData.ok(queryService.queryNodeList(defId));
    }

}
