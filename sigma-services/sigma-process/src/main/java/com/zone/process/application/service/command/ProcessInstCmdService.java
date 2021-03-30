package com.zone.process.application.service.command;

import com.google.common.base.Preconditions;
import com.zone.commons.entity.LoginUser;
import com.zone.process.application.service.command.cmd.InstStartCommand;
import com.zone.process.application.service.command.cmd.InstStopCommand;
import com.zone.process.application.service.command.transfer.ProcessInstAggTransfer;
import com.zone.process.domain.agg.ProcessDefAgg;
import com.zone.process.domain.agg.ProcessInstAgg;
import com.zone.process.domain.repository.ProcessDefAggRepository;
import com.zone.process.domain.repository.ProcessInstAggRepository;
import com.zone.process.domain.service.AggIdentityDomainService;
import com.zone.process.domain.service.InstanceParamDomainService;
import com.zone.process.shared.process.ProcessEngineCommandAPI;
import com.zone.process.shared.process.ProcessEngineQueryAPI;
import com.zone.process.shared.process.valueobject.ProcessInstanceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 10:42 上午
 * @Description:
 */
@Slf4j
@Service
public class ProcessInstCmdService {

    @Autowired
    private ProcessEngineCommandAPI processEngineCommandAPI;

    @Autowired
    private ProcessEngineQueryAPI processEngineQueryAPI;

    @Autowired
    private ProcessDefAggRepository defAggRepository;

    @Autowired
    private ProcessInstAggRepository instAggRepository;

    @Autowired
    private InstanceParamDomainService paramDomainService;

    @Autowired
    private AggIdentityDomainService identityDomainService;


    /**
     * 发起流程实例
     */
    @Transactional
    public Long start(InstStartCommand startCommand, LoginUser loginUser) {

        ProcessDefAgg defAgg = defAggRepository.queryById(startCommand.getDefId());
        Preconditions.checkNotNull(defAgg, "流程定义不存在");

        // 发起流程实例
        Map<String, Object> paramMap = paramDomainService.generateParamMap(startCommand.getFormDataMap(), defAgg.getStartBpmnNodeId(), defAgg);
        String procInstId = processEngineCommandAPI.startInstance(defAgg.getProcDefKey(), paramMap);

        // 初始化相关数据
        ProcessInstAgg instAgg = ProcessInstAggTransfer.getProcessInstAgg(startCommand);
        instAgg.init(identityDomainService.generateInstAggId(), startCommand.getFormDataMap(), loginUser);

        // 同步流程实例的当前状态
        ProcessInstanceVO processInstanceVO = processEngineQueryAPI.syncInstance(procInstId);
        instAgg.sync(processInstanceVO);

        instAggRepository.save(instAgg);

        return instAgg.getId();
    }

    /**
     * 中止流程实例
     */
    @Transactional
    public Long stop(InstStopCommand stopCommand, LoginUser loginUser) {

        ProcessInstAgg instAgg = instAggRepository.queryById(stopCommand.getId());
        Preconditions.checkNotNull(instAgg, "流程实例不存在");

        // 中止流程实例
        processEngineCommandAPI.stopInstance(instAgg.getProcInstId(), stopCommand.getComment());
        instAgg.stop(stopCommand.getComment(), loginUser);

        // 同步流程实例的当前状态
        ProcessInstanceVO processInstanceVO = processEngineQueryAPI.syncInstance(instAgg.getProcInstId());
        instAgg.sync(processInstanceVO);

        // 采用乐观锁更新，失败必须报错，否则camunda中的数据和扩展表中的数据不一致
        Boolean isSuccess = instAggRepository.update(instAgg);
        Preconditions.checkState(isSuccess, "中止流程实例失败");

        return instAgg.getId();
    }
}
