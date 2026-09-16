/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : user-manage

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 11/09/2026 13:12:41
*/


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for certifications
-- ----------------------------
DROP TABLE IF EXISTS `certifications`;
CREATE TABLE `certifications`  (
                                   `certification_id` bigint NOT NULL AUTO_INCREMENT,
                                   `certification_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                   `certification_level` int NOT NULL,
                                   PRIMARY KEY (`certification_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for departments
-- ----------------------------
DROP TABLE IF EXISTS `departments`;
CREATE TABLE `departments`  (
                                `department_id` bigint NOT NULL AUTO_INCREMENT,
                                `department_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                PRIMARY KEY (`department_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for employees
-- ----------------------------
DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees`  (
                              `employee_id` bigint NOT NULL AUTO_INCREMENT,
                              `department_id` bigint NOT NULL,
                              `employee_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                              `employee_name_kana` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                              `employee_birth_date` date NULL DEFAULT NULL,
                              `employee_email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                              `employee_telephone` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                              `employee_login_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                              `employee_login_password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                              `employee_role` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0: user, 1: admin',
                              PRIMARY KEY (`employee_id`) USING BTREE,
                              UNIQUE INDEX `UK_employees_employee_login_id`(`employee_login_id` ASC) USING BTREE,
                              INDEX `FK_employees_departments`(`department_id` ASC) USING BTREE,
                              CONSTRAINT `FK_employees_departments` FOREIGN KEY (`department_id`) REFERENCES `departments` (`department_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 151 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for employees_certifications
-- ----------------------------
DROP TABLE IF EXISTS `employees_certifications`;
CREATE TABLE `employees_certifications`  (
                                             `employee_certification_id` bigint NOT NULL AUTO_INCREMENT,
                                             `employee_id` bigint NOT NULL,
                                             `certification_id` bigint NOT NULL,
                                             `start_date` date NOT NULL,
                                             `end_date` date NOT NULL,
                                             `score` decimal(4, 1) NULL DEFAULT NULL,
                                             PRIMARY KEY (`employee_certification_id`) USING BTREE,
                                             INDEX `FK_employees_certifications_employees`(`employee_id` ASC) USING BTREE,
                                             INDEX `FK_employees_certifications_certifications`(`certification_id` ASC) USING BTREE,
                                             CONSTRAINT `FK_employees_certifications_certifications` FOREIGN KEY (`certification_id`) REFERENCES `certifications` (`certification_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                             CONSTRAINT `FK_employees_certifications_employees` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 166 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for flyway_schema_history
-- ----------------------------
DROP TABLE IF EXISTS `flyway_schema_history`;
CREATE TABLE `flyway_schema_history`  (
                                          `installed_rank` int NOT NULL,
                                          `version` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                          `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                          `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                          `script` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                          `checksum` int NULL DEFAULT NULL,
                                          `installed_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                          `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                          `execution_time` int NOT NULL,
                                          `success` tinyint(1) NOT NULL,
                                          PRIMARY KEY (`installed_rank`) USING BTREE,
                                          INDEX `flyway_schema_history_s_idx`(`success` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

