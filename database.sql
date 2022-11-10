CREATE DATABASE  IF NOT EXISTS `mwanzo_baraka` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mwanzo_baraka`;
-- MySQL dump 10.13  Distrib 8.0.29, for Linux (x86_64)
--
-- Host: localhost    Database: mwanzo_baraka
-- ------------------------------------------------------
-- Server version	8.0.30-0ubuntu0.22.04.1

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
-- Table structure for table `contributions`
--

DROP TABLE IF EXISTS `contributions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contributions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `member_id` varchar(45) DEFAULT NULL,
  `group_name` varchar(45) DEFAULT NULL,
  `amount` varchar(45) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_member_id_idx` (`member_id`),
  KEY `fk_group_id_idx` (`group_name`),
  CONSTRAINT `fk_group_id` FOREIGN KEY (`group_name`) REFERENCES `member_groups` (`name`),
  CONSTRAINT `fk_member_id` FOREIGN KEY (`member_id`) REFERENCES `members` (`national_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contributions`
--

LOCK TABLES `contributions` WRITE;
/*!40000 ALTER TABLE `contributions` DISABLE KEYS */;
INSERT INTO `contributions` VALUES (1,'2324324',NULL,'800','2022-11-02 04:59:16'),(2,'2324324',NULL,'200','2022-11-02 04:59:16'),(3,'2324324',NULL,'1300','2022-11-02 05:05:00'),(4,NULL,'Growth Sacco','200','2022-11-02 05:05:00'),(5,'343564645',NULL,'1000','2022-11-02 05:07:21'),(6,'343564645',NULL,'10000','2022-11-02 11:45:05');
/*!40000 ALTER TABLE `contributions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loans`
--

DROP TABLE IF EXISTS `loans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loans` (
  `id` int NOT NULL AUTO_INCREMENT,
  `member_id` varchar(45) DEFAULT NULL,
  `group_name` varchar(45) DEFAULT NULL,
  `amount` int NOT NULL,
  `period` int NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loans`
--

LOCK TABLES `loans` WRITE;
/*!40000 ALTER TABLE `loans` DISABLE KEYS */;
INSERT INTO `loans` VALUES (4,'2324324',NULL,9000,1,'2022-11-02 08:36:08'),(5,NULL,'Growth Sacco',600,1,'2022-11-02 09:21:44'),(6,'343564645',NULL,30000,2,'2022-11-02 11:45:22');
/*!40000 ALTER TABLE `loans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_groups`
--

DROP TABLE IF EXISTS `member_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_groups` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `reg_fee` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_groups`
--

LOCK TABLES `member_groups` WRITE;
/*!40000 ALTER TABLE `member_groups` DISABLE KEYS */;
INSERT INTO `member_groups` VALUES (2,'Hello Group','5000'),(7,'Baraka Group','5000'),(8,'Growth Sacco','5000');
/*!40000 ALTER TABLE `member_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `national_id` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `gender` varchar(45) NOT NULL,
  `group_name` varchar(45) DEFAULT NULL,
  `reg_fee` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`national_id`),
  UNIQUE KEY `national_id_UNIQUE` (`national_id`),
  KEY `fk_group_name_idx` (`group_name`),
  CONSTRAINT `fk_group_name` FOREIGN KEY (`group_name`) REFERENCES `member_groups` (`name`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES ('2324324','Mary','0456767675','Female','Growth Sacco',NULL),('23432534','Joe','0729343','Male','Hello Group',NULL),('23543353','Adrian','08343434','Male','Baraka Group',NULL),('2446464','Anita','043984595','Female','Growth Sacco',NULL),('343564645','Evans','083495454','Male',NULL,'2000'),('344553434','Anthony','0729304034','Male',NULL,'2000'),('45634','Raphael','074354590','Male',NULL,'2000');
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `loan_id` int NOT NULL,
  `installment` int NOT NULL,
  `amount` int NOT NULL,
  `interest` int NOT NULL,
  `penalty` int DEFAULT NULL,
  `due` date NOT NULL,
  `cleared` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_loan_id_idx` (`loan_id`),
  CONSTRAINT `fk_loan_id` FOREIGN KEY (`loan_id`) REFERENCES `loans` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (61,4,1,750,7,75,'2022-12-31',1),(62,4,2,750,7,NULL,'2023-01-31',1),(63,4,3,750,7,NULL,'2023-02-28',1),(64,4,4,750,7,NULL,'2023-03-31',0),(65,4,5,750,7,NULL,'2023-04-30',0),(66,4,6,750,7,NULL,'2023-05-31',0),(67,4,7,750,7,NULL,'2023-06-30',0),(68,4,8,750,7,NULL,'2023-07-31',0),(69,4,9,750,7,NULL,'2023-08-31',0),(70,4,10,750,7,NULL,'2023-09-30',0),(71,4,11,750,7,NULL,'2023-10-31',0),(72,4,12,750,7,NULL,'2023-11-30',0),(73,5,1,50,0,NULL,'2022-12-31',0),(74,5,2,50,0,NULL,'2023-01-31',0),(75,5,3,50,0,NULL,'2023-02-28',0),(76,5,4,50,0,NULL,'2023-03-31',0),(77,5,5,50,0,NULL,'2023-04-30',0),(78,5,6,50,0,NULL,'2023-05-31',0),(79,5,7,50,0,NULL,'2023-06-30',0),(80,5,8,50,0,NULL,'2023-07-31',0),(81,5,9,50,0,NULL,'2023-08-31',0),(82,5,10,50,0,NULL,'2023-09-30',0),(83,5,11,50,0,NULL,'2023-10-31',0),(84,5,12,50,0,NULL,'2023-11-30',0),(85,6,1,1250,15,NULL,'2022-12-31',1),(86,6,2,1250,15,NULL,'2023-01-31',1),(87,6,3,1250,15,NULL,'2023-02-28',0),(88,6,4,1250,15,NULL,'2023-03-31',0),(89,6,5,1250,15,NULL,'2023-04-30',0),(90,6,6,1250,15,NULL,'2023-05-31',0),(91,6,7,1250,15,NULL,'2023-06-30',0),(92,6,8,1250,15,NULL,'2023-07-31',0),(93,6,9,1250,15,NULL,'2023-08-31',0),(94,6,10,1250,15,NULL,'2023-09-30',0),(95,6,11,1250,15,NULL,'2023-10-31',0),(96,6,12,1250,15,NULL,'2023-11-30',0),(97,6,13,1250,15,NULL,'2023-12-31',0),(98,6,14,1250,15,NULL,'2024-01-31',0),(99,6,15,1250,15,NULL,'2024-02-29',0),(100,6,16,1250,15,NULL,'2024-03-31',0),(101,6,17,1250,15,NULL,'2024-04-30',0),(102,6,18,1250,15,NULL,'2024-05-31',0),(103,6,19,1250,15,NULL,'2024-06-30',0),(104,6,20,1250,15,NULL,'2024-07-31',0),(105,6,21,1250,15,NULL,'2024-08-31',0),(106,6,22,1250,15,NULL,'2024-09-30',0),(107,6,23,1250,15,NULL,'2024-10-31',0),(108,6,24,1250,15,NULL,'2024-11-30',0);
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-10  8:22:31
