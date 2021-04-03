package com.zone.process.application.service.query;

import com.zone.commons.entity.LoginUser;
import com.zone.commons.entity.Page;
import com.zone.process.application.service.query.assembler.TaskDetailDTOAssembler;
import com.zone.process.application.service.query.dto.TaskDetailDTO;
import com.zone.process.infrastructure.db.dataobject.ProcessInstDO;
import com.zone.process.infrastructure.db.query.ProcessInstQuery;
import com.zone.process.shared.process.ProcessEngineQueryAPI;
import com.zone.process.shared.process.valueobject.TaskVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 10:44 上午
 * @Description:
 */
@Slf4j
@Service
public class ProcessTaskQueryService {

    @Autowired
    private ProcessEngineQueryAPI processEngineQueryAPI;

    @Autowired
    private ProcessInstQuery instQuery;

    /**
     * 分页查询任务
     */
    public Page<TaskDetailDTO> page(String name, Long startTime, Long endTime, Long submitBy, Integer pageNo, Integer pageSize, LoginUser loginUser) {

        List<TaskVO> taskList = processEngineQueryAPI.queryRelateTaskList(loginUser.getUserId(), loginUser.getRoleId());
        List<String> taskIdList = taskList.stream().map(task -> task.getTaskId()).collect(Collectors.toList());
        List<String> procInstIdList = taskList.stream().map(task -> task.getProcInstId()).collect(Collectors.toList());

        // 按条件根据流程实例进行过滤
        List<ProcessInstDO> instList = instQuery.queryInProcInstIdList(procInstIdList, name,
                startTime, endTime, submitBy);
        procInstIdList = instList.stream().map(inst -> inst.getProcInstId()).collect(Collectors.toList());

        // 以任务的维度进行分页
        Page<TaskVO> taskPage = processEngineQueryAPI.pageTaskList(taskIdList, procInstIdList, pageNo, pageSize);
        Map<String, ProcessInstDO> instDOMap = instList.stream()
                .collect(Collectors.toMap(key -> key.getProcInstId(), value -> value));
        return taskPage.convert(task -> TaskDetailDTOAssembler.getTaskDetailDTO(
                instDOMap.get(task.getProcInstId()), task.getTaskId()));
    }
}
