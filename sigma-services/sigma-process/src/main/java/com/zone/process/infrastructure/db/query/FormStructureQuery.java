package com.zone.process.infrastructure.db.query;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zone.process.infrastructure.db.dataobject.FormStructureDO;
import com.zone.process.infrastructure.db.mapper.FormStructureMapper;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/3 2:58 下午
 * @Description:
 */
@Service
public class FormStructureQuery {

  @Resource
  private FormStructureMapper formStructureMapper;

  /**
   * 根据','隔开的id来查询表单
   */
  public List<FormStructureDO> queryByIds(String ids) {
    List<FormStructureDO> result = Lists.newArrayList();
    if (StrUtil.isNotBlank(ids)) {
      List<Long> idList = Arrays.asList(ids.split(",")).stream()
          .filter(tmp -> StrUtil.isNotBlank(tmp))
          .map(Long::valueOf).collect(Collectors.toList());
      if (CollectionUtil.isNotEmpty(idList)) {
        QueryWrapper<FormStructureDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(FormStructureDO::getId, idList);
        return formStructureMapper.selectList(wrapper);
      }
    }
    return result;
  }

  /**
   * 查询最新版本的表单
   */
  public FormStructureDO queryByKey(String formKey) {
    QueryWrapper<FormStructureDO> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(FormStructureDO::getFormKey, formKey)
        .eq(FormStructureDO::getIsLatest, true);
    return formStructureMapper.selectOne(queryWrapper);
  }

  /**
   * 分页查询最新版本的表单
   */
  public IPage<FormStructureDO> page(String name, Integer pageNo, Integer pageSize) {
    QueryWrapper<FormStructureDO> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(FormStructureDO::getIsLatest, true)
        .orderByDesc(FormStructureDO::getId);
    if (StrUtil.isNotBlank(name)) {
      queryWrapper.lambda().like(FormStructureDO::getName, name);
    }
    return formStructureMapper.selectPage(new Page<>(pageNo, pageSize), queryWrapper);
  }

  /**
   * 根据名称查询表单列表
   */
  public List<FormStructureDO> list(String name) {
    QueryWrapper<FormStructureDO> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(FormStructureDO::getIsLatest, true)
        .orderByDesc(FormStructureDO::getId);
    if (StrUtil.isNotBlank(name)) {
      queryWrapper.lambda().like(FormStructureDO::getName, name);
    }
    return formStructureMapper.selectList(queryWrapper);
  }
}
