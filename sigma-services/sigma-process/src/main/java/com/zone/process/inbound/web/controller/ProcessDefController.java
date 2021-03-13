package com.zone.process.inbound.web.controller;


import com.zone.process.application.service.command.ProcessDefCmdService;
import com.zone.process.application.service.command.cmd.ProcessDefDeployCommand;
import com.zone.process.application.service.query.ProcessDefQueryService;
import com.zone.process.application.service.query.dto.ProcessDefDetailDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 流程定义 前端控制器
 * </p>
 *
 * @author Jone
 * @since 2021-03-07
 */
@RestController
@RequestMapping("/processDef")
public class ProcessDefController {

    @Autowired
    private ProcessDefCmdService processDefCmdService;

    @Autowired
    private ProcessDefQueryService processDefQueryService;

    @ApiOperation("部署xml流程定义, 包括新增与版本升级")
    @PostMapping("/deploy")
    public ResponseEntity<String> deploy(@RequestBody ProcessDefDeployCommand deployCommand) {
        return ResponseEntity.ok(processDefCmdService.deploy(deployCommand));
    }

    @ApiOperation("查看指定id的xml流程详情")
    @PostMapping("/detail")
    public ResponseEntity<ProcessDefDetailDTO> detail(@RequestParam(name = "id") String id) {
        return ResponseEntity.ok(processDefQueryService.detail(id));
    }

    @ApiOperation("获取开始节点的表单信息，用于发起流程")
    @PostMapping("/startForms")
    public ResponseEntity<List<String>> queryStartForms(@RequestParam(name = "id") String id) {
        return ResponseEntity.ok(processDefQueryService.queryStartForms(id));
    }

}
