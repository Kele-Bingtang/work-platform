-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: work_notice
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
-- Table structure for table `t_app_info`
--

DROP TABLE IF EXISTS `t_app_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_app_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用 ID',
  `app_name` varchar(100) DEFAULT NULL COMMENT '应用名称',
  `owner` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '负责人 ID',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(100) DEFAULT NULL COMMENT '手机号',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `default_sender` tinyint DEFAULT '0' COMMENT '是否使用默认发送器（0 不使用 1 使用）',
  `create_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人',
  `update_by_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人 ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（0 异常 1 正常 ）',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  PRIMARY KEY (`id`),
  KEY `t_app_info_app_id_IDX` (`app_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='app 配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_app_info`
--

LOCK TABLES `t_app_info` WRITE;
/*!40000 ALTER TABLE `t_app_info` DISABLE KEYS */;
INSERT INTO `t_app_info` VALUES (1,'1822641497302216707','Test','刘世鹏',NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,1,0);
/*!40000 ALTER TABLE `t_app_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_notice_info`
--

DROP TABLE IF EXISTS `t_notice_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_notice_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `notice_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息 ID',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '应用 ID',
  `message_type` varchar(20) DEFAULT NULL COMMENT '消息类型（SM 短信 EM 邮件）',
  `from` varchar(100) DEFAULT NULL COMMENT '发件人',
  `from_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '发件人别称',
  `to` varchar(5000) DEFAULT NULL COMMENT '收件人',
  `cc` varchar(5000) DEFAULT NULL COMMENT '抄送人',
  `subject` varchar(500) DEFAULT NULL COMMENT '主题',
  `content` text COMMENT '内容',
  `priority` int DEFAULT '3' COMMENT '优先级',
  `category` varchar(50) DEFAULT NULL COMMENT '类别',
  `sms_template_id` varchar(50) DEFAULT NULL COMMENT '短息模板 ID',
  `sms_message_id` varchar(200) DEFAULT NULL COMMENT '短信 ID',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'New' COMMENT 'New 未发送，Ready 准备发送，Sending 发送中，Completed 发送成功，Failed：发送失败，Hold 挂起、暂停发送',
  `retry` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'Y' COMMENT '是否需要重试（N 否 Y 是）。默认为 N，因收件人地址错误或是号码错误导致发送失败，不会重试',
  `retry_count` int DEFAULT '0' COMMENT '重试次数',
  `file_ids` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '文件 ID，多个用英文逗号隔开',
  `biz_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '业务 ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除（0 否 1 是）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='短信、邮件发送记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_notice_info`
--

LOCK TABLES `t_notice_info` WRITE;
/*!40000 ALTER TABLE `t_notice_info` DISABLE KEYS */;
INSERT INTO `t_notice_info` VALUES (4,'322a96e9640849c28fb9918472b510f3','1822641497302216707','EM',NULL,'我','2456019588@qq.com',NULL,'Test','我的',0,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 07:52:13','2024-09-28 07:52:13',0),(5,'c4015de506e14ba889f2424e9a54056a','1822641497302216707','EM',NULL,'我','2456019588@qq.com',NULL,'Test','我的',0,'ETS',NULL,NULL,'New','Y',0,NULL,'123','2024-09-28 07:55:53','2024-09-28 07:55:53',0),(6,'f295cd4b3e0946f389abc4d19da485fe','1822641497302216707','EM',NULL,'我','2456019588@qq.com',NULL,'Test','我的',0,'ETS',NULL,NULL,'New','Y',0,NULL,'123','2024-09-28 07:57:49','2024-09-28 07:57:49',0),(7,'1d4fb69d5e3e4802a8dcbde5a4624563','1822641497302216707','EM',NULL,'我','2456019588@qq.com',NULL,'Test','我的',0,'ETS',NULL,NULL,'New','Y',0,NULL,'123','2024-09-28 07:58:58','2024-09-28 07:58:58',0),(8,'38c68bb7ac0f41a2a5d73ff19d6633c4','1822641497302216707','EM',NULL,'我','2456019588@qq.com',NULL,'Test','我的',0,'ETS',NULL,NULL,'New','Y',0,NULL,'123','2024-09-28 07:59:41','2024-09-28 07:59:41',0),(9,'f6ea156a7da9443bb5cde85f8f72fe54','1822641497302216707','EM',NULL,'我','2456019588@qq.com',NULL,'Test','我的',0,'ETS',NULL,NULL,'New','Y',0,NULL,'123','2024-09-28 07:59:48','2024-09-28 07:59:48',0),(10,'b119e3f7765546abb6ef9f956fd5d85f','1822641497302216707','EM',NULL,'我','2456019588@qq.com',NULL,'Test','我的',0,'ETS',NULL,NULL,'New','Y',0,NULL,'123','2024-09-28 07:59:56','2024-09-28 07:59:56',0),(11,'beeea387269b474bbb269bd76c523a81','1822641497302216707','EM',NULL,'我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'New','Y',0,NULL,'123','2024-09-28 08:00:27','2024-09-28 08:00:27',0),(12,'c4f99fc673b44799a6a940bdbaa8a29d','1822641497302216707','EM',NULL,'我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'New','Y',0,NULL,'123','2024-09-28 08:02:08','2024-09-28 08:02:08',0),(13,'c726dbaf5d3b4365aea17471fc729987','1822641497302216707','EM',NULL,'我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'New','Y',0,NULL,'123','2024-09-28 08:06:21','2024-09-28 08:06:21',0),(14,'32ab667d35d94a28839ce1421d309eda','1822641497302216707','EM',NULL,'我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'New','Y',0,NULL,'123','2024-09-28 08:07:36','2024-09-28 08:07:36',0),(15,'7e1584f10f974a77bdd394825c9ef195','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:10:12','2024-09-28 08:10:13',0),(16,'3f888a3ca75148e391af1169b87eefd8','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Completed','Y',0,NULL,'123','2024-09-28 08:11:14','2024-09-28 08:11:15',0),(17,'bcf0fc17f30448c98656e6b7daed33dc','1822641497302216707','EM',NULL,'我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'New','Y',0,NULL,'123','2024-09-28 08:11:31','2024-09-28 08:11:31',0),(18,'8b6fb9675b5f4ac69f54fb1c34335699','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:12:18','2024-09-28 08:12:18',0),(19,'37a79c344a514f65b430c2083e22e822','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:13:01','2024-09-28 08:13:01',0),(20,'f44e3512a3f54480ac7cb2303ca53275','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:15:15','2024-09-28 08:15:15',0),(21,'7bbb7cafc79245049fc75de645b73c27','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:15:34','2024-09-28 08:15:54',0),(22,'6272514cacb045af9970e76146778b6b','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:16:14','2024-09-28 08:16:34',0),(23,'a71c38b80b8942a6a8cbc408d56360b6','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:17:41','2024-09-28 08:17:42',0),(24,'7f70f75a1fca445ea9ca677d8c9e0423','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:21:20','2024-09-28 08:21:40',0),(25,'68ef02957b3b42c5a1221e05612e7306','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:24:34','2024-09-28 08:24:35',0),(26,'8dc267d9453a4881a0f49fdff16042ab','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:26:44','2024-09-28 08:26:45',0),(27,'eda0e57125d7490ea89030bb72e5fa65','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Completed','Y',0,NULL,'123','2024-09-28 08:27:07','2024-09-28 08:27:28',0),(28,'e368fd71ad454cbe855bb64d96cf8674','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:28:06','2024-09-28 08:28:06',0),(29,'7d9843cb18614ee98e32a8ed77f4f421','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:28:19','2024-09-28 08:28:20',0),(30,'3b340449ab6748ac816c409ac7e24691','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:28:24','2024-09-28 08:28:59',0),(31,'54b1c8d403ce4e539d831e71e6f33d3b','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Completed','Y',0,NULL,'123','2024-09-28 08:29:07','2024-09-28 08:30:07',0),(32,'acb52efdd7214e349eacbe78c85aca23','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:32:12','2024-09-28 08:32:13',0),(33,'509e2c8188ba47ccbbe1c02f3ace3f7c','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:32:44','2024-09-28 08:32:45',0),(34,'9e711415ad7d4a548dc8d9dd03d2c5c0','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:34:34','2024-09-28 08:35:03',0),(35,'e6fe7cf85657411091ff99b79d7f54bd','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:35:39','2024-09-28 08:35:40',0),(36,'ffaf416816a9432080ea4128dbce56e4','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:36:05','2024-09-28 08:36:06',0),(37,'038da6b235514f8b9edb248da90c932c','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:37:44','2024-09-28 08:37:53',0),(38,'0d9bad030f7c4f1d97e4f0f88b00f6df','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:39:44','2024-09-28 08:39:45',0),(39,'c92642a2020443788e00e8655a71901f','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:46:28','2024-09-28 08:46:29',0),(40,'901c3c1b10e54b8b8010716156489920','1822641497302216707','EM','kele_bingtang@163.com','我','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:48:41','2024-09-28 08:48:54',0),(41,'917ab99f6b4d4dd79d1146aaf2976dd4','1822641497302216707','EM','kele_bingtang@163.com','Kele','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:49:11','2024-09-28 08:49:11',0),(42,'5f394813fe1f4fb3bed51b1d1b0ad702','1822641497302216707','EM','kele_bingtang@163.com','2456019588@qq.com','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 08:49:25','2024-09-28 08:49:25',0),(43,'83bf7cbf4efe47f6a567a2bdca536201','1822641497302216707','EM','kele_bingtang@163.com','kele_bingtang@163.com','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Completed','Y',0,NULL,'123','2024-09-28 08:49:34','2024-09-28 08:49:35',0),(44,'de7c5d8634d4413cbd37220d75dda56d','1822641497302216707','EM','kele_bingtang@163.com','kele_bingtang@163.com','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Failed','Y',0,NULL,'123','2024-09-28 09:00:32','2024-09-28 09:00:33',0),(45,'cebbe6baf1334c6db2e06f51afe2d6a1','1822641497302216707','EM','kele_bingtang@163.com','kele_bingtang@163.com','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Completed','Y',0,NULL,'123','2024-09-28 09:01:34','2024-09-28 09:01:34',0),(46,'76df6e11f9454368afc2e34b00ecd033','1822641497302216707','EM',NULL,'kele_bingtang@163.com','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'New','Y',0,NULL,'123','2024-09-28 09:08:35','2024-09-28 09:08:35',0),(47,'62d78c5211af49ed96f2becee4eaba94','1822641497302216707','EM',NULL,'kele_bingtang@163.com','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'New','Y',0,NULL,'123','2024-09-28 09:16:10','2024-09-28 09:16:10',0),(48,'b20d249e844344a88247caef70f5c5fe','1822641497302216707','EM',NULL,'kele_bingtang@163.com','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'New','Y',0,NULL,'123','2024-09-28 09:17:14','2024-09-28 09:17:14',0),(49,'fd885d7e8cf141cf91c58db4bfe45ee3','1822641497302216707','EM','kele_bingtang@163.com','kele_bingtang@163.com','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Completed','Y',0,NULL,'123','2024-09-28 09:33:04','2024-09-28 09:33:05',0),(50,'2fb593973d26455aa8a43ceb25f9851e','1822641497302216707','EM','kele_bingtang@163.com','kele_bingtang@163.com','2456019588@qq.com',NULL,'Test','我的',3,'ETS',NULL,NULL,'Completed','Y',0,NULL,'123','2024-09-28 09:33:10','2024-09-28 09:33:24',0);
/*!40000 ALTER TABLE `t_notice_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_notice_mail_config`
--

DROP TABLE IF EXISTS `t_notice_mail_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_notice_mail_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `config_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '配置 ID',
  `category` varchar(100) DEFAULT NULL COMMENT '类别',
  `host` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱域名',
  `port` int DEFAULT NULL COMMENT '邮箱端口',
  `protocol` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱协议',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱账号',
  `password` varchar(100) DEFAULT NULL COMMENT '邮箱密码',
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮件昵称',
  `retry_count` int DEFAULT '1' COMMENT '发送失败后重试次数，默认失败后只重发一次',
  `show_tips` tinyint DEFAULT '1' COMMENT '是否显示 "此邮件由系统自动发送，请不要回复"；0：不显示，1：显示',
  `send_limit` int DEFAULT '5' COMMENT '邮件发送限制',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '应用 ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='邮箱配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_notice_mail_config`
--

LOCK TABLES `t_notice_mail_config` WRITE;
/*!40000 ALTER TABLE `t_notice_mail_config` DISABLE KEYS */;
INSERT INTO `t_notice_mail_config` VALUES (1,'1',NULL,'smtp.163.com',465,'smtp','kele_bingtang@163.com','GJQDWNWVGYEVTSMB',NULL,NULL,1,5,'2024-09-27 16:11:54','2024-09-28 08:32:19','1822641497302216707');
/*!40000 ALTER TABLE `t_notice_mail_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'work_notice'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-24 22:45:23
