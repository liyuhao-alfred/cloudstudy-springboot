/*
SQLyog Ultimate v12.4.1 (64 bit)
MySQL - 5.7.3-m13 : Database - cloudstudy
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cloudstudy` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `cloudstudy`;

/*Table structure for table `tbadmin` */

DROP TABLE IF EXISTS `tbadmin`;

CREATE TABLE `tbadmin` (
  `no` varchar(30) NOT NULL COMMENT '工号',
  `account` varchar(60) NOT NULL COMMENT '账号',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `name` varchar(60) NOT NULL COMMENT '姓名',
  `phone` varchar(60) NOT NULL COMMENT '电话',
  `email` varchar(60) NOT NULL COMMENT '邮箱',
  `level` int(1) NOT NULL COMMENT '等级',
  `sex` int(1) NOT NULL DEFAULT '0' COMMENT '性别',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime NOT NULL COMMENT '最后修改时间',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`no`),
  UNIQUE KEY `account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbadmin` */

insert  into `tbadmin`(`no`,`account`,`password`,`name`,`phone`,`email`,`level`,`sex`,`create_time`,`last_modify_time`,`status`) values 
('201801181742407710301','201801181742407710301','201801181742407710301','201801181742407710301','201801181742407710301','201801181742407710301',1,0,'2018-01-18 17:42:43','2018-01-18 17:42:43',0),
('201801181742458258694','201801181742458258694','201801181742458258694','201801181742458258694','201801181742458258694','201801181742458258694',1,0,'2018-01-18 17:42:48','2018-01-18 17:42:48',0),
('20180118174250211697','20180118174250211697','20180118174250211697','20180118174250211697','20180118174250211697','20180118174250211697',1,0,'2018-01-18 17:42:53','2018-01-18 17:42:53',0),
('20180118174254315351','20180118174254315351','20180118174254315351','20180118174254315351','20180118174254315351','20180118174254315351',1,0,'2018-01-18 17:42:57','2018-01-18 17:42:57',0);

/*Table structure for table `tbcourse` */

DROP TABLE IF EXISTS `tbcourse`;

CREATE TABLE `tbcourse` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(60) NOT NULL COMMENT '课程名',
  `description` longtext NOT NULL COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime NOT NULL COMMENT '最后修改时间',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbcourse` */

/*Table structure for table `tbcourserelstudent` */

DROP TABLE IF EXISTS `tbcourserelstudent`;

CREATE TABLE `tbcourserelstudent` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `courserelteacher_id` int(20) NOT NULL COMMENT '课程教师id',
  `student_no` varchar(30) NOT NULL COMMENT '学生学号',
  `grade` int(20) NOT NULL DEFAULT '0' COMMENT '分数',
  `task_declare_num` int(20) NOT NULL DEFAULT '0' COMMENT '该课程宣布的作业数目',
  `task_accept_num` int(20) NOT NULL DEFAULT '0' COMMENT '该课程提交的作业数目',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime NOT NULL COMMENT '最后修改时间',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbcourserelstudent` */

/*Table structure for table `tbcourserelteacher` */

DROP TABLE IF EXISTS `tbcourserelteacher`;

CREATE TABLE `tbcourserelteacher` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `course_id` int(20) NOT NULL COMMENT '课程id',
  `teacher_no` varchar(30) NOT NULL COMMENT '教师工号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime NOT NULL COMMENT '修改时间',
  `begin_time` datetime NOT NULL COMMENT '开始上课时间',
  `end_time` datetime NOT NULL COMMENT '结束上课时间',
  `declare_num` int(20) NOT NULL DEFAULT '0' COMMENT '可选课人数',
  `accept_num` int(20) NOT NULL DEFAULT '0' COMMENT '已选课人数',
  `count` int(10) NOT NULL DEFAULT '24' COMMENT '课时',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbcourserelteacher` */

/*Table structure for table `tbfile` */

DROP TABLE IF EXISTS `tbfile`;

CREATE TABLE `tbfile` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(60) NOT NULL COMMENT '文件名',
  `path` varchar(120) NOT NULL COMMENT '文件路径',
  `type` varchar(10) NOT NULL COMMENT '文件类型',
  `size` int(11) NOT NULL COMMENT '文件大小',
  `memo` longtext NOT NULL COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbfile` */

/*Table structure for table `tbfilerelcourse` */

DROP TABLE IF EXISTS `tbfilerelcourse`;

CREATE TABLE `tbfilerelcourse` (
  `file_id` int(20) NOT NULL COMMENT '文件id',
  `course_id` int(20) NOT NULL COMMENT '课程Id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbfilerelcourse` */

/*Table structure for table `tbfilereljob` */

DROP TABLE IF EXISTS `tbfilereljob`;

CREATE TABLE `tbfilereljob` (
  `file_id` int(20) NOT NULL COMMENT '文件id',
  `job_id` int(20) NOT NULL COMMENT '作业id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbfilereljob` */

/*Table structure for table `tbfilereltask` */

DROP TABLE IF EXISTS `tbfilereltask`;

CREATE TABLE `tbfilereltask` (
  `file_id` int(20) NOT NULL COMMENT '文件id',
  `task_id` int(20) NOT NULL COMMENT '任务id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbfilereltask` */

/*Table structure for table `tbjob` */

DROP TABLE IF EXISTS `tbjob`;

CREATE TABLE `tbjob` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `title` varchar(60) NOT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `courserelstudent_id` int(20) DEFAULT NULL COMMENT '课程的id',
  `task_id` int(20) NOT NULL COMMENT '任务id',
  `grade` int(20) NOT NULL COMMENT '分数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime NOT NULL COMMENT '最后修改时间',
  `status` int(1) NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `title` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbjob` */

/*Table structure for table `tboperatelog` */

DROP TABLE IF EXISTS `tboperatelog`;

CREATE TABLE `tboperatelog` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `operator_no` varchar(30) DEFAULT NULL COMMENT '操作员编号',
  `operator_type` varchar(32) DEFAULT NULL COMMENT '操作员类型',
  `operator_name` varchar(60) DEFAULT NULL COMMENT '操作员姓名',
  `request_ip` varchar(32) DEFAULT NULL COMMENT '请求IP地址',
  `request_uri` varchar(512) DEFAULT NULL COMMENT '请求URI',
  `request_content` longtext COMMENT '请求内容',
  `operation_start_time` datetime DEFAULT NULL COMMENT '操作开始时间',
  `operation_end_time` datetime DEFAULT NULL COMMENT '操作结束时间',
  `operation_description` varchar(128) DEFAULT NULL COMMENT '操作说明',
  `operation_result_flage` int(10) DEFAULT NULL COMMENT '操作结果标志',
  `operation_result` longtext COMMENT '操作结果',
  `operation_error_code` varchar(128) DEFAULT NULL COMMENT '错误代码',
  `operation_error_message` longtext COMMENT '错误信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tboperatelog` */

/*Table structure for table `tbpermission` */

DROP TABLE IF EXISTS `tbpermission`;

CREATE TABLE `tbpermission` (
  `id` int(30) NOT NULL COMMENT '权限ID',
  `name` varchar(60) NOT NULL COMMENT '权限名',
  `code` varchar(60) NOT NULL COMMENT '权限代码',
  `description` varchar(1024) DEFAULT NULL COMMENT '权限描述',
  `parent_id` int(30) DEFAULT NULL COMMENT '权限的上级ID',
  `status` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbpermission` */

insert  into `tbpermission`(`id`,`name`,`code`,`description`,`parent_id`,`status`) values 
(-1,'用户分配权限管理','',NULL,NULL,0),
(100,'用户管理','',NULL,-1,0),
(101,'用户增加','User:add',NULL,100,0),
(102,'用户删除','User:delete',NULL,100,0),
(103,'用户更新','User:update',NULL,100,0),
(104,'用户查询','User:query',NULL,100,0),
(105,'用户冻结','User:freeze',NULL,100,0),
(106,'用户激活','User:unfreeze',NULL,100,0),
(107,'用户查看','User:view',NULL,100,0),
(200,'角色管理','',NULL,-1,0),
(201,'角色增加','Role:add',NULL,200,0),
(202,'角色删除','Role:delete',NULL,200,0),
(203,'角色更新','Role:update',NULL,200,0),
(204,'角色查询','Role:query',NULL,200,0),
(205,'角色冻结','Role:freeze',NULL,200,0),
(206,'角色激活','Role:unfreeze',NULL,200,0),
(207,'角色查看','Role:view',NULL,200,0),
(300,'课程管理','',NULL,-1,0),
(301,'课程增加','Course:add',NULL,300,0),
(302,'课程删除','Course:delete',NULL,300,0),
(303,'课程更新','Course:update',NULL,300,0),
(304,'课程查询','Course:query',NULL,300,0),
(305,'课程冻结','Course:freeze',NULL,300,0),
(306,'课程激活','Course:unfreeze',NULL,300,0),
(307,'课程选择','Course:select',NULL,300,0),
(308,'课程查看','Course:view',NULL,300,0),
(400,'作业管理','',NULL,-1,0),
(401,'作业发布','HomeWork:declare',NULL,400,0),
(402,'作业删除','HomeWork:delete',NULL,400,0),
(403,'作业更新','HomeWork:update',NULL,400,0),
(404,'作业查询','HomeWork:query',NULL,400,0),
(405,'作业冻结','HomeWork:freeze',NULL,400,0),
(406,'作业激活','HomeWork:unfreeze',NULL,400,0),
(407,'作业查看','HomeWork:view',NULL,400,0),
(408,'作业提交','HomeWork:commit',NULL,400,0),
(500,'成绩管理','',NULL,-1,0),
(501,'成绩增加','Grade:add',NULL,500,0),
(502,'成绩删除','Grade:delete',NULL,500,0),
(503,'成绩更新','Grade:update',NULL,500,0),
(504,'成绩查询','Grade:query',NULL,500,0),
(505,'成绩查看','Grade:view',NULL,500,0),
(600,'文件管理','',NULL,-1,0),
(601,'文件上传','File:upload',NULL,600,0),
(602,'文件下载','File:download',NULL,600,0),
(603,'文件删除','File:delete',NULL,600,0),
(604,'文件查询','File:query',NULL,600,0),
(605,'文件查看','File:view',NULL,600,0),
(700,'系统日志管理','',NULL,-1,0),
(701,'系统日志下载','SysLog:download',NULL,700,0),
(702,'系统日志查询','SysLog:query',NULL,700,0),
(703,'系统日志查看','SysLog:view',NULL,700,0),
(800,'操作日志管理','',NULL,-1,0),
(801,'操作日志下载','OpLog:download',NULL,800,0),
(802,'操作日志查询','OpLog:query',NULL,800,0),
(803,'操作日志查看','OpLog:view',NULL,800,0),
(900,'系统权限管理','',NULL,-1,0),
(901,'系统权限增加','Permission:add',NULL,900,0),
(902,'系统权限删除','Permission:delete',NULL,900,0),
(903,'系统权限更新','Permission:update',NULL,900,0),
(904,'系统权限查询','Permission:query',NULL,900,0),
(905,'系统权限冻结','Permission:freeze',NULL,900,0),
(906,'系统权限激活','Permission:unfreeze',NULL,900,0),
(907,'系统权限查看','Permission:view',NULL,900,0);

/*Table structure for table `tbrole` */

DROP TABLE IF EXISTS `tbrole`;

CREATE TABLE `tbrole` (
  `id` int(30) NOT NULL COMMENT '角色ID',
  `name` varchar(60) NOT NULL COMMENT '角色名',
  `code` varchar(60) NOT NULL COMMENT '角色代码',
  `description` varchar(60) DEFAULT NULL COMMENT '角色描述',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '角色状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbrole` */

insert  into `tbrole`(`id`,`name`,`code`,`description`,`status`) values 
(1,'超级管理员','superadmin','超级管理员角色',0),
(2,'管理员','admin','管理员角色',0),
(3,'教师','teacher','教师角色',0),
(4,'学生','student','学生角色',0);

/*Table structure for table `tbrolerelpermission` */

DROP TABLE IF EXISTS `tbrolerelpermission`;

CREATE TABLE `tbrolerelpermission` (
  `role_id` int(30) NOT NULL COMMENT '角色ID',
  `permission_id` int(30) NOT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbrolerelpermission` */

insert  into `tbrolerelpermission`(`role_id`,`permission_id`) values 
(1,-1),
(1,100),
(1,101),
(1,102),
(1,103),
(1,104),
(1,105),
(1,106),
(1,107),
(1,200),
(1,201),
(1,202),
(1,203),
(1,204),
(1,205),
(1,206),
(1,207),
(1,300),
(1,301),
(1,302),
(1,303),
(1,304),
(1,305),
(1,306),
(1,307),
(1,308),
(1,400),
(1,401),
(1,402),
(1,403),
(1,404),
(1,405),
(1,406),
(1,407),
(1,408),
(1,500),
(1,501),
(1,502),
(1,503),
(1,504),
(1,505),
(1,600),
(1,601),
(1,602),
(1,603),
(1,604),
(1,605),
(1,700),
(1,701),
(1,702),
(1,703),
(1,800),
(1,801),
(1,802),
(1,803),
(1,900),
(1,901),
(1,902),
(1,903),
(1,904),
(1,905),
(1,906),
(1,907),
(2,-1),
(2,100),
(2,101),
(2,102),
(2,103),
(2,104),
(2,105),
(2,106),
(2,107),
(2,200),
(2,201),
(2,202),
(2,203),
(2,204),
(2,205),
(2,206),
(2,207),
(2,300),
(2,301),
(2,302),
(2,303),
(2,304),
(2,305),
(2,306),
(2,308),
(2,400),
(2,401),
(2,402),
(2,403),
(2,404),
(2,405),
(2,406),
(2,407),
(2,500),
(2,501),
(2,502),
(2,503),
(2,504),
(2,505),
(2,600),
(2,601),
(2,602),
(2,603),
(2,604),
(2,605),
(2,700),
(2,701),
(2,702),
(2,703),
(2,800),
(2,801),
(2,802),
(2,803),
(3,-1),
(3,100),
(3,101),
(3,103),
(3,104),
(3,105),
(3,106),
(3,107),
(3,200),
(3,201),
(3,203),
(3,204),
(3,205),
(3,206),
(3,207),
(3,300),
(3,301),
(3,302),
(3,303),
(3,304),
(3,305),
(3,306),
(3,308),
(3,400),
(3,401),
(3,402),
(3,403),
(3,404),
(3,405),
(3,406),
(3,407),
(3,500),
(3,501),
(3,502),
(3,503),
(3,504),
(3,505),
(3,600),
(3,601),
(3,602),
(3,603),
(3,604),
(3,605),
(4,-1),
(4,100),
(4,107),
(4,200),
(4,207),
(4,300),
(4,304),
(4,307),
(4,308),
(4,400),
(4,402),
(4,404),
(4,407),
(4,408),
(4,500),
(4,504),
(4,505),
(4,600),
(4,601),
(4,602),
(4,603),
(4,604),
(4,605);

/*Table structure for table `tbrolereluser` */

DROP TABLE IF EXISTS `tbrolereluser`;

CREATE TABLE `tbrolereluser` (
  `user_no` varchar(60) NOT NULL COMMENT '用户ID',
  `role_id` int(30) NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbrolereluser` */

insert  into `tbrolereluser`(`user_no`,`role_id`) values 
('1',1),
('3',3),
('4',4),
('1',2),
('1',3),
('2',3),
('3',4);

/*Table structure for table `tbstudent` */

DROP TABLE IF EXISTS `tbstudent`;

CREATE TABLE `tbstudent` (
  `no` varchar(30) NOT NULL COMMENT '学号',
  `account` varchar(60) NOT NULL COMMENT '账号',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `name` varchar(60) NOT NULL COMMENT '姓名',
  `phone` varchar(60) NOT NULL COMMENT '电话',
  `email` varchar(60) NOT NULL COMMENT '邮箱',
  `sex` int(1) NOT NULL DEFAULT '0' COMMENT '性别',
  `age` int(10) NOT NULL COMMENT '年龄',
  `birthday` date NOT NULL COMMENT '生日',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime NOT NULL COMMENT '修改时间',
  `regist_time` datetime NOT NULL COMMENT '入学时间',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`no`),
  UNIQUE KEY `account` (`account`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbstudent` */

/*Table structure for table `tbsystemlog` */

DROP TABLE IF EXISTS `tbsystemlog`;

CREATE TABLE `tbsystemlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `log_level` varchar(8) DEFAULT NULL COMMENT '日志级别',
  `log_time` datetime DEFAULT NULL COMMENT '日志记录时间',
  `log_type` varchar(32) DEFAULT NULL COMMENT '日志类型',
  `remote_call_ip` varchar(32) DEFAULT NULL COMMENT '远程调用的IP地址',
  `thread_id` varchar(128) DEFAULT NULL COMMENT '线程编号',
  `thread_name` varchar(128) DEFAULT NULL COMMENT '线程名称',
  `service_class` varchar(512) DEFAULT NULL COMMENT '调用服务的全类名',
  `service_method` varchar(256) DEFAULT NULL COMMENT '调用服务的方法名',
  `service_args` longtext COMMENT '调用服务的参数',
  `service_result_flage` int(11) DEFAULT NULL COMMENT '调用服务的结果标志',
  `service_result` longtext COMMENT '调用服务的结果',
  `service_error_code` varchar(128) DEFAULT NULL COMMENT '调用服务错误代码',
  `service_error_message` longtext COMMENT '调用服务错误信息',
  `service_running_time` varchar(32) DEFAULT NULL COMMENT '调用服务的运行时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbsystemlog` */

/*Table structure for table `tbtask` */

DROP TABLE IF EXISTS `tbtask`;

CREATE TABLE `tbtask` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `title` varchar(60) NOT NULL COMMENT '题目',
  `content` longtext NOT NULL COMMENT '内容',
  `courserelteacher_id` int(20) NOT NULL COMMENT '关联的课程id',
  `dead_line` datetime NOT NULL COMMENT '最后提交时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime NOT NULL COMMENT '最后修改时间',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `title` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbtask` */

/*Table structure for table `tbteacher` */

DROP TABLE IF EXISTS `tbteacher`;

CREATE TABLE `tbteacher` (
  `no` varchar(30) NOT NULL COMMENT '工号',
  `account` varchar(60) NOT NULL COMMENT '账号',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `name` varchar(60) NOT NULL COMMENT '名字',
  `phone` varchar(30) NOT NULL COMMENT '电话',
  `email` varchar(60) NOT NULL COMMENT '邮箱',
  `sex` int(1) NOT NULL DEFAULT '0' COMMENT '性别',
  `description` longtext NOT NULL COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime NOT NULL COMMENT '最后修改时间',
  `regist_time` datetime NOT NULL COMMENT '开始教学时间',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`no`),
  UNIQUE KEY `account` (`account`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbteacher` */

/*Table structure for table `tbuser` */

DROP TABLE IF EXISTS `tbuser`;

CREATE TABLE `tbuser` (
  `no` varchar(30) NOT NULL COMMENT '工号或者学号',
  `account` varchar(60) NOT NULL COMMENT '账号',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `salt` varchar(60) NOT NULL COMMENT '加密的盐',
  `name` varchar(60) NOT NULL COMMENT '名字',
  `phone` varchar(30) NOT NULL COMMENT '电话',
  `email` varchar(60) NOT NULL COMMENT '邮箱',
  `sex` int(1) NOT NULL DEFAULT '0' COMMENT '性别',
  `age` int(10) NOT NULL DEFAULT '0' COMMENT '年龄',
  `birthday` date NOT NULL COMMENT '生日',
  `description` longtext NOT NULL COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime NOT NULL COMMENT '最后修改时间',
  `regist_time` datetime NOT NULL COMMENT '开始教学时间或者入学时间',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`no`),
  UNIQUE KEY `account` (`account`),
  UNIQUE KEY `salt` (`salt`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbuser` */

insert  into `tbuser`(`no`,`account`,`password`,`salt`,`name`,`phone`,`email`,`sex`,`age`,`birthday`,`description`,`create_time`,`last_modify_time`,`regist_time`,`status`) values 
('1','11111111','11111111','1','1','1','1',1,1,'2018-01-19','1','2018-01-19 11:54:27','2018-01-19 11:54:33','2018-01-19 11:54:36',0),
('2','2','2','2','2','2','2',0,1,'2018-01-19','2','2018-01-19 11:54:56','2018-01-19 11:54:59','2018-01-19 11:55:05',0),
('3','3','3','3','3','3','3',0,1,'2018-01-19','3','2018-01-19 11:55:21','2018-01-19 11:55:23','2018-01-19 11:55:25',0),
('4','4','4','4','4','4','4',0,1,'2018-01-19','4','2018-01-19 14:27:13','2018-01-19 14:27:15','2018-01-19 14:27:17',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
