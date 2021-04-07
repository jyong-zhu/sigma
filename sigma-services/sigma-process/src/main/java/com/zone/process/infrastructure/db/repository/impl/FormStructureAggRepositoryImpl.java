package com.zone.process.infrastructure.db.repository.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Preconditions;
import com.zone.process.domain.agg.FormStructureAgg;
import com.zone.process.domain.repository.FormStructureAggRepository;
import com.zone.process.infrastructure.db.dataobject.FormStructureDO;
import com.zone.process.infrastructure.db.mapper.FormStructureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:08 下午
 * @Description:
 */
@Service
public class FormStructureAggRepositoryImpl implements FormStructureAggRepository {

    @Autowired
    private FormStructureMapper formMapper;

    @Override
    public FormStructureAgg queryByKey(String formKey) {
        QueryWrapper<FormStructureDO> queryWrapper = new QueryWrapper<FormStructureDO>()
                .eq("form_key", formKey)
                .eq("is_latest", 1);
        FormStructureDO formDO = formMapper.selectOne(queryWrapper);
        return formDO == null ? null : BeanUtil.copyProperties(formDO, FormStructureAgg.class);
    }

    @Override
    public void save(FormStructureAgg newFormAgg, FormStructureAgg oldFormAgg) {
        // 将原先的表单的 is_latest 字段更新掉
        if (oldFormAgg != null) {
            FormStructureDO oldForm = BeanUtil.copyProperties(oldFormAgg, FormStructureDO.class);
            oldForm.setIsLatest(false);
            int num = formMapper.updateById(oldForm);
            Preconditions.checkState(num > 0, "保存表单失败");
        }
        FormStructureDO newForm = BeanUtil.copyProperties(newFormAgg, FormStructureDO.class);
        formMapper.insert(newForm);
    }
}
