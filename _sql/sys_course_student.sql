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

 Date: 12/24/2020 10:23:23 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_course_student`
-- ----------------------------
DROP TABLE IF EXISTS `sys_course_student`;
CREATE TABLE `sys_course_student` (
  `cid` varchar(255) DEFAULT NULL,
  `sid` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_course_student`
-- ----------------------------
BEGIN;
INSERT INTO `sys_course_student` VALUES ('class001', 'stu001'), ('class001', 'stu002'), ('class002', 'stu001'), ('class002', 'stu003'), ('class003', 'stu002');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
