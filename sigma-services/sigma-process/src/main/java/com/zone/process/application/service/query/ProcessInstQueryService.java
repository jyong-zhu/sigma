package com.zone.process.application.service.query;

import com.google.common.base.Preconditions;
import com.zone.commons.entity.LoginUser;
import com.zone.commons.entity.Page;
import com.zone.mybatis.util.PlusPageConverter;
import com.zone.process.application.service.query.assembler.InstDetailDTOAssembler;
import com.zone.process.application.service.query.assembler.InstNodeDataDTOAssembler;
import com.zone.process.application.service.query.assembler.InstTransferDTOAssembler;
import com.zone.process.application.service.query.dto.InstDetailDTO;
import com.zone.process.application.service.query.dto.InstNodeDataDTO;
import com.zone.process.application.service.query.dto.InstTransferDTO;
import com.zone.process.infrastructure.db.dataobject.FormStructureDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeDO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstDO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstDataDO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstOperationDO;
import com.zone.process.infrastructure.db.query.FormStructureQuery;
import com.zone.process.infrastructure.db.query.ProcessDefQuery;
import com.zone.process.infrastructure.db.query.ProcessInstQuery;
import com.zone.process.shared.enums.BpmnNodeTypeEnum;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 10:44 上午
 * @Description:
 */
@Slf4j
@Service
public class ProcessInstQueryService {

  @Autowired
  private ProcessInstQuery instQuery;

  @Autowired
  private ProcessDefQuery defQuery;

  @Autowired
  private FormStructureQuery formStructureQuery;

  /**
   * 分页查询实例列表：我发起的 + 我处理的
   */
  public Page<InstDetailDTO> page(String name, String defName, Long startTime, Long endTime, Long curHandlerId, Long submitBy, String status,
      Integer pageNo, Integer pageSize, LoginUser loginUser) {
    // 查询当前用户操作过的流程实例
    List<Long> instIdList = instQuery.queryRelateInstIdList(loginUser.getAccountId());

    // 分页查询
    Page<ProcessInstDO> instPage = PlusPageConverter.convert(instQuery.pageInIdList(instIdList, name, defName, startTime, endTime, curHandlerId,
        submitBy, status, pageNo, pageSize));

    return instPage.convert(instDO -> InstDetailDTOAssembler.getInstDetailDTO(instDO));
  }

  /**
   * 查询流程实例详情
   */
  public InstDetailDTO detail(Long instId, LoginUser loginUser) {

    ProcessInstDO instDO = instQuery.queryInstById(instId);
    ProcessInstOperationDO operationDO = instQuery.queryRelateOperation(instId, loginUser.getAccountId());
    Preconditions.checkState(instDO != null && operationDO != null, "流程实例不存在");

    // 详情中只返回开始节点的表单数据，要查看其他节点上的表单需要调接口进行切换
    ProcessDefNodeDO startNode = defQuery.queryByNodeType(instDO.getDefId(), BpmnNodeTypeEnum.START_EVENT, "");
    Preconditions.checkNotNull(startNode, "开始节点不存在");

    String formIds = startNode.getInputFormIds() + "," + startNode.getDisplayFormIds();
    List<FormStructureDO> formList = formStructureQuery.queryByIds(formIds);
    List<ProcessInstDataDO> instDataDO = instQuery.queryDataByFormIds(instId, formIds);

    return InstDetailDTOAssembler.getInstDetailDTO(instDO, instDataDO, formList);
  }

  /**
   * 查询指定节点上流程实例的数据
   */
  public InstNodeDataDTO queryInstNodeData(Long instId, String bpmnNodeId, LoginUser loginUser) {

    ProcessInstDO instDO = instQuery.queryInstById(instId);
    ProcessInstOperationDO operationDO = instQuery.queryRelateOperation(instId, loginUser.getAccountId());
    Preconditions.checkState(instDO != null && operationDO != null, "流程实例不存在");

    // 查询指定节点的信息
    ProcessDefNodeDO node = defQuery.queryNodeById(instDO.getDefId(), bpmnNodeId);
    Preconditions.checkNotNull(node, "节点不存在");

    String formIds = node.getInputFormIds() + "," + node.getDisplayFormIds();
    List<FormStructureDO> formList = formStructureQuery.queryByIds(formIds);
    List<ProcessInstDataDO> instDataDOList = instQuery.queryDataByFormIds(instId, formIds);

    return InstNodeDataDTOAssembler.getInstNodeDataDTO(instId, bpmnNodeId, instDataDOList, formList);
  }

  /**
   * 获取流程实例的流转记录
   */
  public List<InstTransferDTO> queryInstTransfer(Long instId) {

    ProcessInstDO instDO = instQuery.queryInstById(instId);
    Preconditions.checkNotNull(instDO, "流程实例不存在");

    List<ProcessInstOperationDO> operationDOList = instQuery.queryOperationList(instId);

    return InstTransferDTOAssembler.getInstTransferDTOList(operationDOList);
  }
}
