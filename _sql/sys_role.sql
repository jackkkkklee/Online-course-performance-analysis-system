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

 Date: 12/20/2020 15:46:38 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `rid` varchar(25) NOT NULL COMMENT '角色id',
  `rname` varchar(50) DEFAULT NULL COMMENT '角色名，用于显示',
  `rdesc` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `rval` varchar(100) NOT NULL COMMENT '角色值，用于权限判断',
  `created` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updated` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`rid`),
  UNIQUE KEY `rval` (`rval`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1002748319131680769', '普通用户', '具有一般的权限，不具备系统菜单权限1', 'common', '2018-06-02 11:06:44', '2018-06-02 11:10:57'), ('1002806178141937666', '财务', '拥有财务相关权限', 'finance', '2018-06-02 14:56:39', null), ('1002806220860923906', '仓管', '拥有财务相关权限', 'stock', '2018-06-02 14:56:49', null), ('1002806266750803970', '销售', '拥有财务相关权限', 'sale', '2018-06-02 14:57:00', null), ('1002807171923550210', '文员', '拥有文员相关的权限', 'stuff', '2018-06-02 15:00:36', null), ('1002807288885911553', '啊', 'asdf', 'sdf', '2018-06-02 15:01:04', null), ('1002807344665960449', '阿斯达', 'sdfwerty', 'sgsf', '2018-06-02 15:01:17', null), ('1002807369559154689', '阿斯蒂芬', '撒旦法GV', 'asdfgewrgr', '2018-06-02 15:01:23', null), ('1002807394460737537', '阿斯蒂芬', '颂德歌功', 'asdgwergreh', '2018-06-02 15:01:29', null), ('1002807427771899906', 'asddfgswf', '阿道夫噶', 'drgregerg', '2018-06-02 15:01:37', null), ('999999888888777777', '超级管理员', '具有本系统中最高权限', 'root', '2018-04-19 17:34:33', null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
