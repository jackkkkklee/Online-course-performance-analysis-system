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

 Date: 12/20/2020 15:38:29 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_course`
-- ----------------------------
DROP TABLE IF EXISTS `sys_course`;
CREATE TABLE `sys_course` (
  `cid` varchar(20) NOT NULL,
  `tid` varchar(20) NOT NULL,
  `course` varchar(20) NOT NULL,
  `startTime` datetime(6) DEFAULT NULL,
  `endTime` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_course`
-- ----------------------------
BEGIN;
INSERT INTO `sys_course` VALUES ('class001', 'tea001', '化学', '2020-12-19 20:00:00.000000', '2020-12-19 20:45:00.000000'), ('class002', 'tea001', '物理', '2020-12-20 20:00:00.000000', '2020-12-20 20:45:31.000000'), ('class003', 'tea002', '地理', '2020-12-21 20:00:00.000000', '2020-12-21 20:45:00.000000');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
