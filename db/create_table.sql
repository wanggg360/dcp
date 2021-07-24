use dcpdb;
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
    `id` bigint(20) NOT NULL COMMENT '主键',
    `username` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
    `name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '姓名',
    `password` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
    `pwd_update_date` datetime DEFAULT NULL COMMENT '密码最后更新时间',
    `email` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱',
    `mobile` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号',
    `status` char(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态  1：禁用   0：正常',
    `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '是否删除',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
    `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
    `comp_id` bigint(20) DEFAULT NULL COMMENT '公司ID',
    `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
    `card_id` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户工号',
    `avatar` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '用户头像',
    `remark` varchar(1000) COLLATE utf8mb4_bin DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `u_key_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统用户';