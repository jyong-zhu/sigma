package com.zone.process.application.service.command.transfer;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.google.common.collect.Lists;
import com.zone.commons.util.IdWorkerUtil;
import com.zone.process.application.service.command.cmd.InstStartCommand;
import com.zone.process.domain.agg.ProcessInstAgg;
import com.zone.process.domain.valueobject.InstDataVO;
import com.zone.process.domain.valueobject.InstOperationVO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstDataDO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstOperationDO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 4:11 下午
 * @Description:
 */
public class ProcessInstAggTransfer {

    public static ProcessInstAgg getProcessInstAgg(InstStartCommand startCommand) {
        ProcessInstAgg processInstAgg = new ProcessInstAgg();
        BeanUtil.copyProperties(startCommand, processInstAgg, "dueTime");
        if (startCommand.getDueTime() != null) {
            processInstAgg.setDueTime(LocalDateTimeUtil.of(startCommand.getDueTime()));
        }
        return processInstAgg;
    }

    public static List<ProcessInstOperationDO> getInstOperationDOList(List<InstOperationVO> operationVOList, Long instanceId) {
        return CollectionUtil.isNotEmpty(operationVOList) ? operationVOList.stream().map(tmp -> {
            ProcessInstOperationDO operationDO = BeanUtil.copyProperties(tmp, ProcessInstOperationDO.class);
            operationDO.setId(IdWorkerUtil.nextId());
            operationDO.setInstanceId(instanceId);
            return operationDO;
        }).collect(Collectors.toList()) : Lists.newArrayList();
    }

    public static List<ProcessInstDataDO> getInstDataDOList(List<InstDataVO> dataVOList, Long instanceId) {
        return CollectionUtil.isNotEmpty(dataVOList) ? dataVOList.stream().map(tmp -> {
            ProcessInstDataDO dataDO = BeanUtil.copyProperties(tmp, ProcessInstDataDO.class);
            dataDO.setId(IdWorkerUtil.nextId());
            dataDO.setInstanceId(instanceId);
            return dataDO;
        }).collect(Collectors.toList()) : Lists.newArrayList();
    }
}
