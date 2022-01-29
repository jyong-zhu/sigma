package com.zone.process.application.service.query.assembler;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.zone.process.application.service.query.dto.FormStructureDTO;
import com.zone.process.application.service.query.dto.StartNodeDTO;
import com.zone.process.infrastructure.db.dataobject.FormStructureDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefDO;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/6 6:17 下午
 * @Description:
 */
public class StartNodeDTOAssembler {

  public static StartNodeDTO getStartNodeDTO(ProcessDefDO defDO, List<FormStructureDO> formDOList) {
    List<FormStructureDTO> formDTOList = CollectionUtil.isEmpty(formDOList) ? Lists.newArrayList() :
        formDOList.stream().map(FormStructureDTOAssembler::getFormStructureDTO)
            .collect(Collectors.toList());

    return new StartNodeDTO()
        .setDefId(defDO.getId())
        .setDefName(defDO.getName())
        .setInputFormList(formDTOList);
  }
}
