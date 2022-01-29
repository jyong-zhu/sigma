package com.zone.process.infrastructure.db.query;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zone.process.infrastructure.db.dataobject.ProcessDefDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodePropertyDO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeVariableDO;
import com.zone.process.infrastructure.db.mapper.ProcessDefMapper;
import com.zone.process.infrastructure.db.mapper.ProcessDefNodeMapper;
import com.zone.process.infrastructure.db.mapper.ProcessDefNodePropertyMapper;
import com.zone.process.infrastructure.db.mapper.ProcessDefNodeVariableMapper;
import com.zone.process.shared.enums.BpmnNodeTypeEnum;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/3 1:53 下午
 * @Description:
 */
@Service
public class ProcessDefQuery {

  @Resource
  private ProcessDefMapper defMapper;

  @Resource
  private ProcessDefNodeMapper defNodeMapper;

  @Resource
  private ProcessDefNodeVariableMapper nodeVariableMapper;

  @Resource
  private ProcessDefNodePropertyMapper nodePropertyMapper;

  /**
   * 根据节点类型查询指定流程定义下的节点
   */
  public ProcessDefNodeDO queryByNodeType(Long defId, BpmnNodeTypeEnum nodeType, String parentBpmnNodeId) {

    QueryWrapper<ProcessDefNodeDO> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(ProcessDefNodeDO::getDefId, defId)
        .eq(ProcessDefNodeDO::getBpmnNodeType, nodeType.getCode())
        .eq(ProcessDefNodeDO::getParentBpmnNodeId, parentBpmnNodeId);

    return defNodeMapper.selectOne(queryWrapper);
  }

  public ProcessDefNodeDO queryNodeById(Long defId, String bpmnNodeId) {
    return defNodeMapper.selectOne(new QueryWrapper<ProcessDefNodeDO>().lambda()
        .eq(ProcessDefNodeDO::getDefId, defId)
        .eq(ProcessDefNodeDO::getBpmnNodeId, bpmnNodeId));
  }

  public IPage<ProcessDefDO> page(Long categoryId, String name, Integer pageNo, Integer pageSize) {
    QueryWrapper<ProcessDefDO> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(ProcessDefDO::getCategoryId, categoryId)
        .eq(ProcessDefDO::getIsLatest, true)
        .orderByDesc(ProcessDefDO::getId);
    if (StrUtil.isNotBlank(name)) {
      queryWrapper.lambda().like(ProcessDefDO::getName, name);
    }
    return defMapper.selectPage(new Page<>(pageNo, pageSize), queryWrapper);
  }

  /**
   * 根据 procKey 查询最新版本的流程定义
   */
  public ProcessDefDO queryDefByProcKey(String procDefKey) {
    QueryWrapper<ProcessDefDO> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(ProcessDefDO::getProcDefKey, procDefKey)
        .eq(ProcessDefDO::getIsLatest, true);
    return defMapper.selectOne(queryWrapper);
  }

  public List<ProcessDefNodeDO> queryNodeListByDefId(Long id) {
    QueryWrapper<ProcessDefNodeDO> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(ProcessDefNodeDO::getDefId, id);
    return defNodeMapper.selectList(queryWrapper);
  }

  public List<ProcessDefNodeVariableDO> queryNodeVariableList(List<Long> nodeIdList) {
    if (CollectionUtil.isNotEmpty(nodeIdList)) {
      QueryWrapper<ProcessDefNodeVariableDO> queryWrapper = new QueryWrapper<>();
      queryWrapper.lambda().in(ProcessDefNodeVariableDO::getNodeId, nodeIdList);
      return nodeVariableMapper.selectList(queryWrapper);
    }
    return Lists.newArrayList();
  }

  public List<ProcessDefNodePropertyDO> queryNodePropertyList(List<Long> nodeIdList) {
    if (CollectionUtil.isNotEmpty(nodeIdList)) {
      QueryWrapper<ProcessDefNodePropertyDO> queryWrapper = new QueryWrapper<>();
      queryWrapper.lambda().in(ProcessDefNodePropertyDO::getNodeId, nodeIdList);
      return nodePropertyMapper.selectList(queryWrapper);
    }
    return Lists.newArrayList();
  }
}
