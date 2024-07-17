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


-- Dumping database structure for tenant-management
DROP DATABASE IF EXISTS `tenant-management`;
CREATE DATABASE IF NOT EXISTS `tenant-management` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `tenant-management`;

-- Dumping structure for table tenant-management.tenant
DROP TABLE IF EXISTS `tenant`;
CREATE TABLE IF NOT EXISTS `tenant` (
  `tenant_client_id` int(10) unsigned NOT NULL,
  `db_name` varchar(50) NOT NULL,
  `url` varchar(100) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `driver_class` varchar(100) NOT NULL,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`tenant_client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table tenant-management.tenant: ~0 rows (approximately)
/*!40000 ALTER TABLE `tenant` DISABLE KEYS */;
INSERT INTO `tenant` (`tenant_client_id`, `db_name`, `url`, `user_name`, `password`, `driver_class`, `status`) VALUES
	(100, 'user-management', 'jdbc:mysql://user-mysql:3306/user-management?useSSL=false', 'root', 'root', 'com.mysql.cj.jdbc.Driver', 'active');
/*!40000 ALTER TABLE `tenant` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
