package com.zone.process.infrastructure.db.adapter;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import com.zone.commons.util.IdWorkerUtil;
import com.zone.process.domain.valueobject.InstDataVO;
import com.zone.process.domain.valueobject.InstOperationVO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstDataDO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstOperationDO;

import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:37 下午
 * @Description:
 */
public class ProcessInstAggAdapter {

    public static List<ProcessInstDataDO> getInstDataDOList(List<InstDataVO> dataVOList, Long instanceId) {
        List<ProcessInstDataDO> list = Lists.newArrayList();
        dataVOList.forEach(tmp -> {
            ProcessInstDataDO dataDO = BeanUtil.copyProperties(tmp, ProcessInstDataDO.class);
            dataDO.setId(IdWorkerUtil.nextId());
            dataDO.setInstanceId(instanceId);
            dataDO.setVersion(0);
            list.add(dataDO);
        });
        return list;
    }

    public static List<ProcessInstOperationDO> getInstOperationDOList(List<InstOperationVO> operationVOList, Long instanceId) {
        List<ProcessInstOperationDO> list = Lists.newArrayList();
        operationVOList.forEach(tmp -> {
            ProcessInstOperationDO operationDO = BeanUtil.copyProperties(tmp, ProcessInstOperationDO.class);
            operationDO.setId(IdWorkerUtil.nextId());
            operationDO.setInstanceId(instanceId);
            operationDO.setVersion(0);
            list.add(operationDO);
        });
        return list;
    }
}
