-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: work_ag
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_category`
--

DROP TABLE IF EXISTS `t_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类 ID',
  `category_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '目录编码',
  `category_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '目录名称',
  `is_main` tinyint DEFAULT '0' COMMENT '是否是主目录（0 不是 1 是）',
  `order_num` int DEFAULT NULL COMMENT '显示顺序',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `project_id` varchar(32) NOT NULL COMMENT '项目 id',
  `team_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '团队 ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='目录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_category`
--

LOCK TABLES `t_category` WRITE;
/*!40000 ALTER TABLE `t_category` DISABLE KEYS */;
INSERT INTO `t_category` VALUES (7,'1806684710245838849','Default','默认目录',1,0,'刘世鹏','k100338','2024-06-28 21:42:56','刘世鹏','k100338','2024-07-20 23:56:10',1,0,'1806684710178729986','1804535966066860034'),(8,'1807040357470588930','PC','PC 端',0,1,'刘世鹏','k100338','2024-06-29 21:16:09','刘世鹏','k100338','2024-06-29 21:16:09',1,0,'1806684710178729986','1804535966066860034'),(9,'1807051260933009410','1','1',0,0,'刘世鹏','k100338','2024-06-29 21:59:29','刘世鹏','k100338','2024-06-29 21:59:29',1,1,'1806684710178729986','1804535966066860034'),(10,'1812444563228676098','Default','默认目录',1,NULL,'刘世鹏','k100338','2024-07-14 19:10:32','刘世鹏','k100338','2024-07-14 19:10:32',1,1,'1812444563228676097','1804535966066860034'),(11,'1812444953806479361','11','2',0,0,'刘世鹏','k100338','2024-07-14 19:12:05','刘世鹏','k100338','2024-07-14 19:12:09',1,1,'1806684710178729986','1804535966066860034');
/*!40000 ALTER TABLE `t_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_data_source`
--

DROP TABLE IF EXISTS `t_data_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_data_source` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `data_source_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '数据源 ID',
  `data_source_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '数据源名称',
  `data_source_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '数据源类型',
  `jdbc_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '数据源链接地址',
  `host` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '数据源服务器地址',
  `port` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '数据源端口',
  `driver_class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '数据源驱动类',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '数据源账号',
  `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '数据源密码',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '数据源描述',
  `secret_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '密码加解密密钥',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `team_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '团队 ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='数据源配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_data_source`
--

LOCK TABLES `t_data_source` WRITE;
/*!40000 ALTER TABLE `t_data_source` DISABLE KEYS */;
INSERT INTO `t_data_source` VALUES (1,'1807428476678242305','Work AG','MySQL','jdbc:mysql://localhost:3306/work_ag?useSSL-=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&allowMultiQueries=true',NULL,NULL,'com.mysql.cj.jdbc.Driver','root','YoungKbt1234','1',NULL,'刘世鹏','k100338','2024-06-30 22:58:24','刘世鹏','k100338','2024-07-14 19:04:11',1,0,'1804535966066860034');
/*!40000 ALTER TABLE `t_data_source` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_dict_data`
--

DROP TABLE IF EXISTS `t_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_dict_data` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `data_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字典数据 ID',
  `parent_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' COMMENT '父级字典数据 ID',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字典键值',
  `dict_sort` int DEFAULT '0' COMMENT '字典排序',
  `tag_el` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'tag 标签：el-tag | el-check-tag',
  `tag_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'tag 类型：primary | success | info | warning | danger',
  `tag_effect` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'tag 主题：dark | light | plain',
  `tag_attributes` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'tag 其他属性',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `dict_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_dict_data`
--

LOCK TABLES `t_dict_data` WRITE;
/*!40000 ALTER TABLE `t_dict_data` DISABLE KEYS */;
INSERT INTO `t_dict_data` VALUES (1,'1762478726938198029','0','el-tag','el-tag',0,'','default','light','','N','刘世鹏','','2024-03-06 22:02:51','刘世鹏','','2024-03-06 22:02:51',1,0,'sys_dict_tag_el'),(2,'1762478726938198030','0','el-check-tag','el-check-tag',1,'','default','light','','N','刘世鹏','','2024-03-06 22:02:58','刘世鹏','','2024-03-06 22:02:58',1,0,'sys_dict_tag_el'),(3,'1762478726938198031','0','primary','primary',1,'','','','','','刘世鹏','','2024-03-06 22:08:34','刘世鹏','','2024-03-07 01:30:36',1,0,'sys_dict_tag_type'),(4,'1762478726938198032','0','success','success',2,'','','','','N','刘世鹏','','2024-03-06 22:09:02','刘世鹏','','2024-03-07 01:30:40',1,0,'sys_dict_tag_type'),(5,'1762478726938198033','0','info','info',3,'','','','','N','刘世鹏','','2024-03-06 22:09:08','刘世鹏','','2024-03-07 01:30:43',1,0,'sys_dict_tag_type'),(6,'1762478726938198034','0','warning','warning',4,'','','','','N','刘世鹏','','2024-03-06 22:09:13','刘世鹏','','2024-03-07 01:30:46',1,0,'sys_dict_tag_type'),(7,'1762478726938198035','0','danger','danger',5,'','','','','N','刘世鹏','','2024-03-06 22:09:18','刘世鹏','','2024-03-07 01:30:49',1,0,'sys_dict_tag_type'),(8,'1762478726938198036','0','light','light',0,'','','','','Y','刘世鹏','','2024-03-06 22:10:09','刘世鹏','','2024-03-07 02:01:46',1,0,'sys_dict_tag_effect'),(9,'1762478726938198037','0','dark','dark',1,'','','','','N','刘世鹏','','2024-03-06 22:10:15','刘世鹏','','2024-03-06 22:10:15',1,0,'sys_dict_tag_effect'),(10,'1762478726938198038','0','plain','plain',2,'','','','','N','刘世鹏','','2024-03-06 22:10:21','刘世鹏','','2024-03-06 22:10:21',1,0,'sys_dict_tag_effect'),(11,'1762478726938198039','0','default','default',0,'','','','','Y','刘世鹏','','2024-03-07 01:30:32','刘世鹏','','2024-03-07 02:01:42',1,0,'sys_dict_tag_type'),(12,'1804577434156449793','0','所有者','1',1,'el-tag','warning','light',NULL,'N','刘世鹏','k100338','2024-06-23 02:09:22','刘世鹏','k100338','2024-06-23 02:25:05',1,0,'team_role'),(13,'1804577475751362561','0','管理员','2',2,'el-tag','info','light',NULL,'N','刘世鹏','k100338','2024-06-23 02:09:32','刘世鹏','k100338','2024-06-23 02:25:18',1,0,'team_role'),(14,'1804577537084669954','0','普通成员','3',3,'el-tag','primary','light',NULL,'N','刘世鹏','k100338','2024-06-23 02:09:47','刘世鹏','k100338','2024-06-23 02:25:26',1,0,'team_role'),(15,'1762478726938198022','0','异常','0',2,'','','','','N','刘世鹏','','2024-02-19 22:21:46','刘世鹏','','2024-02-19 22:43:56',1,0,'sys_normal_status'),(16,'1762478726938198023','0','正常','1',1,'','','','','N','刘世鹏','','2024-02-19 22:21:54','刘世鹏','','2024-02-19 22:43:56',1,0,'sys_normal_status'),(17,'1804899383000735746','0','管理员','1',1,NULL,NULL,NULL,NULL,'N','刘世鹏','k100338','2024-06-23 23:28:41','刘世鹏','k100338','2024-06-23 23:28:41',1,0,'project_role'),(18,'1804899420808192002','0','普通成员','2',2,NULL,NULL,NULL,NULL,'N','刘世鹏','k100338','2024-06-23 23:28:50','刘世鹏','k100338','2024-06-23 23:28:50',1,0,'project_role'),(19,'1804899457713872897','0','只读成员','3',3,NULL,NULL,NULL,NULL,'N','刘世鹏','k100338','2024-06-23 23:28:59','刘世鹏','k100338','2024-06-23 23:28:59',1,0,'project_role'),(20,'1804899491046006785','0','禁止访问','4',4,NULL,NULL,NULL,NULL,'N','刘世鹏','k100338','2024-06-23 23:29:07','刘世鹏','k100338','2024-06-23 23:29:07',1,0,'project_role'),(21,'1807425986448674817','0','MySQL','MySQL',1,NULL,NULL,NULL,NULL,'N','刘世鹏','k100338','2024-06-30 22:48:30','刘世鹏','k100338','2024-06-30 23:37:50',1,0,'data_source_type'),(22,'1807426028966334466','0','Oracle','Oracle',2,NULL,NULL,NULL,NULL,'N','刘世鹏','k100338','2024-06-30 22:48:40','刘世鹏','k100338','2024-07-01 00:00:29',1,0,'data_source_type'),(23,'1807426056531300354','0','Gauss','Gauss',3,NULL,NULL,NULL,NULL,'N','刘世鹏','k100338','2024-06-30 22:48:47','刘世鹏','k100338','2024-07-01 00:00:34',1,1,'data_source_type'),(24,'1807438313793343490','1807425986448674817','com.mysql.cj.jdbc.Driver','com.mysql.cj.jdbc.Driver',1,NULL,NULL,NULL,NULL,'N','刘世鹏','k100338','2024-06-30 23:37:29','刘世鹏','k100338','2024-06-30 23:37:29',1,0,'data_source_type'),(25,'1807438373889331201','1807425986448674817','com.mysql.jdbc.Driver','com.mysql.jdbc.Driver',2,NULL,NULL,NULL,NULL,'N','刘世鹏','k100338','2024-06-30 23:37:44','刘世鹏','k100338','2024-07-01 00:00:26',1,0,'data_source_type'),(26,'1807453648651087873','1807426028966334466','oracle.jdbc.OracleDriver','oracle.jdbc.OracleDriver',1,NULL,NULL,NULL,NULL,'N','刘世鹏','k100338','2024-07-01 00:38:25','刘世鹏','k100338','2024-07-01 00:38:25',1,0,'data_source_type'),(27,'1820018366607650817','0','保密','0',1,NULL,NULL,NULL,NULL,'N',NULL,'Test','2024-08-04 16:46:08',NULL,'Test','2024-08-04 16:46:08',1,0,'sys_user_sex'),(28,'1820018388602580994','0','男','1',1,NULL,NULL,NULL,NULL,'N',NULL,'Test','2024-08-04 16:46:13',NULL,'Test','2024-08-04 16:46:13',1,0,'sys_user_sex'),(29,'1820018403660132354','0','女','2',1,NULL,NULL,NULL,NULL,'N',NULL,'Test','2024-08-04 16:46:16',NULL,'Test','2024-08-04 16:46:16',1,0,'sys_user_sex');
/*!40000 ALTER TABLE `t_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_dict_type`
--

DROP TABLE IF EXISTS `t_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_dict_type` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dict_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字典 ID',
  `dict_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典类型',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字典名称',
  `is_cascade` tinyint DEFAULT '0' COMMENT '是否开启级联（0 不开启，1 开启）',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字典描述',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_dict_type`
--

LOCK TABLES `t_dict_type` WRITE;
/*!40000 ALTER TABLE `t_dict_type` DISABLE KEYS */;
INSERT INTO `t_dict_type` VALUES (2,'1804575264128430081','team_role','团队角色',0,NULL,'刘世鹏','k100338','2024-06-23 02:00:45','刘世鹏','k100338','2024-06-23 02:00:45',1,0),(3,'1765377432494645249','sys_dict_tag_el','字典配置 tag 标签',0,'','k100338','','2024-03-06 22:02:34','k100338','','2024-03-06 22:02:34',1,0),(4,'1765378690764873730','sys_dict_tag_type','字典配置 tag 类型',0,'','k100338','','2024-03-06 22:07:34','k100338','','2024-03-06 22:07:34',1,0),(5,'1765378746410704898','sys_dict_tag_effect','字典配置 tag 主题',0,'','k100338','','2024-03-06 22:07:47','k100338','','2024-03-06 22:07:47',1,0),(6,'1752002324606578689','sys_normal_status','系统状态',0,'','k100338','','2024-01-30 00:14:39','k100338','','2024-02-19 22:43:56',1,0),(7,'1804899221356453889','project_role','项目角色',0,NULL,'刘世鹏','k100338','2024-06-23 23:28:02','刘世鹏','k100338','2024-06-23 23:28:02',1,0),(8,'1807423903024631810','data_source_type','数据库源类型',1,NULL,'刘世鹏','k100338','2024-06-30 22:40:13','刘世鹏','k100338','2024-06-30 23:35:53',1,0),(9,'1820018286840377345','sys_user_sex','用户性别',0,NULL,NULL,'Test','2024-08-04 16:45:49',NULL,'Test','2024-08-04 16:45:49',1,0);
/*!40000 ALTER TABLE `t_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_login_log`
--

DROP TABLE IF EXISTS `t_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_login_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录 ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户账号',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '登录 IP 地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '操作系统',
  `msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '提示消息',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_login_log`
--

LOCK TABLES `t_login_log` WRITE;
/*!40000 ALTER TABLE `t_login_log` DISABLE KEYS */;
INSERT INTO `t_login_log` VALUES (1,'1804472948905795586','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 19:14:11'),(2,'1804473493695549441','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 19:16:21'),(3,'1804475603266883585','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 19:24:44'),(4,'1804475656089948161','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 19:24:57'),(5,'1804476008671539201','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 19:26:20'),(6,'1804476022122672130','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 19:26:24'),(7,'1804479502023454722','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 19:40:13'),(8,'1804479541013704706','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 19:40:23'),(9,'1804480177142820865','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 19:42:54'),(10,'1804482160914395137','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 19:50:47'),(11,'1804482211447369730','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 19:51:00'),(12,'1804482942447448066','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 19:53:54'),(13,'1804482961716080641','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 19:53:58'),(14,'1804483146185764866','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 19:54:42'),(15,'1804483162501607425','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 19:54:46'),(16,'1804483275861061634','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 19:55:13'),(17,'1804483296161492993','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 19:55:18'),(18,'1804483650399825922','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 19:56:43'),(19,'1804483681995517953','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 19:56:50'),(20,'1804483913219108865','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 19:57:45'),(21,'1804483927207112705','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 19:57:49'),(22,'1804484011940442113','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 19:58:09'),(23,'1804484036623921154','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 19:58:15'),(24,'1804484356112445441','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 19:59:31'),(25,'1804484398307143681','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 19:59:41'),(26,'1804484794069086210','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:01:15'),(27,'1804484805297238017','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:01:18'),(28,'1804484961874800641','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:01:55'),(29,'1804484986969321474','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:02:01'),(30,'1804485039347789825','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:02:14'),(31,'1804485050462695425','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:02:16'),(32,'1804485078967185410','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:02:23'),(33,'1804485088744108034','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:02:26'),(34,'1804485126182465538','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:02:34'),(35,'1804485139159642113','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:02:38'),(36,'1804485645345026050','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:04:38'),(37,'1804485688936427522','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:04:49'),(38,'1804486211001446402','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:06:53'),(39,'1804486225220136962','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:06:56'),(40,'1804486403406753793','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:07:39'),(41,'1804486432292925441','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:07:46'),(42,'1804486462970064897','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:07:53'),(43,'1804486486705631234','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:07:59'),(44,'1804486952143351809','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:09:50'),(45,'1804487290086813697','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:11:10'),(46,'1804487306130026498','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:11:14'),(47,'1804487314757709825','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:11:16'),(48,'1804489241260257281','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:18:56'),(49,'1804489577815404546','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:20:16'),(50,'1804489603996250113','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:20:22'),(51,'1804489635113791490','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:20:29'),(52,'1804490023154020353','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:22:02'),(53,'1804490041931919362','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:22:06'),(54,'1804491738745991170','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:28:51'),(55,'1804492414263816193','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:31:32'),(56,'1804492769957572610','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:32:57'),(57,'1804492791742787585','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:33:02'),(58,'1804493023608107010','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:33:57'),(59,'1804493059297439746','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:34:06'),(60,'1804493571551981569','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:36:08'),(61,'1804493600719171586','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:36:15'),(62,'1804494699580682241','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:40:37'),(63,'1804494723937005569','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:40:43'),(64,'1804495099897638914','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:42:12'),(65,'1804495126808293377','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:42:19'),(66,'1804495276981153793','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:42:55'),(67,'1804495436352122882','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:43:33'),(68,'1804495571089944577','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:44:05'),(69,'1804495596197048321','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:44:11'),(70,'1804495714564501506','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:44:39'),(71,'1804495735158534146','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:44:44'),(72,'1804496160909750273','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:46:25'),(73,'1804496178576158722','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:46:30'),(74,'1804496262701314049','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:46:50'),(75,'1804496280879427585','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:46:54'),(76,'1804496471833505793','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:47:39'),(77,'1804496607838007298','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:48:12'),(78,'1804496618629951489','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:48:14'),(79,'1804497304012779521','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:50:58'),(80,'1804497316520194049','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:51:01'),(81,'1804498097931612161','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:54:07'),(82,'1804498237249613826','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:54:40'),(83,'1804498650849931266','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:56:19'),(84,'1804498974713114625','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:57:36'),(85,'1804499229408030722','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:58:37'),(86,'1804499244977287169','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:58:41'),(87,'1804499370428919810','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 20:59:11'),(88,'1804499380344254465','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 20:59:13'),(89,'1804500326260469761','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 21:02:58'),(90,'1804500776925851649','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 21:04:46'),(91,'1804510145038053378','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 21:41:59'),(92,'1804511936861499393','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 21:49:07'),(93,'1804515454003507202','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 22:03:05'),(94,'1804515469409185794','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-06-22 22:03:09'),(95,'1804515477722296322','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 22:03:11'),(96,'1804525621269037057','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 22:43:29'),(97,'1804525657155502082','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 22:43:38'),(98,'1804526108043120641','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 22:45:25'),(99,'1804527401113575425','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-22 22:50:34'),(100,'1804574297957908481','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-23 01:56:55'),(101,'1804574394955382786','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-23 01:57:18'),(102,'1804574656390549505','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-23 01:58:20'),(103,'1804726888457060354','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-23 12:03:15'),(104,'1804753090450309122','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-23 13:47:22'),(105,'1804912568785215489','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-24 00:21:05'),(106,'1805238998240935937','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-24 21:58:12'),(107,'1806345041347944450','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-27 23:13:13'),(108,'1806684455244738561','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-28 21:41:55'),(109,'1807019764780126209','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-29 19:54:19'),(110,'1807418528804196353','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-06-30 22:18:52'),(111,'1807472727801786369','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-01 01:54:14'),(112,'1807782320297779202','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-01 22:24:27'),(113,'1808142236325810178','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-02 22:14:37'),(114,'1808171931796865026','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-03 00:12:37'),(115,'1808508461631524866','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-03 22:29:52'),(116,'1809469411276693506','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-06 14:08:21'),(117,'1809603458770481154','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-06 23:01:00'),(118,'1809848006847250433','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-07 15:12:45'),(119,'1811783695306412034','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-12 23:24:29'),(120,'1812373296425287681','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-14 14:27:21'),(121,'1813596994536742913','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-17 23:29:53'),(122,'1814689888039436289','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-20 23:52:39'),(123,'1814960976266915842','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-21 17:49:52'),(124,'1814991385499308034','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-21 19:50:42'),(125,'1815020113776427010','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-21 21:44:51'),(126,'1815052461586841602','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-21 23:53:24'),(127,'1816884873518202881','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-27 01:14:45'),(128,'1817092944081195010','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-27 15:01:32'),(129,'1817255126194528257','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-28 01:46:00'),(130,'1817258922584940545','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-28 02:01:05'),(131,'1817435775169794049','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-28 13:43:50'),(132,'1818649343873560577','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-07-31 22:06:07'),(133,'1819982648321564674','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-08-04 14:24:12'),(134,'1820004522929729537','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','退出成功',1,'2024-08-04 15:51:07'),(135,'1820007347206230017','Test','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-08-04 16:02:20'),(136,'1840053534927982594','k100338','0:0:0:0:0:0:0:1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-09-28 23:38:44');
/*!40000 ALTER TABLE `t_login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_opera_log`
--

DROP TABLE IF EXISTS `t_opera_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_opera_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `opera_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '日志 ID',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '模块标题',
  `business_type` tinyint DEFAULT NULL COMMENT '业务类型（0 其它 1 新增 2 修改 3 删除）',
  `method` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '请求方式',
  `operator_type` tinyint DEFAULT NULL COMMENT '操作类别（0 其它 1 后台用户 2 手机端用户）',
  `opera_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '操作人员',
  `opera_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '请求 URL',
  `opera_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '主机地址',
  `opera_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '操作地点',
  `opera_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '返回参数',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '错误消息',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `opera_time` datetime DEFAULT NULL COMMENT '创建时间',
  `cost_time` int DEFAULT NULL COMMENT '消耗时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_opera_log`
--

LOCK TABLES `t_opera_log` WRITE;
/*!40000 ALTER TABLE `t_opera_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_opera_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_project`
--

DROP TABLE IF EXISTS `t_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_project` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目 ID',
  `project_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目名',
  `base_url` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '接口基础路径',
  `description` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '项目描述',
  `secret_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目密钥，唯一',
  `data_source_id` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '数据源 ID',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `team_id` varchar(32) DEFAULT NULL COMMENT '团队 ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='项目表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_project`
--

LOCK TABLES `t_project` WRITE;
/*!40000 ALTER TABLE `t_project` DISABLE KEYS */;
INSERT INTO `t_project` VALUES (8,'1806684710178729986','OP Passdown','/op-passdown','OP Passdown','271684b466e84b26aefa291fa0a17f36','1807428476678242305','刘世鹏','k100338','2024-06-28 21:42:56','刘世鹏','k100338','2024-07-17 23:32:05',1,0,'1804535966066860034'),(10,'1812444563228676097','1','2',NULL,'d24e1e5ceece48b1ad11993368e6c798','','刘世鹏','k100338','2024-07-14 19:10:32','刘世鹏','k100338','2024-07-14 19:10:32',1,1,'1804535966066860034');
/*!40000 ALTER TABLE `t_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_project_member`
--

DROP TABLE IF EXISTS `t_project_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_project_member` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `project_id` varchar(32) NOT NULL COMMENT '项目 ID',
  `project_role` tinyint NOT NULL DEFAULT '3' COMMENT '项目角色（1 管理员 2 普通成员 3 只读成员 4 禁止访问）',
  `belong_type` tinyint NOT NULL DEFAULT '2' COMMENT '1 项目创建者 2 项目加入者',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `team_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '团队 ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='项目成员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_project_member`
--

LOCK TABLES `t_project_member` WRITE;
/*!40000 ALTER TABLE `t_project_member` DISABLE KEYS */;
INSERT INTO `t_project_member` VALUES (7,'100338','1806684710178729986',1,1,'刘世鹏','k100338','2024-06-28 21:42:56','刘世鹏','k100338','2024-06-28 21:42:56',1,1,'1804535966066860034'),(8,'100338','1812444563228676097',1,1,'刘世鹏','k100338','2024-07-14 19:10:32','刘世鹏','k100338','2024-07-14 19:10:32',1,1,'1804535966066860034'),(9,'100338','1806684710178729986',1,1,'刘世鹏','k100338','2024-07-18 01:34:49','刘世鹏','k100338','2024-07-18 01:34:49',1,1,'1813627209019334657'),(10,'100338','1806684710178729986',1,1,'刘世鹏','k100338','2024-07-18 01:36:23','刘世鹏','k100338','2024-07-28 18:23:52',1,0,'1804535966066860034');
/*!40000 ALTER TABLE `t_project_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_report`
--

DROP TABLE IF EXISTS `t_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_report` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `report_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '报表 ID',
  `report_title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '报表名称',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '报名描述',
  `allow_add` tinyint DEFAULT '1' COMMENT '是否允许新增（0 不允许，1 允许）',
  `allow_edit` tinyint DEFAULT '1' COMMENT '是否允许编辑（0 不允许，1 允许）',
  `allow_remove` tinyint DEFAULT '1' COMMENT '是否允许删除（0 不允许，1 允许）',
  `allow_filter` tinyint DEFAULT '1' COMMENT '是否允许查询（0 不允许，1 允许）',
  `allow_export` tinyint DEFAULT '1' COMMENT '是否允许导出（0 不允许，1 允许）',
  `allow_row` tinyint DEFAULT '0' COMMENT '报表是否出现行数（0 不允许，1 允许）',
  `dialog_width` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '50%' COMMENT '弹出框宽度',
  `page_size` int DEFAULT '20' COMMENT '一页显示多少条数据',
  `chart_type` tinyint DEFAULT '0' COMMENT '是否开启图标，0 不开启，1 饼图，2 折线图',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `category_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '目录 ID',
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '项目 ID',
  `service_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '服务 ID',
  `team_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '团队 ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='报表表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_report`
--

LOCK TABLES `t_report` WRITE;
/*!40000 ALTER TABLE `t_report` DISABLE KEYS */;
INSERT INTO `t_report` VALUES (1,'1807783664219889667','Test','Test1',1,1,1,1,1,1,'50%',50,0,'刘世鹏','k100338','2024-07-01 22:29:47','刘世鹏','k100338','2024-07-27 23:08:33',1,0,'1806684710245838849','1806684710178729986','1807783664219889666','1804535966066860034'),(2,'1812445578602557443','22','22',1,1,1,1,1,0,'50%',20,0,'刘世鹏','k100338','2024-07-14 19:14:34','刘世鹏','k100338','2024-07-14 19:14:34',1,1,'1806684710245838849','1806684710178729986','1812445578602557442','1804535966066860034');
/*!40000 ALTER TABLE `t_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_service`
--

DROP TABLE IF EXISTS `t_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_service` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `service_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '服务 ID',
  `service_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '服务名',
  `service_url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '服务地址',
  `full_url` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '服务完整地址',
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '服务描述',
  `data_source_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '数据源 ID',
  `select_sql` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '接口的查询 SQL 语句',
  `select_table` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行查询语句的表名',
  `insert_table` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行插入语句的表名',
  `update_table` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行更新语句的表名',
  `delete_table` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行删除语句的表名',
  `is_auth` tinyint DEFAULT '0' COMMENT '是否进行认证（0 不认证 1 认证）',
  `service_version` int DEFAULT '1' COMMENT '接口版本号（修改一次 +1）',
  `breaking_respond` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '降级响应',
  `response_template` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '响应模板',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `report_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '报表 ID',
  `category_id` varchar(32) NOT NULL COMMENT '目录 ID',
  `project_id` varchar(32) NOT NULL COMMENT '项目 ID',
  `team_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '团队 ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='服务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_service`
--

LOCK TABLES `t_service` WRITE;
/*!40000 ALTER TABLE `t_service` DISABLE KEYS */;
INSERT INTO `t_service` VALUES (1,'1807783664219889666','Test','/test','/op-passdown/test',NULL,'1807428476678242305','select * from t_test ',NULL,'t_test','t_test','t_test',0,1,'{\"meesage\":1}','{\"_root\":[\"id\",\"projectId\",\"projectName\",\"baseUrl\",\"description\",\"secretKey\",\"dataSourceId\",\"createBy\",\"createById\",\"createTime\",\"updateBy\",\"updateById\",\"updateTime\",\"status\",\"isDeleted\"],\"test\":[\"updateTime\",\"status\",\"isDeleted\"]}','刘世鹏','k100338','2024-07-01 22:29:47','刘世鹏','k100338','2024-09-29 01:27:55',1,0,'1807783664219889667','1806684710245838849','1806684710178729986','1804535966066860034'),(2,'1812445578602557442','2222','/2',NULL,NULL,'1807428476678242305','select 1',NULL,'2','2','2',0,1,'1',NULL,'刘世鹏','k100338','2024-07-14 19:14:34','刘世鹏','k100338','2024-07-14 19:18:11',1,1,'1812445578602557443','1806684710245838849','1806684710178729986','1804535966066860034');
/*!40000 ALTER TABLE `t_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_service_col`
--

DROP TABLE IF EXISTS `t_service_col`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_service_col` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `col_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字段 ID',
  `table_col` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '表字段名称',
  `json_col` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求返回的 JSON 字段名称',
  `report_col` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '报表字段名称',
  `col_type` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字段类型',
  `col_length` int DEFAULT NULL COMMENT '字段类型长度',
  `is_where_key` tinyint DEFAULT '0' COMMENT '增删改时，是否作为 where 条件的主键，0 不作为，1 作为',
  `default_value` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字段默认值',
  `data_encrypt` tinyint DEFAULT '0' COMMENT '数据是否加密（0 不加密 1 加密）',
  `query_filter` tinyint DEFAULT '0' COMMENT 'Select 时的条件筛选，如 where 字段 = xx、like %xx%。\r\n0 为不筛选\r\n1 为 =，精准匹配\r\n2 为 !=，不等于\r\n3 为 like %xx\r\n4 为 like xx%\r\n5 为 like %xx%\r\n6 为 lt，即小于\r\n7 为 gt，即大于\r\n8 为 le，即小于等于\r\n9 为 ge，即大于等于\r\n10 为 value range，即值范围查询\r\n11 为 in',
  `order_by` int DEFAULT '99' COMMENT '排序，负数表示 desc，正数表示 asc',
  `allow_insert` tinyint DEFAULT '1' COMMENT '是否允许插入（0 不允许 1 允许）',
  `allow_update` tinyint DEFAULT '1' COMMENT '是否允许更新（0 不允许 1 允许）',
  `allow_request` tinyint DEFAULT '1' COMMENT '是否允许返回在请求里（0 不允许 1 允许）',
  `allow_show_in_report` tinyint DEFAULT '1' COMMENT '是否允许出现在报表（0 不允许 1 允许）',
  `allow_show_in_detail` tinyint DEFAULT '1' COMMENT '是否允许出现在报表的增删改弹出框（0 不允许 1 允许）',
  `display_seq` int DEFAULT '99' COMMENT '报表字段出现的顺序',
  `report_col_width` int DEFAULT '-1' COMMENT '报表字段显示的宽度，-1 为 auto，其他为准确的数值+px',
  `detail_col_span` int DEFAULT '-1' COMMENT '报表的增删改弹出框，该字段的输入框栅格占比，默认 - 1 不设置',
  `col_align` tinyint DEFAULT '1' COMMENT '报表显示的列对齐（1 为左对齐 2 为居中 3 为右对齐）',
  `dropdown_config` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '下拉值配置',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `category_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '目录 ID',
  `service_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '服务 ID',
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '项目 ID',
  `team_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '团队 ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='列配置项表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_service_col`
--

LOCK TABLES `t_service_col` WRITE;
/*!40000 ALTER TABLE `t_service_col` DISABLE KEYS */;
INSERT INTO `t_service_col` VALUES (81,'1809636952569610242','id','id','id','Integer',19,1,NULL,0,0,99,1,1,1,1,0,99,60,-1,1,NULL,'刘世鹏','k100338','2024-07-07 01:14:06','刘世鹏','k100338','2024-07-27 23:59:24',1,'1806684710245838849','1807783664219889666','1806684710178729986','1804535966066860034'),(82,'1809636952569610243','project_id','projectId','projectId','String',32,0,NULL,0,0,99,0,0,1,1,1,99,-1,-1,1,NULL,'刘世鹏','k100338','2024-07-07 01:14:06','刘世鹏','k100338','2024-07-14 19:45:40',1,'1806684710245838849','1807783664219889666','1806684710178729986','1804535966066860034'),(83,'1809636952569610244','project_name','projectName','projectName','String',200,0,NULL,0,1,99,1,1,1,1,1,99,-1,-1,1,NULL,'刘世鹏','k100338','2024-07-07 01:14:06','刘世鹏','k100338','2024-07-07 21:43:39',1,'1806684710245838849','1807783664219889666','1806684710178729986','1804535966066860034'),(84,'1809636952569610245','base_url','baseUrl','baseUrl','String',32,0,NULL,0,0,99,1,1,0,0,1,99,-1,-1,1,NULL,'刘世鹏','k100338','2024-07-07 01:14:06','刘世鹏','k100338','2024-07-07 21:55:04',1,'1806684710245838849','1807783664219889666','1806684710178729986','1804535966066860034'),(85,'1809636952603164674','description','description','description','String',1024,0,NULL,0,0,99,1,1,1,1,1,99,-1,-1,1,NULL,'刘世鹏','k100338','2024-07-07 01:14:06','刘世鹏','k100338','2024-07-07 01:14:06',1,'1806684710245838849','1807783664219889666','1806684710178729986','1804535966066860034'),(86,'1809636952603164675','secret_key','secretKey','secretKey','String',64,0,NULL,0,0,99,1,1,1,1,1,99,-1,24,1,NULL,'刘世鹏','k100338','2024-07-07 01:14:06','刘世鹏','k100338','2024-07-07 01:14:06',1,'1806684710245838849','1807783664219889666','1806684710178729986','1804535966066860034'),(87,'1809636952603164676','data_source_id','dataSourceId','dataSourceId','String',300,0,NULL,0,0,99,1,1,1,1,1,99,-1,-1,1,'{}','刘世鹏','k100338','2024-07-07 01:14:06','刘世鹏','k100338','2024-07-28 17:36:27',1,'1806684710245838849','1807783664219889666','1806684710178729986','1804535966066860034'),(88,'1809636952603164677','create_by','createBy','createBy','String',16,0,NULL,0,0,99,1,1,1,1,1,99,-1,-1,1,NULL,'刘世鹏','k100338','2024-07-07 01:14:06','刘世鹏','k100338','2024-07-07 01:14:06',1,'1806684710245838849','1807783664219889666','1806684710178729986','1804535966066860034'),(89,'1809636952603164678','create_by_id','createById','createById','String',16,0,NULL,0,0,99,1,1,1,1,1,99,-1,-1,1,NULL,'刘世鹏','k100338','2024-07-07 01:14:06','刘世鹏','k100338','2024-07-07 01:14:06',1,'1806684710245838849','1807783664219889666','1806684710178729986','1804535966066860034'),(90,'1809636952603164679','create_time','createTime','createTime','DateTime',19,0,NULL,0,10,99,1,1,1,1,1,99,-1,-1,1,NULL,'刘世鹏','k100338','2024-07-07 01:14:06','刘世鹏','k100338','2024-07-07 23:49:34',1,'1806684710245838849','1807783664219889666','1806684710178729986','1804535966066860034'),(91,'1809636952603164680','update_by','updateBy','updateBy','String',16,0,NULL,0,0,99,1,1,1,1,1,99,-1,-1,1,NULL,'刘世鹏','k100338','2024-07-07 01:14:06','刘世鹏','k100338','2024-07-07 01:14:06',1,'1806684710245838849','1807783664219889666','1806684710178729986','1804535966066860034'),(92,'1809636952603164681','update_by_id','updateById','updateById','String',16,0,NULL,0,0,99,1,1,1,1,1,99,-1,-1,1,NULL,'刘世鹏','k100338','2024-07-07 01:14:06','刘世鹏','k100338','2024-07-07 01:14:06',1,'1806684710245838849','1807783664219889666','1806684710178729986','1804535966066860034'),(93,'1809636952666079233','update_time','updateTime','updateTime','DateTime',19,0,NULL,0,0,99,1,1,1,1,1,99,-1,-1,1,NULL,'刘世鹏','k100338','2024-07-07 01:14:06','刘世鹏','k100338','2024-07-07 01:14:06',1,'1806684710245838849','1807783664219889666','1806684710178729986','1804535966066860034'),(94,'1809636952666079234','status','status','status','Integer',3,0,NULL,0,0,99,1,1,1,1,1,99,-1,-1,1,'','刘世鹏','k100338','2024-07-07 01:14:06','刘世鹏','k100338','2024-07-07 01:14:06',1,'1806684710245838849','1807783664219889666','1806684710178729986','1804535966066860034'),(115,'1809637942320467970','is_deleted','isDeleted','isDeleted','Integer',3,0,NULL,0,0,99,1,1,1,1,1,99,-1,-1,1,NULL,'刘世鹏','k100338','2024-07-07 01:18:02','刘世鹏','k100338','2024-07-07 01:18:02',1,'1806684710245838849','1807783664219889666','1806684710178729986','1804535966066860034');
/*!40000 ALTER TABLE `t_service_col` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_team`
--

DROP TABLE IF EXISTS `t_team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_team` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `team_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '团队 ID',
  `team_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '团队名字',
  `description` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '团队介绍',
  `owner_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '负责人 ID',
  `owner_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '负责人',
  `order_num` int DEFAULT NULL COMMENT '显示顺序',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='团队表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_team`
--

LOCK TABLES `t_team` WRITE;
/*!40000 ALTER TABLE `t_team` DISABLE KEYS */;
INSERT INTO `t_team` VALUES (1,'1804535966066860034','我的团队',NULL,'100338','k100338',1,'刘世鹏','k100338','2024-06-22 23:24:36','刘世鹏','k100338','2024-07-14 19:03:38',1,0),(2,'1804865036159889410','测试解散1',NULL,'100338','k100338',NULL,'刘世鹏','k100338','2024-06-23 21:12:12','刘世鹏','k100338','2024-06-28 02:23:14',1,1),(3,'1804873100527206401','测试退出',NULL,'100338','k100338',NULL,'刘世鹏','k100338','2024-06-23 21:44:15','刘世鹏','k100338','2024-06-23 21:44:15',1,1),(4,'1813627209019334657','Test',NULL,'100338','k100338',NULL,'刘世鹏','k100338','2024-07-18 01:29:57','刘世鹏','k100338','2024-07-18 01:29:57',1,0);
/*!40000 ALTER TABLE `t_team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_team_member`
--

DROP TABLE IF EXISTS `t_team_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_team_member` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户 ID',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '团队内昵称',
  `team_role` tinyint DEFAULT '3' COMMENT '团队角色（1 所有者 2 管理员 3 普通成员）',
  `belong_type` tinyint DEFAULT '2' COMMENT '1 团队创建者 2 团队加入者',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `team_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '团队 ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='团队成员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_team_member`
--

LOCK TABLES `t_team_member` WRITE;
/*!40000 ALTER TABLE `t_team_member` DISABLE KEYS */;
INSERT INTO `t_team_member` VALUES (1,'100338','k100338','',1,1,'刘世鹏','k100338','2024-06-22 23:24:36','刘世鹏','k100338','2024-07-28 18:25:13',1,0,'1804535966066860034'),(4,'100338','k100338',NULL,1,1,'刘世鹏','k100338','2024-07-18 01:29:57','刘世鹏','k100338','2024-07-18 01:29:57',1,0,'1813627209019334657'),(5,'123','123','刘世鹏',3,2,'刘世鹏','k100338','2024-07-28 19:18:36','刘世鹏','k100338','2024-07-28 19:18:36',1,0,'1804535966066860034');
/*!40000 ALTER TABLE `t_team_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_test`
--

DROP TABLE IF EXISTS `t_test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_test` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '项目 ID',
  `project_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '项目名',
  `base_url` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '接口基础路径',
  `description` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '项目描述',
  `secret_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '项目密钥，唯一',
  `data_source_id` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '数据源 ID',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_test`
--

LOCK TABLES `t_test` WRITE;
/*!40000 ALTER TABLE `t_test` DISABLE KEYS */;
INSERT INTO `t_test` VALUES (1,'1','Test',NULL,'Test','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0),(2,'2','Test',NULL,'Test','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0),(3,'3','Test3',NULL,'Test3','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0),(4,'4','Test',NULL,'Test','1',NULL,NULL,NULL,'2024-07-21 21:26:43',NULL,NULL,NULL,1,0),(6,'5','Test6',NULL,'Test61','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0);
/*!40000 ALTER TABLE `t_test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户 ID',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `nickname` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱',
  `sex` tinyint DEFAULT '0' COMMENT '性别（0 保密 1 男 2 女）',
  `birthday` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '生日',
  `phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号码',
  `user_status` tinyint DEFAULT '0' COMMENT '状态（0 离线 1 在线）',
  `avatar` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `login_ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '最后登录 IP',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'100338','k100338','刘世鹏','$2a$10$KuLstWV.3ER9B3rL8l64huUdewsMeeOFqEHK33ijh8IooSb6ukn/2','2456019588@qq.com',0,'1999.07.27','13377492843',1,NULL,'2024-06-22 09:28:12','0:0:0:0:0:0:0:1','2024-09-28 23:38:44',NULL,NULL,NULL,'刘世鹏','k100338','2024-09-28 23:38:44',1,0),(2,'123','123','刘世鹏','$2a$10$KuLstWV.3ER9B3rL8l64huUdewsMeeOFqEHK33ijh8IooSb6ukn/2',NULL,0,NULL,NULL,0,NULL,'2024-07-28 11:18:23',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0),(3,'1820007284098732034','Test',NULL,'$2a$10$6pT.QAaLta6pHKjuwlLTV.G3RjJ0nVTWr9zzhru0xHM4qCtxFrGKi','2456019581@qq.com',0,NULL,'13377492842',1,NULL,'2024-08-04 08:02:05','0:0:0:0:0:0:0:1','2024-08-04 16:02:20',NULL,NULL,'2024-08-04 16:02:05',NULL,'Test','2024-08-04 16:51:18',1,0);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'work_ag'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-24 22:45:22
