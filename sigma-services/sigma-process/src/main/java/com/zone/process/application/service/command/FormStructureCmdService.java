package com.zone.process.application.service.command;

import com.zone.process.application.service.command.cmd.FormCommand;
import com.zone.process.application.service.command.transfer.FormStructureAggTransfer;
import com.zone.process.domain.agg.FormStructureAgg;
import com.zone.process.domain.repository.FormStructureAggRepository;
import com.zone.process.domain.service.AggIdentityDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 10:45 上午
 * @Description:
 */
@Slf4j
@Service
public class FormStructureCmdService {

    @Autowired
    private FormStructureAggRepository formAggRepository;

    @Autowired
    private AggIdentityDomainService identityDomainService;

    /**
     * 保存表单
     */
    @Transactional
    public Long save(FormCommand categoryCommand) {

        FormStructureAgg oldFormAgg = formAggRepository.queryByKey(categoryCommand.getFormKey());

        FormStructureAgg newFormAgg = FormStructureAggTransfer.getFormStructureAgg(categoryCommand);

        newFormAgg.init(identityDomainService.generateFormAggId(), oldFormAgg);

        formAggRepository.save(newFormAgg, oldFormAgg);

        return newFormAgg.getId();
    }
}
