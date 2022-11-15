-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: srmsystem
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `documents`
--

use heroku_9de761e705435a5;

DROP TABLE IF EXISTS `documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `documents` (
  `DID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `acronym` varchar(45) NOT NULL,
  `type` varchar(100) NOT NULL,
  `price` float NOT NULL,
  PRIMARY KEY (`DID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
INSERT INTO `documents` VALUES (1,'Honorable Dismissal / Transfer Credential','doc-hd','normal',50),(2,'Transcript of Record(Graduate)','doc-tor-grad','normal',50),(3,'TOR Undergraduate','doc-tor-undergrad','normal',30),(4,'Diploma 1st copy','doc-diploma-first','normal',80),(5,'Diploma 2nd / succeeding copy','doc-diploma-succeeding','normal',50),(6,'Graduate','doc-cert-grad','certification',40),(7,'Medium of Instruction','doc-cert-moi','certification',100),(8,'CHED Authentication','doc-cert-ched','certification',90),(9,'Red Ribbon DFA','doc-cert-DFA','certification',90),(10,'Visa Extension','doc-cert-visa','certification',980),(11,'Certified True Copy','doc-cert-ctc','certification',70),(12,'Subject Description','doc-cert-sd','certification',120),(13,'Enrollment','doc-cert-enroll','certification',190),(14,'General Weighted Average','doc-cert-gwa','certification',50),(15,'Others','doc-others','other',0);
/*!40000 ALTER TABLE `documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `login` (
  `username` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(45) DEFAULT 'USER',
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES ('admin','$2a$12$l2wohAJfaBmdEWjKtSk63OG4f38lWJ0Ws6cP1jorvsuBgUCwNu9mq','ADMIN'),('anna','$2a$10$4U3EaPaSJAuh4CV7mUY0f.BwpvYCUGWjCK2NMFaBedwMxgIyqgI/i','STUDENT'),('Juan','$2a$12$i1QtQZgzzGc8PuUeSdBsLu8om.rZQw/icRVRnCcx2iLxlfUCx6f56','STUDENT');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requests`
--

DROP TABLE IF EXISTS `requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `requests` (
  `RID` int NOT NULL AUTO_INCREMENT,
  `TID` int NOT NULL,
  `DID` int NOT NULL,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(100) DEFAULT 'FOR CHECKING',
  PRIMARY KEY (`RID`),
  KEY `DID_idx` (`DID`),
  CONSTRAINT `DID` FOREIGN KEY (`DID`) REFERENCES `documents` (`DID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requests`
--

LOCK TABLES `requests` WRITE;
/*!40000 ALTER TABLE `requests` DISABLE KEYS */;
INSERT INTO `requests` VALUES (1,17,3,'2022-11-15 08:23:19','FOR PICKUP'),(2,17,8,'2022-11-15 08:28:30','REJECTED'),(3,18,3,'2022-11-15 08:19:42','FOR PICKUP'),(4,18,8,'2022-11-15 08:23:15','FOR PICKUP'),(5,14,8,'2022-11-15 05:32:41','FOR CHECKING'),(6,4,2,'2022-11-15 08:24:48','FOR PICKUP'),(7,4,5,'2022-11-15 06:16:31','FOR CHECKING');
/*!40000 ALTER TABLE `requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `transactions` (
  `TID` int NOT NULL AUTO_INCREMENT,
  `UID` int NOT NULL,
  `documents` varchar(100) DEFAULT NULL,
  `payment` varchar(255) DEFAULT NULL,
  `status` varchar(45) DEFAULT 'UNPAID',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`TID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,1,'3,5','asdaasdasdas','PAID','2022-11-14 21:53:34'),(2,2,'2,6','asda','PAID','2022-11-14 22:15:24'),(3,1,'4,8','asdaasdasdas','UNPAID','2022-11-14 22:15:33'),(4,1,'2,5','asdas','PAID','2022-11-14 22:15:43'),(5,2,'5,8','asdsa','UNPAID','2022-11-14 22:45:16'),(6,2,'3,5,8','gadasda','UNPAID','2022-11-14 23:01:24'),(7,1,'3,8','asdaasdasdas','UNPAID','2022-11-14 23:02:10'),(8,1,'5,8','asdaasdasdas','UNPAID','2022-11-14 23:15:07'),(9,1,'3,5','gaqweqweq','UNPAID','2022-11-14 23:30:41'),(10,2,'6','asdas','UNPAID','2022-11-14 23:31:53'),(11,2,'2,3','asd','UNPAID','2022-11-14 23:39:42'),(12,2,'5,7','asdas','UNPAID','2022-11-15 10:28:25'),(13,2,'8,9','adfa','UNPAID','2022-11-15 10:29:12'),(14,2,'8','das','PAID','2022-11-15 10:33:27'),(15,2,'8,9','asda','UNPAID','2022-11-15 10:37:16'),(16,1,'2,3','adsda','UNPAID','2022-11-15 13:55:57'),(17,1,'3,8','gadas','PAID','2022-11-15 13:56:49'),(18,1,'3,8','asf','PAID','2022-11-15 14:03:58');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `UID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `email` varchar(100) NOT NULL,
  `student_number` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `middle_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `mobile_number` varchar(45) NOT NULL,
  PRIMARY KEY (`UID`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `student_number_UNIQUE` (`student_number`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Juan','juan@gmail.com','202212345','Juan','Santos','Dela Cruz','09123456789'),(2,'anna','anna@gmail.com','202212346','Anna','-','Delos Santos','09987654321'),(3,'admin','admin@gmail.com','-','-','-','-','-');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-15 21:03:19
