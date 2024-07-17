-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.37 - MySQL Community Server (GPL)
-- Server OS:                    Linux
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for user-management
DROP DATABASE IF EXISTS `user-management`;
CREATE DATABASE IF NOT EXISTS `user-management` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `user-management`;

-- Dumping structure for table user-management.refreshtoken
DROP TABLE IF EXISTS `refreshtoken`;
CREATE TABLE IF NOT EXISTS `refreshtoken` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `expiry_date` datetime NOT NULL,
  `token` varchar(255) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Dumping data for table user-management.refreshtoken: ~5 rows (approximately)
/*!40000 ALTER TABLE `refreshtoken` DISABLE KEYS */;
INSERT INTO `refreshtoken` (`id`, `expiry_date`, `token`, `user_id`) VALUES
	(1, '2022-01-26 13:00:33', '7ceec489-babf-459e-8db5-875c8a9f35ae', 6),
	(2, '2022-01-26 16:09:02', '57addfdf-d360-4d9a-8c92-6814dae7168c', 6),
	(3, '2022-01-26 16:20:54', '210e3600-59d6-490b-a14c-baf9e06fbafa', 6),
	(4, '2022-01-28 08:38:45', 'c78742a4-f53d-44f8-add2-e00a99cc438a', 6),
	(5, '2022-01-28 08:44:50', '2fc0557e-5e4f-4646-bd52-dd565a9c9cb4', 6);
/*!40000 ALTER TABLE `refreshtoken` ENABLE KEYS */;

-- Dumping structure for table user-management.roles
DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table user-management.roles: ~1 rows (approximately)
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`, `name`) VALUES
	(1, 'ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

-- Dumping structure for table user-management.tbl_product
DROP TABLE IF EXISTS `tbl_product`;
CREATE TABLE IF NOT EXISTS `tbl_product` (
  `product_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `product_name` varchar(50) NOT NULL,
  `quantity` int(10) unsigned NOT NULL DEFAULT '0',
  `size` varchar(3) NOT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table user-management.tbl_product: ~0 rows (approximately)
/*!40000 ALTER TABLE `tbl_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_product` ENABLE KEYS */;

-- Dumping structure for table user-management.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(100) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_name` (`user_name`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- Dumping data for table user-management.user: ~4 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `full_name`, `gender`, `user_name`, `email`, `password`, `status`) VALUES
	(1, ' rohit mehra', 'male', 'rohit', 'rohit@paxaris.com', '$2a$12$cL/wBTCT.f9BWdXzYUP3A.w/xi6HZKC/ugMNGZI54rQ5TfQnU4YxO', 'active'),
	(4, 'rohit Mehra', 'male', 'rohit1', 'rohit1@paxaris.com', '$2a$10$9ywDFHr2FabuqncbdXSgH.tv.Hd.9AkveXQ5qAyNyYoQnv3.IOfPm', 'ACTIVE'),
	(5, 'rohit mehra', 'male', 'rohit2', 'rohit2@paxaris.com', '$2a$10$l.0OZ2zNH9NbjByRQvtLMOr8EjamZQJxEpA.j7TWfM53Bi6gBmjhi', 'ACTIVE'),
	(6, 'rohit mehra', 'male', 'rohit3', 'rohit3@paxaris.com', '$2a$10$qYcaobhpJGLGFzJR4XHgf.CUS2j/kJ33nKoqxpHNOuVFGzsMn9Z0e', 'ACTIVE');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table user-management.user_roles
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE IF NOT EXISTS `user_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  UNIQUE KEY `user_id_role_id` (`user_id`,`role_id`),
  KEY `FK_user_roles_roles` (`role_id`),
  CONSTRAINT `FK_user_roles_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FK_user_roles_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table user-management.user_roles: ~4 rows (approximately)
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
	(1, 1),
	(4, 1),
	(5, 1),
	(6, 1);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
