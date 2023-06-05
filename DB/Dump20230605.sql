CREATE DATABASE  IF NOT EXISTS `bicycle` /*!40100 DEFAULT CHARACTER SET latin1 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bicycle`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bicycle
-- ------------------------------------------------------
-- Server version	8.0.33

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appuser`
--

LOCK TABLES `appuser` WRITE;
/*!40000 ALTER TABLE `appuser` DISABLE KEYS */;
INSERT INTO `appuser` VALUES (1,'$2a$10$lo0BRu.wS4T3YoH9Lz.JwerTRiQp2Qd2ylYoXpb4Mlrw05KYCfXki','Yago','Lacoste',27,'2281461938','yagolacos@gmail.com',1,85,175);
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
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
INSERT INTO `refreshtoken` VALUES ('2023-06-06 11:54:08.803402','47dd278d-40e6-4c54-8490-59ac091cedfd',1),('2023-06-06 14:55:50.923822','7c8e346d-2dda-4095-bec4-5c27a2282a83',1),('2023-06-01 15:02:05.038308','a2e9c798-aa93-4ebe-8bd3-8a9cdcf96171',1),('2023-06-06 11:38:47.488396','dd6309af-b3cd-4a2a-adf8-45a9cd455bb1',1);
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
  `distance` float NOT NULL,
  `avgProm` time NOT NULL,
  PRIMARY KEY (`id_route`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route`
--

LOCK TABLES `route` WRITE;
/*!40000 ALTER TABLE `route` DISABLE KEYS */;
INSERT INTO `route` VALUES ('1-3oqkHe0T84','Me movi de temperies','[{\"Latitude\": -37.3233, \"Longitude\": -59.141665}, {\"Latitude\": -37.32348, \"Longitude\": -59.1415567}, {\"Latitude\": -37.3235867, \"Longitude\": -59.14149}, {\"Latitude\": -37.32372, \"Longitude\": -59.14141}, {\"Latitude\": -37.3237917, \"Longitude\": -59.1413667}]','temperies',0,'00:00:00'),('1-Cj3xVUSIww','Otra ruta de temperies','[{\"Latitude\": -37.3238628, \"Longitude\": -59.1413236}, {\"Latitude\": -37.3244378, \"Longitude\": -59.1409767}, {\"Latitude\": -37.3246637, \"Longitude\": -59.14084}, {\"Latitude\": -37.3248876, \"Longitude\": -59.1407058}, {\"Latitude\": -37.3250642, \"Longitude\": -59.1405978}, {\"Latitude\": -37.3252892, \"Longitude\": -59.1404626}, {\"Latitude\": -37.3254922, \"Longitude\": -59.1403368}, {\"Latitude\": -37.3257657, \"Longitude\": -59.14017}]','Temperies2',0,'00:00:00'),('1-kjGdvhIcZu','Camino de temperies','[{\"Latitude\": -37.3272966, \"Longitude\": -59.1392315}, {\"Latitude\": -37.3274429, \"Longitude\": -59.1391419}, {\"Latitude\": -37.3275245, \"Longitude\": -59.1390927}, {\"Latitude\": -37.3276594, \"Longitude\": -59.1390109}, {\"Latitude\": -37.3277654, \"Longitude\": -59.1389465}, {\"Latitude\": -37.3278794, \"Longitude\": -59.1388781}, {\"Latitude\": -37.3279548, \"Longitude\": -59.1388326}, {\"Latitude\": -37.3280582, \"Longitude\": -59.1387695}, {\"Latitude\": -37.3281954, \"Longitude\": -59.1386865}, {\"Latitude\": -37.3283031, \"Longitude\": -59.1386216}, {\"Latitude\": -37.3283878, \"Longitude\": -59.138571}, {\"Latitude\": -37.3285103, \"Longitude\": -59.1384654}, {\"Latitude\": -37.3285415, \"Longitude\": -59.1383623}, {\"Latitude\": -37.3285911, \"Longitude\": -59.1382215}, {\"Latitude\": -37.3286389, \"Longitude\": -59.1380865}]','Temperies',0,'00:00:00');
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
INSERT INTO `statistics` VALUES ('7LiiKbL5YY',1,'1-kjGdvhIcZu',4.42443,'00:00:44',187.516,'2023-06-05 00:00:00',NULL),('jPhwKLQaiA',1,'1-3oqkHe0T84',4.42422,'00:00:17',60.6394,'2023-06-05 00:00:00',NULL),('nPGtFyOp5V',1,'1-Cj3xVUSIww',8.6863,'00:00:23',234.641,'2023-06-05 00:00:00',NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stored_document`
--

LOCK TABLES `stored_document` WRITE;
/*!40000 ALTER TABLE `stored_document` DISABLE KEYS */;
INSERT INTO `stored_document` VALUES (1,'png-transparent-the-binding-of-isaac-afterbirth-plus-nicalis-video-game-tarot-others-miscellaneous-face-head.png','199fab0e-82cc-4639-905d-af7551c91c33','.png',NULL,_binary '\0'),(2,'png-transparent-the-binding-of-isaac-afterbirth-plus-nicalis-video-game-tarot-others-miscellaneous-face-head.png','52b6993b-b16a-450e-bde1-d703a108bffe','.png',NULL,_binary '\0');
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
INSERT INTO `user_roles` VALUES (1,1);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-05 16:41:36
