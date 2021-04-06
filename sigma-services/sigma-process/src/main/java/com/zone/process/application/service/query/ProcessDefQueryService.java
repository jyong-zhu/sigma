package com.zone.process.application.service.query;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zone.commons.entity.Page;
import com.zone.mybatis.util.PlusPageConverter;
import com.zone.process.application.service.query.assembler.DefDetailDTOAssembler;
import com.zone.process.application.service.query.assembler.DefNodeDetailDTOAssembler;
import com.zone.process.application.service.query.assembler.StartNodeDTOAssembler;
import com.zone.process.application.service.query.dto.DefDetailDTO;
import com.zone.process.application.service.query.dto.DefNodeDetailDTO;
import com.zone.process.application.service.query.dto.StartNodeDTO;
import com.zone.process.infrastructure.db.dataobject.*;
import com.zone.process.infrastructure.db.query.FormStructureQuery;
import com.zone.process.infrastructure.db.query.ProcessDefQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 10:43 上午
 * @Description:
 */
@Slf4j
@Service
public class ProcessDefQueryService {

    @Autowired
    private ProcessDefQuery defQuery;

    @Autowired
    private FormStructureQuery formQuery;

    /**
     * 分页查询流程定义，不含节点的信息
     */
    public Page<DefDetailDTO> page(Long categoryId, String name, Integer pageNo, Integer pageSize) {
        Page<ProcessDefDO> page = PlusPageConverter.convert(defQuery.page(categoryId, name, pageNo, pageSize));

        return page.convert(processDefDO -> BeanUtil.copyProperties(processDefDO, DefDetailDTO.class));
    }

    /**
     * 含有完整的节点的信息，包括节点变量与节点属性
     */
    public DefDetailDTO queryDetailByKey(String procDefKey) {
        ProcessDefDO def = defQuery.queryDefByProcKey(procDefKey);
        Preconditions.checkNotNull(def, "流程定义不存在");

        List<ProcessDefNodeDO> nodeList = defQuery.queryNodeListByDefId(def.getId());
        List<Long> nodeIdList = nodeList.stream().map(tmp -> tmp.getId()).collect(Collectors.toList());

        List<ProcessDefNodeVariableDO> variableList = defQuery.queryNodeVariableList(nodeIdList);
        List<ProcessDefNodePropertyDO> propertyList = defQuery.queryNodePropertyList(nodeIdList);

        return DefDetailDTOAssembler.getDefDetailDTO(def, nodeList, variableList, propertyList);
    }

    /**
     * 开始节点的信息，主要是开始节点的表单信息
     */
    public StartNodeDTO queryStartNodeDetail(String procDefKey) {
        ProcessDefDO def = defQuery.queryDefByProcKey(procDefKey);
        Preconditions.checkNotNull(def, "流程定义不存在");

        ProcessDefNodeDO startNode = defQuery.queryStartNode(def.getId());

        List<FormStructureDO> formDOList = formQuery.queryByIds(startNode.getInputFormIds());

        return StartNodeDTOAssembler.getStartNodeDTO(def, formDOList);
    }

    /**
     * 只有节点的信息
     */
    public List<DefNodeDetailDTO> queryNodeList(Long defId) {
        List<ProcessDefNodeDO> nodeDOList = defQuery.queryNodeListByDefId(defId);
        return DefNodeDetailDTOAssembler.getDefNodeDetailDTOList(nodeDOList, Lists.newArrayList(), Lists.newArrayList());
    }

}
