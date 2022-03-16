DROP TABLE IF EXISTS `task_siteinfo`;
CREATE TABLE `task_siteinfo` (
                            `id` int(10) NOT NULL auto_increment COMMENT '主键',
                            `data_type` int(5) NOT NULL COMMENT '数据类型:1(监管措施);',
                            `branch_id` varchar(32) NOT NULL COMMENT '机构ID:1(总局);各分局使用标准省市代码',
                            `branch_name` varchar(256) NOT NULL COMMENT '机构名称',
                            `hostname` varchar(32) DEFAULT NULL COMMENT '域名地址，以http或者https开头',
                            `url` varchar(256) NOT NULL COMMENT '完整连接',
                            `site_menu` varchar(256) DEFAULT NULL COMMENT '网站菜单',
                            `gather_type` varchar(8) DEFAULT '1' COMMENT '采集类型:1(解析html);2(接口请求)',
                            `gather_period` varchar(256) DEFAULT NULL COMMENT '采集周期',
                            `remark` varchar(256) DEFAULT NULL COMMENT '说明',
                            `isvalid` varchar(8) DEFAULT '1' COMMENT '有效标记:1(有效);0(无效)',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='数据采集网站信息表';
