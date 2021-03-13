package com.zone.process.inbound.web.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 流程实例信息 前端控制器
 * </p>
 *
 * @author Jone
 * @since 2021-03-07
 */
@RestController
@RequestMapping("/processInstanceDO")
public class ProcessInstanceController {

    // 1. 发起流程实例
    // 2. 获取待办任务列表，获取待办任务数量
    // 3. 查看待办任务详情, 表单数据，
    //    第一版先支持查看开始节点的表单信息以及当前节点要填写的表单
    //    后期做到前置节点所有表单数据均可查看
    // 4. 操作待办任务, 流转流程, 修改流程上下文变量
    // 5. 获取流程实例列表，我发起的/我处理的
    // 6. 获取流程实例详情，已操作的各个节点的表单数据均可展示
    // 7. 获取流程实例的流转过程
}
