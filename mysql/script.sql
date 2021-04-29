-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: taskmanagerdb
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Current Database: `taskmanagerdb`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `taskmanagerdb` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `taskmanagerdb`;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (8);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_USER'),(2,'ROLE_ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `todo_seq`
--

DROP TABLE IF EXISTS `todo_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `todo_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `todo_seq`
--

LOCK TABLES `todo_seq` WRITE;
/*!40000 ALTER TABLE `todo_seq` DISABLE KEYS */;
INSERT INTO `todo_seq` VALUES (451);
/*!40000 ALTER TABLE `todo_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `todos`
--

DROP TABLE IF EXISTS `todos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `todos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `finished` datetime DEFAULT NULL,
  `started` datetime DEFAULT NULL,
  `target_date` date DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `1_idx` (`user_id`),
  KEY `2_idx` (`user_id`),
  CONSTRAINT `2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=569 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `todos`
--

LOCK TABLES `todos` WRITE;
/*!40000 ALTER TABLE `todos` DISABLE KEYS */;
INSERT INTO `todos` VALUES (52,'2021-02-12 15:57:17','some task for user 2 for today',NULL,'2021-02-15 17:59:00','2021-02-12',3),(53,'2021-02-12 15:57:34','some task for user 2 for tomorrow',NULL,'2021-02-21 17:47:24','2021-02-13',3),(54,'2021-02-12 15:58:18','task for user3 for nex year','2021-02-21 17:56:05','2021-02-21 17:56:02','2022-06-12',4),(55,'2021-02-12 15:58:33','one more task for user 3','2021-03-04 14:17:09','2021-03-04 14:17:02','2021-02-14',4),(102,'2021-02-12 15:27:19','new tusk for myself',NULL,'2021-02-28 18:28:09','2021-02-13',2),(103,'2021-02-12 15:36:32','new ывывм фы ',NULL,NULL,'2021-02-05',2),(104,'2021-02-12 17:36:44','new task 2222',NULL,NULL,'2021-02-13',2),(152,'2021-02-15 13:54:54','Нове завдання 123',NULL,NULL,'2021-02-16',2),(154,'2021-02-15 14:04:59','Нове завдання для Користувача 1',NULL,NULL,'2021-02-10',3),(202,'2021-02-15 12:13:13','завдання 1',NULL,'2021-02-15 12:13:19','2021-02-16',2),(252,'2021-02-15 14:41:30','one more task for user 2',NULL,NULL,'2021-02-14',2),(302,'2021-02-15 18:27:55','bbbbbbbbbbb',NULL,NULL,'2021-02-04',2),(352,'2021-02-28 19:55:55','ымыввы',NULL,NULL,'2021-02-28',5),(353,'2021-02-28 19:56:13','фсцыасцасы',NULL,NULL,'2021-02-28',6),(505,'2021-02-15 13:57:58','new todo for some user',NULL,NULL,'2021-02-15',2),(506,'2021-02-15 14:02:05','new todo for some user',NULL,NULL,'2021-02-15',2),(507,'2021-02-15 14:05:34','new todo for user 2',NULL,NULL,'2021-02-15',2),(508,'2021-02-15 15:22:35','new todo for user 2',NULL,NULL,'2021-02-15',2),(510,'2021-02-20 20:19:31','ascasvcsvc',NULL,NULL,'2021-02-19',2),(511,'2021-02-20 20:23:13','11111111111',NULL,NULL,'2021-02-19',2),(517,'2021-02-20 18:37:29','6666666666666','2021-02-27 18:34:38','2021-02-21 15:27:51','2021-02-22',2),(518,'2021-02-20 20:42:08','666666666666','2021-02-21 17:46:32','2021-02-21 17:45:12','2021-02-19',2),(519,'2021-02-20 20:44:43','111111111111','2021-02-21 17:46:36','2021-02-21 17:46:35','2021-02-19',2),(520,'2021-02-20 20:49:54','2222222222','2021-02-21 17:55:53','2021-02-21 17:46:38','2021-02-19',2),(521,'2021-02-20 20:52:27','11111111111','2021-02-27 16:49:10','2021-02-21 19:34:12','2021-02-19',2),(523,'2021-02-20 21:03:32','new todo for user 2','2021-02-27 18:44:14','2021-02-22 17:48:17','2021-02-19',2),(524,'2021-02-20 21:08:50','new todo for user 2',NULL,'2021-02-22 18:12:18','2021-02-21',2),(525,'2021-02-20 19:14:49','new todo for user 2',NULL,NULL,'2021-02-19',2),(526,'2021-02-20 21:14:49','new todo for user 2',NULL,NULL,'2021-02-19',2),(527,'2021-02-20 21:17:03','new todo for user 2',NULL,NULL,'2021-02-19',2),(528,'2021-02-20 21:17:03','new todo for user 2',NULL,NULL,'2021-02-19',2),(529,'2021-02-20 21:18:16','new todo for user 2',NULL,NULL,'2021-02-19',2),(530,'2021-02-20 21:18:16','new todo for user 2','2021-02-27 16:49:29',NULL,'2021-02-19',2),(531,'2021-02-20 21:24:11','new todo for user 2',NULL,NULL,'2021-02-19',2),(532,'2021-02-20 21:25:51','new todo for user 2',NULL,NULL,'2021-02-19',2),(533,'2021-02-20 21:24:11','descr',NULL,NULL,'2021-02-20',2),(534,'2021-02-20 21:38:21','new todo for user 2',NULL,NULL,'2021-02-19',2),(535,'2021-02-21 09:01:09','new todo for user 2',NULL,NULL,'2021-02-20',2),(536,'2021-02-21 09:02:05','new todo for user 2',NULL,NULL,'2021-02-20',2),(537,'2021-02-21 11:23:19','new todo for user 2',NULL,NULL,'2021-02-20',2),(538,'2021-02-21 11:26:38','new todo for user 2',NULL,NULL,'2021-02-20',2),(539,'2021-02-21 11:34:21','new todo for user 2',NULL,NULL,'2021-02-20',2),(540,'2021-02-21 11:46:29','new todo for user 2',NULL,NULL,'2021-02-21',2),(541,'2021-02-21 11:47:03','new todo for user 2',NULL,NULL,'2021-02-20',2),(542,'2021-02-21 11:47:29','new todo for user 2',NULL,NULL,'2021-02-20',2),(543,'2021-02-21 11:47:47','new todo for user 2',NULL,NULL,'2021-02-21',2),(544,'2021-02-21 11:50:57','111111',NULL,NULL,'2021-02-21',2),(545,'2021-02-20 18:25:10','222222222222222',NULL,NULL,'2021-02-19',2),(546,'2021-02-20 18:25:10','222222222222222',NULL,NULL,'2021-02-19',2),(547,'2021-02-20 18:25:10','33333333',NULL,NULL,'2021-02-19',2),(548,'2021-02-20 18:25:10','33333333',NULL,NULL,'2021-02-19',2),(549,'2021-02-20 18:25:10','333333333333333333',NULL,NULL,'2021-02-21',2),(550,'2021-02-22 17:55:02','ascasvascvsdvav',NULL,NULL,'2021-02-19',2),(551,'2021-02-22 18:12:35','casc acaacasvasdz ',NULL,NULL,'2021-02-18',2),(552,'2021-02-27 16:20:00','acacac',NULL,NULL,'2021-02-28',2),(553,'2021-02-28 16:55:04','qdqdqdq',NULL,NULL,'2021-02-13',2),(554,'2021-02-28 16:55:05','qdqdqdq',NULL,NULL,'2021-02-13',2),(555,'2021-02-28 16:57:20','11111111111',NULL,NULL,'2021-02-13',2),(556,'2021-02-28 17:06:47','sqdqdqd',NULL,NULL,'2021-02-28',2),(557,'2021-02-28 17:08:50','11111111111',NULL,NULL,'2021-02-28',2),(558,'2021-02-28 17:09:08','3333333333',NULL,NULL,'2021-02-28',3),(559,'2021-02-28 17:09:25','44444444444',NULL,NULL,'2021-02-28',3),(560,'2021-02-28 17:15:51','xc zxd v ',NULL,NULL,'2021-02-06',2),(561,'2021-02-28 18:28:44','фсфсфс',NULL,NULL,'2021-02-27',2),(562,'2021-02-28 18:30:28','ÑÑÑÑÑÑÑÐ¹Ð¹',NULL,NULL,'2021-02-28',2),(563,'2021-02-28 18:42:26','Нове завдання',NULL,NULL,'2021-02-28',2),(564,'2021-02-28 18:43:27','нове завдання',NULL,NULL,'2021-02-28',2),(565,'2021-02-28 18:44:27','нове завдання',NULL,NULL,'2021-02-28',2),(566,'2021-02-28 18:46:38','нове завдання\\',NULL,NULL,'2021-02-27',214),(567,'2021-02-28 19:24:44','фмфмфмфм',NULL,NULL,'2021-02-26',212),(568,'2021-03-04 14:16:52','xv SVSVsV',NULL,NULL,'2021-03-05',4);
/*!40000 ALTER TABLE `todos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `first_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=215 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci KEY_BLOCK_SIZE=2;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'admin@gmail.com','Admin','Admin ','{bcrypt}$2a$10$pL1oszLc74Eg1EwetiLtgukDOALc6o4LQcGraEhQ.7wsrHtvKM2xu'),(3,'user1@gmail.com','User1','User1','{bcrypt}$2a$10$FkUGmoVtBPsYfWVPOf2zmugExT0JbOFwmc5/DFfDD5QJ.QJyvJH7a'),(4,'user2@gmail.com','User2','User2','{bcrypt}$2a$10$L/VvhcFs.6yWJoRI5aUQHOD5dfEb3LpFbZUTSlBWMEyit2mFCwA22'),(5,'user3@gmail.com','user3','user3','{bcrypt}$2a$10$ImYefn4PWE6ZNroJTDLwLuputKPfPCFSXRSSpRRIZsHYF71ia0AGC'),(6,'qwerty@gmail.com','qq','qq','{bcrypt}$2a$10$lDr/.PrgG/abpY0F53AEmOvws32NovljlHOOQz4t7rK6dAcRRemVG'),(7,'user7@gmail.com','u7','u7','{bcrypt}$2a$10$TCgJQIftOJLKVWHmytIu3e.VFOYZ6wrae7oHaDA726dKsHS0VoO9q'),(212,'user4@gmail.com','user4','user4','{bcrypt}$2a$10$ad35qC8hPbdL5BOik8KGhuplSCH3OO.uqmIlxpwWKUveeS6ZSHTCW'),(214,'user5@gmail.com','u','u','{bcrypt}$2a$10$OWiYbAgqjGVIwi97AvMuxevu5Xp1kL3z0znu46fHZT/RTdKcnz1e6');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `roles_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`roles_id`),
  KEY `FKj9553ass9uctjrmh0gkqsmv0d` (`roles_id`),
  CONSTRAINT `1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKj9553ass9uctjrmh0gkqsmv0d` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(212,1),(214,1),(2,2);
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

-- Dump completed on 2021-04-27 13:02:29
