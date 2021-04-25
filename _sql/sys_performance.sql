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

 Date: 12/20/2020 15:46:28 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_performance`
-- ----------------------------
DROP TABLE IF EXISTS `sys_performance`;
CREATE TABLE `sys_performance` (
  `cid` varchar(20) DEFAULT NULL,
  `sid` varchar(20) DEFAULT NULL,
  `timesetoff` double DEFAULT NULL,
  `attention_value` double DEFAULT NULL,
  `expression_value` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_performance`
-- ----------------------------
BEGIN;
INSERT INTO `sys_performance` VALUES ('class001', 'stu001', '20', '90', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
