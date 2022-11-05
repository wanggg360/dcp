DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
    `id` BIGINT(15) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `user_id` varchar(64) NOT NULL COMMENT '用户工号',
    `full_name` varchar(256) DEFAULT NULL COMMENT '姓名',
    `password` varchar(512) NOT NULL COMMENT '密码',
    `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
    `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
    `avatar` varchar(2048) DEFAULT NULL COMMENT '用户头像',
    `status` varchar(10) DEFAULT '0' COMMENT '状态 2：删除 1：禁用   0：正常',
    `create_type` varchar(10) DEFAULT '1' COMMENT '创建方式 1：注册 2：管理员创建',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `update_by` varchar(64) DEFAULT NULL COMMENT '更新者ID',
    `create_by` varchar(64) DEFAULT NULL COMMENT '创建者ID',
    `remark` varchar(512) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `u_key_user` (`user_id`)
) ENGINE=InnoDB COMMENT='系统用户表';


DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
    `id` BIGINT(15) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `dept_code` varchar(32) NOT NULL COMMENT '部门统一编号',
    `dept_name` varchar(256) NOT NULL COMMENT '部门名称',
    `dept_level` varchar(10) NOT NULL COMMENT '部门级别',
    `dept_type` varchar(10) NOT NULL COMMENT '部门类型 1:总部 2:子公司 3:分公司',
    `dept_manager_id` varchar(64) COMMENT '部门主管id',
    `dept_manager_name` varchar(256) COMMENT '部门主管名称',
    `parent_dept_code` varchar(64) COMMENT '上级部门统一编号',
    `status` varchar(10) DEFAULT '0' COMMENT '状态 2：删除 1：禁用   0：正常',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `update_by` varchar(64) DEFAULT NULL COMMENT '更新者ID',
    `create_by` varchar(64) DEFAULT NULL COMMENT '创建者ID',
    `remark` varchar(512) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `u_key_deparment` (`dept_code`)
) ENGINE=InnoDB COMMENT='系统部门表';


DROP TABLE IF EXISTS `sys_user_department`;
CREATE TABLE `sys_user_department` (
    `id` BIGINT(15) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `user_id` varchar(64) NOT NULL COMMENT '用户工号',
    `dept_code` varchar(32) NOT NULL COMMENT '部门统一编号',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='系统用户部门表';


DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE IF NOT EXISTS `sys_user_role`
(
    `id` BIGINT(15) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `user_id`     VARCHAR(32)  NOT NULL COMMENT '用户ID',
    `rid`         VARCHAR(32)  NULL COMMENT '角色ID',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
        PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT='系统用户角色表';


DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE IF NOT EXISTS `sys_role` (
    `id` BIGINT(15) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `rid` VARCHAR(32) NOT NULL COMMENT '系统角色ID',
    `name` VARCHAR(64) NULL COMMENT '角色名称',
    `label` VARCHAR(64) NULL COMMENT '角色中文名称',
    `status` varchar(10) DEFAULT '0' COMMENT '状态 2：删除 1：禁用   0：正常',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `update_by` varchar(64) DEFAULT NULL COMMENT '更新者ID',
    `create_by` varchar(64) DEFAULT NULL COMMENT '创建者ID',
    `remark` varchar(512) DEFAULT NULL COMMENT '备注',
        PRIMARY KEY (`id`),
    UNIQUE INDEX `u_key_rid` (`rid` ASC) VISIBLE)
ENGINE = InnoDB COMMENT='系统角色表';


DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE IF NOT EXISTS `sys_menu` (
    `id` BIGINT(15) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `mid` VARCHAR(32) NOT NULL COMMENT '系统菜单ID',
    `name` VARCHAR(64) NULL COMMENT '菜单名称',
    `label` VARCHAR(64) NULL COMMENT '菜单中文名称',
    `pid` VARCHAR(32) NULL COMMENT '页面id',
    `parent_mid` VARCHAR(32) NULL COMMENT '系统父菜单id',
    `weight` int null comment '页面权重',
    `icon` VARCHAR(32) NULL COMMENT '图标',
    `level` varchar(10) NULL COMMENT '菜单层级1,2,3对应1-3级菜单',
    `component_path` VARCHAR(64) NULL COMMENT '前端组件路径，路由中使用',
    `redirect_path` VARCHAR(64) NULL COMMENT '重定向路径',
    `status` varchar(10) DEFAULT '0' COMMENT '状态 2：删除 1：禁用   0：正常',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `update_by` varchar(64) DEFAULT NULL COMMENT '更新者ID',
    `create_by` varchar(64) DEFAULT NULL COMMENT '创建者ID',
    `remark` varchar(512) DEFAULT NULL COMMENT '备注',
        PRIMARY KEY (`id`),
    UNIQUE INDEX `u_key_mid` (`mid` ASC) VISIBLE)
ENGINE = InnoDB COMMENT='系统菜单表';


DROP TABLE IF EXISTS `sys_page`;
CREATE TABLE IF NOT EXISTS `sys_page` (
    `id` BIGINT(15) NOT NULL COMMENT '自增主键',
    `pid` VARCHAR(32) NOT NULL COMMENT '系统页面ID',
    `name` VARCHAR(64) NULL COMMENT '页面名称',
    `label` VARCHAR(64) NULL COMMENT '页面中文名称',
    `path` VARCHAR(32) NOT NULL COMMENT '页面访问相对路径,host后面部分',
    `status` varchar(10) DEFAULT '0' COMMENT '状态 2：删除 1：禁用   0：正常',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `update_by` varchar(64) DEFAULT NULL COMMENT '更新者ID',
    `create_by` varchar(64) DEFAULT NULL COMMENT '创建者ID',
    `remark` varchar(512) DEFAULT NULL COMMENT '备注',
        PRIMARY KEY (`id`),
    UNIQUE INDEX `u_key_path` (`path` ASC) VISIBLE,
    UNIQUE INDEX `u_key_pid` (`pid` ASC) VISIBLE)
ENGINE = InnoDB COMMENT='系统页面表';

DROP TABLE IF EXISTS `sys_page_component`;
CREATE TABLE IF NOT EXISTS `sys_page_component` (
    `id` BIGINT(15) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `cid` VARCHAR(32) NOT NULL COMMENT '系统组件ID',
    `name` VARCHAR(64) NULL COMMENT '页面组件名称',
    `label` VARCHAR(64) NULL COMMENT '页面组件中文名称',
    `pid` VARCHAR(32) NULL COMMENT '所在页面ID',
    `status` varchar(10) DEFAULT '0' COMMENT '状态 2：删除 1：禁用   0：正常',
    `del_flag` varchar(10) DEFAULT '0' COMMENT '是否删除',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `update_by` varchar(64) DEFAULT NULL COMMENT '更新者ID',
    `create_by` varchar(64) DEFAULT NULL COMMENT '创建者ID',
    `remark` varchar(512) DEFAULT NULL COMMENT '备注',
        PRIMARY KEY (`id`),
    UNIQUE INDEX `u_key_cid` (`cid` ASC) VISIBLE)
ENGINE = InnoDB COMMENT='系统页面组件表';

DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE IF NOT EXISTS `sys_role_resource` (
    `id` BIGINT(15) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `rid` VARCHAR(32) NOT NULL COMMENT '角色ID',
    `res_id` VARCHAR(32) NOT NULL COMMENT '资源ID',
    `res_type` varchar(10) NOT NULL COMMENT '资源类型 1：菜单 2：页面 3：组件',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
        PRIMARY KEY (`id`))
ENGINE = InnoDB COMMENT='系统角色资源表';
