drop schema if exists `sigma_auth`;
create schema `sigma_auth`;
use `sigma_auth`;

CREATE TABLE `auth_account`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`         varchar(255)        NOT NULL DEFAULT '' COMMENT '姓名',
    `phone`        varchar(50)         NOT NULL DEFAULT '' COMMENT '手机号',
    `password`     varchar(255)        NOT NULL COMMENT '密码，采用哈希算法进行单向加密',
    `account_type` tinyint(2)          NOT NULL DEFAULT '2' COMMENT '1-超管 2-非超管',
    `email`        varchar(45)         NOT NULL COMMENT '邮箱',
    `status`       tinyint(1)          NOT NULL DEFAULT '1' COMMENT '0-停用 1-正常',

    `version`      int                 NOT NULL DEFAULT '0' COMMENT '乐观锁版本',
    `create_time`  timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`    bigint(20)          NOT NULL DEFAULT '0' COMMENT 'user_id',
    `create_name`  varchar(45)         NOT NULL DEFAULT '' COMMENT 'user_name',
    `update_time`  timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`    bigint(20)          NOT NULL DEFAULT '0' COMMENT 'user_id',
    `update_name`  varchar(45)         NOT NULL DEFAULT '' COMMENT 'user_name',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_phone` (`phone`)
) ENGINE = InnoDB
  CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='账号表';

CREATE TABLE `auth_account_role`
(
    `id`         bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_id` bigint(20)          NOT NULL DEFAULT '0' COMMENT '账号id',
    `role_id`    bigint(20)          NOT NULL DEFAULT '0' COMMENT '角色id',
    PRIMARY KEY (`id`),
    KEY `idx_account_id` (`account_id`),
    KEY `idx_role_id` (`role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='账号角色关联表';

CREATE TABLE `auth_role`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_name`   varchar(50)         NOT NULL DEFAULT '' COMMENT '角色名称',
    `resources`   varchar(1024)       NOT NULL DEFAULT '' COMMENT '角色拥有的资源id，多个用,隔开',
    `status`      tinyint(1)          NOT NULL DEFAULT '1' COMMENT '0-停用 1-正常',

    `version`     int                 NOT NULL DEFAULT '0' COMMENT '乐观锁版本',
    `create_time` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`   bigint(20)          NOT NULL DEFAULT '0' COMMENT 'user_id',
    `create_name` varchar(45)         NOT NULL DEFAULT '' COMMENT 'user_name',
    `update_time` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`   bigint(20)          NOT NULL DEFAULT '0' COMMENT 'user_id',
    `update_name` varchar(45)         NOT NULL DEFAULT '' COMMENT 'user_name',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_role_name` (`role_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='角色表';


CREATE TABLE `auth_resource`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `type`         tinyint(2)          NOT NULL DEFAULT '0' COMMENT '权限类型 1-菜单权限 2-功能权限',
    `key`          varchar(255)        NOT NULL DEFAULT '' COMMENT 'key',
    `name`         varchar(255)        NOT NULL DEFAULT '' COMMENT '菜单名称',
    `resource_url` varchar(255)        NOT NULL DEFAULT '' COMMENT '资源点对应的url',
    `icon_url`     varchar(255)        NOT NULL DEFAULT '' COMMENT 'icon图标地址',
    `parent_id`    bigint(20)          NOT NULL DEFAULT '0' COMMENT '功能权限对应菜单的id',
    `visible`      tinyint(1)          NOT NULL DEFAULT '1' COMMENT '是否可见 0-隐藏 1-可见',
    `status`       tinyint(1)          NOT NULL DEFAULT '1' COMMENT '0-停用 1-正常',
    `sort_num`     int(11)             NOT NULL DEFAULT '0' COMMENT '排序值',

    `version`      int                 NOT NULL DEFAULT '0' COMMENT '乐观锁版本',
    `create_time`  timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`    bigint(20)          NOT NULL DEFAULT '0' COMMENT 'user_id',
    `create_name`  varchar(45)         NOT NULL DEFAULT '' COMMENT 'user_name',
    `update_time`  timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`    bigint(20)          NOT NULL DEFAULT '0' COMMENT 'user_id',
    `update_name`  varchar(45)         NOT NULL DEFAULT '' COMMENT 'user_name',
    PRIMARY KEY (`id`),
    KEY `idx_auth_url` (`resource_url`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='资源表';

INSERT INTO auth_account(name, phone, password, account_type, email)
    value ('Jone', '15900000000', '7c4a8d09ca3762af61e59520943dc26494f8941b', 1, 'admin@163.com');