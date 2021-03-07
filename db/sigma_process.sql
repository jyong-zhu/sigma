drop schema if exists `sigma_process`;
create schema `sigma_process`;
use `sigma_process`;

CREATE TABLE `process_category`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`        varchar(45)  NOT NULL COMMENT '分类名称',
    `icon_url`    varchar(255) NOT NULL DEFAULT '' COMMENT '分类展示图标',

    `create_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`   bigint(20)   NOT NULL COMMENT 'user_id',
    `create_name` varchar(45)  NOT NULL COMMENT 'user_name',
    `update_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`   bigint(20)   NULL COMMENT 'user_id',
    `update_name` varchar(45)  NULL COMMENT 'user_name',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `category_name` (`name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='流程分类';

-- proc_def_key + proc_def_version 决定唯一的流程定义
-- 表单的定义与流程的定义是互不干扰的，在流程中能够选择表单
CREATE TABLE `process_def`
(
    `id`               bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `category_id`      varchar(45)  NOT NULL COMMENT '流程分类id',
    `proc_def_id`      varchar(200) NOT NULL COMMENT 'camunda中的流程定义id，关联到ACT_RE_PROCDEF',
    `proc_def_key`     varchar(200) NOT NULL COMMENT 'camunda中的流程定义key，关联到ACT_RE_PROCDEF的key',
    `proc_def_version` int          NOT NULL DEFAULT '1' COMMENT 'camunda中的流程定义版本，关联到ACT_RE_PROCDEF的version',
    `name`             varchar(45)  NOT NULL COMMENT '流程定义名称',
    `status`           tinyint(1)   NOT NULL DEFAULT '1' COMMENT '流程定义的状态，0-禁用 1-启用',
    `bpmn_xml`         text         NULL COMMENT '当前流程的bpmn2.0的xml定义',
    `form_ids`         text         NULL COMMENT '当前流程定义下所关联的全部表单id, 用,隔开',
    `icon_url`         varchar(255) NOT NULL DEFAULT '' COMMENT '分类展示图标',

    `create_time`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`        bigint(20)   NOT NULL COMMENT 'user_id',
    `create_name`      varchar(45)  NOT NULL COMMENT 'user_name',
    `update_time`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`        bigint(20)   NULL COMMENT 'user_id',
    `update_name`      varchar(45)  NULL COMMENT 'user_name',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `process_def_name` (`name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='流程定义';

-- 前置节点的必填字段才能作为条件
-- 对于 camunda 中的参数，这里做一个限制：不涉及除了流转人以外的所有参数的设置，这些参数都用扩展表来管理
CREATE TABLE `process_def_node`
(
    `id`                  bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `def_id`              varchar(45) NOT NULL COMMENT '流程定义id',
    `name`                varchar(45) NOT NULL COMMENT '流程节点名称',
    `bpmn_node_type`      varchar(45) NOT NULL COMMENT 'bpmn2.0中节点的类型, userTask/startEvent/endEvent',
    `bpmn_node_id`        varchar(45) NOT NULL COMMENT 'bpmn2.0中节点的id',
    `parent_bpmn_node_id` varchar(45) NOT NULL COMMENT '当前节点所属的父节点的id',
    -- 以下两种字段只有在节点类型是 task 的时候才会有，表示流转到当前节点的处理人是谁
    `node_people_type`    varchar(45) NOT NULL DEFAULT '' COMMENT '当前流程的处理人类型，指定用户/指定角色/用户参数/角色参数',
    -- 用户在查询待办的时候通过 所属角色 && user_id 去找待办
    `node_people_value`   varchar(45) NOT NULL DEFAULT '' COMMENT '当前流程的处理人的信息，可以为参数${xxx}, 也可以为具体的值，这部分信息存储在candidateUser中',
    `input_form_ids`      text        NULL COMMENT '挂靠在当前节点用于输入的表单， 用,隔开',
    `display_form_ids`    text        NULL COMMENT '挂靠在当前节点用于展示的表单， 用,隔开',

    `create_time`         timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`           bigint(20)  NOT NULL COMMENT 'user_id',
    `create_name`         varchar(45) NOT NULL COMMENT 'user_name',
    `update_time`         timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`           bigint(20)  NULL COMMENT 'user_id',
    `update_name`         varchar(45) NULL COMMENT 'user_name',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='流程定义中的节点信息';

-- 流程节点上的参数
-- 表示当前 node_id 中需要传入流程中的参数, 这个参数的值来源于 form_id 这张表中的 field_id 这个字段
CREATE TABLE `process_def_node_variable`
(
    `id`            bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `node_id`       varchar(45)  NOT NULL COMMENT '所属流程节点id',
    `variable_name` varchar(45)  NOT NULL COMMENT '流程节点的变量名称，这个参数可以在后续的节点中被引用',
    `form_id`       varchar(45)  NOT NULL DEFAULT '' COMMENT '当前节点上挂靠的表单id',
    `field_id`      varchar(45)  NOT NULL DEFAULT '' COMMENT '该表单中的字段id',
    `default_value` varchar(255) NOT NULL DEFAULT '' COMMENT '默认的参数值',
    `ext`           varchar(255) NOT NULL DEFAULT '' COMMENT '扩展字段',

    `create_time`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`     bigint(20)   NOT NULL COMMENT 'user_id',
    `create_name`   varchar(45)  NOT NULL COMMENT 'user_name',
    `update_time`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`     bigint(20)   NULL COMMENT 'user_id',
    `update_name`   varchar(45)  NULL COMMENT 'user_name',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_variable_name` (`node_id`, `variable_name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='流程定义中节点上的变量信息';

-- property 与 variable 配合能够完成节点参数的动态配置
-- property 用于节点属性的配置 variable 用于节点的流转
CREATE TABLE `process_def_node_property`
(
    `id`             bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `node_id`        varchar(45)  NOT NULL COMMENT '所属流程节点id',
    `property_name`  varchar(45)  NOT NULL COMMENT '流程节点中的属性名称',
    `property_value` text         NOT NULL COMMENT '属性值',
    `desc`           varchar(255) NOT NULL DEFAULT '' COMMENT '属性作用描述',
    `ext`            varchar(255) NOT NULL DEFAULT '' COMMENT '扩展字段',

    `create_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`      bigint(20)   NOT NULL COMMENT 'user_id',
    `create_name`    varchar(45)  NOT NULL COMMENT 'user_name',
    `update_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`      bigint(20)   NULL COMMENT 'user_id',
    `update_name`    varchar(45)  NULL COMMENT 'user_name',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_property_name` (`node_id`, `property_name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='流程定义中节点上的属性信息';

-- 流程实例相关
CREATE TABLE `process_instance`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `proc_inst_id` varchar(45)  NOT NULL COMMENT 'camunda中的流程实例id',
    `def_id`       varchar(45)  NOT NULL COMMENT '流程定义id',
    `name`         varchar(45)  NOT NULL COMMENT '流程实例名称',
    `status`       varchar(45)  NOT NULL COMMENT '流程实例的状态，处理中/已完结',
    `start_time`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '流程实例发起时间',
    `due_time`     timestamp    NULL COMMENT '流程实例要求时间',
    `submit_by`    varchar(45)  NOT NULL COMMENT '流程实例提交人的user_id',
    `submit_name`  varchar(45)  NOT NULL COMMENT '流程实例提交人的姓名',
    `desc`         varchar(255) NOT NULL DEFAULT '' COMMENT '描述信息',

    `create_time`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`    bigint(20)   NOT NULL COMMENT 'user_id',
    `create_name`  varchar(45)  NOT NULL COMMENT 'user_name',
    `update_time`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`    bigint(20)   NULL COMMENT 'user_id',
    `update_name`  varchar(45)  NULL COMMENT 'user_name',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='流程实例信息';

-- 操作记录
CREATE TABLE `process_instance_operation`
(
    `id`             bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `instance_id`    varchar(45)  NOT NULL COMMENT '流程实例id',
    `operation_type` varchar(45)  NOT NULL COMMENT '操作类型（发起/中止/提交/更新）',
    `bpmn_node_id`   varchar(45)  NOT NULL COMMENT '操作所处的节点id',
    `comment`        varchar(255) NOT NULL DEFAULT '' COMMENT '操作备注',
    `operate_by`     varchar(45)  NOT NULL COMMENT '操作人的user_id',
    `operate_name`   varchar(45)  NOT NULL COMMENT '操作人的姓名',
    `ext`            varchar(255) NOT NULL DEFAULT '' COMMENT '扩展数据',

    `create_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`      bigint(20)   NOT NULL COMMENT 'user_id',
    `create_name`    varchar(45)  NOT NULL COMMENT 'user_name',
    `update_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`      bigint(20)   NULL COMMENT 'user_id',
    `update_name`    varchar(45)  NULL COMMENT 'user_name',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='流程实例操作信息';

-- 表单数据存储
CREATE TABLE `process_instance_data`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `instance_id`  varchar(45)  NOT NULL COMMENT '流程实例id',
    `bpmn_node_id` varchar(45)  NOT NULL COMMENT '当前录入表单所处的节点id',
    `form_id`      bigint(20)   NOT NULL COMMENT '所属表单id',
    `form_json`    text         NOT NULL COMMENT '表单的json串，冗余字段',
    `form_data`    text         NOT NULL COMMENT '流程实例在表单中对应的数据',
    `ext`          varchar(255) NOT NULL DEFAULT '' COMMENT '扩展信息',

    `create_time`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`    bigint(20)   NOT NULL COMMENT 'user_id',
    `create_name`  varchar(45)  NOT NULL COMMENT 'user_name',
    `update_time`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`    bigint(20)   NULL COMMENT 'user_id',
    `update_name`  varchar(45)  NULL COMMENT 'user_name',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='流程实例的表单数据';

-- 表单定义相关
CREATE TABLE `form_structure`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`        varchar(45)  NOT NULL COMMENT '表单名称',
    `form_key`    varchar(45)  NOT NULL COMMENT '表单的key',
    `version`     int          NOT NULL DEFAULT '1' COMMENT '表单的版本',
    `form_json`   text         NOT NULL COMMENT '表单的json串',
    `desc`        varchar(255) NOT NULL DEFAULT '' COMMENT '描述',

    `create_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`   bigint(20)   NOT NULL COMMENT 'user_id',
    `create_name` varchar(45)  NOT NULL COMMENT 'user_name',
    `update_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`   bigint(20)   NULL COMMENT 'user_id',
    `update_name` varchar(45)  NULL COMMENT 'user_name',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_form_version` (`form_key`, `version`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='表单结构信息';
