/*
SQLyog Community v13.3.0 (64 bit)
MySQL - 8.0.42 : Database - vc_booking_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`vc_booking_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `vc_booking_db`;

/*Table structure for table `bookings` */

DROP TABLE IF EXISTS `bookings`;

CREATE TABLE `bookings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `booking_date` date NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `room_number` varchar(20) NOT NULL,
  `guest_name` varchar(200) NOT NULL,
  `subject` varchar(500) NOT NULL,
  `user_id` bigint NOT NULL,
  `officer_name` varchar(200) NOT NULL,
  `internal_no` varchar(50) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'ACTIVE',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_bookings_date` (`booking_date`),
  KEY `idx_bookings_room_date` (`room_number`,`booking_date`),
  KEY `idx_bookings_user` (`user_id`),
  KEY `idx_bookings_status` (`status`),
  KEY `idx_bookings_date_status` (`booking_date`,`status`),
  CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `login` (`login_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bookings` */

insert  into `bookings`(`id`,`booking_date`,`start_time`,`end_time`,`room_number`,`guest_name`,`subject`,`user_id`,`officer_name`,`internal_no`,`status`,`created_at`,`updated_at`) values 
(1,'2025-12-06','09:00:00','10:00:00','311','ABC Corporation','Project Review Meeting',1,'System Administrator','1000','ACTIVE','2025-12-06 16:03:44','2025-12-06 16:03:44'),
(6,'2025-12-04','15:00:00','16:00:00','311','Cancelled Client','Cancelled Meeting',1,'System Administrator','1000','CANCELLED','2025-12-06 16:03:44','2025-12-06 16:03:44');

/*Table structure for table `cancelled_bookings` */

DROP TABLE IF EXISTS `cancelled_bookings`;

CREATE TABLE `cancelled_bookings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `booking_id` bigint NOT NULL,
  `cancellation_date` date NOT NULL,
  `reason` varchar(1000) NOT NULL,
  `cancelled_by` bigint NOT NULL,
  `cancelled_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_cancelled_date` (`cancellation_date`),
  KEY `idx_cancelled_by_user` (`cancelled_by`),
  KEY `idx_cancelled_booking` (`booking_id`),
  CONSTRAINT `cancelled_bookings_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`) ON DELETE CASCADE,
  CONSTRAINT `cancelled_bookings_ibfk_2` FOREIGN KEY (`cancelled_by`) REFERENCES `login` (`login_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `cancelled_bookings` */

insert  into `cancelled_bookings`(`id`,`booking_id`,`cancellation_date`,`reason`,`cancelled_by`,`cancelled_at`) values 
(1,6,'2025-12-05','Client postponed the meeting',1,'2025-12-06 16:03:44');

/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `emp_id` bigint NOT NULL AUTO_INCREMENT,
  `labcode` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `emp_no` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `emp_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `desig_id` bigint NOT NULL,
  `extension_no` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `group_id` bigint NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `is_active` int NOT NULL DEFAULT '1',
  `created_by` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`emp_id`),
  UNIQUE KEY `OffCode_Uk` (`emp_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

/*Data for the table `employee` */

insert  into `employee`(`emp_id`,`labcode`,`emp_no`,`emp_name`,`desig_id`,`extension_no`,`group_id`,`email`,`is_active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values 
(1,'CASDIC','EMP001','saran',2,'4354',1,'saran@gmail.com',1,'admin','2025-12-22 23:36:59',NULL,NULL);

/*Table structure for table `employee_desig` */

DROP TABLE IF EXISTS `employee_desig`;

CREATE TABLE `employee_desig` (
  `desig_id` bigint NOT NULL AUTO_INCREMENT,
  `desig_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `designation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`desig_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;

/*Data for the table `employee_desig` */

insert  into `employee_desig`(`desig_id`,`desig_code`,`designation`) values 
(1,'DIR','Director-1'),
(2,'SCG','Sc G'),
(3,'SCF','Sc F'),
(4,'SCE','Sc E'),
(5,'SCD','Sc D'),
(6,'SCC','Sc C'),
(7,'SCB','Sc B'),
(8,'TOD','TO D'),
(9,'TOC','TO C'),
(10,'TOB','TO B'),
(11,'TOA','TO A');

/*Table structure for table `employee_group` */

DROP TABLE IF EXISTS `employee_group`;

CREATE TABLE `employee_group` (
  `group_id` bigint NOT NULL AUTO_INCREMENT,
  `labcode` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `group_code` varchar(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `group_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `group_head_id` bigint NOT NULL,
  `is_active` int NOT NULL DEFAULT '1',
  `created_by` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `employee_group` */

insert  into `employee_group`(`group_id`,`labcode`,`group_code`,`group_name`,`group_head_id`,`is_active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values 
(1,'CASDIC','GRP','Group 1',1,1,'admin','2025-12-22 23:38:06',NULL,NULL);

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role_id` varchar(255) DEFAULT NULL,
  `emp_id` bigint NOT NULL,
  `is_active` int NOT NULL DEFAULT '1',
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` varchar(100) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`login_id`),
  UNIQUE KEY `user_id` (`user_name`),
  UNIQUE KEY `username` (`password`),
  KEY `idx_users_username` (`password`),
  KEY `idx_users_user_id` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `login` */

insert  into `login`(`login_id`,`user_name`,`password`,`role_id`,`emp_id`,`is_active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values 
(1,'admin','$2a$10$h86fDOzkFzWg3wGtkDMY7esLJE2K/ZNAJCs4IpaKUxQtJXbxAwakG','1',1,1,'admin','2025-12-19 23:46:04',NULL,NULL);

/*Table structure for table `role_security` */

DROP TABLE IF EXISTS `role_security`;

CREATE TABLE `role_security` (
  `role_id` bigint NOT NULL,
  `role_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `role_security` */

insert  into `role_security`(`role_id`,`role_name`) values 
(1,'ROLE_USER'),
(2,'ROLE_ADMIN');

/* Trigger structure for table `bookings` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `validate_booking_time` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `validate_booking_time` BEFORE INSERT ON `bookings` FOR EACH ROW BEGIN
    IF NEW.end_time <= NEW.start_time THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'End time must be after start time';
    END IF;
END */$$


DELIMITER ;

/* Trigger structure for table `bookings` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `prevent_past_booking` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `prevent_past_booking` BEFORE INSERT ON `bookings` FOR EACH ROW BEGIN
    IF NEW.booking_date < CURDATE() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot book for past dates';
    END IF;
END */$$


DELIMITER ;

/* Procedure structure for procedure `CancelBookingProcedure` */

/*!50003 DROP PROCEDURE IF EXISTS  `CancelBookingProcedure` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `CancelBookingProcedure`(
    IN p_booking_id BIGINT,
    IN p_reason VARCHAR(1000),
    IN p_cancelled_by_userid VARCHAR(50)
)
BEGIN
    DECLARE v_cancelled_by_id BIGINT;
    
    -- Get user ID
    SELECT id INTO v_cancelled_by_id FROM users WHERE user_id = p_cancelled_by_userid;
    
    -- Update booking status
    UPDATE bookings 
    SET status = 'CANCELLED', 
        updated_at = CURRENT_TIMESTAMP
    WHERE id = p_booking_id;
    
    -- Insert cancellation record
    INSERT INTO cancelled_bookings (booking_id, cancellation_date, reason, cancelled_by)
    VALUES (p_booking_id, CURDATE(), p_reason, v_cancelled_by_id);
    
    SELECT 'Booking cancelled successfully' as message;
END */$$
DELIMITER ;

/* Procedure structure for procedure `CheckRoomAvailability` */

/*!50003 DROP PROCEDURE IF EXISTS  `CheckRoomAvailability` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `CheckRoomAvailability`(
    IN p_room_number VARCHAR(20),
    IN p_booking_date DATE,
    IN p_start_time TIME,
    IN p_end_time TIME
)
BEGIN
    SELECT 
        b.*,
        u.username as booked_by_username,
        u.officer_name as booked_by_officer
    FROM bookings b
    JOIN users u ON b.user_id = u.id
    WHERE b.room_number = p_room_number
        AND b.booking_date = p_booking_date
        AND b.status = 'ACTIVE'
        AND (
            (b.start_time < p_end_time AND b.end_time > p_start_time)
        )
    ORDER BY b.start_time;
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
