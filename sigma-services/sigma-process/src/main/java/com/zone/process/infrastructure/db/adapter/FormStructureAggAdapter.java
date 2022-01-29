package com.zone.process.infrastructure.db.adapter;

import com.zone.process.domain.agg.FormStructureAgg;
import com.zone.process.infrastructure.db.dataobject.FormStructureDO;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/29 5:07 下午
 * @Description:
 */
public class FormStructureAggAdapter {

  public static FormStructureAgg getFormStructureAgg(FormStructureDO formDO) {
    if (formDO == null) {
      return null;
    }
    FormStructureAgg formStructureAgg = new FormStructureAgg();
    formStructureAgg.setId(formDO.getId());
    formStructureAgg.setName(formDO.getName());
    formStructureAgg.setFormKey(formDO.getFormKey());
    formStructureAgg.setFormVersion(formDO.getFormVersion());
    formStructureAgg.setFormJson(formDO.getFormJson());
    formStructureAgg.setDescription(formDO.getDescription());
    formStructureAgg.setVersion(formDO.getVersion());
    formStructureAgg.setCreateTime(formDO.getCreateTime());
    formStructureAgg.setCreateBy(formDO.getCreateBy());
    formStructureAgg.setCreateName(formDO.getCreateName());
    return formStructureAgg;
  }

  public static FormStructureDO getFormStructureDO(FormStructureAgg oldFormAgg) {
    FormStructureDO formStructureDO = new FormStructureDO();
    formStructureDO.setId(oldFormAgg.getId());
    formStructureDO.setName(oldFormAgg.getName());
    formStructureDO.setFormKey(oldFormAgg.getFormKey());
    formStructureDO.setFormVersion(oldFormAgg.getFormVersion());
    formStructureDO.setIsLatest(true);
    formStructureDO.setFormJson(oldFormAgg.getFormJson());
    formStructureDO.setDescription(oldFormAgg.getDescription());
    formStructureDO.setVersion(oldFormAgg.getVersion());
    formStructureDO.setCreateTime(oldFormAgg.getCreateTime());
    formStructureDO.setCreateBy(oldFormAgg.getCreateBy());
    formStructureDO.setCreateName(oldFormAgg.getCreateName());
    return formStructureDO;
  }
}
