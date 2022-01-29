package com.zone.process.application.service.query.assembler;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.zone.process.application.service.query.dto.FormStructureDTO;
import com.zone.process.infrastructure.db.dataobject.FormStructureDO;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/29 3:14 下午
 * @Description:
 */
public class FormStructureDTOAssembler {

  public static FormStructureDTO getFormStructureDTO(FormStructureDO formDO) {
    if (formDO == null) {
      return null;
    }
    FormStructureDTO formStructureDTO = new FormStructureDTO();
    formStructureDTO.setId(formDO.getId());
    formStructureDTO.setFormJson(formDO.getFormJson());
    formStructureDTO.setName(formDO.getName());
    formStructureDTO.setFormKey(formDO.getFormKey());
    formStructureDTO.setFormVersion(formDO.getFormVersion());
    formStructureDTO.setDescription(formDO.getDescription());
    return formStructureDTO;
  }

  public static List<FormStructureDTO> getFormStructureDTOList(List<FormStructureDO> list) {
    if (CollectionUtil.isEmpty(list)) {
      return Lists.newArrayList();
    }
    return list.stream().filter(tmp -> tmp != null)
        .map(FormStructureDTOAssembler::getFormStructureDTO)
        .collect(Collectors.toList());
  }
}
