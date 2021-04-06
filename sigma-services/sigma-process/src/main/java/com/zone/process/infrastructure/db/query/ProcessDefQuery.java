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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/3 1:53 下午
 * @Description:
 */
@Service
public class ProcessDefQuery {

    @Autowired
    private ProcessDefMapper defMapper;

    @Autowired
    private ProcessDefNodeMapper defNodeMapper;

    @Autowired
    private ProcessDefNodeVariableMapper nodeVariableMapper;

    @Autowired
    private ProcessDefNodePropertyMapper nodePropertyMapper;

    public ProcessDefNodeDO queryStartNode(Long defId) {
        return defNodeMapper.selectOne(new QueryWrapper<ProcessDefNodeDO>()
                .eq("def_id", defId)
                .eq("bpmn_node_type", BpmnNodeTypeEnum.START_EVENT.getCode())
                .eq("parent_bpmn_node_id", ""));
    }

    public ProcessDefNodeDO queryNodeById(Long defId, String bpmnNodeId) {
        return defNodeMapper.selectOne(new QueryWrapper<ProcessDefNodeDO>()
                .eq("def_id", defId)
                .eq("bpmn_node_id", bpmnNodeId));
    }

    public IPage<ProcessDefDO> page(Long categoryId, String name, Integer pageNo, Integer pageSize) {
        QueryWrapper<ProcessDefDO> queryWrapper = new QueryWrapper<ProcessDefDO>()
                .eq("category_id", categoryId)
                .eq("is_latest", true);
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        return defMapper.selectPage(new Page<>(pageNo, pageSize), queryWrapper);
    }

    /**
     * 根据 procKey 查询最新版本的流程定义
     */
    public ProcessDefDO queryDefByProcKey(String procDefKey) {
        QueryWrapper<ProcessDefDO> queryWrapper = new QueryWrapper<ProcessDefDO>()
                .eq("proc_def_key", procDefKey)
                .eq("is_latest", 1);
        return defMapper.selectOne(queryWrapper);
    }

    public List<ProcessDefNodeDO> queryNodeListByDefId(Long id) {
        QueryWrapper<ProcessDefNodeDO> queryWrapper = new QueryWrapper<ProcessDefNodeDO>()
                .eq("def_id", id);
        return defNodeMapper.selectList(queryWrapper);
    }

    public List<ProcessDefNodeVariableDO> queryNodeVariableList(List<Long> nodeIdList) {
        if (CollectionUtil.isNotEmpty(nodeIdList)) {
            QueryWrapper<ProcessDefNodeVariableDO> queryWrapper = new QueryWrapper<ProcessDefNodeVariableDO>()
                    .in("node_id", nodeIdList);
            return nodeVariableMapper.selectList(queryWrapper);
        }
        return Lists.newArrayList();
    }

    public List<ProcessDefNodePropertyDO> queryNodePropertyList(List<Long> nodeIdList) {
        if (CollectionUtil.isNotEmpty(nodeIdList)) {
            QueryWrapper<ProcessDefNodePropertyDO> queryWrapper = new QueryWrapper<ProcessDefNodePropertyDO>()
                    .in("node_id", nodeIdList);
            return nodePropertyMapper.selectList(queryWrapper);
        }
        return Lists.newArrayList();
    }
}
