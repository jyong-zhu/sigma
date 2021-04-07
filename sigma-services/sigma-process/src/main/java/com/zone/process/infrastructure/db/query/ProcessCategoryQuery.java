package com.zone.process.infrastructure.db.query;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zone.process.infrastructure.db.dataobject.ProcessCategoryDO;
import com.zone.process.infrastructure.db.mapper.ProcessCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/3 11:57 下午
 * @Description:
 */
@Service
public class ProcessCategoryQuery {

    @Autowired
    private ProcessCategoryMapper categoryMapper;

    public IPage<ProcessCategoryDO> page(String name, Integer pageNo, Integer pageSize) {
        QueryWrapper<ProcessCategoryDO> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        return categoryMapper.selectPage(new Page<>(pageNo, pageSize), queryWrapper);
    }
}
