-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: work_uac
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
-- Table structure for table `t_role_dept_link`
--

DROP TABLE IF EXISTS `t_role_dept_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_role_dept_link` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色 ID',
  `dept_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门 ID',
  `valid_from` date DEFAULT NULL COMMENT '生效时间',
  `expire_on` date DEFAULT NULL COMMENT '过期时间',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_role_dept_link_un` (`app_id`,`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色关联部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role_dept_link`
--

LOCK TABLES `t_role_dept_link` WRITE;
/*!40000 ALTER TABLE `t_role_dept_link` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_role_dept_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role_menu_link`
--

DROP TABLE IF EXISTS `t_role_menu_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_role_menu_link` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色 ID',
  `menu_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单 ID',
  `valid_from` date DEFAULT NULL COMMENT '生效时间',
  `expire_on` date DEFAULT NULL COMMENT '过期时间',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_role_menu_link_un` (`app_id`,`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色关联菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role_menu_link`
--

LOCK TABLES `t_role_menu_link` WRITE;
/*!40000 ALTER TABLE `t_role_menu_link` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_role_menu_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_app`
--

DROP TABLE IF EXISTS `t_sys_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_app` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用 ID',
  `app_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '应用码',
  `app_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '应用名',
  `intro` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '应用介绍',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `owner_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '负责人 ID',
  `owner_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '负责人 username',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  `dept_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '部门 ID',
  `client_id` varchar(32) DEFAULT NULL COMMENT '客户端 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_sys_app_un` (`tenant_id`,`app_id`,`app_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='应用表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_app`
--

LOCK TABLES `t_sys_app` WRITE;
/*!40000 ALTER TABLE `t_sys_app` DISABLE KEYS */;
INSERT INTO `t_sys_app` VALUES (1,'7121dc943f424087bb94db94ab15b87f','My','工作平台','个人工作平台',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000',NULL,'738570f7a7fb4b48a3b36bbbd011cde4'),(2,'5c91ee29781c404ba31d011edda5ceec','UAC','权限管控平台','User Acount Control',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000',NULL,'738570f7a7fb4b48a3b36bbbd011cde4'),(3,'1765788282028130305','cs','传说111','传说222',3,NULL,NULL,'k100338',NULL,'2024-03-08 01:15:08','k100338',NULL,'2024-03-09 23:25:23',1,0,'000000',NULL,'c0533b5b75be8409d70a4491cb599d99');
/*!40000 ALTER TABLE `t_sys_app` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_client`
--

DROP TABLE IF EXISTS `t_sys_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_client` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `client_id` varchar(32) DEFAULT NULL COMMENT '客户端 ID',
  `client_key` varchar(32) DEFAULT NULL COMMENT '客户端 Key',
  `client_name` varchar(32) DEFAULT NULL COMMENT '客户端名称',
  `client_secret` varchar(32) DEFAULT NULL COMMENT '客户端秘钥',
  `grant_types` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '授权类型',
  `active_timeout` int DEFAULT '-1' COMMENT 'token 最低活跃频率时间，超出则 token 失效（-1 不限制，单位秒）',
  `timeout` int DEFAULT '2592000' COMMENT 'token 有效期，超出则 token 失效，默认 30 天（单位秒）',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户端授权表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_client`
--

LOCK TABLES `t_sys_client` WRITE;
/*!40000 ALTER TABLE `t_sys_client` DISABLE KEYS */;
INSERT INTO `t_sys_client` VALUES (1,'738570f7a7fb4b48a3b36bbbd011cde4','pc','pc','77f95a3a7ccc43769c56ed3d919a6e5d','password',-1,2592000,NULL,NULL,NULL,NULL,NULL,NULL,1,0),(2,'2fb2c6ea758e445b99a08b2b11e6d289','app','app','4729f3f312424ceb9206009ef189b725','password',-1,2592000,NULL,NULL,NULL,NULL,NULL,NULL,1,0),(3,'0c9fc35cc5f044d2b906886acbd86417','mobile','mobile','0f823600845041838e387eb66961cdde','password',-1,2592000,NULL,NULL,NULL,NULL,NULL,NULL,1,0),(4,'bfc44d21668842e9bbb50733c0289faf','iPad','iPad','7c99a6109e394d9aae7345ca1d2f0efb','password',-1,2592000,NULL,NULL,NULL,NULL,NULL,NULL,1,0),(49,'c0533b5b75be8409d70a4491cb599d99','cs','传说1','1233','password,sms,email,xcx,social',10800,10800,'k100338',NULL,'2024-03-05 00:04:00','k100338',NULL,'2024-03-06 01:46:47',1,0);
/*!40000 ALTER TABLE `t_sys_client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_dept`
--

DROP TABLE IF EXISTS `t_sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_dept` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dept_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门 ID',
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '父级部门 ID',
  `ancestors` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '祖级列表',
  `dept_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '部门名',
  `icon` varchar(160) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '部门图标',
  `order_num` int DEFAULT NULL COMMENT '部门显示顺序',
  `user_count` int DEFAULT '0' COMMENT '部门用户数量',
  `leader` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '部门负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '负责电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `intro` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '部门介绍',
  `level` int DEFAULT NULL COMMENT '部门层级',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_sys_dept_un` (`dept_id`,`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_dept`
--

LOCK TABLES `t_sys_dept` WRITE;
/*!40000 ALTER TABLE `t_sys_dept` DISABLE KEYS */;
INSERT INTO `t_sys_dept` VALUES (1,'100','0',NULL,'XXX 科技','mdi:account-box',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'k100338',NULL,'2024-02-19 23:02:11',1,0,'000000'),(2,'101','100','0,100','深圳总公司',NULL,1,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'k100338',NULL,'2024-02-21 22:49:36',1,0,'000000'),(3,'102','100','0,100','北京分公司',NULL,2,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000'),(4,'103','101','0,100,101','研发部门',NULL,3,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'k100338',NULL,'2024-02-29 22:14:14',1,0,'000000'),(5,'104','101','0,100,101','市场部门',NULL,4,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'k100338',NULL,'2024-02-19 23:02:22',1,0,'000000'),(6,'105','101','0,100,101','测试部门',NULL,5,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000'),(7,'106','101','0,100,101','财务部门',NULL,6,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000'),(8,'107','102','0,100,101','运维部门',NULL,7,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000'),(9,'108','102','0,100,102','市场部门',NULL,8,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000'),(10,'109','102','0,100,102','财务部门',NULL,9,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000'),(14,'1762492939005956098','0',NULL,'12',NULL,0,0,'22',NULL,NULL,NULL,NULL,'k100338',NULL,'2024-02-27 23:00:37','k100338',NULL,'2024-02-27 23:00:37',1,1,'000000'),(15,'1762493526342680578','0',NULL,'22',NULL,0,0,'22',NULL,NULL,NULL,NULL,'k100338',NULL,'2024-02-27 23:02:57','k100338',NULL,'2024-02-27 23:02:57',1,1,'000000');
/*!40000 ALTER TABLE `t_sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_dict_data`
--

DROP TABLE IF EXISTS `t_sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_dict_data` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字典键值',
  `dict_sort` int DEFAULT '0' COMMENT '字典排序',
  `tag_el` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'tag 标签：el-tag | el-check-tag',
  `tag_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'tag 类型：primary | success | info | warning | danger',
  `tag_effect` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'tag 主题：dark | light | plain',
  `tag_attributes` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'tag 其他属性',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `parent_dict_code` varchar(100) DEFAULT NULL COMMENT '父级字典编码',
  `parent_code_value` varchar(100) DEFAULT NULL COMMENT '父级字典键值',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用 ID',
  `dict_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_dict_data`
--

LOCK TABLES `t_sys_dict_data` WRITE;
/*!40000 ALTER TABLE `t_sys_dict_data` DISABLE KEYS */;
INSERT INTO `t_sys_dict_data` VALUES (1,'男','1',1,NULL,NULL,NULL,NULL,'N',NULL,NULL,'k100338',NULL,'2024-01-30 00:20:22','k100338',NULL,'2024-02-04 00:18:14',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_user_sex'),(2,'女','2',2,NULL,NULL,NULL,NULL,'N',NULL,NULL,'k100338',NULL,'2024-01-30 00:22:00','k100338',NULL,'2024-02-04 00:18:14',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_user_sex'),(3,'保密','0',3,NULL,NULL,NULL,NULL,'N',NULL,NULL,'k100338',NULL,'2024-01-30 00:28:34','k100338',NULL,'2024-02-04 00:18:14',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_user_sex'),(4,'停用','0',2,NULL,NULL,NULL,NULL,'N',NULL,NULL,'k100338',NULL,'2024-02-19 22:21:46','k100338',NULL,'2024-02-19 22:43:56',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_normal_status'),(5,'启用','1',1,NULL,NULL,NULL,NULL,'N',NULL,NULL,'k100338',NULL,'2024-02-19 22:21:54','k100338',NULL,'2024-02-19 22:43:56',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_normal_status'),(8,'密码认证','password',0,'el-check-tag','primary','','','N',NULL,NULL,'k100338',NULL,'2024-03-04 21:57:56','k100338',NULL,'2024-03-07 02:01:32',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_grant_type'),(9,'短信认证','sms',1,'el-check-tag','primary','',NULL,'N',NULL,NULL,'k100338',NULL,'2024-03-04 21:58:07','k100338',NULL,'2024-03-07 01:26:59',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_grant_type'),(10,'邮件认证','email',2,'el-check-tag','primary','',NULL,'N',NULL,NULL,'k100338',NULL,'2024-03-04 21:58:18','k100338',NULL,'2024-03-06 23:08:47',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_grant_type'),(11,'小程序认证','xcx',3,'el-check-tag','primary','',NULL,'N',NULL,NULL,'k100338',NULL,'2024-03-04 21:58:38','k100338',NULL,'2024-03-07 01:11:27',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_grant_type'),(12,'三方登录认证','social',4,'el-check-tag','primary','',NULL,'N',NULL,NULL,'k100338',NULL,'2024-03-04 21:58:51','k100338',NULL,'2024-03-06 23:08:58',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_grant_type'),(13,'el-tag','el-tag',0,NULL,'default','light',NULL,'N',NULL,NULL,'k100338',NULL,'2024-03-06 22:02:51','k100338',NULL,'2024-03-06 22:02:51',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_dict_tag_el'),(14,'el-check-tag','el-check-tag',1,NULL,'default','light',NULL,'N',NULL,NULL,'k100338',NULL,'2024-03-06 22:02:58','k100338',NULL,'2024-03-06 22:02:58',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_dict_tag_el'),(15,'primary','primary',1,NULL,'','',NULL,'',NULL,NULL,'k100338',NULL,'2024-03-06 22:08:34','k100338',NULL,'2024-03-07 01:30:36',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_dict_tag_type'),(16,'success','success',2,NULL,'','',NULL,'N',NULL,NULL,'k100338',NULL,'2024-03-06 22:09:02','k100338',NULL,'2024-03-07 01:30:40',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_dict_tag_type'),(17,'info','info',3,NULL,'','',NULL,'N',NULL,NULL,'k100338',NULL,'2024-03-06 22:09:08','k100338',NULL,'2024-03-07 01:30:43',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_dict_tag_type'),(18,'warning','warning',4,NULL,'','',NULL,'N',NULL,NULL,'k100338',NULL,'2024-03-06 22:09:13','k100338',NULL,'2024-03-07 01:30:46',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_dict_tag_type'),(19,'danger','danger',5,NULL,'','',NULL,'N',NULL,NULL,'k100338',NULL,'2024-03-06 22:09:18','k100338',NULL,'2024-03-07 01:30:49',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_dict_tag_type'),(20,'light','light',0,NULL,'','',NULL,'Y',NULL,NULL,'k100338',NULL,'2024-03-06 22:10:09','k100338',NULL,'2024-03-07 02:01:46',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_dict_tag_effect'),(21,'dark','dark',1,NULL,NULL,NULL,NULL,'N',NULL,NULL,'k100338',NULL,'2024-03-06 22:10:15','k100338',NULL,'2024-03-06 22:10:15',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_dict_tag_effect'),(22,'plain','plain',2,NULL,NULL,NULL,NULL,'N',NULL,NULL,'k100338',NULL,'2024-03-06 22:10:21','k100338',NULL,'2024-03-06 22:10:21',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_dict_tag_effect'),(23,'default','default',0,NULL,'','',NULL,'Y',NULL,NULL,'k100338',NULL,'2024-03-07 01:30:32','k100338',NULL,'2024-03-07 02:01:42',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_dict_tag_type'),(24,'1','1',0,NULL,'','',NULL,'N',NULL,NULL,'k100338',NULL,'2024-03-07 22:23:48','k100338',NULL,'2024-03-07 22:23:48',1,1,'000000','5c91ee29781c404ba31d011edda5ceec','sys_normal_status'),(25,'1','1',12,NULL,'','',NULL,'N',NULL,NULL,'k100338',NULL,'2024-03-10 00:06:56','k100338',NULL,'2024-03-10 00:06:56',1,1,'000000','5c91ee29781c404ba31d011edda5ceec','sys_grant_type'),(26,'长期','-1',0,NULL,'','',NULL,'N',NULL,NULL,'k100338',NULL,'2024-03-12 21:40:24','k100338',NULL,'2024-03-12 21:50:55',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_expire_on'),(27,'一年','1',1,NULL,'','',NULL,'N',NULL,NULL,'k100338',NULL,'2024-03-12 21:40:34','k100338',NULL,'2024-03-12 21:50:55',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_expire_on'),(28,'两年','2',2,NULL,'','',NULL,'N',NULL,NULL,'k100338',NULL,'2024-03-12 21:40:42','k100338',NULL,'2024-03-12 21:50:55',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_expire_on'),(29,'三年','3',3,NULL,'','',NULL,'N',NULL,NULL,'k100338',NULL,'2024-03-12 21:40:52','k100338',NULL,'2024-03-12 21:50:55',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_expire_on');
/*!40000 ALTER TABLE `t_sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_dict_type`
--

DROP TABLE IF EXISTS `t_sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_dict_type` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dict_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字典 ID',
  `dict_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典类型',
  `dict_name` varchar(100) DEFAULT NULL COMMENT '字典名称',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  `app_id` varchar(32) NOT NULL COMMENT '应用 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_sys_dict_type_un` (`tenant_id`,`app_id`,`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_dict_type`
--

LOCK TABLES `t_sys_dict_type` WRITE;
/*!40000 ALTER TABLE `t_sys_dict_type` DISABLE KEYS */;
INSERT INTO `t_sys_dict_type` VALUES (2,'1751643544622538753','sys_user_sex','用户性别','k100338',NULL,'2024-01-29 00:28:59','k100338',NULL,'2024-02-04 00:18:14',1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(3,'1752002324606578689','sys_normal_status','系统开关','k100338',NULL,'2024-01-30 00:14:39','k100338',NULL,'2024-02-19 22:43:56',1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(4,'1764651373855776770','sys_grant_type','授权类型','k100338',NULL,'2024-03-04 21:57:28','k100338',NULL,'2024-03-04 21:57:28',1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(5,'1765377432494645249','sys_dict_tag_el','字典配置 tag 标签','k100338',NULL,'2024-03-06 22:02:34','k100338',NULL,'2024-03-06 22:02:34',1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(6,'1765378690764873730','sys_dict_tag_type','字典配置 tag 类型','k100338',NULL,'2024-03-06 22:07:34','k100338',NULL,'2024-03-06 22:07:34',1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(7,'1765378746410704898','sys_dict_tag_effect','字典配置 tag 主题','k100338',NULL,'2024-03-06 22:07:47','k100338',NULL,'2024-03-06 22:07:47',1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(8,'1765745273337065473','1','1','k100338',NULL,'2024-03-07 22:24:14','k100338',NULL,'2024-03-07 22:24:14',1,1,'000000','7121dc943f424087bb94db94ab15b87f'),(9,'1765745300235137025','1','1','k100338',NULL,'2024-03-07 22:24:20','k100338',NULL,'2024-03-07 22:24:20',1,1,'000000','5c91ee29781c404ba31d011edda5ceec'),(10,'1766495972383612929','12','12','k100338',NULL,'2024-03-10 00:07:14','k100338',NULL,'2024-03-10 00:07:14',1,0,'000000','1765788282028130305'),(11,'1767546093179305985','sys_expire_on','授权有效期','k100338',NULL,'2024-03-12 21:40:03','k100338',NULL,'2024-03-12 21:50:55',1,0,'000000','5c91ee29781c404ba31d011edda5ceec');
/*!40000 ALTER TABLE `t_sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_login_log`
--

DROP TABLE IF EXISTS `t_sys_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_login_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '访问 ID',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户账号',
  `client_key` varchar(32) DEFAULT NULL COMMENT '客户端',
  `device_type` varchar(32) DEFAULT NULL COMMENT '设备类型',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '登录 IP 地址',
  `login_location` varchar(255) DEFAULT NULL COMMENT '登录地点',
  `browser` varchar(50) DEFAULT NULL COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT NULL COMMENT '操作系统',
  `msg` varchar(2000) DEFAULT NULL COMMENT '提示消息',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  `tenant_id` varchar(32) NOT NULL COMMENT '租户编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_login_log`
--

LOCK TABLES `t_sys_login_log` WRITE;
/*!40000 ALTER TABLE `t_sys_login_log` DISABLE KEYS */;
INSERT INTO `t_sys_login_log` VALUES (1,'1749117187317387266','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-22 01:10:09','000000'),(2,'1749456103924916226','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-22 23:36:53','000000'),(3,'1749470768755204098','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-23 00:35:10','000000'),(4,'1749805492413472770','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-23 22:45:14','000000'),(5,'1749820060791545858','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-23 23:43:07','000000'),(6,'1749831070382297090','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-24 00:26:52','000000'),(7,'1749842114492235777','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-24 01:10:45','000000'),(8,'1749859520270827522','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-24 02:19:55','000000'),(9,'1749861094024019969','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-24 02:26:10','000000'),(10,'1750178577385861122','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-24 23:27:44','000000'),(11,'1750190491780251650','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-25 00:15:05','000000'),(12,'1750190497627111426','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-25 00:15:06','000000'),(13,'1750201727829934081','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-25 00:59:44','000000'),(14,'1750212677052207105','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-25 01:43:14','000000'),(15,'1750534455154728962','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-25 23:01:52','000000'),(16,'1750536897850269697','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-25 23:11:35','000000'),(17,'1750553260866211841','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-26 00:16:36','000000'),(18,'1750554974138748930','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-26 00:23:24','000000'),(19,'1750555235003486209','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-26 00:24:26','000000'),(20,'1750557003053539329','12',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-26 00:31:28','000000'),(21,'1750557279823077378','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-26 00:32:34','000000'),(22,'1750569489312116738','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-26 01:21:05','000000'),(23,'1751612064512970753','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-28 22:23:54','000000'),(24,'1751624796259803138','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-28 23:14:30','000000'),(25,'1751638896536797185','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-29 00:10:31','000000'),(26,'1751647512241131522','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-29 00:44:46','000000'),(27,'1751980639266369537','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-29 22:48:29','000000'),(28,'1751991750061551618','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-29 23:32:38','000000'),(29,'1752002733718990850','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-30 00:16:17','000000'),(30,'1752348377553907714','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-30 23:09:45','000000'),(31,'1752359672827138050','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-30 23:54:38','000000'),(32,'1752371140263989249','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-31 00:40:12','000000'),(33,'1752382151830327298','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-31 01:23:57','000000'),(34,'1753758856117526529','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-03 20:34:29','000000'),(35,'1753771927158431745','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-03 21:26:26','000000'),(36,'1753786317207281666','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-03 22:23:36','000000'),(37,'1753799896224538626','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-03 23:17:34','000000'),(38,'1753814956284059650','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-04 00:17:25','000000'),(39,'1753836191260876801','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-04 01:41:47','000000'),(40,'1754506928758026241','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-05 22:07:04','000000'),(41,'1754521152108457985','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-05 23:03:35','000000'),(42,'1754547349211127809','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-06 00:47:41','000000'),(43,'1754558233748815874','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-06 01:30:56','000000'),(44,'1759569821077692417','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-19 21:25:11','000000'),(45,'1759583133123305473','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-19 22:18:05','000000'),(46,'1759594107528065025','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-19 23:01:42','000000'),(47,'1759935276330106882','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-20 21:37:23','000000'),(48,'1759943587087618049','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-20 22:10:24','000000'),(49,'1759954485839761410','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-20 22:53:42','000000'),(50,'1760302054486913025','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-21 21:54:49','000000'),(51,'1760315739024424962','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-21 22:49:12','000000'),(52,'1760663430438916097','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-22 21:50:48','000000'),(53,'1760674680841510913','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-22 22:35:30','000000'),(54,'1760686767097638913','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-22 23:23:32','000000'),(55,'1762461325823574017','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-27 20:55:00','000000'),(56,'1762472717674872833','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-27 21:40:16','000000'),(57,'1762484512796016642','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-27 22:27:08','000000'),(58,'1762495565634256898','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-27 23:11:03','000000'),(65,'1763182763027234818','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','密码输入错误 5 次，帐户锁定 10 分钟',0,'2024-02-29 20:41:44','000000'),(66,'1763183957300772865','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','密码输入错误 5 次，帐户锁定 10 分钟',0,'2024-02-29 20:46:28','000000'),(69,'1763184494578597889','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','密码输入错误 2 次',0,'2024-02-29 20:48:37','000000'),(70,'1763184701626159105','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','密码输入错误 3 次，还能输入 2 次',0,'2024-02-29 20:49:26','000000'),(71,'1763184933743136769','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-29 20:50:21','000000'),(72,'1763197183711285249','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-29 21:39:02','000000'),(73,'1763208076213948418','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-29 22:22:19','000000'),(74,'1763219030439989249','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-29 23:05:51','000000'),(75,'1763231465884479490','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-29 23:55:15','000000'),(76,'1763851629638270977','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-02 16:59:34','000000'),(77,'1763870017026605057','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-02 18:12:38','000000'),(78,'1763955990683025409','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-02 23:54:16','000000'),(79,'1763970317116866561','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-03 00:51:11','000000'),(80,'1763981306239389698','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-03 01:34:51','000000'),(81,'1764309807115014145','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-03 23:20:12','000000'),(82,'1764320853666041857','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-04 00:04:06','000000'),(83,'1764331762589372417','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-04 00:47:27','000000'),(84,'1764625744561262594','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-04 20:15:37','000000'),(85,'1764636647226634241','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-04 20:58:57','000000'),(86,'1764647634906415106','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-04 21:42:36','000000'),(87,'1764660206263083009','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-04 22:32:34','000000'),(88,'1764683107540525058','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-05 00:03:34','000000'),(89,'1764698700922560514','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-05 01:05:31','000000'),(90,'1766436043874398210','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-09 20:09:06','000000'),(91,'1766465241770930178','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-09 22:05:08','000000'),(92,'1767162281417039873','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-11 20:14:55','000000'),(93,'1767186666672676865','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-11 21:51:49','000000'),(94,'1767595343888113666','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-13 00:55:45','000000'),(95,'1767909151697391618','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-13 21:42:43','000000'),(96,'1767939380394409985','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-13 23:42:50','000000'),(97,'1767943899459219457','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-14 00:00:47','000000'),(98,'1767971020478955522','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-03-14 01:48:33','000000');
/*!40000 ALTER TABLE `t_sys_login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_menu`
--

DROP TABLE IF EXISTS `t_sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单 ID',
  `menu_code` varchar(32) NOT NULL COMMENT '菜单编码',
  `menu_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '菜单名',
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '父级菜单 ID',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '菜单地址',
  `param` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '路由参数',
  `icon` varchar(64) DEFAULT NULL COMMENT '图标',
  `order_num` int DEFAULT NULL COMMENT '显示顺序',
  `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '权限标识',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '组件路径',
  `visible` tinyint DEFAULT '1' COMMENT '显示状态（0 隐藏 1 显示）',
  `is_cache` tinyint DEFAULT '0' COMMENT '是否缓存（0 不缓存 1 缓存）',
  `is_frame` tinyint DEFAULT '0' COMMENT '是否为外链（0 否 1 是）',
  `meta` varchar(1000) DEFAULT NULL COMMENT '菜单前端额外配置',
  `intro` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '菜单介绍',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_sys_menu_un` (`app_id`,`menu_id`,`menu_code`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_menu`
--

LOCK TABLES `t_sys_menu` WRITE;
/*!40000 ALTER TABLE `t_sys_menu` DISABLE KEYS */;
INSERT INTO `t_sys_menu` VALUES (1,'1','Home','首页','0','home',NULL,NULL,1,NULL,'C','/home/index',1,0,0,NULL,NULL,NULL,NULL,NULL,'k100338',NULL,'2024-02-27 22:44:11',1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(2,'2','SystemManage','系统管理','0','system',NULL,NULL,2,NULL,'C',NULL,1,0,0,NULL,NULL,NULL,NULL,NULL,'k100338',NULL,'2024-02-22 23:11:23',1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(3,'3','ApplicationManage','应用管理','0','application',NULL,NULL,3,NULL,'C',NULL,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(4,'4','TenantManage','租户管理','2','tenant',NULL,NULL,1,NULL,'C','/system/tenant/index',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(5,'5','UserManage','用户管理','2','user',NULL,NULL,2,NULL,'C','/system/user/index',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(6,'6','RoleManage','角色管理','2','role',NULL,NULL,3,NULL,'C','/system/role/index',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(7,'7','MenuManage','菜单管理','2','menu',NULL,NULL,4,NULL,'C','/system/menu/index',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(8,'8','DeptManage','部门管理','2','dept',NULL,NULL,5,NULL,'C','/system/dept/index',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(9,'9','PostManage','岗位管理','2','post',NULL,NULL,6,NULL,'C','/system/post/index',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(10,'10','DictManage','字典管理','2','dict',NULL,NULL,7,NULL,'C','/system/dict/index',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(11,'11','ClientManage','客户端管理','3','client',NULL,NULL,1,NULL,'C','/application//client/index',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(12,'12','AppManage','App 管理','3','app',NULL,NULL,2,NULL,'C','/application/app/index',1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(13,'1762478726938198018','CS','测试','3','1222',NULL,'Icon',0,NULL,'','/cs/index',1,0,0,'isAffix: true, title: \"首页\"','测试','k100338',NULL,'2024-02-27 22:04:08','k100338',NULL,'2024-02-27 22:43:57',1,1,'000000','5c91ee29781c404ba31d011edda5ceec'),(14,'1762489029180620801','In','仪表盘','0','',NULL,NULL,0,NULL,'',NULL,1,0,0,NULL,NULL,'k100338',NULL,'2024-02-27 22:45:05','k100338',NULL,'2024-02-27 22:46:24',1,1,'000000','5c91ee29781c404ba31d011edda5ceec'),(16,'1762489533554065410','11','22','0','',NULL,NULL,0,NULL,'',NULL,1,0,0,NULL,NULL,'k100338',NULL,'2024-02-27 22:47:05','k100338',NULL,'2024-02-27 22:47:05',1,1,'000000','5c91ee29781c404ba31d011edda5ceec'),(17,'1762490861877583873','11','222','0','',NULL,NULL,2,NULL,'',NULL,1,0,0,NULL,NULL,'k100338',NULL,'2024-02-27 22:52:21','k100338',NULL,'2024-02-27 22:52:21',1,1,'000000','5c91ee29781c404ba31d011edda5ceec'),(18,'1762492806008770561','C','仪表板','0','',NULL,NULL,0,NULL,'',NULL,1,0,0,NULL,NULL,'k100338',NULL,'2024-02-27 23:00:05','k100338',NULL,'2024-02-27 23:00:05',1,1,'000000','5c91ee29781c404ba31d011edda5ceec'),(19,'1762492863642701826','2','仪表板1','1762492806008770561','',NULL,NULL,0,NULL,'',NULL,1,0,0,NULL,NULL,'k100338',NULL,'2024-02-27 23:00:19','k100338',NULL,'2024-02-27 23:00:19',1,1,'000000','5c91ee29781c404ba31d011edda5ceec'),(20,'1762493720341823490','1','仪表盘','0','',NULL,NULL,0,NULL,'',NULL,1,0,0,NULL,NULL,'k100338',NULL,'2024-02-27 23:03:43','k100338',NULL,'2024-02-27 23:03:43',1,1,'000000','5c91ee29781c404ba31d011edda5ceec'),(21,'1762493747827097601','2','仪表盘22','1762493720341823490','',NULL,NULL,0,NULL,'',NULL,1,0,0,NULL,NULL,'k100338',NULL,'2024-02-27 23:03:50','k100338',NULL,'2024-02-27 23:03:50',1,1,'000000','5c91ee29781c404ba31d011edda5ceec'),(22,'1763187768652238850','11','222','0','/12121','1',NULL,0,NULL,'M','1',1,0,0,NULL,NULL,'k100338',NULL,'2024-02-29 21:01:37','k100338',NULL,'2024-02-29 22:10:34',1,1,'000000','5c91ee29781c404ba31d011edda5ceec');
/*!40000 ALTER TABLE `t_sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_opera_log`
--

DROP TABLE IF EXISTS `t_sys_opera_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_opera_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `opera_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '日志 ID',
  `business_type` tinyint DEFAULT NULL COMMENT '业务类型（0 其它 1 新增 2 修改 3 删除）',
  `method` varchar(64) DEFAULT NULL COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT NULL COMMENT '请求方式',
  `operator_type` tinyint DEFAULT NULL COMMENT '操作类别（0 其它 1 后台用户 2 手机端用户）',
  `opera_name` varchar(50) DEFAULT NULL COMMENT '操作人员',
  `dept_name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `opera_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '请求 URL',
  `opera_ip` varchar(128) DEFAULT NULL COMMENT '主机地址',
  `opera_location` varchar(255) DEFAULT NULL COMMENT '操作地点',
  `opera_param` varchar(2000) DEFAULT NULL COMMENT '请求参数',
  `json_result` varchar(2000) DEFAULT NULL COMMENT '返回参数',
  `error_msg` varchar(2000) DEFAULT NULL COMMENT '错误消息',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `cost_time` int DEFAULT NULL COMMENT '消耗时间',
  `tenant_id` varchar(32) NOT NULL COMMENT '租户编号',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用 ID',
  `menu_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单 ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_opera_log`
--

LOCK TABLES `t_sys_opera_log` WRITE;
/*!40000 ALTER TABLE `t_sys_opera_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_opera_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_post`
--

DROP TABLE IF EXISTS `t_sys_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_post` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `post_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位 ID',
  `post_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '岗位名称',
  `order_num` int DEFAULT NULL COMMENT '显示顺序',
  `user_count` int DEFAULT '0' COMMENT '岗位用户数量',
  `intro` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '岗位介绍',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_sys_post_un` (`post_id`,`post_code`,`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='岗位表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_post`
--

LOCK TABLES `t_sys_post` WRITE;
/*!40000 ALTER TABLE `t_sys_post` DISABLE KEYS */;
INSERT INTO `t_sys_post` VALUES (1,'1','ceo','董事长',1,0,NULL,NULL,NULL,NULL,'k100338',NULL,'2024-02-29 22:19:45',1,0,'000000'),(2,'1759954926753386497','hr','人力资源',3,0,'人力资源','k100338',NULL,'2024-02-20 22:55:28','k100338',NULL,'2024-02-21 22:57:01',1,0,'000000'),(3,'1759955173613309953','3','测试',2,0,'测试','k100338',NULL,'2024-02-20 22:56:26','k100338',NULL,'2024-02-20 22:56:35',1,1,'000000');
/*!40000 ALTER TABLE `t_sys_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_role`
--

DROP TABLE IF EXISTS `t_sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色 ID',
  `role_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色码',
  `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '角色名',
  `intro` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '角色介绍',
  `order_num` int DEFAULT NULL COMMENT '显示顺序',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_sys_role_un` (`tenant_id`,`app_id`,`role_id`,`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='应用角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_role`
--

LOCK TABLES `t_sys_role` WRITE;
/*!40000 ALTER TABLE `t_sys_role` DISABLE KEYS */;
INSERT INTO `t_sys_role` VALUES (1,'1','superadmin','超级管理员',NULL,1,NULL,NULL,NULL,'k100338',NULL,'2024-03-09 23:30:20',1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(2,'1766487297958965249','11','22',NULL,0,'k100338',NULL,'2024-03-09 23:32:46','k100338',NULL,'2024-03-09 23:32:46',1,1,'000000','7121dc943f424087bb94db94ab15b87f'),(3,'1766487328803876865','1','传说',NULL,0,'k100338',NULL,'2024-03-09 23:32:54','k100338',NULL,'2024-03-09 23:32:54',1,1,'000000','1765788282028130305'),(4,'1767609451874164737','cs','cs',NULL,1,'k100338',NULL,'2024-03-13 01:51:42','k100338',NULL,'2024-03-13 01:53:31',1,0,'000000','5c91ee29781c404ba31d011edda5ceec');
/*!40000 ALTER TABLE `t_sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_tenant`
--

DROP TABLE IF EXISTS `t_sys_tenant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_tenant` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  `license_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '统一社会信用代码',
  `tenant_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '企业名',
  `icon` varchar(160) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '企业图标',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '企业所在地',
  `user_count_capacity` int DEFAULT '-1' COMMENT '企业用户数量（-1 不限制）',
  `user_count` int DEFAULT NULL COMMENT '用户实际数量',
  `founder` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '企业创始人',
  `intro` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '企业介绍',
  `contact_user_name` varchar(20) DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `domain` varchar(200) DEFAULT NULL COMMENT '企业域名',
  `expire_time` datetime DEFAULT NULL COMMENT '租户过期时间（-1 无限期）',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_sys_tenant_un` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='租户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_tenant`
--

LOCK TABLES `t_sys_tenant` WRITE;
/*!40000 ALTER TABLE `t_sys_tenant` DISABLE KEYS */;
INSERT INTO `t_sys_tenant` VALUES (1,'000000',NULL,'YoungKbt有限公司',NULL,'广东省深圳市龙岗区平湖街道',-1,10000,'刘世鹏',NULL,'管理组','15888888888',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0),(6,'003698','123456789','传说','Icon','深圳龙岗',1000,NULL,'刘世鹏','传说','刘世鹏','13377492843','youngkbt','2024-03-28 08:00:00','k100338',NULL,'2024-03-04 20:59:44','k100338',NULL,'2024-03-04 20:59:44',1,1);
/*!40000 ALTER TABLE `t_sys_tenant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_user`
--

DROP TABLE IF EXISTS `t_sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户 ID',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '密码',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱',
  `sex` tinyint DEFAULT '0' COMMENT '性别（0 保密 1 男 2 女）',
  `birthday` varchar(16) DEFAULT NULL COMMENT '生日',
  `phone` varchar(16) DEFAULT NULL COMMENT '手机号码',
  `user_status` tinyint DEFAULT '0' COMMENT '状态（0 离线 1 在线）',
  `avatar` varchar(64) DEFAULT NULL COMMENT '头像',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `login_ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '最后登录 IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  `dept_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '部门 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_sys_user_un` (`tenant_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_user`
--

LOCK TABLES `t_sys_user` WRITE;
/*!40000 ALTER TABLE `t_sys_user` DISABLE KEYS */;
INSERT INTO `t_sys_user` VALUES (1,'100338','k100338','刘世鹏','$2a$10$pIcwqMRFC76uP7UKFKN6/.hwKnmBGm4j5lBaa65HNVrYirHZnxaTa','2456019588@qq.com',0,'1999.07.27','13377492843',1,'','2024-01-21 00:00:00','127.0.0.1','2024-03-14 01:48:33',NULL,NULL,NULL,'k100338','100338','2024-03-14 01:48:33',1,0,'000000','103'),(5,'1763976066245165057','CS','传说1','123456',NULL,2,NULL,NULL,0,NULL,'2024-03-03 01:14:02',NULL,NULL,'k100338',NULL,'2024-03-03 01:14:02','k100338',NULL,'2024-03-06 01:50:11',1,0,'000000',NULL);
/*!40000 ALTER TABLE `t_sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_user_group`
--

DROP TABLE IF EXISTS `t_sys_user_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_user_group` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `group_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户组 ID',
  `group_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户组名',
  `intro` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户组描述',
  `owner_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '负责人 ID',
  `owner_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '负责人 username',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_sys_user_group_un` (`group_id`,`tenant_id`,`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_user_group`
--

LOCK TABLES `t_sys_user_group` WRITE;
/*!40000 ALTER TABLE `t_sys_user_group` DISABLE KEYS */;
INSERT INTO `t_sys_user_group` VALUES (1,'1','公共用户组','公共用户组',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(2,'2','测试用户组','测试用户组',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000','5c91ee29781c404ba31d011edda5ceec');
/*!40000 ALTER TABLE `t_sys_user_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_group_link`
--

DROP TABLE IF EXISTS `t_user_group_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_group_link` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户 ID',
  `user_group_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户组 ID',
  `valid_from` date DEFAULT NULL COMMENT '生效时间',
  `expire_on` date DEFAULT NULL COMMENT '过期时间',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_user_group_link_un` (`tenant_id`,`user_id`,`user_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户关联用户组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_group_link`
--

LOCK TABLES `t_user_group_link` WRITE;
/*!40000 ALTER TABLE `t_user_group_link` DISABLE KEYS */;
INSERT INTO `t_user_group_link` VALUES (4,'100338','1','2024-03-14','2025-03-14','k100338','100338','2024-03-14 00:21:57','k100338','100338','2024-03-14 00:21:57',1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(5,'100338','2','2024-03-14','2026-03-14','k100338','100338','2024-03-14 00:24:15','k100338','100338','2024-03-14 00:24:15',1,0,'000000','5c91ee29781c404ba31d011edda5ceec');
/*!40000 ALTER TABLE `t_user_group_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_group_role_link`
--

DROP TABLE IF EXISTS `t_user_group_role_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_group_role_link` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_group_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户组 ID',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色 ID',
  `valid_from` date DEFAULT NULL COMMENT '生效时间',
  `expire_on` date DEFAULT NULL COMMENT '过期时间',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_user_group_role_link_un` (`app_id`,`user_group_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户组关联角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_group_role_link`
--

LOCK TABLES `t_user_group_role_link` WRITE;
/*!40000 ALTER TABLE `t_user_group_role_link` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_user_group_role_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_post_link`
--

DROP TABLE IF EXISTS `t_user_post_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_post_link` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户 ID',
  `post_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位 ID',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_user_post_link_un` (`tenant_id`,`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户关联岗位表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_post_link`
--

LOCK TABLES `t_user_post_link` WRITE;
/*!40000 ALTER TABLE `t_user_post_link` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_user_post_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_role_link`
--

DROP TABLE IF EXISTS `t_user_role_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_role_link` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户 ID',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色 ID',
  `valid_from` date DEFAULT NULL COMMENT '生效时间',
  `expire_on` date DEFAULT NULL COMMENT '过期时间',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_user_role_link_un` (`app_id`,`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户关联角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_role_link`
--

LOCK TABLES `t_user_role_link` WRITE;
/*!40000 ALTER TABLE `t_user_role_link` DISABLE KEYS */;
INSERT INTO `t_user_role_link` VALUES (1,'100338','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000','5c91ee29781c404ba31d011edda5ceec');
/*!40000 ALTER TABLE `t_user_role_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'work_uac'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-14  2:07:47
