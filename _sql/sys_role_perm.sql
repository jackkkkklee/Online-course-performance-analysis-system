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

 Date: 12/20/2020 15:46:43 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_role_perm`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_perm`;
CREATE TABLE `sys_role_perm` (
  `role_id` varchar(25) NOT NULL,
  `perm_val` varchar(25) NOT NULL,
  `perm_type` int(5) DEFAULT NULL,
  PRIMARY KEY (`role_id`,`perm_val`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_role_perm`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_perm` VALUES ('1002748319131680769', 'a:perm:update', '3'), ('1002748319131680769', 'a:sys:perm', '3'), ('1002748319131680769', 'b:user:add', '2'), ('1002748319131680769', 'm:sys', '1'), ('1002748319131680769', 'm:sys:user', '1'), ('999999888888777777', '*', null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
