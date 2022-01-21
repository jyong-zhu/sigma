package com.zone.process.shared.process;

import com.zone.commons.entity.Page;
import com.zone.process.shared.process.valueobject.ProcessInstanceVO;
import com.zone.process.shared.process.valueobject.TaskVO;
import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 3:53 下午
 * @Description:
 */
public interface ProcessEngineQueryAPI {

  ProcessInstanceVO syncInstance(String procInstId);

  TaskVO queryRelateTaskById(String taskId, Long userId, List<Long> roleIdList);

  List<TaskVO> queryRelateTaskList(Long userId, List<Long> roleIdList);

  Page<TaskVO> pageTaskList(List<String> taskIdList, List<String> procInstIdList, Integer pageNo, Integer pageSize);
}
