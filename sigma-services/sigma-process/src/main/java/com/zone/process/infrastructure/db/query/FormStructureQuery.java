package com.zone.process.infrastructure.db.query;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.zone.process.infrastructure.db.dataobject.FormStructureDO;
import com.zone.process.infrastructure.db.mapper.FormStructureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/3 2:58 下午
 * @Description:
 */
@Service
public class FormStructureQuery {

    @Autowired
    private FormStructureMapper formStructureMapper;

    /**
     * 根据','隔开的id来查询表单
     */
    public List<FormStructureDO> queryByIds(String ids) {
        List<FormStructureDO> result = Lists.newArrayList();
        if (StrUtil.isNotBlank(ids)) {
            List<Long> idList = Arrays.asList(ids.split(","))
                    .stream().filter(tmp -> StrUtil.isNotBlank(tmp))
                    .map(tmp -> Long.valueOf(tmp)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(idList)) {
                return formStructureMapper.selectList(
                        new QueryWrapper<FormStructureDO>().in("id", idList));
            }
        }
        return result;
    }
}
