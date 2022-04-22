DROP TABLE IF EXISTS `task_siteinfo`;
CREATE TABLE `task_siteinfo` (
  `id` int(10) NOT NULL auto_increment COMMENT '主键',
  `data_type` int(5) NOT NULL COMMENT '数据类型:1(监管措施);',
  `branch_category` varchar(32) NOT NULL COMMENT '机构类型:zjh(证监会)',
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


DROP TABLE IF EXISTS `task_noticebrief`;
CREATE TABLE `task_noticebrief` (
  `id` int(10) NOT NULL auto_increment COMMENT '主键',
  `task_id` varchar(256) NOT NULL COMMENT '采集任务ID',
  `data_type` int(5) NOT NULL COMMENT '数据类型:1(监管措施);',
  `branch_category` varchar(32) NOT NULL COMMENT '机构类型:zjh(证监会)',
  `branch_id` varchar(32) NOT NULL COMMENT '机构ID:1(总局);各分局使用标准省市代码',
  `content_url` varchar(256) NOT NULL COMMENT '完整连接',
  `title` varchar(256) NOT NULL COMMENT '标题',
  `publish_date` date NOT NULL COMMENT '发布日期',
  `create_time` timestamp NOT NULL COMMENT '数据创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='公告概要信息表';


DROP TABLE IF EXISTS `task_noticedetails`;
CREATE TABLE `task_noticedetails` (
  `id` int(10) NOT NULL auto_increment COMMENT '主键',
  `task_id` varchar(256) NOT NULL COMMENT '采集任务ID',
  `brief_id` int(10) NOT NULL COMMENT '公告概要ID',
  `title` varchar(256) COMMENT '标题',
  `notice_object` varchar(256) COMMENT '公告对象',
  `content` MEDIUMTEXT COMMENT '公告详情',
  `notice_date` date COMMENT '公告正文日期',
  `publish_date` date NOT NULL COMMENT '发布日期',
  `create_time` timestamp NOT NULL COMMENT '数据创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='公告详情信息表';