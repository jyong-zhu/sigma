package com.zone.process.application.service.query.assembler;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.zone.process.application.service.query.dto.InstDataDTO;
import com.zone.process.infrastructure.db.dataobject.FormStructureDO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstDataDO;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/3 4:11 下午
 * @Description:
 */
public class InstDataDTOAssembler {

  /**
   * 流程实例上表单数据的展示逻辑：
   * <p> 1. 返回节点上所有的表单结构，若有数据，则把表单的数据拼接好并返回。
   * <p> 2. 节点上判断表单能否编辑通过返回 inputFormIdList 来实现
   * <p> 所以节点上的所有的表单与表单数据是一并返回的，能不能编辑是要判断的
   *
   * @param instDataDOList 存在数据的表单列表
   * @param formList       全部的表单列表
   */
  public static List<InstDataDTO> getInstDataDTOList(List<ProcessInstDataDO> instDataDOList, List<FormStructureDO> formList) {

    if (CollectionUtil.isEmpty(formList) || CollectionUtil.isEmpty(instDataDOList)) {
      return Lists.newArrayList();
    }

    Map<Long, ProcessInstDataDO> dataMap = instDataDOList.stream()
        .collect(Collectors.toMap(key -> key.getFormId(), value -> value));

    // 设置实例的表单数据
    List<InstDataDTO> dataList = formList.stream().filter(tmp -> tmp != null).map(form -> {
      InstDataDTO dataDTO = getInstDataDTO(form);
      ProcessInstDataDO instDataDO = dataMap.getOrDefault(form.getId(), new ProcessInstDataDO());
      dataDTO.setBpmnNodeId(instDataDO.getBpmnNodeId());
      dataDTO.setFormData(instDataDO.getFormData());
      dataDTO.setId(instDataDO.getId());
      dataDTO.setInstanceId(instDataDO.getInstanceId());
      return dataDTO;
    }).collect(Collectors.toList());

    return dataList;
  }

  private static InstDataDTO getInstDataDTO(FormStructureDO formDO) {
    if (formDO == null) {
      return null;
    }
    InstDataDTO instDataDTO = new InstDataDTO();
    instDataDTO.setFormId(formDO.getId());
    instDataDTO.setFormJson(formDO.getFormJson());
    instDataDTO.setFormName(formDO.getName());
    instDataDTO.setFormKey(formDO.getFormKey());
    instDataDTO.setFormVersion(formDO.getFormVersion());
    instDataDTO.setDescription(formDO.getDescription());
    return instDataDTO;

  }
}
