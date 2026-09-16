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

 Date: 11/09/2026 13:09:54
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
-- Records of certifications
-- ----------------------------
INSERT INTO `certifications` VALUES (1, 'Trình độ tiếng nhật cấp 1', 1);
INSERT INTO `certifications` VALUES (2, 'Trình độ tiếng nhật cấp 2', 2);
INSERT INTO `certifications` VALUES (3, 'Trình độ tiếng nhật cấp 3', 3);
INSERT INTO `certifications` VALUES (4, 'Trình độ tiếng nhật cấp 4', 4);
INSERT INTO `certifications` VALUES (5, 'Trình độ tiếng nhật cấp 5', 5);

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
-- Records of departments
-- ----------------------------
INSERT INTO `departments` VALUES (1, 'DEV1');
INSERT INTO `departments` VALUES (2, 'DEV2');
INSERT INTO `departments` VALUES (3, 'DEV3');
INSERT INTO `departments` VALUES (4, 'DEV4');
INSERT INTO `departments` VALUES (5, 'DEV5');

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
-- Records of employees
-- ----------------------------
INSERT INTO `employees` VALUES (1, 1, 'Administrator', NULL, NULL, 'la@luvina.net', NULL, 'admin', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 1);
INSERT INTO `employees` VALUES (2, 1, 'Nguyễn Thị Mai Hương', 'グエン ティ マイ フオン', '1983-07-08', 'ntmhuong@luvina.net', '0914326386', 'huongntm', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (3, 1, 'Lê Thị Xoa', 'レ ティ ソア', '1983-07-08', 'xoalt@luvina.net', '1234567894', 'xoalt', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (4, 3, 'Đặng Thị Hân', 'ダン ティ ハン', '1983-07-08', 'handt@luvina.net', '0914326386', 'handt', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (5, 1, 'Lê Nghiêm Thủy', 'レ ギエム トゥイ', '1983-07-08', 'thuyln@luvina.net', '1234567894', 'thuyln', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (6, 1, 'Lê Phương Anh', 'レ フオン アイン', '1983-07-08', 'anhlp@luvina.net', '1234567894', 'anhlp', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (7, 2, 'Trần Văn Bình', 'チャン ヴァン ビン', '1990-02-14', 'binhtv@luvina.net', '0901234567', 'binhtv', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (8, 2, 'Hoàng Thị Dung', 'ホアン ティ ズン', '1995-11-20', 'dunghth@luvina.net', '0988776655', 'dunghth', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (9, 3, 'Phạm Minh Đức', 'ファム ミン ドゥック', '1992-05-18', 'ducpm@luvina.net', '0977112233', 'ducpm', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (10, 1, 'Vũ Hải Yến', 'ヴー ハイ イエン', '1997-09-09', 'yenvh@luvina.net', '0933445566', 'yenvh', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (11, 2, 'Đỗ Quốc Tuấn', 'ドー クオック トゥアン', '1991-12-25', 'tuandq@luvina.net', '0966554433', 'tuandq', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (12, 1, 'Nguyễn Văn An', 'グエン ヴァン アン', '1993-01-15', 'anhv@luvina.net', '0912345012', 'anhv', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (13, 2, 'Trần Thị Mai', 'チャン ティ マイ', '1994-03-22', 'bichpt@luvina.net', '0912345013', 'bichpt', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (14, 3, 'Lê Hoàng Long', 'レ ホアン ロン', '1990-08-10', 'cuongbv@luvina.net', '0912345014', 'cuongbv', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (15, 1, 'Đặng Hải Đăng', 'ダン ハイ ダン', '1995-12-05', 'dangdh@luvina.net', '0912345015', 'dangdh', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (16, 2, 'Trịnh Thu Giang', 'チン トゥ ジアン', '1996-04-18', 'giangtt@luvina.net', '0912345016', 'giangtt', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (17, 3, 'Ngô Văn Hải', 'ゴー ヴァン ハイ', '1988-09-30', 'hainv@luvina.net', '0912345017', 'hainv', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (18, 1, 'Nguyễn Văn An', 'グエン ヴァン アン', '1997-02-14', 'hanhdt@luvina.net', '0912345018', 'hanhdt', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (19, 2, 'Trần Thị Mai', 'チャン ティ マイ', '1992-11-28', 'hungvq@luvina.net', '0912345019', 'hungvq', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (20, 3, 'Lê Hoàng Long', 'レ ホアン ロン', '1998-06-03', 'huyenlk@luvina.net', '0912345020', 'huyenlk', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (21, 1, 'Mai Tuấn Kiên', 'マイ トゥアン キエン', '1991-05-19', 'kienmt@luvina.net', '0912345021', 'kienmt', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (22, 2, 'Chu Thùy Linh', 'チュー トゥイ リン', '1999-10-12', 'linhct@luvina.net', '0912345022', 'linhct', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (23, 3, 'Hà Quang Long', 'ハー クアン ロン', '1989-07-25', 'longhq@luvina.net', '0912345023', 'longhq', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (24, 1, 'Nguyễn Văn An', 'グエン ヴァン アン', '1996-01-08', 'minhtt@luvina.net', '0912345024', 'minhtt', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (25, 2, 'Trần Thị Mai', 'チャン ティ マイ', '1993-09-17', 'namdt@luvina.net', '0912345025', 'namdt', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (26, 3, 'Lê Hoàng Long', 'レ ホアン ロン', '1995-03-31', 'ngact@luvina.net', '0912345026', 'ngact', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (27, 1, 'Lâm Hoàng Phúc', 'ラム ホアン フック', '1990-12-20', 'phuclh@luvina.net', '0912345027', 'phuclh', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (28, 2, 'Hồ Thị Quỳnh', 'ホー ティ クイン', '1997-08-14', 'quynhht@luvina.net', '0912345028', 'quynhht', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (29, 3, 'Đoàn Thái Sơn', 'ドアン タイ ソン', '1992-04-05', 'sondt@luvina.net', '0912345029', 'sondt', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (30, 1, 'Nguyễn Văn An', 'グエン ヴァン アン', '1994-11-11', 'tamlm@luvina.net', '0912345030', 'tamlm', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (31, 2, 'Trần Thị Mai', 'チャン ティ マイ', '1987-06-24', 'thangtd@luvina.net', '0912345031', 'thangtd', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (32, 3, 'Lê Hoàng Long', 'レ ホアン ロン', '1998-02-17', 'thaonp@luvina.net', '0912345032', 'thaonp', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (33, 1, 'Huỳnh Ngọc Thịnh', 'フイン ゴック ティン', '1993-10-09', 'thinhhn@luvina.net', '0912345033', 'thinhhn', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (34, 2, 'Trương Cẩm Tú', 'チュオン カム トゥー', '1996-05-23', 'tutc@luvina.net', '0912345034', 'tutc', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (35, 3, 'Võ Hoàng Việt', 'ボー ホアン ヴィエット', '1991-08-15', 'vietvh@luvina.net', '0912345035', 'vietvh', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (36, 1, 'Nguyễn Văn An', 'グエン ヴァン アン', '1999-12-01', 'uyenbm@luvina.net', '0912345036', 'uyenbm', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (37, 2, 'Quách Tuấn Anh', 'クアック トゥアン アイン', '1995-07-19', 'anhqt@luvina.net', '0912345037', 'anhqt', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (38, 3, 'Tiêu Hải Bình', 'ティエウ ハイ ビン', '1990-03-04', 'binhth@luvina.net', '0912345038', 'binhth', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (39, 1, 'Nông Thị Cúc', 'ノン ティ クック', '1997-09-27', 'cucnt@luvina.net', '0912345039', 'cucnt', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (40, 2, 'Triệu Quốc Dũng', 'チエウ クオック ズン', '1992-01-30', 'dungtq@luvina.net', '0912345040', 'dungtq', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (41, 3, 'Khổng Thị Hoa', 'コン ティ ホア', '1994-06-16', 'hoakt@luvina.net', '0912345041', 'hoakt', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (42, 1, 'Mạc Đình Khoa', 'マク ディン コア', '1988-10-02', 'khoamd@luvina.net', '0912345042', 'khoamd', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (43, 2, 'Giáp Thị Lan', 'ザップ ティ ラン', '1996-12-14', 'langt@luvina.net', '0912345043', 'langt', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (44, 3, 'Uông Thế Long', 'ウオン テー ロン', '1993-04-26', 'longut@luvina.net', '0912345044', 'longut', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (45, 1, 'La Thị Mơ', 'ラー ティ モー', '1998-08-08', 'molt@luvina.net', '0912345045', 'molt', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (46, 2, 'Sầm Đức Nam', 'サム ドゥック ナム', '1991-02-21', 'namsd@luvina.net', '0912345046', 'namsd', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (47, 3, 'Ninh Thị Nhung', 'ニン ティ ニュン', '1995-11-03', 'nhungnt@luvina.net', '0912345047', 'nhungnt', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (48, 1, 'Ôn Gia Phú', 'オン ザー フー', '1990-07-17', 'phuog@luvina.net', '0912345048', 'phuog', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (49, 2, 'Thiều Thị Sen', 'ティエウ ティ セン', '1997-05-29', 'sentt@luvina.net', '0912345049', 'sentt', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (50, 3, 'Đới Văn Thành', 'ドイ ヴァン タイン', '1992-10-10', 'thanhvd@luvina.net', '0912345050', 'thanhvd', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (51, 1, 'Vi Thị Xuân', 'ヴィー ティ スアン', '1999-03-05', 'xuanvt@luvina.net', '0912345051', 'xuanvt', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (52, 4, 'Nguyễn An Bình', 'グエン アン ビン', '1992-01-10', 'binhna52@luvina.net', '0912345052', 'binhna52', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (53, 4, 'Trần Bảo Châu', 'チャン バオ チャウ', '1993-02-15', 'chautb53@luvina.net', '0912345053', 'chautb53', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (54, 4, 'Lê Công Danh', 'レ コン ダン', '1991-03-20', 'danhlc54@luvina.net', '0912345054', 'danhlc54', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (55, 4, 'Phạm Đức Dũng', 'ファム ドゥック ズン', '1994-04-25', 'dungpd55@luvina.net', '0912345055', 'dungpd55', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (56, 4, 'Hoàng Gia Hân', 'ホアン ザー ハン', '1995-05-30', 'hanhg56@luvina.net', '0912345056', 'hanhg56', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (57, 4, 'Vũ Hải Đăng', 'ヴー ハイ ダン', '1990-06-05', 'dangvh57@luvina.net', '0912345057', 'dangvh57', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (58, 4, 'Đặng Hữu Phước', 'ダン フウ フオック', '1996-07-10', 'phuocdh58@luvina.net', '0912345058', 'phuocdh58', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (59, 4, 'Bùi Khánh Linh', 'ブイ カイン リン', '1997-08-15', 'linhbk59@luvina.net', '0912345059', 'linhbk59', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (60, 4, 'Đỗ Mạnh Hùng', 'ドー マイン フン', '1989-09-20', 'hungdm60@luvina.net', '0912345060', 'hungdm60', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (61, 4, 'Hồ Minh Quân', 'ホー ミン クアン', '1998-10-25', 'quanhm61@luvina.net', '0912345061', 'quanhm61', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (62, 4, 'Ngô Ngọc Ánh', 'ゴー ゴック アイン', '1993-11-30', 'anhnn62@luvina.net', '0912345062', 'anhnn62', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (63, 4, 'Dương Phúc Thịnh', 'ズオン フック ティン', '1992-12-05', 'thinhdp63@luvina.net', '0912345063', 'thinhdp63', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (64, 4, 'Lý Quang Vinh', 'リー クアン ヴィン', '1994-01-15', 'vinhlq64@luvina.net', '0912345064', 'vinhlq64', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (65, 4, 'Mai Quỳnh Chi', 'マイ クイン チー', '1995-02-20', 'chimq65@luvina.net', '0912345065', 'chimq65', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (66, 4, 'Trịnh Quốc Bảo', 'チン クオック バオ', '1990-03-25', 'baotq66@luvina.net', '0912345066', 'baotq66', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (67, 4, 'Đoàn Sơn Tùng', 'ドアン ソン トゥン', '1996-04-30', 'tungds67@luvina.net', '0912345067', 'tungds67', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (68, 4, 'Lương Thanh Hà', 'ルオン タイン ハー', '1997-05-05', 'halt68@luvina.net', '0912345068', 'halt68', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (69, 4, 'Cao Tiến Đạt', 'カオ ティエン ダット', '1988-06-10', 'datct69@luvina.net', '0912345069', 'datct69', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (70, 4, 'Phan Trọng Hiếu', 'ファン チョン ヒエウ', '1999-07-15', 'hieupt70@luvina.net', '0912345070', 'hieupt70', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (71, 4, 'Chu Tuyết Mai', 'チュー トゥエット マイ', '1991-08-20', 'maict71@luvina.net', '0912345071', 'maict71', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (72, 4, 'Hà Tuấn Khang', 'ハー トゥアン カン', '1993-09-25', 'khanght72@luvina.net', '0912345072', 'khanght72', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (73, 4, 'Tạ Văn Kiên', 'ター ヴァン キエン', '1992-10-30', 'kientv73@luvina.net', '0912345073', 'kientv73', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (74, 4, 'Võ Xuân Bắc', 'ボー スアン バック', '1994-11-05', 'bacvx74@luvina.net', '0912345074', 'bacvx74', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (75, 4, 'Đinh Yến Nhi', 'ディン イエン ニー', '1995-12-10', 'nhidy75@luvina.net', '0912345075', 'nhidy75', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (76, 4, 'Bạch An Nhiên', 'バク アン ニエン', '1998-01-15', 'nhienba76@luvina.net', '0912345076', 'nhienba76', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (77, 4, 'Lâm Bình An', 'ラム ビン アン', '1990-02-20', 'anlb77@luvina.net', '0912345077', 'anlb77', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (78, 4, 'Huỳnh Công Thành', 'フイン コン タイン', '1996-03-25', 'thanhhc78@luvina.net', '0912345078', 'thanhhc78', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (79, 4, 'Trương Duy Khánh', 'チュオン ズイ カイン', '1997-04-30', 'khanhtd79@luvina.net', '0912345079', 'khanhtd79', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (80, 4, 'Quách Gia Bảo', 'クアック ザー バオ', '1987-05-05', 'baoqg80@luvina.net', '0912345080', 'baoqg80', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (81, 5, 'Nghiêm Hữu Tài', 'ギエム フウ タイ', '1993-06-10', 'tainh81@luvina.net', '0912345081', 'tainh81', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (82, 5, 'Tiêu Hồng Nhung', 'ティエウ ホン ニュン', '1995-07-15', 'nhungth82@luvina.net', '0912345082', 'nhungth82', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (83, 5, 'Nông Khắc Huy', 'ノン カック フイ', '1991-08-20', 'huynk83@luvina.net', '0912345083', 'huynk83', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (84, 5, 'Triệu Lan Hương', 'チエウ ラン フオン', '1994-09-25', 'huongtl84@luvina.net', '0912345084', 'huongtl84', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (85, 5, 'Khổng Minh Trí', 'コン ミン チー', '1992-10-30', 'trikm85@luvina.net', '0912345085', 'trikm85', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (86, 5, 'Mạc Như Quỳnh', 'マク ニュー クイン', '1996-11-05', 'quynhnm86@luvina.net', '0912345086', 'quynhnm86', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (87, 5, 'Giáp Phi Long', 'ザップ フィー ロン', '1989-12-10', 'longgp87@luvina.net', '0912345087', 'longgp87', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (88, 5, 'Uông Quốc Trung', 'ウオン クオック チュン', '1998-01-15', 'trunguq88@luvina.net', '0912345088', 'trunguq88', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (89, 5, 'La Sơn Hải', 'ラー ソン ハイ', '1993-02-20', 'hails89@luvina.net', '0912345089', 'hails89', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (91, 5, 'Ninh Trí Dũng', 'ニン チー ズン', '1990-04-30', 'dungnt91@luvina.net', '0912345091', 'dungnt91', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (92, 5, 'Ôn Tuấn Kiệt', 'オン トゥアン キエット', '1997-05-05', 'kietot92@luvina.net', '0912345092', 'kietot92', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (93, 5, 'Thiều Văn Nam', 'ティエウ ヴァン ナム', '1991-06-10', 'namtv93@luvina.net', '0912345093', 'namtv93', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (94, 5, 'Đới Xuân Hòa', 'ドイ スアン ホア', '1994-07-15', 'hoadx94@luvina.net', '0912345094', 'hoadx94', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (95, 5, 'Vi Ý Nhi', 'ヴィー イー ニー', '1996-08-20', 'nhivy95@luvina.net', '0912345095', 'nhivy95', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (96, 5, 'Ân Bá Cường', 'アン バー クオン', '1988-09-25', 'cuongab96@luvina.net', '0912345096', 'cuongab96', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (97, 5, 'Cù Cẩm Tú', 'クー カム トゥー', '1999-10-30', 'tucc97@luvina.net', '0912345097', 'tucc97', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (98, 5, 'Dư Đăng Quang', 'ズー ダン クアン', '1992-11-05', 'quangdd98@luvina.net', '0912345098', 'quangdd98', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (99, 5, 'Giản Đức Toàn', 'ザン ドゥック トアン', '1993-12-10', 'toangd99@luvina.net', '0912345099', 'toangd99', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (100, 5, 'Hạ Gia Huy', 'ハー ザー フイ', '1995-01-15', 'huyhg100@luvina.net', '0912345100', 'huyhg100', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (101, 5, 'Khâu Hải Yến', 'カウ ハイ イエン', '1997-02-20', 'yenkh101@luvina.net', '0912345101', 'yenkh101', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (102, 5, 'Liêu Hùng Cường', 'リエウ フン クオン', '1990-03-25', 'cuonglh102@luvina.net', '0912345102', 'cuonglh102', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (103, 5, 'Mã Kim Ngân', 'マー キム ガン', '1996-04-30', 'nganmk103@luvina.net', '0912345103', 'nganmk103', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (104, 5, 'Ngụy Minh Đức', 'グイ ミン ドゥック', '1991-05-05', 'ducnm104@luvina.net', '0912345104', 'ducnm104', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (105, 5, 'Nhâm Ngọc Bích', 'ニャム ゴック ビック', '1994-06-10', 'bichnn105@luvina.net', '0912345105', 'bichnn105', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (106, 5, 'Phù Phương Oanh', 'フー フオン オアン', '1998-07-15', 'oanhpp106@luvina.net', '0912345106', 'oanhpp106', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (107, 5, 'Quản Quốc Tuấn', 'クアン クオック トゥアン', '1993-08-20', 'tuanqq107@luvina.net', '0912345107', 'tuanqq107', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (108, 5, 'Sử Sơn Trà', 'スー ソン チャー', '1995-09-25', 'trass108@luvina.net', '0912345108', 'trass108', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (109, 5, 'Tống Thái Bình', 'トン タイ ビン', '1989-10-30', 'binhtt109@luvina.net', '0912345109', 'binhtt109', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (110, 5, 'Ung Thu Trang', 'ウン トゥー チャン', '1997-11-05', 'trangut110@luvina.net', '0912345110', 'trangut110', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (111, 5, 'Văn Tiến Thành', 'ヴァン ティエン タイン', '1992-12-10', 'thanhvt111@luvina.net', '0912345111', 'thanhvt111', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (112, 5, 'Bạch Trọng Nghĩa', 'バク チョン ギア', '1994-01-15', 'nghiabt112@luvina.net', '0912345112', 'nghiabt112', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (113, 5, 'Cung Tuyết Trinh', 'クン トゥエット チン', '1996-02-20', 'trinhct113@luvina.net', '0912345113', 'trinhct113', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (114, 5, 'Diệp Văn Tuấn', 'ジエップ ヴァン トゥアン', '1990-03-25', 'tuandv114@luvina.net', '0912345114', 'tuandv114', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (115, 5, 'Hạ Xuân Trường', 'ハー スアン チュオン', '1998-04-30', 'truonghx115@luvina.net', '0912345115', 'truonghx115', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (116, 5, 'Kha Ánh Nguyệt', 'カー アイン グエット', '1993-05-05', 'nguyetka116@luvina.net', '0912345116', 'nguyetka116', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (117, 5, 'Lục Bảo Nam', 'ルック バオ ナム', '1991-06-10', 'namlb117@luvina.net', '0912345117', 'namlb117', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (118, 5, 'Mục Cát Tường', 'ムック カッ トゥオン', '1995-07-15', 'tuongmc118@luvina.net', '0912345118', 'tuongmc118', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (119, 5, 'Nhan Diệu Linh', 'ニャン ジエウ リン', '1997-08-20', 'linhnd119@luvina.net', '0912345119', 'linhnd119', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (120, 5, 'Ông Gia Lạc', 'オン ザー ラック', '1987-09-25', 'lacog120@luvina.net', '0912345120', 'lacog120', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (121, 5, 'Phó Hoàng Long', 'フォー ホアン ロン', '1999-10-30', 'longph121@luvina.net', '0912345121', 'longph121', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (122, 5, 'Quyền Hữu Trí', 'クエン フウ チー', '1992-11-05', 'triqh122@luvina.net', '0912345122', 'triqh122', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (123, 5, 'Sái Khải Hoàn', 'サイ カイ ホアン', '1994-12-10', 'hoansk123@luvina.net', '0912345123', 'hoansk123', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (124, 5, 'Thạch Lam Vy', 'タック ラム ヴィー', '1996-01-15', 'vytl124@luvina.net', '0912345124', 'vytl124', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (125, 5, 'Viên Minh Khôi', 'ヴィエン ミン コイ', '1990-02-20', 'khoivm125@luvina.net', '0912345125', 'khoivm125', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (126, 5, 'Ấu Như Ý', 'アウ ニュー イー', '1998-03-25', 'yian126@luvina.net', '0912345126', 'yian126', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (128, 5, 'Cổ Quốc Thái', 'コー クオック タイ', '1995-05-05', 'thaicq128@luvina.net', '0912345128', 'thaicq128', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (129, 5, 'Doãn Sơn Nam', 'ドアン ソン ナム', '1988-06-10', 'namds129@luvina.net', '0912345129', 'namds129', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (130, 5, 'Hầu Thanh Liêm', 'ハウ タイン リエム', '1997-07-15', 'liemht130@luvina.net', '0912345130', 'liemht130', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (131, 5, 'Kỷ Thảo Tiên', 'キー タオ ティエン', '1991-08-20', 'tienkt131@luvina.net', '0912345131', 'tienkt131', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (132, 5, 'Lưu Trọng Tấn', 'ルー チョン タン', '1994-09-25', 'tanlt132@luvina.net', '0912345132', 'tanlt132', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (133, 5, 'Mai Tuệ Mẫn', 'マイ トゥエ マン', '1996-10-30', 'manmt133@luvina.net', '0912345133', 'manmt133', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (134, 5, 'Nghiêm Vĩnh Phúc', 'ギエム ヴィン フック', '1990-11-05', 'phucnv134@luvina.net', '0912345134', 'phucnv134', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (135, 5, 'Phan Uyển Nhi', 'ファン ウイエン ニー', '1998-12-10', 'nhipu135@luvina.net', '0912345135', 'nhipu135', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (136, 5, 'Quách Xuân Vinh', 'クアック スアン ヴィン', '1992-01-15', 'vinhqx136@luvina.net', '0912345136', 'vinhqx136', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (137, 5, 'Thẩm Yến Trang', 'タム イエン チャン', '1995-02-20', 'trangty137@luvina.net', '0912345137', 'trangty137', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (138, 5, 'Trương An Khang', 'チュオン アン カン', '1989-03-25', 'khangta138@luvina.net', '0912345138', 'khangta138', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (139, 5, 'Vương Bá Đạt', 'ヴオン バー ダット', '1997-04-30', 'datvb139@luvina.net', '0912345139', 'datvb139', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (140, 5, 'Xầm Cẩm Ly', 'サム カム リー', '1993-05-05', 'lyxc140@luvina.net', '0912345140', 'lyxc140', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (141, 5, 'Yên Đăng Khoa', 'イエン ダン コア', '1991-06-10', 'khoayd141@luvina.net', '0912345141', 'khoayd141', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (142, 1, 'Nguyễn Thị Mai Phương Anh', 'グエン ティ マイ フオン アイン', '1995-05-15', 'anhntmp@luvina.net', '0912345678', 'anhntmp', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (143, 1, 'Pham Thi Thanh Nga', 'グループ', '2002-02-02', 'ngantt@luvina.net', '778520123', 'ngaptt265', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (144, 2, 'QuỳnhNga/', 'グループ', '2002-02-02', 'nga@luvina.net', '778520123', 'ngaptt266', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (145, 3, 'Quỳnh%Nga', 'グループ', '2002-02-02', 'nga@luvina.net', '778520123', 'ngaptt267', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (146, 3, 'Quỳnh_Nga', 'グループ', '2002-02-02', 'nga@luvina.net', '778520123', 'ngaptt268', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (147, 3, 'Quỳnh;Nga', 'グループ', '2002-02-02', 'nga@luvina.net', '778520123', 'ngaptt269', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
INSERT INTO `employees` VALUES (148, 4, 'Quỳnh,Nga', 'グループ', '2002-02-02', 'nga@luvina.net', '778520123', 'ngaptt270', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 0);
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
-- Records of employees_certifications
-- ----------------------------
INSERT INTO `employees_certifications` VALUES (1, 1, 1, '2023-01-01', '2025-01-01', 170.0);
INSERT INTO `employees_certifications` VALUES (2, 2, 4, '2010-07-08', '2011-07-08', 290.0);
INSERT INTO `employees_certifications` VALUES (3, 3, 4, '2010-07-08', '2011-07-08', 290.0);
INSERT INTO `employees_certifications` VALUES (4, 4, 4, '2010-07-08', '2011-07-08', 290.0);
INSERT INTO `employees_certifications` VALUES (5, 5, 4, '2010-07-08', '2011-07-08', 290.0);
INSERT INTO `employees_certifications` VALUES (6, 6, 4, '2010-07-08', '2011-07-08', 290.0);
INSERT INTO `employees_certifications` VALUES (7, 7, 2, '2020-01-01', '2024-01-01', 160.0);
INSERT INTO `employees_certifications` VALUES (8, 8, 3, '2021-05-15', '2025-05-15', 145.0);
INSERT INTO `employees_certifications` VALUES (9, 10, 1, '2022-07-01', '2026-07-01', 175.0);
INSERT INTO `employees_certifications` VALUES (10, 11, 2, '2019-12-01', '2023-12-01', 155.0);
INSERT INTO `employees_certifications` VALUES (11, 12, 1, '2023-01-01', '2027-01-01', 165.0);
INSERT INTO `employees_certifications` VALUES (12, 13, 2, '2022-06-01', '2026-06-01', 150.0);
INSERT INTO `employees_certifications` VALUES (13, 14, 3, '2021-03-15', '2025-03-15', 140.0);
INSERT INTO `employees_certifications` VALUES (14, 15, 4, '2020-07-01', '2024-07-01', 130.0);
INSERT INTO `employees_certifications` VALUES (15, 16, 1, '2024-01-10', '2028-01-10', 175.0);
INSERT INTO `employees_certifications` VALUES (16, 17, 2, '2022-12-01', '2026-12-01', 155.0);
INSERT INTO `employees_certifications` VALUES (17, 18, 5, '2021-08-20', '2025-08-20', 120.0);
INSERT INTO `employees_certifications` VALUES (18, 20, 3, '2023-04-01', '2027-04-01', 145.0);
INSERT INTO `employees_certifications` VALUES (19, 21, 2, '2020-11-15', '2024-11-15', 160.0);
INSERT INTO `employees_certifications` VALUES (20, 23, 1, '2021-05-01', '2025-05-01', 170.0);
INSERT INTO `employees_certifications` VALUES (21, 25, 4, '2022-09-01', '2026-09-01', 135.0);
INSERT INTO `employees_certifications` VALUES (22, 27, 3, '2020-02-10', '2024-02-10', 142.0);
INSERT INTO `employees_certifications` VALUES (23, 28, 2, '2023-08-01', '2027-08-01', 158.0);
INSERT INTO `employees_certifications` VALUES (24, 30, 1, '2022-04-15', '2026-04-15', 168.0);
INSERT INTO `employees_certifications` VALUES (25, 32, 3, '2021-10-20', '2025-10-20', 148.0);
INSERT INTO `employees_certifications` VALUES (26, 34, 2, '2024-02-01', '2028-02-01', 162.0);
INSERT INTO `employees_certifications` VALUES (27, 36, 4, '2023-06-15', '2027-06-15', 138.0);
INSERT INTO `employees_certifications` VALUES (28, 38, 1, '2020-09-01', '2024-09-01', 172.0);
INSERT INTO `employees_certifications` VALUES (29, 40, 2, '2021-11-01', '2025-11-01', 152.0);
INSERT INTO `employees_certifications` VALUES (30, 42, 3, '2022-03-01', '2026-03-01', 144.0);
INSERT INTO `employees_certifications` VALUES (31, 44, 1, '2023-10-01', '2027-10-01', 180.0);
INSERT INTO `employees_certifications` VALUES (32, 46, 2, '2020-05-15', '2024-05-15', 156.0);
INSERT INTO `employees_certifications` VALUES (33, 48, 4, '2021-07-20', '2025-07-20', 128.0);
INSERT INTO `employees_certifications` VALUES (34, 50, 3, '2024-01-01', '2028-01-01', 146.0);
INSERT INTO `employees_certifications` VALUES (35, 51, 2, '2023-11-15', '2027-11-15', 164.0);
INSERT INTO `employees_certifications` VALUES (36, 52, 1, '2023-01-10', '2027-01-10', 170.0);
INSERT INTO `employees_certifications` VALUES (37, 53, 2, '2022-03-15', '2026-03-15', 155.0);
INSERT INTO `employees_certifications` VALUES (38, 54, 3, '2021-05-20', '2025-05-20', 140.0);
INSERT INTO `employees_certifications` VALUES (39, 55, 4, '2020-07-25', '2024-07-25', 132.0);
INSERT INTO `employees_certifications` VALUES (40, 56, 5, '2024-02-01', '2028-02-01', 120.0);
INSERT INTO `employees_certifications` VALUES (41, 57, 1, '2022-11-15', '2026-11-15', 168.0);
INSERT INTO `employees_certifications` VALUES (42, 58, 2, '2023-04-10', '2027-04-10', 158.0);
INSERT INTO `employees_certifications` VALUES (43, 60, 3, '2021-08-05', '2025-08-05', 145.0);
INSERT INTO `employees_certifications` VALUES (44, 61, 4, '2020-09-12', '2024-09-12', 128.0);
INSERT INTO `employees_certifications` VALUES (45, 62, 1, '2023-06-18', '2027-06-18', 175.0);
INSERT INTO `employees_certifications` VALUES (46, 64, 2, '2022-10-22', '2026-10-22', 162.0);
INSERT INTO `employees_certifications` VALUES (47, 65, 3, '2021-01-30', '2025-01-30', 148.0);
INSERT INTO `employees_certifications` VALUES (48, 66, 4, '2024-03-05', '2028-03-05', 135.0);
INSERT INTO `employees_certifications` VALUES (49, 67, 1, '2022-05-14', '2026-05-14', 180.0);
INSERT INTO `employees_certifications` VALUES (50, 69, 2, '2020-12-01', '2024-12-01', 150.0);
INSERT INTO `employees_certifications` VALUES (51, 70, 3, '2023-09-19', '2027-09-19', 142.0);
INSERT INTO `employees_certifications` VALUES (52, 72, 1, '2021-04-25', '2025-04-25', 165.0);
INSERT INTO `employees_certifications` VALUES (53, 73, 2, '2022-07-08', '2026-07-08', 154.0);
INSERT INTO `employees_certifications` VALUES (54, 75, 4, '2023-11-11', '2027-11-11', 130.0);
INSERT INTO `employees_certifications` VALUES (55, 76, 5, '2024-01-15', '2028-01-15', 115.0);
INSERT INTO `employees_certifications` VALUES (56, 77, 1, '2020-08-20', '2024-08-20', 172.0);
INSERT INTO `employees_certifications` VALUES (57, 78, 2, '2022-02-14', '2026-02-14', 160.0);
INSERT INTO `employees_certifications` VALUES (58, 80, 3, '2021-06-30', '2025-06-30', 146.0);
INSERT INTO `employees_certifications` VALUES (59, 81, 4, '2023-03-18', '2027-03-18', 136.0);
INSERT INTO `employees_certifications` VALUES (60, 82, 1, '2024-04-01', '2028-04-01', 178.0);
INSERT INTO `employees_certifications` VALUES (61, 84, 2, '2022-09-09', '2026-09-09', 152.0);
INSERT INTO `employees_certifications` VALUES (62, 85, 3, '2020-11-23', '2024-11-23', 144.0);
INSERT INTO `employees_certifications` VALUES (63, 86, 4, '2023-05-15', '2027-05-15', 134.0);
INSERT INTO `employees_certifications` VALUES (64, 88, 1, '2021-10-10', '2025-10-10', 166.0);
INSERT INTO `employees_certifications` VALUES (65, 89, 2, '2022-08-28', '2026-08-28', 156.0);
INSERT INTO `employees_certifications` VALUES (66, 91, 3, '2024-02-20', '2028-02-20', 149.0);
INSERT INTO `employees_certifications` VALUES (67, 92, 4, '2020-06-15', '2024-06-15', 125.0);
INSERT INTO `employees_certifications` VALUES (68, 93, 1, '2023-12-05', '2027-12-05', 174.0);
INSERT INTO `employees_certifications` VALUES (69, 95, 2, '2021-03-18', '2025-03-18', 164.0);
INSERT INTO `employees_certifications` VALUES (70, 96, 3, '2022-01-22', '2026-01-22', 147.0);
INSERT INTO `employees_certifications` VALUES (71, 98, 1, '2024-05-10', '2028-05-10', 176.0);
INSERT INTO `employees_certifications` VALUES (72, 99, 2, '2020-07-07', '2024-07-07', 153.0);
INSERT INTO `employees_certifications` VALUES (73, 100, 4, '2023-08-14', '2027-08-14', 138.0);
INSERT INTO `employees_certifications` VALUES (74, 102, 1, '2022-04-19', '2026-04-19', 169.0);
INSERT INTO `employees_certifications` VALUES (75, 103, 2, '2021-09-25', '2025-09-25', 159.0);
INSERT INTO `employees_certifications` VALUES (76, 105, 3, '2023-02-28', '2027-02-28', 143.0);
INSERT INTO `employees_certifications` VALUES (77, 106, 5, '2020-10-15', '2024-10-15', 118.0);
INSERT INTO `employees_certifications` VALUES (78, 107, 1, '2024-01-08', '2028-01-08', 171.0);
INSERT INTO `employees_certifications` VALUES (79, 109, 2, '2022-06-12', '2026-06-12', 157.0);
INSERT INTO `employees_certifications` VALUES (80, 110, 3, '2023-07-01', '2027-07-01', 141.0);
INSERT INTO `employees_certifications` VALUES (81, 112, 4, '2021-12-18', '2025-12-18', 133.0);
INSERT INTO `employees_certifications` VALUES (82, 113, 1, '2020-05-22', '2024-05-22', 173.0);
INSERT INTO `employees_certifications` VALUES (83, 115, 2, '2023-10-30', '2027-10-30', 161.0);
INSERT INTO `employees_certifications` VALUES (84, 116, 3, '2022-03-04', '2026-03-04', 145.0);
INSERT INTO `employees_certifications` VALUES (85, 118, 1, '2024-03-15', '2028-03-15', 179.0);
INSERT INTO `employees_certifications` VALUES (86, 119, 2, '2021-08-11', '2025-08-11', 155.0);
INSERT INTO `employees_certifications` VALUES (87, 121, 3, '2020-04-20', '2024-04-20', 140.0);
INSERT INTO `employees_certifications` VALUES (88, 122, 4, '2023-09-05', '2027-09-05', 137.0);
INSERT INTO `employees_certifications` VALUES (89, 124, 1, '2022-11-28', '2026-11-28', 167.0);
INSERT INTO `employees_certifications` VALUES (90, 125, 2, '2024-02-14', '2028-02-14', 163.0);
INSERT INTO `employees_certifications` VALUES (92, 129, 1, '2023-05-24', '2027-05-24', 177.0);
INSERT INTO `employees_certifications` VALUES (93, 131, 2, '2020-09-17', '2024-09-17', 151.0);
INSERT INTO `employees_certifications` VALUES (94, 133, 3, '2022-12-15', '2026-12-15', 142.0);
INSERT INTO `employees_certifications` VALUES (95, 136, 1, '2024-04-18', '2028-04-18', 180.0);
INSERT INTO `employees_certifications` VALUES (96, 9, 3, '2021-03-01', '2025-03-01', 145.0);
INSERT INTO `employees_certifications` VALUES (97, 12, 1, '2023-01-15', '2027-01-15', 175.0);
INSERT INTO `employees_certifications` VALUES (98, 18, 2, '2022-06-20', '2026-06-20', 160.0);
INSERT INTO `employees_certifications` VALUES (99, 24, 3, '2021-09-10', '2025-09-10', 148.0);
INSERT INTO `employees_certifications` VALUES (100, 30, 4, '2020-11-05', '2024-11-05', 135.0);
INSERT INTO `employees_certifications` VALUES (101, 36, 5, '2024-02-01', '2028-02-01', 120.0);
INSERT INTO `employees_certifications` VALUES (102, 13, 1, '2022-04-10', '2026-04-10', 170.0);
INSERT INTO `employees_certifications` VALUES (103, 19, 1, '2024-01-15', '2028-01-15', 178.0);
INSERT INTO `employees_certifications` VALUES (104, 25, 2, '2021-08-12', '2025-08-12', 155.0);
INSERT INTO `employees_certifications` VALUES (105, 31, 2, '2023-05-20', '2027-05-20', 162.0);
INSERT INTO `employees_certifications` VALUES (106, 14, 1, '2020-03-01', '2024-03-01', 165.0);
INSERT INTO `employees_certifications` VALUES (107, 20, 1, '2021-07-15', '2025-07-15', 168.0);
INSERT INTO `employees_certifications` VALUES (108, 26, 1, '2022-10-20', '2026-10-20', 172.0);
INSERT INTO `employees_certifications` VALUES (109, 32, 1, '2023-12-25', '2027-12-25', 176.0);
INSERT INTO `employees_certifications` VALUES (110, 15, 2, '2022-01-10', '2026-01-10', 158.0);
INSERT INTO `employees_certifications` VALUES (111, 17, 3, '2021-05-18', '2025-05-18', 142.0);
INSERT INTO `employees_certifications` VALUES (112, 22, 2, '2023-07-22', '2027-07-22', 164.0);
INSERT INTO `employees_certifications` VALUES (113, 27, 4, '2020-09-30', '2024-09-30', 130.0);
INSERT INTO `employees_certifications` VALUES (114, 29, 1, '2024-03-05', '2028-03-05', 179.0);
INSERT INTO `employees_certifications` VALUES (115, 33, 2, '2022-11-12', '2026-11-12', 156.0);
INSERT INTO `employees_certifications` VALUES (116, 35, 3, '2021-02-14', '2025-02-14', 146.0);
INSERT INTO `employees_certifications` VALUES (117, 37, 1, '2023-08-08', '2027-08-08', 171.0);
INSERT INTO `employees_certifications` VALUES (118, 39, 4, '2020-06-18', '2024-06-18', 128.0);
INSERT INTO `employees_certifications` VALUES (119, 41, 2, '2022-03-25', '2026-03-25', 154.0);
INSERT INTO `employees_certifications` VALUES (120, 43, 3, '2021-12-05', '2025-12-05', 144.0);
INSERT INTO `employees_certifications` VALUES (121, 45, 1, '2024-04-20', '2028-04-20', 177.0);
INSERT INTO `employees_certifications` VALUES (122, 47, 2, '2023-09-15', '2027-09-15', 160.0);
INSERT INTO `employees_certifications` VALUES (123, 49, 3, '2020-10-10', '2024-10-10', 140.0);
INSERT INTO `employees_certifications` VALUES (124, 59, 2, '2022-05-10', '2026-05-10', 158.0);
INSERT INTO `employees_certifications` VALUES (125, 63, 1, '2023-09-15', '2027-09-15', 172.0);
INSERT INTO `employees_certifications` VALUES (126, 68, 3, '2021-04-20', '2025-04-20', 144.0);
INSERT INTO `employees_certifications` VALUES (127, 71, 2, '2022-12-01', '2026-12-01', 156.0);
INSERT INTO `employees_certifications` VALUES (128, 74, 4, '2020-08-15', '2024-08-15', 132.0);
INSERT INTO `employees_certifications` VALUES (129, 79, 1, '2024-02-10', '2028-02-10', 176.0);
INSERT INTO `employees_certifications` VALUES (130, 83, 2, '2023-06-25', '2027-06-25', 162.0);
INSERT INTO `employees_certifications` VALUES (131, 87, 3, '2021-10-18', '2025-10-18', 146.0);
INSERT INTO `employees_certifications` VALUES (133, 94, 2, '2020-11-22', '2024-11-22', 154.0);
INSERT INTO `employees_certifications` VALUES (134, 97, 3, '2023-03-14', '2027-03-14', 148.0);
INSERT INTO `employees_certifications` VALUES (135, 101, 1, '2024-05-01', '2028-05-01', 180.0);
INSERT INTO `employees_certifications` VALUES (136, 104, 2, '2022-01-20', '2026-01-20', 160.0);
INSERT INTO `employees_certifications` VALUES (137, 108, 4, '2021-09-09', '2025-09-09', 135.0);
INSERT INTO `employees_certifications` VALUES (138, 111, 1, '2023-11-15', '2027-11-15', 170.0);
INSERT INTO `employees_certifications` VALUES (139, 114, 2, '2020-04-12', '2024-04-12', 152.0);
INSERT INTO `employees_certifications` VALUES (140, 117, 3, '2022-08-05', '2026-08-05', 142.0);
INSERT INTO `employees_certifications` VALUES (141, 120, 1, '2024-01-25', '2028-01-25', 178.0);
INSERT INTO `employees_certifications` VALUES (142, 123, 2, '2021-06-30', '2025-06-30', 164.0);
INSERT INTO `employees_certifications` VALUES (143, 126, 3, '2023-10-10', '2027-10-10', 145.0);
INSERT INTO `employees_certifications` VALUES (144, 128, 4, '2020-12-18', '2024-12-18', 128.0);
INSERT INTO `employees_certifications` VALUES (145, 130, 1, '2022-03-15', '2026-03-15', 173.0);
INSERT INTO `employees_certifications` VALUES (146, 132, 2, '2024-04-05', '2028-04-05', 159.0);
INSERT INTO `employees_certifications` VALUES (147, 134, 3, '2021-07-22', '2025-07-22', 147.0);
INSERT INTO `employees_certifications` VALUES (148, 135, 1, '2023-01-30', '2027-01-30', 177.0);
INSERT INTO `employees_certifications` VALUES (149, 137, 2, '2020-05-14', '2024-05-14', 155.0);
INSERT INTO `employees_certifications` VALUES (150, 138, 3, '2022-09-28', '2026-09-28', 143.0);
INSERT INTO `employees_certifications` VALUES (151, 139, 4, '2021-11-11', '2025-11-11', 136.0);
INSERT INTO `employees_certifications` VALUES (152, 140, 1, '2024-03-01', '2028-03-01', 180.0);
INSERT INTO `employees_certifications` VALUES (153, 141, 2, '2023-08-19', '2027-08-19', 161.0);
INSERT INTO `employees_certifications` VALUES (154, 12, 3, '2020-01-01', '2024-01-01', 140.0);
INSERT INTO `employees_certifications` VALUES (155, 13, 4, '2019-06-01', '2023-06-01', 130.0);
INSERT INTO `employees_certifications` VALUES (156, 14, 2, '2021-01-01', '2025-01-01', 155.0);
INSERT INTO `employees_certifications` VALUES (157, 142, 1, '2023-06-01', '2027-06-01', 180.0);
INSERT INTO `employees_certifications` VALUES (158, 143, 1, '2023-01-01', '2027-01-01', 175.0);
INSERT INTO `employees_certifications` VALUES (159, 144, 2, '2023-02-01', '2027-02-01', 160.0);
INSERT INTO `employees_certifications` VALUES (160, 145, 3, '2023-03-01', '2027-03-01', 150.0);
INSERT INTO `employees_certifications` VALUES (161, 146, 1, '2023-04-01', '2027-04-01', 170.0);
INSERT INTO `employees_certifications` VALUES (162, 147, 2, '2023-05-01', '2027-05-01', 165.0);
INSERT INTO `employees_certifications` VALUES (163, 148, 3, '2023-06-01', '2027-06-01', 155.0);
INSERT INTO `employees_certifications` VALUES (164, 148, 1, '2023-06-01', '2027-06-01', 160.0);


