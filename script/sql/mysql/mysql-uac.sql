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
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
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
-- Table structure for table `t_role_group_link`
--

DROP TABLE IF EXISTS `t_role_group_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_role_group_link` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色 ID',
  `role_group_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色组 ID',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_role_group_link_un` (`app_id`,`role_id`,`role_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色关联角色组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role_group_link`
--

LOCK TABLES `t_role_group_link` WRITE;
/*!40000 ALTER TABLE `t_role_group_link` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_role_group_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role_group_menu_link`
--

DROP TABLE IF EXISTS `t_role_group_menu_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_role_group_menu_link` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_group_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色组 ID',
  `menu_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单 ID',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_role_group_menu_link_un` (`app_id`,`role_group_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色组关联菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role_group_menu_link`
--

LOCK TABLES `t_role_group_menu_link` WRITE;
/*!40000 ALTER TABLE `t_role_group_menu_link` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_role_group_menu_link` ENABLE KEYS */;
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
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
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
  `grant_types` varchar(200) DEFAULT NULL COMMENT '授权类型',
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='应用表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_app`
--

LOCK TABLES `t_sys_app` WRITE;
/*!40000 ALTER TABLE `t_sys_app` DISABLE KEYS */;
INSERT INTO `t_sys_app` VALUES (1,'7121dc943f424087bb94db94ab15b87f','My','工作平台','个人工作平台',0,'password',NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000',NULL,'738570f7a7fb4b48a3b36bbbd011cde4'),(2,'5c91ee29781c404ba31d011edda5ceec','UAC','权限管控平台','User Acount Control',0,'password',NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000',NULL,'738570f7a7fb4b48a3b36bbbd011cde4');
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
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户端授权表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_client`
--

LOCK TABLES `t_sys_client` WRITE;
/*!40000 ALTER TABLE `t_sys_client` DISABLE KEYS */;
INSERT INTO `t_sys_client` VALUES (1,'738570f7a7fb4b48a3b36bbbd011cde4','pc','pc','77f95a3a7ccc43769c56ed3d919a6e5d','password',-1,2592000,NULL,NULL,NULL,NULL,NULL,NULL,1,0),(2,'2fb2c6ea758e445b99a08b2b11e6d289','app','app','4729f3f312424ceb9206009ef189b725','password',-1,2592000,NULL,NULL,NULL,NULL,NULL,NULL,1,0),(3,'0c9fc35cc5f044d2b906886acbd86417','mobile','mobile','0f823600845041838e387eb66961cdde','password',-1,2592000,NULL,NULL,NULL,NULL,NULL,NULL,1,0),(4,'bfc44d21668842e9bbb50733c0289faf','iPad','iPad','7c99a6109e394d9aae7345ca1d2f0efb','password',-1,2592000,NULL,NULL,NULL,NULL,NULL,NULL,1,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_dept`
--

LOCK TABLES `t_sys_dept` WRITE;
/*!40000 ALTER TABLE `t_sys_dept` DISABLE KEYS */;
INSERT INTO `t_sys_dept` VALUES (1,'100','0',NULL,'XXX 科技','mdi:account-box',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000'),(2,'101','100','0,100','深圳总公司',NULL,1,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000'),(3,'102','100','0,100','北京分公司',NULL,2,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000'),(4,'103','101','0,100,101','研发部门',NULL,3,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000'),(5,'104','101','0,100,101','市场部门',NULL,4,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000'),(6,'105','101','0,100,101','测试部门',NULL,5,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000'),(7,'106','101','0,100,101','财务部门',NULL,6,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000'),(8,'107','102','0,100,101','运维部门',NULL,7,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000'),(9,'108','102','0,100,102','市场部门',NULL,8,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000'),(10,'109','102','0,100,102','财务部门',NULL,9,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000');
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
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_dict_data`
--

LOCK TABLES `t_sys_dict_data` WRITE;
/*!40000 ALTER TABLE `t_sys_dict_data` DISABLE KEYS */;
INSERT INTO `t_sys_dict_data` VALUES (1,'男','1',1,NULL,NULL,'N',NULL,NULL,'k100338',NULL,'2024-01-30 00:20:22','k100338',NULL,'2024-02-04 00:18:14',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_user_sex'),(2,'女','2',2,NULL,NULL,'N',NULL,NULL,'k100338',NULL,'2024-01-30 00:22:00','k100338',NULL,'2024-02-04 00:18:14',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_user_sex'),(3,'保密','0',3,NULL,NULL,'N',NULL,NULL,'k100338',NULL,'2024-01-30 00:28:34','k100338',NULL,'2024-02-04 00:18:14',1,0,'000000','5c91ee29781c404ba31d011edda5ceec','sys_user_sex');
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_dict_type`
--

LOCK TABLES `t_sys_dict_type` WRITE;
/*!40000 ALTER TABLE `t_sys_dict_type` DISABLE KEYS */;
INSERT INTO `t_sys_dict_type` VALUES (2,'1751643544622538753','sys_user_sex','用户性别','k100338',NULL,'2024-01-29 00:28:59','k100338',NULL,'2024-02-04 00:18:14',1,0,'000000','5c91ee29781c404ba31d011edda5ceec'),(3,'1752002324606578689','sys_normal_disable','系统开关','k100338',NULL,'2024-01-30 00:14:39','k100338',NULL,'2024-01-30 00:14:39',1,0,'000000','5c91ee29781c404ba31d011edda5ceec');
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
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_login_log`
--

LOCK TABLES `t_sys_login_log` WRITE;
/*!40000 ALTER TABLE `t_sys_login_log` DISABLE KEYS */;
INSERT INTO `t_sys_login_log` VALUES (1,'1749117187317387266','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-22 01:10:09','000000'),(2,'1749456103924916226','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-22 23:36:53','000000'),(3,'1749470768755204098','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-23 00:35:10','000000'),(4,'1749805492413472770','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-23 22:45:14','000000'),(5,'1749820060791545858','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-23 23:43:07','000000'),(6,'1749831070382297090','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-24 00:26:52','000000'),(7,'1749842114492235777','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-24 01:10:45','000000'),(8,'1749859520270827522','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-24 02:19:55','000000'),(9,'1749861094024019969','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-24 02:26:10','000000'),(10,'1750178577385861122','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-24 23:27:44','000000'),(11,'1750190491780251650','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-25 00:15:05','000000'),(12,'1750190497627111426','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-25 00:15:06','000000'),(13,'1750201727829934081','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-25 00:59:44','000000'),(14,'1750212677052207105','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-25 01:43:14','000000'),(15,'1750534455154728962','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-25 23:01:52','000000'),(16,'1750536897850269697','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-25 23:11:35','000000'),(17,'1750553260866211841','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-26 00:16:36','000000'),(18,'1750554974138748930','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-26 00:23:24','000000'),(19,'1750555235003486209','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-26 00:24:26','000000'),(20,'1750557003053539329','12',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-26 00:31:28','000000'),(21,'1750557279823077378','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-26 00:32:34','000000'),(22,'1750569489312116738','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-26 01:21:05','000000'),(23,'1751612064512970753','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-28 22:23:54','000000'),(24,'1751624796259803138','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-28 23:14:30','000000'),(25,'1751638896536797185','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-29 00:10:31','000000'),(26,'1751647512241131522','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-29 00:44:46','000000'),(27,'1751980639266369537','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-29 22:48:29','000000'),(28,'1751991750061551618','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-29 23:32:38','000000'),(29,'1752002733718990850','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-30 00:16:17','000000'),(30,'1752348377553907714','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-30 23:09:45','000000'),(31,'1752359672827138050','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-30 23:54:38','000000'),(32,'1752371140263989249','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-31 00:40:12','000000'),(33,'1752382151830327298','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-01-31 01:23:57','000000'),(34,'1753758856117526529','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-03 20:34:29','000000'),(35,'1753771927158431745','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-03 21:26:26','000000'),(36,'1753786317207281666','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-03 22:23:36','000000'),(37,'1753799896224538626','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-03 23:17:34','000000'),(38,'1753814956284059650','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-04 00:17:25','000000'),(39,'1753836191260876801','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-04 01:41:47','000000'),(40,'1754506928758026241','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-05 22:07:04','000000'),(41,'1754521152108457985','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-05 23:03:35','000000'),(42,'1754547349211127809','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-06 00:47:41','000000'),(43,'1754558233748815874','k100338',NULL,NULL,'127.0.0.1','内网IP','Chrome','Windows 10 or Windows Server 2016','登录成功',1,'2024-02-06 01:30:56','000000');
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
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '父级菜单 ID',
  `menu_code` varchar(32) NOT NULL COMMENT '菜单编码',
  `menu_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '菜单名',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '菜单地址',
  `param` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '查询参数',
  `icon` varchar(64) DEFAULT NULL COMMENT '图标',
  `order_num` int DEFAULT NULL COMMENT '显示顺序',
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
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_sys_menu_un` (`app_id`,`menu_id`,`menu_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_menu`
--

LOCK TABLES `t_sys_menu` WRITE;
/*!40000 ALTER TABLE `t_sys_menu` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='岗位表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_post`
--

LOCK TABLES `t_sys_post` WRITE;
/*!40000 ALTER TABLE `t_sys_post` DISABLE KEYS */;
INSERT INTO `t_sys_post` VALUES (1,'1','ceo','董事长',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000');
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='应用角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_role`
--

LOCK TABLES `t_sys_role` WRITE;
/*!40000 ALTER TABLE `t_sys_role` DISABLE KEYS */;
INSERT INTO `t_sys_role` VALUES (1,'1','superadmin','超级管理员',NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'000000','5c91ee29781c404ba31d011edda5ceec');
/*!40000 ALTER TABLE `t_sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_role_group`
--

DROP TABLE IF EXISTS `t_sys_role_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_sys_role_group` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `group_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色组 ID',
  `group_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '角色组名',
  `intro` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '角色组介绍',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_sys_role_group_un` (`group_id`,`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_role_group`
--

LOCK TABLES `t_sys_role_group` WRITE;
/*!40000 ALTER TABLE `t_sys_role_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_role_group` ENABLE KEYS */;
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
  `parent_tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '父级租户 ID',
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='租户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_tenant`
--

LOCK TABLES `t_sys_tenant` WRITE;
/*!40000 ALTER TABLE `t_sys_tenant` DISABLE KEYS */;
INSERT INTO `t_sys_tenant` VALUES (1,'000000',NULL,'YoungKbt有限公司',NULL,'广东省深圳市龙岗区平湖街道',-1,10000,'刘世鹏',NULL,'管理组','15888888888',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_user`
--

LOCK TABLES `t_sys_user` WRITE;
/*!40000 ALTER TABLE `t_sys_user` DISABLE KEYS */;
INSERT INTO `t_sys_user` VALUES (1,'100338','k100338','刘世鹏','$2a$10$NPiJRtHyLNESynk6FQf4LeWZkSnNw.XoVark5.JXF98v0AT/fTu4q','2456019588@qq.com',0,'1999.07.27','12345678987',1,'','2024-01-21 00:00:00','127.0.0.1','2024-02-06 01:30:56',NULL,NULL,NULL,'k100338',NULL,'2024-02-06 01:30:56',1,0,'000000',NULL),(4,'1750556940440969218','12','','$2a$10$pIcwqMRFC76uP7UKFKN6/.hwKnmBGm4j5lBaa65HNVrYirHZnxaTa',NULL,0,NULL,'22',0,NULL,NULL,'127.0.0.1','2024-01-26 00:31:28','k100338',NULL,'2024-01-26 00:31:13','12',NULL,'2024-01-26 00:31:28',1,1,'000000',NULL);
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
  UNIQUE KEY `t_sys_user_group_un` (`tenant_id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_user_group`
--

LOCK TABLES `t_sys_user_group` WRITE;
/*!40000 ALTER TABLE `t_sys_user_group` DISABLE KEYS */;
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
  UNIQUE KEY `t_user_group_link_un` (`tenant_id`,`user_id`,`user_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户关联用户组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_group_link`
--

LOCK TABLES `t_user_group_link` WRITE;
/*!40000 ALTER TABLE `t_user_group_link` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_user_group_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_group_role_group_link`
--

DROP TABLE IF EXISTS `t_user_group_role_group_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_group_role_group_link` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_group_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户组 ID',
  `role_group_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色组 ID',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_user_group_role_group_link_un` (`app_id`,`user_group_id`,`role_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户组关联角色组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_group_role_group_link`
--

LOCK TABLES `t_user_group_role_group_link` WRITE;
/*!40000 ALTER TABLE `t_user_group_role_group_link` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_user_group_role_group_link` ENABLE KEYS */;
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
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
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
-- Table structure for table `t_user_role_group_link`
--

DROP TABLE IF EXISTS `t_user_role_group_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_role_group_link` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户 ID',
  `role_group_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色组 ID',
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_user_role_group_link_un` (`app_id`,`user_id`,`role_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户关联角色组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_role_group_link`
--

LOCK TABLES `t_user_role_group_link` WRITE;
/*!40000 ALTER TABLE `t_user_role_group_link` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_user_role_group_link` ENABLE KEYS */;
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
  `create_by` varchar(16) DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_user_role_link_un` (`app_id`,`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户关联角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_role_link`
--

LOCK TABLES `t_user_role_link` WRITE;
/*!40000 ALTER TABLE `t_user_role_link` DISABLE KEYS */;
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

-- Dump completed on 2024-02-08  0:45:47
