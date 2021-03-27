package com.zone.process.inbound.web.controller;


import com.zone.process.application.service.command.FormStructureCmdService;
import com.zone.process.application.service.query.FormStructureQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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

    // 新增（版本升级）/列表/详情（最新版本）
}
