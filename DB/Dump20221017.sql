CREATE DATABASE  IF NOT EXISTS `bicycle` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `bicycle`;
-- MySQL dump 10.13  Distrib 8.0.0-dmr, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bicycle
-- ------------------------------------------------------
-- Server version	8.0.0-dmr-log

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
-- Table structure for table `appuser`
--

DROP TABLE IF EXISTS `appuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appuser` (
  `appuser` int(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `id_identity_type` varchar(20) NOT NULL,
  `identity` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `age` int(3) NOT NULL,
  `birthday` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `phone` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `creation_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`appuser`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appuser`
--

LOCK TABLES `appuser` WRITE;
/*!40000 ALTER TABLE `appuser` DISABLE KEYS */;
INSERT INTO `appuser` VALUES (1,'Yago','fsdf','DNI','39147837','Pinto 1301','Yago','Lacoste',27,'2022-09-25 13:17:33','2281461938','yago@gmail.com','2022-09-25 13:17:33','2022-09-25 13:17:33',0,1);
/*!40000 ALTER TABLE `appuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appuser_has_route`
--

DROP TABLE IF EXISTS `appuser_has_route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appuser_has_route` (
  `appuser_appuser` int(20) NOT NULL,
  `route_id_route` int(11) NOT NULL,
  `speed` double DEFAULT NULL,
  `timespeed` double DEFAULT NULL,
  `kilometres` double DEFAULT NULL,
  `timesession` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id` int(20) NOT NULL,
  PRIMARY KEY (`appuser_appuser`,`route_id_route`),
  KEY `fk_appuser_has_recorrido_recorrido1` (`route_id_route`),
  CONSTRAINT `fk_appuser_has_recorrido_appuser1` FOREIGN KEY (`appuser_appuser`) REFERENCES `appuser` (`appuser`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_appuser_has_recorrido_recorrido1` FOREIGN KEY (`route_id_route`) REFERENCES `route` (`id_route`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appuser_has_route`
--

LOCK TABLES `appuser_has_route` WRITE;
/*!40000 ALTER TABLE `appuser_has_route` DISABLE KEYS */;
INSERT INTO `appuser_has_route` VALUES (1,2,0.15180782352884611,9984664593544,292.5270981788635,'2022-09-26 23:23:40',0),(1,3,0.1427628993988037,1664234574803,0,'2022-09-26 23:24:22',0),(1,4,0.8637527077678695,29960754058859,129.06060075759888,'2022-09-29 21:20:31',0),(1,8,0.14956951886415482,6656442151544,291.54939818382263,'2022-09-25 13:17:41',0),(1,9,0.15180782352884611,9984664593544,292.5270981788635,'2022-09-25 13:18:07',0),(1,14,NULL,NULL,NULL,'2022-09-29 21:47:36',0);
/*!40000 ALTER TABLE `appuser_has_route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friends` (
  `appuser_id` int(20) NOT NULL,
  `appuser_id_friends` int(20) NOT NULL,
  PRIMARY KEY (`appuser_id`,`appuser_id_friends`),
  KEY `fk_appuser_has_appuser_appuser2` (`appuser_id_friends`),
  CONSTRAINT `fk_appuser_has_appuser_appuser1` FOREIGN KEY (`appuser_id`) REFERENCES `appuser` (`appuser`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_appuser_has_appuser_appuser2` FOREIGN KEY (`appuser_id_friends`) REFERENCES `appuser` (`appuser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `route` (
  `id_route` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `weather` varchar(255) DEFAULT NULL,
  `coordinates` json NOT NULL,
  PRIMARY KEY (`id_route`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route`
--

LOCK TABLES `route` WRITE;
/*!40000 ALTER TABLE `route` DISABLE KEYS */;
INSERT INTO `route` VALUES (1,NULL,'','{\"point\": \"[-37.3236136,-59.125374,0.0, -37.3236061,-59.1254157,0.0, -37.3235803,-59.125428,0.0]\"}'),(2,NULL,'','{\"point\": \"[-37.3243493,-59.1215768,0.0, -37.3236033,-59.1254075,0.0, -37.323603,-59.1254048,0.0]\"}'),(3,NULL,NULL,'{\"Point0\": \"-37.3236065\"}'),(4,NULL,NULL,'{\"id\": 116298, \"name\": \"Data Analysis\", \"language\": \"en\"}'),(5,NULL,'','{\"Point0\": \"-37.3213438,-59.1248061,0.0\", \"Point1\": \"-37.3236044,-59.1253945,0.0\", \"Point2\": \"-37.3236055,-59.1253972,0.0\"}'),(6,NULL,'','{\"Point0\": \"-37.3213438,-59.1248061,0.0\", \"Point1\": \"-37.3236044,-59.1253945,0.0\", \"Point2\": \"-37.3236055,-59.1253972,0.0\"}'),(7,NULL,'','{\"Point0\": \"-37.3213438,-59.1248061,0.0\", \"Point1\": \"-37.3236044,-59.1253945,0.0\", \"Point2\": \"-37.3236055,-59.1253972,0.0\", \"Point3\": \"-37.3235892,-59.125436,0.0\", \"Point4\": \"-37.3235938,-59.1253877,0.0\", \"Point5\": \"-37.3235937,-59.1253893,0.0\", \"Point6\": \"-37.3236216,-59.1254292,0.0\", \"Point7\": \"-37.3236131,-59.1254191,0.0\"}'),(8,NULL,'','{\"0\": \"-37.3236157,-59.1253903,0.0\", \"1\": \"-37.3236221,-59.125338,0.0\"}'),(9,NULL,'','{\"0\": \"-37.3236157,-59.1253903,0.0\", \"1\": \"-37.3236221,-59.125338,0.0\"}'),(10,NULL,'','{\"0\": \"-37.321041,-59.1248061,0.0\", \"1\": \"-37.3236035,-59.1254211,0.0\", \"2\": \"-37.3236107,-59.125436,0.0\", \"3\": \"-37.323611,-59.1254137,0.0\", \"4\": \"-37.3236043,-59.1254108,0.0\", \"5\": \"-37.323607,-59.1254213,0.0\"}'),(11,NULL,'','{\"0\": \"-37.321041,-59.1248061,0.0\", \"1\": \"-37.3236035,-59.1254211,0.0\", \"2\": \"-37.3236107,-59.125436,0.0\", \"3\": \"-37.323611,-59.1254137,0.0\", \"4\": \"-37.3236043,-59.1254108,0.0\", \"5\": \"-37.323607,-59.1254213,0.0\"}'),(12,NULL,'','{\"0\": \"-37.3236112,-59.1254229,0.0\"}'),(13,NULL,'','{\"0\": \"-37.3236137,-59.1254218,0.0\", \"1\": \"-37.3236571,-59.1254951,0.0\", \"2\": \"-37.3236477,-59.1255207,0.0\", \"3\": \"-37.3238141,-59.125446,0.0\", \"4\": \"-37.3238751,-59.1254186,0.0\", \"5\": \"-37.3239358,-59.1254077,0.0\", \"6\": \"-37.3239913,-59.1253943,0.0\", \"7\": \"-37.3239702,-59.1253177,0.0\", \"8\": \"-37.3239106,-59.125315,0.0\", \"9\": \"-37.3243155,-59.1252031,0.0\", \"10\": \"-37.3241693,-59.1252304,0.0\", \"11\": \"-37.3240889,-59.1252712,0.0\", \"12\": \"-37.3240794,-59.1253022,0.0\", \"13\": \"-37.3239787,-59.1253496,0.0\", \"14\": \"-37.3238922,-59.1253978,0.0\", \"15\": \"-37.3238122,-59.125425,0.0\", \"16\": \"-37.3237628,-59.1254027,0.0\", \"17\": \"-37.3237011,-59.1254937,0.0\"}'),(14,NULL,'','{\"0\": \"-37.3236137,-59.1254218,0.0\", \"1\": \"-37.3236571,-59.1254951,0.0\", \"2\": \"-37.3236477,-59.1255207,0.0\", \"3\": \"-37.3238141,-59.125446,0.0\", \"4\": \"-37.3238751,-59.1254186,0.0\", \"5\": \"-37.3239358,-59.1254077,0.0\", \"6\": \"-37.3239913,-59.1253943,0.0\", \"7\": \"-37.3239702,-59.1253177,0.0\", \"8\": \"-37.3239106,-59.125315,0.0\", \"9\": \"-37.3243155,-59.1252031,0.0\", \"10\": \"-37.3241693,-59.1252304,0.0\", \"11\": \"-37.3240889,-59.1252712,0.0\", \"12\": \"-37.3240794,-59.1253022,0.0\", \"13\": \"-37.3239787,-59.1253496,0.0\", \"14\": \"-37.3238922,-59.1253978,0.0\", \"15\": \"-37.3238122,-59.125425,0.0\", \"16\": \"-37.3237628,-59.1254027,0.0\", \"17\": \"-37.3237011,-59.1254937,0.0\"}');
/*!40000 ALTER TABLE `route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'bicycle'
--

--
-- Dumping routines for database 'bicycle'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-17 17:33:44
