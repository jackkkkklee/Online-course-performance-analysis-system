/*
 Navicat Premium Data Transfer

 Source Server         : BigData_mysql
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost
 Source Database       : db_R&D

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : utf-8

 Date: 12/20/2020 15:46:47 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_student`
-- ----------------------------
DROP TABLE IF EXISTS `sys_student`;
CREATE TABLE `sys_student` (
  `sid` varchar(20) NOT NULL,
  `studentName` varchar(50) DEFAULT NULL,
  `ssex` varchar(50) DEFAULT NULL,
  `sage` int(255) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_student`
-- ----------------------------
BEGIN;
INSERT INTO `sys_student` VALUES ('stu001', '张三', '男', '19'), ('stu002', '李四', '男', '20'), ('stu003', '王五', '女', '21');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
