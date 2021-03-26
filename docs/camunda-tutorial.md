## Camunda Service 简介

| service             | description                                                  |
| ------------------- | ------------------------------------------------------------ |
| RepositoryService   | 操作静态数据，管理 process definition 与 deployment          |
| RuntimeService      | 操作动态数据，如保存流程上下文的变量，查询 process instance 与 execution |
| TaskService         | 操作任务，把任务分配给用户，完成任务，申请任务               |
| IdentityService     | 管理角色或者用户，camunda 本身不关注用户是否合规             |
| FormService         | 操作 camunda 中自带的表单，非必需                            |
| HistoryService      | 提供对 process instance、task 的历史数据查询功能             |
| ManagementService   | 管理数据库表的元数据信息，同时提供对 jobs 的相关操作         |
| FilterService       | 用于过滤数据                                                 |
| ExternalTaskService | 用于外部任务的相关操作                                       |

## Camunda 查询示例

1. **链式查询**

   ```java
   List<Task> tasks = taskService.createTaskQuery()
     .taskAssignee("kermit")
     .processVariableValueEquals("orderId", "0815")
     .orderByDueDate().asc()
     .list();
   ```

2. **分页查询**

   ```java
   List<Task> tasks = taskService.createTaskQuery()
     .taskAssignee("kermit")
     .processVariableValueEquals("orderId", "0815")
     .orderByDueDate().asc()
     .listPage(20, 50);
   ```

3. **or：仅适用于对 process instance 和 task 操作**

   ```java
   // assignee = "John Munda" AND (name = "Approve Invoice" OR priority = 5)
   // or 和 endOr 之间的关联关系是 or
   List<Task> tasks = taskService.createTaskQuery()
     .taskAssignee("John Munda")
     .or()
       .taskName("Approve Invoice")
       .taskPriority(5)
     .endOr()
     .list();
   ```

   **or 中关于参数的过滤条件不会被覆盖，但是对非参数的过滤条件会被覆盖，如下：**

   ```java
   List<Task> tasks = taskService.createTaskQuery()
     .or()
     	// 以下3个过滤条件不会彼此覆盖
       .processVariableValueEquals("orderId", "0815")
       .processVariableValueEquals("orderId", "4711")
       .processVariableValueEquals("orderId", "4712")
     .endOr()
     .list();
   
   List<Task> tasks = taskService.createTaskQuery()
     .or()
     	// 以下2个过滤条件会被覆盖，这个语句中只会查出 controlling，不会查出 sales
       .taskCandidateGroup("sales")
       .taskCandidateGroup("controlling")
     .endOr()
     .list();
   
   // 针对以上这种覆盖的条件，不用 or，可以用 in 来做，如下：
   taskCandidateGroupIn()
   tenantIdIn()
   processDefinitionKeyIn()
   ```

4. **Native Queries：自定义 sql 查询**

   ```java
   List<Task> tasks = taskService.createNativeTaskQuery().sql("SELECT count(*) FROM " + managementService.getTableName(Task.class) + " T WHERE T.NAME_ = #{taskName}") .parameter("taskName", "aOpenTask").list();
   
   long count = taskService.createNativeTaskQuery().sql("SELECT count(*) FROM " + managementService.getTableName(Task.class) + " T1, " + managementService.getTableName(VariableInstanceEntity.class) + " V1 WHERE V1.TASK_ID_ = T1.ID_").count();
   ```

## Camunda 使用

1. **部署流程定义**

   ```java
   Deployment deployment = repositoryService.createDeployment().addInputStream(processName + ".bpmn", xmlStream).deploy();
   ```

2. **发起流程实例**

   ```java
   runtimeService.startProcessInstanceByKey(processDef.getProcDefKey(), paramMap);
   ```

3. **中止流程实例**

   ```java
   // 首先，camunda 将 runtime 中的一些数据删除
   // 再将 ACT_HI_PROCINST 中的流程实例状态改为 INTERNALLY_TERMINATED
   runtimeService.deleteProcessInstance(procInstId, comment);
   ```

4. **查询任务列表**

   ```java
   // 任务的执行人信息均在 cadidateGroup 中，无论是 userId 或者 roleId
   // identityList 包含查询人的 userId 和 roleId
   taskService.createTaskQuery().taskCandidateGroupIn(identityList).list();
   ```

5. **提交任务**

   ```java
   taskService.complete(task.getId(), paramMap);
   ```

6. **查询流程实例列表**

   ```java
   // 进行中的流程实例
   historyService.createHistoricProcessInstanceQuery().active().list();
   // 已结束的流程实例
   historyService.createHistoricProcessInstanceQuery().finished().list();
   ```

7. **查询流程实例处于活跃状态的活动**

   ```java
   runtimeService.getActiveActivityIds(processInstance.getId());
   ```

8. **更新流程实例上下文的变量值**

   ```java
   runtimeService.setVariables(processInstance.getId(), paramMap);
   ```

## 参考资料

1. Camunda 官网：https://camunda.com/
2. Camunda 官方文档：https://docs.camunda.org/manual/latest/

