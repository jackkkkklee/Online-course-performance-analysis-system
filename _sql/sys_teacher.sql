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

 Date: 12/20/2020 15:46:51 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_teacher`
-- ----------------------------
DROP TABLE IF EXISTS `sys_teacher`;
CREATE TABLE `sys_teacher` (
  `tid` varchar(20) NOT NULL,
  `teacherName` varchar(20) DEFAULT NULL,
  `tsex` varchar(20) DEFAULT NULL,
  `tage` int(255) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_teacher`
-- ----------------------------
BEGIN;
INSERT INTO `sys_teacher` VALUES ('tea001', '赵六', '男', '25'), ('tea002', '李二', '女', '45');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
