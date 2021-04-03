drop schema if exists `sigma_process`;
create schema `sigma_process`;
use `sigma_process`;

CREATE TABLE `process_category`
(
    `id`          bigint(20)   NOT NULL COMMENT '主键ID',
    `name`        varchar(45)  NOT NULL COMMENT '分类名称',
    `icon_url`    varchar(255) NOT NULL DEFAULT '' COMMENT '分类展示图标',

    `version`     int          NOT NULL DEFAULT '0' COMMENT '乐观锁版本',
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

CREATE TABLE `process_def`
(
    `id`                 bigint(20)   NOT NULL COMMENT '主键ID',
    `category_id`        bigint(20)   NOT NULL COMMENT '流程分类id',
    `proc_def_id`        varchar(200) NOT NULL COMMENT 'camunda中的流程定义id，关联到ACT_RE_PROCDEF',
    `proc_def_key`       varchar(200) NOT NULL COMMENT 'camunda中的流程定义key，关联到ACT_RE_PROCDEF的key',
    `proc_def_version`   int          NOT NULL DEFAULT '1' COMMENT 'camunda中的流程定义版本，关联到ACT_RE_PROCDEF的version',
    `name`               varchar(45)  NOT NULL COMMENT '流程定义名称',
    `status`             tinyint(1)   NOT NULL DEFAULT '1' COMMENT '流程定义的状态，0-禁用 1-启用',
    `bpmn_xml`           text         NULL COMMENT '当前流程的bpmn2.0的xml定义',
    `start_bpmn_node_id` varchar(45)  NOT NULL COMMENT '开始节点的id',
    `form_ids`           text         NULL COMMENT '当前流程定义下所关联的全部表单id, 用,隔开',
    `icon_url`           varchar(255) NOT NULL DEFAULT '' COMMENT '流程定义展示图标',

    `version`            int          NOT NULL DEFAULT '0' COMMENT '乐观锁版本',
    `create_time`        timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`          bigint(20)   NOT NULL COMMENT 'user_id',
    `create_name`        varchar(45)  NOT NULL COMMENT 'user_name',
    `update_time`        timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`          bigint(20)   NULL COMMENT 'user_id',
    `update_name`        varchar(45)  NULL COMMENT 'user_name',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_proc_key_version` (`proc_def_key`, `proc_def_version`) USING BTREE,
    UNIQUE KEY `uk_proc_def_id` (`proc_def_id`) USING BTREE,
    INDEX `index_category_id` (`category_id`) USING BTREE,
    INDEX `index_def_name` (`name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='流程定义';

CREATE TABLE `process_def_node`
(
    `id`                  bigint(20)  NOT NULL COMMENT '主键ID',
    `def_id`              bigint(20)  NOT NULL COMMENT '流程定义id',
    `name`                varchar(45) NOT NULL COMMENT '流程节点名称',
    `bpmn_node_type`      varchar(45) NOT NULL COMMENT 'bpmn2.0中节点的类型, userTask/startEvent/endEvent',
    `bpmn_node_id`        varchar(45) NOT NULL COMMENT 'bpmn2.0中节点的id',
    `parent_bpmn_node_id` varchar(45) NOT NULL COMMENT '当前节点所属的父节点的id',
    `node_people_type`    varchar(45)          DEFAULT NULL COMMENT '当前节点处理人类型，ID/PARAM/PAR_MULTI_INSTANCE/SEQ_MULTI_INSTANCE',
    `node_people_value`   varchar(45)          DEFAULT NULL COMMENT '当前节点处理人的值，可以是具体值，也可以是变量名',
    `input_form_ids`      text        NULL COMMENT '挂靠在当前节点用于输入的表单， 用,隔开',
    `display_form_ids`    text        NULL COMMENT '挂靠在当前节点用于展示的表单， 用,隔开',

    `version`             int         NOT NULL DEFAULT '0' COMMENT '乐观锁版本',
    `create_time`         timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`           bigint(20)  NOT NULL COMMENT 'user_id',
    `create_name`         varchar(45) NOT NULL COMMENT 'user_name',
    `update_time`         timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`           bigint(20)  NULL COMMENT 'user_id',
    `update_name`         varchar(45) NULL COMMENT 'user_name',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `index_def_id` (`def_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='流程定义中的节点信息';

CREATE TABLE `process_def_node_variable`
(
    `id`            bigint(20)  NOT NULL COMMENT '主键ID',
    `node_id`       bigint(20)  NOT NULL COMMENT '流程节点id',
    `bpmn_node_id`  varchar(45) NOT NULL COMMENT 'bpmn中的流程节点id(冗余)',
    `variable_name` varchar(45) NOT NULL COMMENT '流程节点的变量名称，这个参数可以在后续的节点中被引用',
    `java_type`     varchar(45) NOT NULL DEFAULT 'java.lang.String' COMMENT '流程节点变量的类型',
    `form_id`       varchar(45) NOT NULL COMMENT '当前节点上挂靠的表单id',
    `field_id`      varchar(45) NOT NULL COMMENT '该表单中的字段id',
    `default_value` varchar(255)         DEFAULT NULL COMMENT '默认的参数值',

    `version`       int         NOT NULL DEFAULT '0' COMMENT '乐观锁版本',
    `create_time`   timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`     bigint(20)  NOT NULL COMMENT 'user_id',
    `create_name`   varchar(45) NOT NULL COMMENT 'user_name',
    `update_time`   timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`     bigint(20)  NULL COMMENT 'user_id',
    `update_name`   varchar(45) NULL COMMENT 'user_name',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_variable_name` (`node_id`, `variable_name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='流程定义中节点上的变量信息';

CREATE TABLE `process_def_node_property`
(
    `id`             bigint(20)  NOT NULL COMMENT '主键ID',
    `node_id`        bigint(20)  NOT NULL COMMENT '流程节点id',
    `bpmn_node_id`   varchar(45) NOT NULL COMMENT 'bpmn中的流程节点id(冗余)',
    `property_name`  varchar(45) NOT NULL COMMENT '流程节点中的属性名称',
    `property_value` text        NOT NULL COMMENT '属性值',
    `description`    varchar(255)         DEFAULT NULL COMMENT '属性作用描述',

    `version`        int         NOT NULL DEFAULT '0' COMMENT '乐观锁版本',
    `create_time`    timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`      bigint(20)  NOT NULL COMMENT 'user_id',
    `create_name`    varchar(45) NOT NULL COMMENT 'user_name',
    `update_time`    timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`      bigint(20)  NULL COMMENT 'user_id',
    `update_name`    varchar(45) NULL COMMENT 'user_name',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_property_name` (`node_id`, `property_name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='流程定义中节点上的属性信息';

CREATE TABLE `process_inst`
(
    `id`             bigint(20)   NOT NULL COMMENT '主键ID',
    `proc_inst_id`   varchar(45)  NOT NULL COMMENT 'camunda中的流程实例id',
    `def_id`         bigint(20)   NOT NULL COMMENT '流程定义id',
    `name`           varchar(45)  NOT NULL COMMENT '流程实例名称',
    `status`         varchar(45)  NOT NULL COMMENT '流程实例的状态，进行中/已结束',
    `cur_node_id`    varchar(45)  NOT NULL COMMENT '当前流程停留的节点id, 多个用,隔开',
    `cur_node_name`  varchar(45)  NOT NULL COMMENT '当前流程停留的节点名称, 多个用,隔开',
    `cur_handler_id` varchar(255) NOT NULL COMMENT '当前处理人的id，多个用,隔开，支持userId/roleId',
    `due_time`       timestamp    NULL COMMENT '流程实例要求时间',
    `submit_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '流程实例发起时间',
    `submit_by`      bigint(20)   NOT NULL COMMENT '流程实例提交人的user_id',
    `submit_name`    varchar(45)  NOT NULL COMMENT '流程实例提交人的姓名',
    `comment`        varchar(255) NOT NULL DEFAULT '' COMMENT '操作流程实例的备注',
    `description`    varchar(255) NOT NULL DEFAULT '' COMMENT '描述信息',

    `version`        int          NOT NULL DEFAULT '0' COMMENT '乐观锁版本',
    `create_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`      bigint(20)   NOT NULL COMMENT 'user_id',
    `create_name`    varchar(45)  NOT NULL COMMENT 'user_name',
    `update_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`      bigint(20)   NULL COMMENT 'user_id',
    `update_name`    varchar(45)  NULL COMMENT 'user_name',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `index_name` (`name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='流程实例信息';

CREATE TABLE `process_inst_operation`
(
    `id`             bigint(20)   NOT NULL COMMENT '主键ID',
    `instance_id`    bigint(20)   NOT NULL COMMENT '流程实例id',
    `operation_type` varchar(45)  NOT NULL COMMENT '操作类型（start/complete/update/insert/stop）',
    `bpmn_node_id`   varchar(45)  NOT NULL COMMENT '操作所处的节点id',
    `task_id`        varchar(45)  NOT NULL DEFAULT '' COMMENT '操作的任务id',
    `comment`        varchar(255) NOT NULL DEFAULT '' COMMENT '操作备注',
    `operate_by`     bigint(20)   NOT NULL COMMENT '操作人的user_id',
    `operate_name`   varchar(45)  NOT NULL COMMENT '操作人的姓名',
    `form_data`      text         NOT NULL COMMENT '操作时提交的表单数据',
    `ext`            varchar(255)          DEFAULT NULL COMMENT '扩展字段',

    `version`        int          NOT NULL DEFAULT '0' COMMENT '乐观锁版本',
    `create_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`      bigint(20)   NOT NULL COMMENT 'user_id',
    `create_name`    varchar(45)  NOT NULL COMMENT 'user_name',
    `update_time`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`      bigint(20)   NULL COMMENT 'user_id',
    `update_name`    varchar(45)  NULL COMMENT 'user_name',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `index_operate_by` (`operate_by`) USING BTREE,
    INDEX `index_instance_id` (`instance_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='流程实例操作信息';

CREATE TABLE `process_inst_data`
(
    `id`           bigint(20)  NOT NULL COMMENT '主键ID',
    `instance_id`  bigint(20)  NOT NULL COMMENT '流程实例id',
    `bpmn_node_id` varchar(45) NOT NULL COMMENT '当前录入表单所处的节点id',
    `form_id`      bigint(20)  NOT NULL COMMENT '所属表单id',
    `form_data`    text        NOT NULL COMMENT '流程实例在表单中对应的数据',

    `version`      int         NOT NULL DEFAULT '0' COMMENT '乐观锁版本',
    `create_time`  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`    bigint(20)  NOT NULL COMMENT 'user_id',
    `create_name`  varchar(45) NOT NULL COMMENT 'user_name',
    `update_time`  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`    bigint(20)  NULL COMMENT 'user_id',
    `update_name`  varchar(45) NULL COMMENT 'user_name',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_form_id` (`instance_id`, `form_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='流程实例的表单数据';

CREATE TABLE `form_structure`
(
    `id`           bigint(20)   NOT NULL COMMENT '主键ID',
    `name`         varchar(45)  NOT NULL COMMENT '表单名称',
    `form_key`     varchar(45)  NOT NULL COMMENT '表单的key',
    `form_version` int          NOT NULL DEFAULT '1' COMMENT '表单的版本',
    `form_json`    text         NOT NULL COMMENT '表单的json串',
    `description`  varchar(255) NOT NULL DEFAULT '' COMMENT '描述',

    `version`      int          NOT NULL DEFAULT '0' COMMENT '乐观锁版本',
    `create_time`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`    bigint(20)   NOT NULL COMMENT 'user_id',
    `create_name`  varchar(45)  NOT NULL COMMENT 'user_name',
    `update_time`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`    bigint(20)   NULL COMMENT 'user_id',
    `update_name`  varchar(45)  NULL COMMENT 'user_name',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_form_version` (`form_key`, `version`) USING BTREE,
    INDEX `index_form_name` (`name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='表单结构信息';

INSERT INTO sigma_process.process_category (id, name, icon_url, version, create_time, create_by, create_name,
                                            update_time, update_by, update_name)
VALUES (1, '维修工单', '', 0, '2021-04-01 16:42:38', 1, 'admin', '2021-04-01 16:42:38', 1, 'admin');