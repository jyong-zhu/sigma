package com.zone.process.application.service.query;

import cn.hutool.core.bean.BeanUtil;
import com.zone.commons.entity.Page;
import com.zone.mybatis.util.PlusPageConverter;
import com.zone.process.application.service.query.dto.DefDetailDTO;
import com.zone.process.application.service.query.dto.DefNodeDetailDTO;
import com.zone.process.application.service.query.dto.DefStartNodeDTO;
import com.zone.process.infrastructure.db.dataobject.ProcessDefDO;
import com.zone.process.infrastructure.db.query.ProcessDefQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


        return null;
    }

    /**
     * 开始节点的信息，主要是开始节点的表单信息
     */
    public DefStartNodeDTO queryStartNodeDetail(String procDefKey) {
        return null;
    }

    /**
     * 只有节点的信息
     */
    public List<DefNodeDetailDTO> queryNodeList(Long defId) {
        return null;
    }
}
