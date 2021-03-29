package com.zone.process.infrastructure.inbound.web.controller;


import com.zone.process.application.service.command.ProcessCategoryCmdService;
import com.zone.process.application.service.query.ProcessCategoryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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

    // 新增/编辑/列表/详情
}
