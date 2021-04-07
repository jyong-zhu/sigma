<p align="center"><h1>Sigma</h1></p>

## 项目功能点

- [x] 部署流程定义，支持版本升级
- [x] 支持 BPMN2.0 设计流程定义，节点元素有开始事件、结束事件、用户任务、排他网关、并行网关等。
- [x] 支持用户任务节点上挂靠多个表单
- [x] 支持自定义流程实例上下文的参数
- [x] 支持按角色或者用户 id 分配用户任务
- [x] 支持流程实例上下文的参数与表单数据进行关联
- [x] 发起流程实例操作
- [x] 中止流程实例操作
- [x] 完成用户任务操作
- [x] 转派用户任务操作
- [x] 用户任务支持会签/或签
- [x] 支持表单模版设计
- [x] 流程实例的列表与详情
- [x] 待办任务的列表与详情
- [x] 流程定义的列表与详情
- [x] 流程分类的列表与详情
- [x] 表单的列表与详情
- [ ] 用户系统支持 RBAC 权限模型
- [ ] 集成 vue-element-admin 开发后台界面

## 领域知识

在开始之前，首先需要明确工作流这个领域中的几个概念：流程、流程定义、流程实例、任务、表单。

1. 流程（Process）：`流程`是一个比较宽泛的概念，这个词其实既可以表示`流程定义`，也可以表示`流程实例`。举个例子，我们说`新建一个流程`时，其实说的是`新建一个流程定义`；而我们说`发起一个流程`时，其实说的是`发起一个流程实例`。在实际交流中，`流程`这个词用来指代我们一般理解的工作流，我们需要用`流程定义`和`流程实例`这两个更为准确的词来描述业务场景。
2. 流程定义（Process Definition）：`流程定义`用来描述一个流程的结构，其中包含开始节点、中止节点、网关节点、任务节点等元素。`流程定义`可以类比成 Java 中类的概念，是静态的数据。
3. 流程实例（Process Instance）：`流程实例`是就相当于是`流程定义`的一次执行，它相当于 Java 中的对象的概念，一个类可以生成多个对象，每个对象都有自己独有的属性。类似的，一个`流程定义`可以有多个`流程实例`，每个`流程实例`都有自己的参数值、人员信息等。因此，不同的流程实例在同一个流程定义上对应的执行走向可能是不一样的。
4. 任务（Task）：`任务`是`流程实例`在流转过程中产生的东西。在工作流这个领域中，常常出现一个流程流转到某个节点，然后需要这个节点上的人员执行相关的操作，这个相关的操作指的就是`任务`，它是`流程实例`流转过程中产生的。
5. 表单（Form）：`表单`是挂靠在节点上的，属于静态数据，在`流程定义`中需要指定某个节点上拥有哪些`表单`。表单中的数据是每个流程实例所特有的，比如发起一个报修流程实例的时候需要填写相关的报修数据，不同的报修流程实例的报修数据可以是不一样的。

详细的流程相关的领域知识以及我整个系统的设计思路见[这里](./docs/domain-knowledge-and-design-ideas.md)。

## 落地 DDD

本项目基于领域驱动设计（DDD）的设计思想，并采用六边形架构进行落地实现，代码的结构如下：

```latex
├── application.service
│       ├── command
│       │   ├── cmd
│       │   └── transfer
│       └── query
│           ├── assembler
│           └── dto
├── domain
│   ├── agg
│   ├── event
│   ├── repository
│   ├── service
│   └── valueobject
├── infrastructure
│   ├── client.impl
│   ├── db
│   │   ├── adapter
│   │   ├── dataobject
│   │   ├── mapper
│   │   ├── query
│   │   └── repository.impl
│   ├── inbound
│   │   ├── job.controller
│   │   ├── rpc.controller
│   │   └── web.controller
│   └── process
│       ├── adapter
│       └── impl
└── shared
    ├── client
    ├── constants
    ├── enums
    ├── process
    │   └── valueobject
    └── utils
```

关于本项目的 DDD 的详细介绍与规范见[这里](./docs/ddd-for-this-project.md)。

## 项目脚手架

本项目基于 Spring Cloud 进行开发，采用 Nacos 作为注册中心，Spring Cloud Gateway 作为网关，Open Feign 实现微服务间的调用，同时还封装了一些通用的 starter。详细代码结构如下：

```latex
├── sigma-parent       // 整个项目依赖版本管理 
├── sigma-rpc		   // 服务间调用的 feign 接口定义以及相关的 DTO
├── sigma-services     // 该 module 下包含所有的微服务
│   ├── sigma-auth     // 用户中心
│   ├── sigma-gateway  // 网关
│   ├── sigma-process  // 流程服务，本项目的核心模块
│   └── sigma-registry // 注册中心，nacos
└── sigma-starters     // 封装的一些本项目通用的 starters
    ├── sigma-starter-commons     // 通用的类或方法
    ├── sigma-starter-feign	      // feign 相关的配置，如 feign 调用的拦截器
    ├── sigma-starter-generator   // mybatis-plus 代码生成器
    ├── sigma-starter-mybatis     // mybatis 相关的配置，如日志打印、sql 注入等
    ├── sigma-starter-web         // spring-boot-starter-web 相关的依赖以及一些拦截器、过滤器等
    ├── // starter 继续扩展中
```

## 友情链接

1. **camunda**：https://camunda.org
2. **vue-element-admin**：https://github.com/PanJiaChen/vue-element-admin
3. **ferry**：https://github.com/lanyulei/ferry