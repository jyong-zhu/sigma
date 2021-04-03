package com.zone.process.application.service.query.assembler;

import com.zone.process.application.service.query.dto.InstNodeDataDTO;
import com.zone.process.infrastructure.db.dataobject.FormStructureDO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstDataDO;

import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/3 4:13 下午
 * @Description:
 */
public class InstNodeDataDTOAssembler {

    public static InstNodeDataDTO getInstNodeDataDTO(Long instId, String bpmnNodeId, List<ProcessInstDataDO> instDataDOList,
                                                     List<FormStructureDO> formList) {
        return new InstNodeDataDTO()
                .setBpmnNodeId(bpmnNodeId)
                .setInstanceId(instId)
                .setDataList(InstDataDTOAssembler.getInstDataDTOList(instDataDOList, formList));
    }
}
