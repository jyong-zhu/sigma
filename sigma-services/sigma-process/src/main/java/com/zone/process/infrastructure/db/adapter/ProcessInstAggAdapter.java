package com.zone.process.infrastructure.db.adapter;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.zone.process.domain.agg.ProcessInstAgg;
import com.zone.process.domain.valueobject.InstDataVO;
import com.zone.process.domain.valueobject.InstOperationVO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstDO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstDataDO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstOperationDO;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:37 下午
 * @Description:
 */
public class ProcessInstAggAdapter {

  public static List<ProcessInstDataDO> getInstDataDOList(List<InstDataVO> dataVOList, Long instanceId) {
    if (CollectionUtil.isEmpty(dataVOList)) {
      return Lists.newArrayList();
    }
    return dataVOList.stream().filter(tmp -> tmp != null)
        .map(dataVO -> getInstDataDO(dataVO, instanceId))
        .collect(Collectors.toList());
  }

  public static ProcessInstDataDO getInstDataDO(InstDataVO instDataVO, Long instanceId) {
    if (instDataVO == null) {
      return null;
    }
    ProcessInstDataDO processInstDataDO = new ProcessInstDataDO();
    processInstDataDO.setInstanceId(instanceId);
    processInstDataDO.setBpmnNodeId(instDataVO.getBpmnNodeId());
    processInstDataDO.setFormId(instDataVO.getFormId());
    processInstDataDO.setFormData(instDataVO.getFormData());
    processInstDataDO.setCreateTime(instDataVO.getCreateTime());
    processInstDataDO.setCreateBy(instDataVO.getCreateBy());
    processInstDataDO.setCreateName(instDataVO.getCreateName());
    return processInstDataDO;
  }

  public static List<ProcessInstOperationDO> getInstOperationDOList(List<InstOperationVO> operationVOList, Long instanceId) {
    if (CollectionUtil.isEmpty(operationVOList)) {
      return Lists.newArrayList();
    }
    return operationVOList.stream().filter(tmp -> tmp != null)
        .map(operationVO -> getInstOperationDO(operationVO, instanceId))
        .collect(Collectors.toList());
  }

  public static ProcessInstOperationDO getInstOperationDO(InstOperationVO operationVO, Long instanceId) {
    if (operationVO == null) {
      return null;
    }
    ProcessInstOperationDO processInstOperationDO = new ProcessInstOperationDO();
    processInstOperationDO.setInstanceId(instanceId);
    processInstOperationDO.setOperationType(operationVO.getOperationType());
    processInstOperationDO.setBpmnNodeId(operationVO.getBpmnNodeId());
    processInstOperationDO.setTaskId(operationVO.getTaskId());
    processInstOperationDO.setComment(operationVO.getComment());
    processInstOperationDO.setOperateBy(operationVO.getOperateBy());
    processInstOperationDO.setOperateName(operationVO.getOperateName());
    processInstOperationDO.setFormData(operationVO.getFormData());
    processInstOperationDO.setExt(operationVO.getExt());
    return processInstOperationDO;

  }

  public static ProcessInstAgg getProcessInstAgg(ProcessInstDO instDO) {
    if (instDO == null) {
      return null;
    }
    ProcessInstAgg processInstAgg = new ProcessInstAgg();
    processInstAgg.setId(instDO.getId());
    processInstAgg.setProcInstId(instDO.getProcInstId());
    processInstAgg.setDefId(instDO.getDefId());
    processInstAgg.setDefName(instDO.getDefName());
    processInstAgg.setProcDefKey(instDO.getProcDefKey());
    processInstAgg.setName(instDO.getName());
    processInstAgg.setStatus(instDO.getStatus());
    processInstAgg.setCurNodeId(instDO.getCurNodeId());
    processInstAgg.setCurNodeName(instDO.getCurNodeName());
    processInstAgg.setCurHandlerId(instDO.getCurHandlerId());
    processInstAgg.setDueTime(instDO.getDueTime());
    processInstAgg.setSubmitTime(instDO.getSubmitTime());
    processInstAgg.setSubmitBy(instDO.getSubmitBy());
    processInstAgg.setSubmitName(instDO.getSubmitName());
    processInstAgg.setComment(instDO.getComment());
    processInstAgg.setDescription(instDO.getDescription());
    processInstAgg.setVersion(instDO.getVersion());
    processInstAgg.setCreateTime(instDO.getCreateTime());
    processInstAgg.setCreateBy(instDO.getCreateBy());
    processInstAgg.setCreateName(instDO.getCreateName());
    return processInstAgg;
  }

  public static List<InstOperationVO> getInstOperationVOList(List<ProcessInstOperationDO> operationDOList) {
    if (CollectionUtil.isEmpty(operationDOList)) {
      return Lists.newArrayList();
    }
    return operationDOList.stream().filter(tmp -> tmp != null)
        .map(operationDO -> getInstOperationVO(operationDO))
        .collect(Collectors.toList());
  }

  public static InstOperationVO getInstOperationVO(ProcessInstOperationDO operationDO) {
    if (operationDO == null) {
      return null;
    }
    InstOperationVO instOperationVO = new InstOperationVO();
    instOperationVO.setBpmnNodeId(operationDO.getBpmnNodeId());
    instOperationVO.setTaskId(operationDO.getTaskId());
    instOperationVO.setComment(operationDO.getComment());
    instOperationVO.setOperationType(operationDO.getOperationType());
    instOperationVO.setOperateBy(operationDO.getOperateBy());
    instOperationVO.setOperateName(operationDO.getOperateName());
    instOperationVO.setFormData(operationDO.getFormData());
    instOperationVO.setExt(operationDO.getExt());
    instOperationVO.setCreateTime(operationDO.getCreateTime());
    instOperationVO.setCreateBy(operationDO.getCreateBy());
    instOperationVO.setCreateName(operationDO.getCreateName());
    return instOperationVO;

  }

  public static List<InstDataVO> getInstDataVOList(List<ProcessInstDataDO> dataDOList) {
    if (CollectionUtil.isEmpty(dataDOList)) {
      return Lists.newArrayList();
    }
    return dataDOList.stream().filter(tmp -> tmp != null)
        .map(dataDO -> getInstDataVO(dataDO))
        .collect(Collectors.toList());
  }

  public static InstDataVO getInstDataVO(ProcessInstDataDO dataDO) {
    if (dataDO == null) {
      return null;
    }
    InstDataVO instDataVO = new InstDataVO();
    instDataVO.setBpmnNodeId(dataDO.getBpmnNodeId());
    instDataVO.setFormId(dataDO.getFormId());
    instDataVO.setFormData(dataDO.getFormData());
    instDataVO.setCreateTime(dataDO.getCreateTime());
    instDataVO.setCreateBy(dataDO.getCreateBy());
    instDataVO.setCreateName(dataDO.getCreateName());
    return instDataVO;

  }

  public static ProcessInstDO getInstDO(ProcessInstAgg instAgg) {
    if (instAgg == null) {
      return null;
    }
    ProcessInstDO processInstDO = new ProcessInstDO();
    processInstDO.setId(instAgg.getId());
    processInstDO.setProcInstId(instAgg.getProcInstId());
    processInstDO.setDefId(instAgg.getDefId());
    processInstDO.setDefName(instAgg.getDefName());
    processInstDO.setProcDefKey(instAgg.getProcDefKey());
    processInstDO.setName(instAgg.getName());
    processInstDO.setStatus(instAgg.getStatus());
    processInstDO.setCurNodeId(instAgg.getCurNodeId());
    processInstDO.setCurNodeName(instAgg.getCurNodeName());
    processInstDO.setCurHandlerId(instAgg.getCurHandlerId());
    processInstDO.setDueTime(instAgg.getDueTime());
    processInstDO.setSubmitTime(instAgg.getSubmitTime());
    processInstDO.setSubmitBy(instAgg.getSubmitBy());
    processInstDO.setSubmitName(instAgg.getSubmitName());
    processInstDO.setComment(instAgg.getComment());
    processInstDO.setDescription(instAgg.getDescription());
    processInstDO.setVersion(instAgg.getVersion());
    processInstDO.setCreateTime(instAgg.getCreateTime());
    processInstDO.setCreateBy(instAgg.getCreateBy());
    processInstDO.setCreateName(instAgg.getCreateName());
    return processInstDO;
  }
}
