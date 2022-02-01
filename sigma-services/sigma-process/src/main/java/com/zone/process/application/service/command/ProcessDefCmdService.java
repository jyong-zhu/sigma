package com.zone.process.application.service.command;

import com.google.common.base.Preconditions;
import com.zone.process.application.service.command.cmd.DefDeployCommand;
import com.zone.process.application.service.command.transfer.ProcessDefAggTransfer;
import com.zone.process.domain.agg.ProcessCategoryAgg;
import com.zone.process.domain.agg.ProcessDefAgg;
import com.zone.process.domain.repository.ProcessCategoryAggRepository;
import com.zone.process.domain.repository.ProcessDefAggRepository;
import com.zone.process.shared.process.ProcessEngineCommandAPI;
import com.zone.process.shared.process.valueobject.ProcessDefinitionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 10:42 上午
 * @Description:
 */
@Slf4j
@Service
public class ProcessDefCmdService {

  @Autowired
  private ProcessEngineCommandAPI processEngineCommandAPI;

  @Autowired
  private ProcessCategoryAggRepository categoryAggRepository;

  @Autowired
  private ProcessDefAggRepository defAggRepository;

  /**
   * 部署流程定义
   */
  @Transactional(rollbackFor = Exception.class)
  public Long deploy(DefDeployCommand deployCommand) {

    ProcessCategoryAgg categoryAgg = categoryAggRepository.queryById(deployCommand.getCategoryId());
    Preconditions.checkNotNull(categoryAgg, "流程分类不存在");

    ProcessDefinitionVO definitionVO = processEngineCommandAPI.deploy(deployCommand.getBpmnXml(), deployCommand.getName());
    Preconditions.checkNotNull(definitionVO, "流程部署出错");

    ProcessDefAgg processDefAgg = ProcessDefAggTransfer.getProcessDefAgg(deployCommand);
    processDefAgg.init(definitionVO);
    Long id = defAggRepository.save(processDefAgg);
    Preconditions.checkNotNull(id, "流程部署失败");

    return id;
  }
}
