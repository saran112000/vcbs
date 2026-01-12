/*
SQLyog Community v13.3.1 (64 bit)
MySQL - 8.4.4 : Database - vc_booking_db
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
  `booking_id` bigint NOT NULL AUTO_INCREMENT,
  `booking_date` date NOT NULL,
  `booking_by` bigint NOT NULL DEFAULT '0',
  `room_id` bigint NOT NULL DEFAULT '0',
  `guest_name` varchar(200) NOT NULL,
  `subject` varchar(500) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'ACTIVE',
  `created_by` bigint DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` varchar(100) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`booking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bookings` */

insert  into `bookings`(`booking_id`,`booking_date`,`booking_by`,`room_id`,`guest_name`,`subject`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) values 
(1,'2025-12-06',0,1,'ABC Corporation','Project Review Meeting','ACTIVE',NULL,NULL,NULL,NULL),
(6,'2025-12-04',0,1,'Cancelled Client','Cancelled Meeting','CANCELLED',NULL,NULL,NULL,NULL);

/*Table structure for table `bookings_slot_details` */

DROP TABLE IF EXISTS `bookings_slot_details`;

CREATE TABLE `bookings_slot_details` (
  `slot_id` bigint NOT NULL AUTO_INCREMENT,
  `booking_id` bigint NOT NULL,
  `slot_time` varchar(100) DEFAULT NULL,
  `cancelled_date` datetime DEFAULT NULL,
  `cancelled_by` bigint DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `is_active` int NOT NULL DEFAULT '1',
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` varchar(100) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`slot_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bookings_slot_details` */

/*Table structure for table `cancelled_bookings` */

DROP TABLE IF EXISTS `cancelled_bookings`;

CREATE TABLE `cancelled_bookings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `booking_id` bigint NOT NULL,
  `cancellation_date` date NOT NULL,
  `reason` varchar(1000) NOT NULL,
  `cancelled_by` bigint NOT NULL,
  `cancelled_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
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
  `division_id` bigint NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `is_active` int NOT NULL DEFAULT '1',
  `created_by` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`emp_id`),
  UNIQUE KEY `OffCode_Uk` (`emp_no`,`is_active`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

/*Data for the table `employee` */

insert  into `employee`(`emp_id`,`labcode`,`emp_no`,`emp_name`,`desig_id`,`extension_no`,`division_id`,`email`,`is_active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values 
(1,'CASDIC','EMP001','Administrator',2,'4354',1,'admin@gmail.com',1,'admin','2025-12-22 23:36:59',NULL,NULL),
(2,'CASDIC','EMP002','saran Raj',7,'1111',2,'saranraj@gmail.com',1,'admin','2025-12-22 23:36:59','admin','2026-01-08 01:40:37'),
(3,'CASDIC','EMP-033','Ramesh',7,'4354',1,'ramesh@gmail.com',0,'admin','2026-01-08 01:30:44','admin','2026-01-08 01:40:54');

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

/*Table structure for table `employee_division` */

DROP TABLE IF EXISTS `employee_division`;

CREATE TABLE `employee_division` (
  `division_id` bigint NOT NULL AUTO_INCREMENT,
  `labcode` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `division_code` varchar(25) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `division_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `division_head_id` bigint NOT NULL,
  `is_active` int NOT NULL DEFAULT '1',
  `created_by` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`division_id`),
  UNIQUE KEY `division_code` (`division_code`,`is_active`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `employee_division` */

insert  into `employee_division`(`division_id`,`labcode`,`division_code`,`division_name`,`division_head_id`,`is_active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values 
(1,'CASDIC','ADM','Admin Division',0,1,NULL,'2026-01-06 23:50:13',NULL,NULL),
(2,'CASDIC','ACT','Accounts Division',1,1,'admin','2026-01-07 00:21:59','admin','2026-01-07 00:22:16'),
(8,'CASDIC','MNC','MNC Division',2,1,'admin','2026-01-11 17:46:29',NULL,NULL);

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role_id` bigint NOT NULL DEFAULT '0',
  `emp_id` bigint NOT NULL DEFAULT '0',
  `is_active` int NOT NULL DEFAULT '1',
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` varchar(100) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`login_id`),
  UNIQUE KEY `username` (`user_name`,`is_active`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `login` */

insert  into `login`(`login_id`,`user_name`,`password`,`role_id`,`emp_id`,`is_active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values 
(1,'admin','$2a$10$h86fDOzkFzWg3wGtkDMY7esLJE2K/ZNAJCs4IpaKUxQtJXbxAwakG',1,1,1,'admin','2025-12-19 23:46:04',NULL,NULL),
(2,'saran','$2a$10$h86fDOzkFzWg3wGtkDMY7esLJE2K/ZNAJCs4IpaKUxQtJXbxAwakG',1,2,1,'admin','2025-12-19 23:46:04',NULL,NULL),
(9,'Hari','1234',1,1,0,'admin','2026-01-09 01:06:19','admin','2026-01-09 01:07:08');

/*Table structure for table `module` */

DROP TABLE IF EXISTS `module`;

CREATE TABLE `module` (
  `module_id` bigint NOT NULL AUTO_INCREMENT,
  `module_name` varchar(255) NOT NULL,
  `module_icon` varchar(255) DEFAULT NULL,
  `serial_no` int NOT NULL DEFAULT '0',
  `is_active` int NOT NULL DEFAULT '1',
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` varchar(100) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`module_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `module` */

insert  into `module`(`module_id`,`module_name`,`module_icon`,`serial_no`,`is_active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values 
(1,'Master','fas fa-database',0,1,'admin','2026-01-11 21:41:23',NULL,NULL),
(2,'Booking','fas fa-file-alt',0,1,'admin','2026-01-11 21:41:23',NULL,NULL),
(3,'Reports','fas fa-file-alt',0,1,'admin','2026-01-11 21:41:23',NULL,NULL);

/*Table structure for table `module_details` */

DROP TABLE IF EXISTS `module_details`;

CREATE TABLE `module_details` (
  `module_details_id` bigint NOT NULL AUTO_INCREMENT,
  `module_id` bigint NOT NULL,
  `module_details_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `module_details_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `serial_no` int NOT NULL,
  `is_active` int NOT NULL DEFAULT '1',
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` varchar(100) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`module_details_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `module_details` */

insert  into `module_details`(`module_details_id`,`module_id`,`module_details_name`,`module_details_url`,`serial_no`,`is_active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values 
(1,1,'User Manager','login/list',1,1,'admin','2026-01-11 21:44:27',NULL,NULL),
(2,1,'Division','/division/list',2,1,'admin','2026-01-11 21:44:27',NULL,NULL),
(3,1,'Employee','/employee/list',3,1,'admin','2026-01-11 21:44:27',NULL,NULL),
(4,1,'Rooms','/rooms/list',4,1,'admin','2026-01-11 21:44:27',NULL,NULL),
(5,2,'Rooms Booking','/rooms/list',1,1,'admin','2026-01-11 21:44:27',NULL,NULL),
(6,3,'Bookings Report','/rooms/list',1,1,'admin','2026-01-11 21:44:27',NULL,NULL),
(7,3,'cancelled Report','/rooms/list',2,1,'admin','2026-01-11 21:44:27',NULL,NULL),
(8,1,'Role Access','/role-access',4,1,'admin','2026-01-11 21:44:27',NULL,NULL);

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

/*Table structure for table `role_security_access` */

DROP TABLE IF EXISTS `role_security_access`;

CREATE TABLE `role_security_access` (
  `role_security_access_id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL,
  `module_details_id` bigint NOT NULL,
  `room_type_id` bigint NOT NULL,
  `is_active` int NOT NULL DEFAULT '1',
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` varchar(100) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`role_security_access_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `role_security_access` */

/*Table structure for table `room_details` */

DROP TABLE IF EXISTS `room_details`;

CREATE TABLE `room_details` (
  `room_id` bigint NOT NULL AUTO_INCREMENT,
  `room_no` varchar(20) DEFAULT NULL,
  `room_type_id` bigint NOT NULL,
  `is_active` int NOT NULL DEFAULT '1',
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` varchar(100) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `room_details` */

insert  into `room_details`(`room_id`,`room_no`,`room_type_id`,`is_active`,`created_by`,`created_date`,`modified_by`,`modified_date`) values 
(1,'2',1,1,'admin','2026-01-08 00:28:38',NULL,NULL),
(2,'1005',1,1,NULL,NULL,NULL,NULL),
(3,'1004',2,1,NULL,NULL,NULL,NULL),
(4,'100',2,1,NULL,NULL,NULL,NULL);

/*Table structure for table `room_type` */

DROP TABLE IF EXISTS `room_type`;

CREATE TABLE `room_type` (
  `room_type_id` bigint NOT NULL AUTO_INCREMENT,
  `room_type` varchar(150) DEFAULT NULL,
  `is_active` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`room_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `room_type` */

insert  into `room_type`(`room_type_id`,`room_type`,`is_active`) values 
(1,'DRONA',1),
(2,'INTERNET',1);

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
