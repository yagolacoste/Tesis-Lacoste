CREATE DATABASE  IF NOT EXISTS `bicycle` /*!40100 DEFAULT CHARACTER SET latin1 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bicycle`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: bicycle
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appuser` (
  `id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `age` int NOT NULL,
  `phone` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `stored_document_id` bigint DEFAULT NULL,
  `weight` int NOT NULL,
  `height` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKhcij6hlq32eras7xlmvbx2sbm` (`email`),
  KEY `fk_appuser_Stored_Document1` (`stored_document_id`),
  CONSTRAINT `fk_appuser_Stored_Document1` FOREIGN KEY (`stored_document_id`) REFERENCES `stored_document` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appuser`
--

LOCK TABLES `appuser` WRITE;
/*!40000 ALTER TABLE `appuser` DISABLE KEYS */;
INSERT INTO `appuser` VALUES (1,'$2a$10$F1m6kM3nFSQVWQLgXq5o1eR0eSBYfyrrRHkRECjjlECYAg0H1aX.m','yago','Lacoste',27,'2281461938','yago@gmail.com',1,0,0),(17,'$2a$10$F1m6kM3nFSQVWQLgXq5o1eR0eSBYfyrrRHkRECjjlECYAg0H1aX.m','leo','perazzo@gmail.com',25,'256825','leo@gmail.com',32,0,0);
/*!40000 ALTER TABLE `appuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `battle`
--

DROP TABLE IF EXISTS `battle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `battle` (
  `id_battle` int NOT NULL AUTO_INCREMENT,
  `route_id` varchar(255) NOT NULL,
  `complete_date` date NOT NULL,
  `cant_participants` int NOT NULL,
  PRIMARY KEY (`id_battle`),
  KEY `fk_battle_route1_idx` (`route_id`),
  CONSTRAINT `fk_battle_route1` FOREIGN KEY (`route_id`) REFERENCES `route` (`id_route`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `battle`
--

LOCK TABLES `battle` WRITE;
/*!40000 ALTER TABLE `battle` DISABLE KEYS */;
/*!40000 ALTER TABLE `battle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `battle_participation`
--

DROP TABLE IF EXISTS `battle_participation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `battle_participation` (
  `appuser_id` int NOT NULL,
  `statistics_id` varchar(255) DEFAULT NULL,
  `battle_id` int NOT NULL,
  KEY `fk_appuser_has_battle_has_appuser_has_route_appuser1_idx` (`appuser_id`),
  KEY `fk_battle_participation_statistics1_idx` (`statistics_id`),
  KEY `fk_battle_participation_battle1_idx` (`battle_id`),
  CONSTRAINT `fk_appuser_has_battle_has_appuser_has_route_appuser1` FOREIGN KEY (`appuser_id`) REFERENCES `appuser` (`id`),
  CONSTRAINT `fk_battle_participation_battle1` FOREIGN KEY (`battle_id`) REFERENCES `battle` (`id_battle`),
  CONSTRAINT `fk_battle_participation_statistics1` FOREIGN KEY (`statistics_id`) REFERENCES `statistics` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `battle_participation`
--

LOCK TABLES `battle_participation` WRITE;
/*!40000 ALTER TABLE `battle_participation` DISABLE KEYS */;
/*!40000 ALTER TABLE `battle_participation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friends` (
  `appuser_id` int NOT NULL,
  `appuser_id_friends` int NOT NULL,
  PRIMARY KEY (`appuser_id`,`appuser_id_friends`),
  KEY `fk_appuser_has_appuser_appuser2` (`appuser_id_friends`),
  CONSTRAINT `fk_appuser_has_appuser_appuser1` FOREIGN KEY (`appuser_id`) REFERENCES `appuser` (`id`),
  CONSTRAINT `fk_appuser_has_appuser_appuser2` FOREIGN KEY (`appuser_id_friends`) REFERENCES `appuser` (`id`)
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
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refreshtoken`
--

DROP TABLE IF EXISTS `refreshtoken`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refreshtoken` (
  `expiry_date` datetime(6) NOT NULL,
  `token` varchar(255) NOT NULL,
  `appuser_id` int NOT NULL,
  PRIMARY KEY (`token`),
  UNIQUE KEY `UK_or156wbneyk8noo4jstv55ii3` (`token`),
  KEY `fk_refreshtoken_appuser1_idx` (`appuser_id`),
  CONSTRAINT `fk_refreshtoken_appuser1` FOREIGN KEY (`appuser_id`) REFERENCES `appuser` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refreshtoken`
--

LOCK TABLES `refreshtoken` WRITE;
/*!40000 ALTER TABLE `refreshtoken` DISABLE KEYS */;
/*!40000 ALTER TABLE `refreshtoken` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_USER'),(2,'ROLE_MODERATOR'),(3,'ROLE_ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `route` (
  `id_route` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `coordinates` json NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id_route`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route`
--

LOCK TABLES `route` WRITE;
/*!40000 ALTER TABLE `route` DISABLE KEYS */;
INSERT INTO `route` VALUES ('1-go5sfuGPgk','','[\"-37.3236078,-59.1253996,0.0\", \"-37.3236096,-59.1254191,0.0\", \"-37.3236101,-59.1254238,0.0\"]','bb'),('1-JHstHP66FG','','[\"-36.7914951,-59.8510427,0.0\", \"-36.7914912,-59.8510444,0.0\", \"-36.7914898,-59.8510489,0.0\", \"-36.7914901,-59.8510501,0.0\", \"-36.7914887,-59.8510508,0.0\"]','pruba'),('1-lt9tVcxWLl','','[\"-37.3236039,-59.1254,0.0\", \"-37.3235896,-59.1254927,0.0\"]',''),('1-qOCyGueAdJ','pruebs','[\"-37.3236014,-59.1254131,0.0\", \"-37.3236341,-59.1253955,0.0\"]','ty'),('1-RqhYMgrmEQ','desde punto gym','[\"-37.3264285,-59.1286612,0.0\", \"-37.3264075,-59.1285815,0.0\", \"-37.3264184,-59.1285629,0.0\", \"-37.3264217,-59.1285299,0.0\", \"-37.3264032,-59.1285059,0.0\", \"-37.3263926,-59.1284842,0.0\", \"-37.3263709,-59.128448,0.0\", \"-37.3263321,-59.1283748,0.0\", \"-37.3263083,-59.1283275,0.0\", \"-37.3262823,-59.1282777,0.0\", \"-37.3262645,-59.1282382,0.0\", \"-37.3262476,-59.1281956,0.0\", \"-37.3262638,-59.1281437,0.0\", \"-37.3262651,-59.128101,0.0\", \"-37.3262596,-59.1280099,0.0\", \"-37.3262522,-59.1279286,0.0\", \"-37.3262434,-59.1278632,0.0\", \"-37.3262319,-59.1277809,0.0\", \"-37.3262233,-59.1277344,0.0\", \"-37.3262184,-59.1276818,0.0\", \"-37.3261401,-59.1276535,0.0\", \"-37.3261009,-59.1276184,0.0\", \"-37.3260674,-59.1275738,0.0\", \"-37.3260441,-59.1275533,0.0\", \"-37.3260273,-59.1275087,0.0\", \"-37.3260212,-59.127458,0.0\", \"-37.3260175,-59.1274207,0.0\", \"-37.3260142,-59.1273812,0.0\", \"-37.3260159,-59.1273221,0.0\", \"-37.3260161,-59.1272754,0.0\", \"-37.3260082,-59.1272156,0.0\", \"-37.3259963,-59.1271835,0.0\", \"-37.3259947,-59.1271389,0.0\", \"-37.3259891,-59.1270779,0.0\", \"-37.32598,-59.1270213,0.0\", \"-37.3259744,-59.1269837,0.0\", \"-37.3259506,-59.1269233,0.0\", \"-37.3259373,-59.1268762,0.0\", \"-37.3259271,-59.1268143,0.0\", \"-37.3259211,-59.1267617,0.0\", \"-37.3259141,-59.1267069,0.0\", \"-37.325897,-59.1266271,0.0\", \"-37.3258914,-59.1265794,0.0\", \"-37.325859,-59.1265002,0.0\", \"-37.3258337,-59.1264475,0.0\", \"-37.325822,-59.1263992,0.0\", \"-37.3257625,-59.1263711,0.0\", \"-37.3257097,-59.1263535,0.0\", \"-37.3256837,-59.1263133,0.0\", \"-37.3256476,-59.1263057,0.0\", \"-37.3256144,-59.1262902,0.0\", \"-37.3255796,-59.1262764,0.0\", \"-37.3255243,-59.1262992,0.0\", \"-37.3254863,-59.1263103,0.0\", \"-37.3254424,-59.1263015,0.0\", \"-37.3254196,-59.1262839,0.0\", \"-37.3253861,-59.1262994,0.0\", \"-37.3253543,-59.1263264,0.0\", \"-37.325329,-59.1263395,0.0\", \"-37.3252911,-59.1263608,0.0\", \"-37.3252547,-59.126381,0.0\", \"-37.3252212,-59.1264001,0.0\", \"-37.3251862,-59.1264303,0.0\", \"-37.3251399,-59.1264552,0.0\", \"-37.3250982,-59.126476,0.0\", \"-37.3250622,-59.1265216,0.0\", \"-37.3250319,-59.1265503,0.0\", \"-37.3249948,-59.1265611,0.0\", \"-37.3249292,-59.1265522,0.0\", \"-37.3248826,-59.1265321,0.0\", \"-37.3248451,-59.1265194,0.0\", \"-37.3247873,-59.1264738,0.0\", \"-37.3247705,-59.1264306,0.0\", \"-37.324761,-59.1263696,0.0\", \"-37.3247597,-59.1263445,0.0\", \"-37.324747,-59.1262992,0.0\", \"-37.3247088,-59.1262384,0.0\", \"-37.3246676,-59.1261961,0.0\", \"-37.3246396,-59.1261551,0.0\", \"-37.3246141,-59.1261056,0.0\", \"-37.3245856,-59.1260632,0.0\", \"-37.3245693,-59.1260278,0.0\", \"-37.3245517,-59.1259487,0.0\", \"-37.3245337,-59.1258892,0.0\", \"-37.3245149,-59.1258223,0.0\", \"-37.3245,-59.1257809,0.0\", \"-37.3244844,-59.1257283,0.0\", \"-37.3244851,-59.1257145,0.0\", \"-37.3244751,-59.125678,0.0\", \"-37.324452,-59.1256269,0.0\", \"-37.3244294,-59.125589,0.0\", \"-37.3244041,-59.1255651,0.0\", \"-37.3243655,-59.125532,0.0\", \"-37.3243459,-59.1255097,0.0\", \"-37.3243164,-59.1254863,0.0\", \"-37.324304,-59.1254598,0.0\", \"-37.3243022,-59.1254334,0.0\", \"-37.3242897,-59.1253898,0.0\", \"-37.3243075,-59.1252843,0.0\", \"-37.3243041,-59.125235,0.0\", \"-37.3242516,-59.1252147,0.0\", \"-37.3241774,-59.1252431,0.0\", \"-37.32414,-59.1252755,0.0\", \"-37.3240954,-59.1253106,0.0\", \"-37.3240603,-59.1253353,0.0\", \"-37.3240246,-59.1253558,0.0\", \"-37.3239472,-59.1254052,0.0\", \"-37.3239047,-59.1254262,0.0\", \"-37.3238438,-59.1254408,0.0\", \"-37.3238007,-59.1254518,0.0\", \"-37.3237818,-59.1254535,0.0\", \"-37.3237818,-59.1254276,0.0\", \"-37.3237402,-59.1254283,0.0\", \"-37.3236869,-59.1254632,0.0\", \"-37.3236491,-59.1254712,0.0\"]','Punto gym'),('1-TUMHwZahcD','Punto casa','[\"-37.3236219,-59.1254289,0.0\", \"-37.3236973,-59.1254573,0.0\"]','casa '),('17-MB5KgSjwXh','leo ruta prueba','[\"-37.3235903,-59.1254057,0.0\", \"-37.323598,-59.1253776,0.0\"]','leo route');
/*!40000 ALTER TABLE `route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statistics`
--

DROP TABLE IF EXISTS `statistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `statistics` (
  `id` varchar(255) NOT NULL,
  `appuser_appuser` int NOT NULL,
  `route_id_route` varchar(255) NOT NULL,
  `avg_speed` float DEFAULT NULL,
  `time` time DEFAULT NULL,
  `distance` float DEFAULT NULL,
  `time_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `weather` json DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_appuser_has_recorrido_recorrido1` (`route_id_route`),
  KEY `fk_appuser_has_recorrido_appuser1` (`appuser_appuser`),
  CONSTRAINT `fk_appuser_has_recorrido_appuser1` FOREIGN KEY (`appuser_appuser`) REFERENCES `appuser` (`id`),
  CONSTRAINT `fk_appuser_has_recorrido_recorrido1` FOREIGN KEY (`route_id_route`) REFERENCES `route` (`id_route`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statistics`
--

LOCK TABLES `statistics` WRITE;
/*!40000 ALTER TABLE `statistics` DISABLE KEYS */;
INSERT INTO `statistics` VALUES ('3xMTm26gDC',1,'1-go5sfuGPgk',0,'00:00:09',0,'2023-01-26 00:00:00',NULL),('6cAtfmgxWh',1,'1-go5sfuGPgk',1.31703,'00:00:19',8.04315,'2023-01-26 00:00:00',NULL),('AsIdEkZ9Nh',17,'17-MB5KgSjwXh',0.632242,'00:00:10',2.63312,'2023-04-11 00:00:00',NULL),('egMLoCtSqS',1,'1-go5sfuGPgk',0.246048,'00:00:09',2.16011,'2023-01-26 00:00:00',NULL),('gKzarbH8A2',1,'1-go5sfuGPgk',0.539318,'00:00:15',11.8581,'2023-01-26 00:00:00',NULL),('jlBl3Jqjcr',1,'1-lt9tVcxWLl',0.725074,'00:00:11',8.36816,'2023-01-26 00:00:00',NULL),('mJWHHl6ivT',1,'1-qOCyGueAdJ',0.680953,'00:00:13',3.95022,'2023-01-30 00:00:00',NULL),('mQqdT9EDXt',1,'1-qOCyGueAdJ',0.594524,'00:00:06',3.51747,'2023-01-30 00:00:00',NULL),('PJrt1H4INO',1,'1-RqhYMgrmEQ',1.3915,'00:05:02',559.995,'2023-01-30 00:00:00',NULL),('QsLjn5SExW',1,'1-qOCyGueAdJ',0.594524,'00:00:06',3.51747,'2023-01-30 00:00:00',NULL),('TSCZOe3r1h',1,'1-TUMHwZahcD',0.163775,'00:00:14',8.73856,'2023-05-09 00:00:00',NULL),('WS0zJNHS5V',1,'1-JHstHP66FG',0.0392692,'00:00:28',1.16888,'2023-02-06 00:00:00',NULL);
/*!40000 ALTER TABLE `statistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stored_document`
--

DROP TABLE IF EXISTS `stored_document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stored_document` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `extension` varchar(255) DEFAULT NULL,
  `estate` varchar(255) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stored_document`
--

LOCK TABLES `stored_document` WRITE;
/*!40000 ALTER TABLE `stored_document` DISABLE KEYS */;
INSERT INTO `stored_document` VALUES (1,'tortuga','06e4eb1e-7d37-4515-bbc1-cf575e8d9c43','.jpeg',NULL,_binary '\0'),(19,'IMG-20230404-WA0033.jpg','7c1be094-6379-4bd4-8724-52dbb1cba6cb','.jpg',NULL,_binary '\0'),(23,'IMG-20230404-WA0034.jpg','431c2228-516a-4ebd-87cf-a25a2f86cd5d','.jpg',NULL,_binary '\0'),(24,'IMG-20230404-WA0034.jpg','e706ffe2-e90c-4fe1-9b4a-2b0640702d20','.jpg',NULL,_binary '\0'),(25,'IMG-20230404-WA0034.jpg','bd4820eb-52b1-49a1-a5cb-ec5966881571','.jpg',NULL,_binary '\0'),(26,'IMG-20230404-WA0034.jpg','0b83aa4f-2c4b-4968-b6df-f72d44302f85','.jpg',NULL,_binary '\0'),(27,'IMG-20230404-WA0034.jpg','70feb102-dc99-4ed3-b79b-27d64dcf1f52','.jpg',NULL,_binary '\0'),(28,'IMG-20230404-WA0034.jpg','8800b910-0d38-4e66-a16b-187a66c1914c','.jpg',NULL,_binary '\0'),(29,'IMG-20230404-WA0034.jpg','d989e608-0b8f-49b0-bfd2-59a9d126f8de','.jpg',NULL,_binary '\0'),(30,'IMG-20230404-WA0034.jpg','ab9382b5-e5ed-404d-9124-0160ccd5ce70','.jpg',NULL,_binary '\0'),(31,'IMG-20230404-WA0034.jpg','76d64301-acda-49fc-be12-a4a11d5fe964','.jpg',NULL,_binary '\0'),(32,'IMG-20230404-WA0034.jpg','ca10ea96-21b7-4bcb-bc3a-490fb935243e','.jpg',NULL,_binary '\0'),(33,'IMG-20230404-WA0034.jpg','7e22c2b8-7db7-4089-9a45-246d70052831','.jpg',NULL,_binary '\0'),(34,'IMG-20230526-WA0006.jpeg','d545fd41-5cdf-4b77-b34c-2d9f0bf17653','.jpeg',NULL,_binary '\0'),(35,'IMG-20230526-WA0006.jpeg','562c27c7-e602-4a6f-a8e8-07474cf89305','.jpeg',NULL,_binary '\0'),(36,'IMG-20230526-WA0006.jpeg','8b3fb09a-91b7-40ed-b4af-7880f2b826c0','.jpeg',NULL,_binary '\0'),(37,'IMG-20230526-WA0006.jpeg','f2dc6590-7a2f-43b8-94b0-77390d4a4221','.jpeg',NULL,_binary '\0'),(38,'IMG-20230526-WA0006.jpeg','e4b2c4de-eaff-4a8d-b358-e218f91ed6e6','.jpeg',NULL,_binary '\0'),(39,'IMG-20230526-WA0006.jpeg','fabceabe-2887-44a2-af75-4fca04bab118','.jpeg',NULL,_binary '\0'),(40,'IMG-20230526-WA0006.jpeg','a95e5298-26c8-4e19-8641-32ed51225947','.jpeg',NULL,_binary '\0'),(41,'IMG-20230526-WA0006.jpeg','2218dd56-cbe8-453b-a952-c54d8db234d6','.jpeg',NULL,_binary '\0'),(42,'IMG-20230526-WA0006.jpeg','7fe7981c-cc35-49ab-8799-303ec40ac875','.jpeg',NULL,_binary '\0'),(43,'IMG-20230526-WA0006.jpeg','f5534f25-9460-4ae3-af5c-809cb017ffbc','.jpeg',NULL,_binary '\0'),(44,'IMG-20230526-WA0006.jpeg','3e372657-20eb-4c88-99a1-dd578cea56f9','.jpeg',NULL,_binary '\0'),(45,'IMG-20230526-WA0006.jpeg','52a190de-94f0-476a-bdd5-4407102f61fd','.jpeg',NULL,_binary '\0'),(46,'IMG-20230526-WA0006.jpeg','906c17f2-2012-40a8-8f90-554241d2d698','.jpeg',NULL,_binary '\0');
/*!40000 ALTER TABLE `stored_document` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `role_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_user_roles_appuser1_idx` (`user_id`),
  KEY `fk_user_roles_roles1` (`role_id`),
  CONSTRAINT `fk_user_roles_appuser1` FOREIGN KEY (`user_id`) REFERENCES `appuser` (`id`),
  CONSTRAINT `fk_user_roles_roles1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
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

-- Dump completed on 2023-05-30 23:08:57
