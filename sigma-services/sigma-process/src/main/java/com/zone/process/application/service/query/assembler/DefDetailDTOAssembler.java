package com.zone.process.application.service.query.assembler;

import com.zone.process.application.service.query.dto.DefDetailDTO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodePropertyDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeVariableDO;
import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/6 5:14 下午
 * @Description:
 */
public class DefDetailDTOAssembler {

  /**
   * 不包含节点信息
   */
  public static DefDetailDTO getDefDetailDTO(ProcessDefDO def) {
    if (def == null) {
      return null;
    }
    DefDetailDTO defDetailDTO = new DefDetailDTO();
    defDetailDTO.setId(def.getId());
    defDetailDTO.setCategoryId(def.getCategoryId());
    defDetailDTO.setBpmnXml(def.getBpmnXml());
    defDetailDTO.setProcDefId(def.getProcDefId());
    defDetailDTO.setProcDefKey(def.getProcDefKey());
    defDetailDTO.setProcDefVersion(def.getProcDefVersion());
    defDetailDTO.setName(def.getName());
    defDetailDTO.setFormIds(def.getFormIds());
    defDetailDTO.setIconUrl(def.getIconUrl());
    return defDetailDTO;
  }

  public static DefDetailDTO getDefDetailDTO(ProcessDefDO def, List<ProcessDefNodeDO> nodeList,
      List<ProcessDefNodeVariableDO> variableList, List<ProcessDefNodePropertyDO> propertyList) {

    DefDetailDTO defDTO = getDefDetailDTO(def);
    if (defDTO == null) {
      return null;
    }

    defDTO.setNodeList(DefNodeDetailDTOAssembler.getDefNodeDetailDTOList(nodeList, variableList, propertyList));

    return defDTO;
  }
}
