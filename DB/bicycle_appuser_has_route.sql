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
-- Table structure for table `appuser_has_route`
--

DROP TABLE IF EXISTS `appuser_has_route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appuser_has_route` (
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
  CONSTRAINT `fk_appuser_has_recorrido_appuser1` FOREIGN KEY (`appuser_appuser`) REFERENCES `appuser` (`appuser`),
  CONSTRAINT `fk_appuser_has_recorrido_recorrido1` FOREIGN KEY (`route_id_route`) REFERENCES `route` (`id_route`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appuser_has_route`
--

LOCK TABLES `appuser_has_route` WRITE;
/*!40000 ALTER TABLE `appuser_has_route` DISABLE KEYS */;
INSERT INTO `appuser_has_route` VALUES ('3WUK9vFi11',1,'1-EzQ3O0BEHZ',0.630194,'12:00:04',3.63498,'2023-01-23 00:00:00',NULL),('9vWt1mpOlN',1,'1-2qO3vdAhCL',0.670255,'12:00:11',2.16402,'2023-01-23 00:00:00',NULL),('aVTKrAkw8e',1,'1-3YHXFyss6p',0.219209,'00:00:06',1.21594,'2023-01-23 00:00:00',NULL);
/*!40000 ALTER TABLE `appuser_has_route` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-25  8:44:35
