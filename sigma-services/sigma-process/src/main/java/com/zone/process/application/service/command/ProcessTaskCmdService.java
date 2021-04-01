package com.zone.process.application.service.command;

import com.google.common.base.Preconditions;
import com.zone.commons.entity.LoginUser;
import com.zone.process.application.service.command.cmd.TaskOperateCommand;
import com.zone.process.domain.agg.ProcessDefAgg;
import com.zone.process.domain.agg.ProcessInstAgg;
import com.zone.process.domain.repository.ProcessDefAggRepository;
import com.zone.process.domain.repository.ProcessInstAggRepository;
import com.zone.process.domain.service.InstanceDataDomainService;
import com.zone.process.domain.service.InstanceParamDomainService;
import com.zone.process.shared.process.ProcessEngineCommandAPI;
import com.zone.process.shared.process.ProcessEngineQueryAPI;
import com.zone.process.shared.process.valueobject.ProcessInstanceVO;
import com.zone.process.shared.process.valueobject.TaskVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 10:43 上午
 * @Description:
 */
@Slf4j
@Service
public class ProcessTaskCmdService {


    @Autowired
    private ProcessEngineQueryAPI processEngineQueryAPI;

    @Autowired
    private ProcessEngineCommandAPI processEngineCommandAPI;

    @Autowired
    private ProcessInstAggRepository instAggRepository;

    @Autowired
    private ProcessDefAggRepository defAggRepository;

    @Autowired
    private InstanceParamDomainService paramDomainService;

    @Autowired
    private InstanceDataDomainService dataDomainService;


    /**
     * 操作任务
     */
    @Transactional
    public Long operate(TaskOperateCommand operateCommand, LoginUser loginUser) {

        TaskVO taskVO = processEngineQueryAPI.queryTaskById(operateCommand.getTaskId());
        Preconditions.checkNotNull(taskVO, "任务不存在");

        ProcessInstAgg instAgg = instAggRepository.queryByInstId(taskVO.getProcInstId());
        Preconditions.checkNotNull(instAgg, "任务所属的流程实例不存在");

        ProcessDefAgg defAgg = defAggRepository.queryById(instAgg.getDefId());
        Preconditions.checkNotNull(defAgg, "任务对应的流程定义不存在");

        // 操作任务
        Map<String, Object> paramMap = paramDomainService.generateParamMap(operateCommand.getFormDataMap(), taskVO.getCurNodeId(), defAgg);
        processEngineCommandAPI.operateTask(taskVO.getTaskId(), instAgg.getProcInstId(), paramMap, operateCommand.getIdentityList(), operateCommand.getOperationType());

        // 保存相关数据
        dataDomainService.saveUserTaskData(instAgg, taskVO, operateCommand.getOperationType(), operateCommand.getComment(), operateCommand.getFormDataMap(), defAgg, loginUser);

        // 同步流程实例的状态
        ProcessInstanceVO processInstanceVO = processEngineQueryAPI.syncInstance(taskVO.getProcInstId());
        instAgg.sync(processInstanceVO, defAgg);

        Boolean isSuccess = instAggRepository.update(instAgg);
        Preconditions.checkState(isSuccess, "操作任务失败");

        return instAgg.getId();
    }
}
