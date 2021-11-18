DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                            `id` bigint(15) NOT NULL auto_increment COMMENT '主键',
                            `user_id` varchar(64) NOT NULL COMMENT '用户工号',
                            `user_name` varchar(256) NOT NULL COMMENT '姓名',
                            `password` varchar(512) NOT NULL COMMENT '密码',
                            `pwd_update_date` datetime DEFAULT NULL COMMENT '密码最后更新时间',
                            `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
                            `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
                            `status` char(1) DEFAULT '0' COMMENT '状态  1：禁用   0：正常',
                            `del_flag` char(1) DEFAULT '0' COMMENT '是否删除',
                            `create_time` datetime NOT NULL COMMENT '创建时间',
                            `create_type` char(1) DEFAULT '1' COMMENT '创建方式 1：注册 2：管理员创建',
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `update_by` varchar(64) DEFAULT NULL COMMENT '更新者ID',
                            `create_by` varchar(64) DEFAULT NULL COMMENT '创建者ID',
                            `comp_id` int DEFAULT NULL COMMENT '公司ID',
                            `dept_id` int DEFAULT NULL COMMENT '部门ID',
                            `avatar` varchar(2048) DEFAULT NULL COMMENT '用户头像',
                            `remark` varchar(512) DEFAULT NULL COMMENT '备注',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `u_key_username` (`user_id`)
) ENGINE=InnoDB COMMENT='系统用户表';