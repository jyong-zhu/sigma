package com.zone.process.infrastructure.db.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zone.process.domain.agg.FormStructureAgg;
import com.zone.process.domain.repository.FormStructureAggRepository;
import com.zone.process.infrastructure.db.adapter.FormStructureAggAdapter;
import com.zone.process.infrastructure.db.dataobject.FormStructureDO;
import com.zone.process.infrastructure.db.mapper.FormStructureMapper;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:08 下午
 * @Description:
 */
@Slf4j
@Repository
public class FormStructureAggRepositoryImpl implements FormStructureAggRepository {

  @Resource
  private FormStructureMapper formMapper;

  @Override
  public FormStructureAgg queryByKey(String formKey) {
    QueryWrapper<FormStructureDO> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(FormStructureDO::getFormKey, formKey)
        .eq(FormStructureDO::getIsLatest, true);
    FormStructureDO formDO = formMapper.selectOne(queryWrapper);
    return FormStructureAggAdapter.getFormStructureAgg(formDO);
  }

  @Override
  public Long save(FormStructureAgg newFormAgg, FormStructureAgg oldFormAgg) {
    // 将原先的表单的 is_latest 字段更新掉
    if (oldFormAgg != null) {
      FormStructureDO oldForm = FormStructureAggAdapter.getFormStructureDO(oldFormAgg);
      oldForm.setIsLatest(false);
      int num = formMapper.updateById(oldForm);

      // 更新失败直接返回
      if (num == 0) {
        log.error("更新原先表单isLatest失败，newFormAgg=[{}], oldFormAgg=[{}]", newFormAgg, oldFormAgg);
        return null;
      }
    }
    FormStructureDO newForm = FormStructureAggAdapter.getFormStructureDO(newFormAgg);
    formMapper.insert(newForm);
    return newForm.getId();
  }
}
