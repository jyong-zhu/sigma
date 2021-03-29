package com.zone.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 11:46 下午
 * @Description: 扩展 mybatis-plus 的 mapper
 */
public interface SigmaMapper<T> extends BaseMapper<T> {

    int insertBatchSomeColumn(List<T> entityList);
}
