package com.zone.process.infrastructure.db.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zone.process.infrastructure.db.dataobject.ProcessDefNodeDO;
import com.zone.process.infrastructure.db.mapper.ProcessDefMapper;
import com.zone.process.infrastructure.db.mapper.ProcessDefNodeMapper;
import com.zone.process.infrastructure.db.mapper.ProcessDefNodePropertyMapper;
import com.zone.process.infrastructure.db.mapper.ProcessDefNodeVariableMapper;
import com.zone.process.shared.enums.BpmnNodeTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
