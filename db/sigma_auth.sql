drop schema if exists `sigma_auth`;
create schema `sigma_auth`;
use `sigma_auth`;

CREATE TABLE user_basic
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `account_name` varchar(45)  NOT NULL COMMENT '账户名',
    `user_name`    varchar(45)  NOT NULL COMMENT '姓名',  -- 唯一索引
    `phone`        varchar(45)  NOT NULL COMMENT '手机号', -- 唯一索引
    `password`     varchar(255) NOT NULL COMMENT '密码，采用哈希算法进行单向加密',
    `email`        varchar(45)  NOT NULL COMMENT '邮箱',
    `role_id`      varchar(45)  NOT NULL DEFAULT '' COMMENT '角色id',

    `create_time`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`    bigint(20)   NOT NULL COMMENT 'user_id',
    `create_name`  varchar(45)  NOT NULL COMMENT 'user_name',
    `update_time`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`    bigint(20)   NULL COMMENT 'user_id',
    `update_name`  varchar(45)  NULL COMMENT 'user_name',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_account_name` (`account_name`) using btree,
    UNIQUE KEY `uk_phone` (`phone`) using btree
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='用户基础信息表';

CREATE TABLE `user_ext`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`     bigint(20)   NOT NULL COMMENT '逻辑主键id',
    `field`       varchar(255) NOT NULL COMMENT '扩展字段名称',
    `field_type`  varchar(255) NOT NULL COMMENT '扩展字段的类型，Integer/Double/Long/String/Object等',
    `value`       text COMMENT '扩展字段的值',

    `create_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`   bigint(20)   NOT NULL COMMENT 'user_id',
    `create_name` varchar(45)  NOT NULL COMMENT 'user_name',
    `update_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`   bigint(20)   NULL COMMENT 'user_id',
    `update_name` varchar(45)  NULL COMMENT 'user_name',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='用户扩展信息表';
