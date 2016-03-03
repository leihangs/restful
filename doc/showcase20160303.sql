-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: showcase
-- ------------------------------------------------------
-- Server version	5.6.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `rest_call_detail`
--

DROP TABLE IF EXISTS `rest_call_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_call_detail` (
  `callid` int(11) NOT NULL AUTO_INCREMENT COMMENT '调用记录ID',
  `appid` varchar(8) CHARACTER SET utf8 DEFAULT NULL COMMENT '调用方ID',
  `cardid` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '卡号',
  `resultcode` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '错误码。00：成功，其他表示失败',
  `starttime` datetime DEFAULT NULL COMMENT '调用时间',
  `endtime` datetime DEFAULT NULL COMMENT '调用结束时间',
  `createtime` datetime DEFAULT NULL,
  `seqid` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '消息序列号',
  PRIMARY KEY (`callid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='调用记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rest_call_detail`
--

LOCK TABLES `rest_call_detail` WRITE;
/*!40000 ALTER TABLE `rest_call_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `rest_call_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rest_product`
--

DROP TABLE IF EXISTS `rest_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_product` (
  `productid` int(11) NOT NULL AUTO_INCREMENT COMMENT '产品编码',
  `productname` varchar(192) CHARACTER SET utf8 DEFAULT NULL COMMENT '产品名称',
  `availabledate` datetime DEFAULT NULL COMMENT '可用时间',
  `enddate` datetime DEFAULT NULL COMMENT '停用时间',
  `status` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '状态：1-有效；0-无效',
  `description` varchar(128) CHARACTER SET utf8 DEFAULT NULL COMMENT '产品描述',
  PRIMARY KEY (`productid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rest_product`
--

LOCK TABLES `rest_product` WRITE;
/*!40000 ALTER TABLE `rest_product` DISABLE KEYS */;
INSERT INTO `rest_product` VALUES (1,'10元套餐',NULL,NULL,'1','无'),(2,'11',NULL,'2015-12-12 00:00:00','1',NULL),(3,'13',NULL,NULL,'1','ok'),(4,'14',NULL,NULL,'1','22');
/*!40000 ALTER TABLE `rest_product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-03 14:55:47
