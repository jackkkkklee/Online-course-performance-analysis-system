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

 Date: 12/20/2020 15:46:33 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_perm`
-- ----------------------------
DROP TABLE IF EXISTS `sys_perm`;
CREATE TABLE `sys_perm` (
  `pval` varchar(50) NOT NULL COMMENT '权限值，shiro的权限控制表达式',
  `parent` varchar(25) DEFAULT NULL COMMENT '父权限id',
  `pname` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `ptype` int(3) DEFAULT NULL COMMENT '权限类型：1.菜单 2.按钮 3.接口 4.特殊',
  `leaf` tinyint(1) DEFAULT NULL COMMENT '是否叶子节点',
  `created` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updated` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`pval`),
  UNIQUE KEY `pval` (`pval`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限';

-- ----------------------------
--  Records of `sys_perm`
-- ----------------------------
BEGIN;
INSERT INTO `sys_perm` VALUES ('*', null, '所有权限', '0', null, '2018-04-19 18:14:12', null), ('a:auth', null, '登录模块', '3', '1', null, null), ('a:gradleBuild', 'a:test', '构建gradle', '3', '1', null, null), ('a:mvn:install', 'a:test', 'mvnInstall', '3', '1', null, null), ('a:option', null, '选项模块', '3', '1', null, null), ('a:perm:query', 'a:sys:perm', '查询sys_perm', '3', '1', null, null), ('a:perm:update', 'a:sys:perm', 'update', '3', '1', null, null), ('a:role:query', 'a:sys:role', 'query', '3', '1', null, null), ('a:role:update', 'a:sys:role', 'update', '3', '1', null, null), ('a:sys:perm', null, '系统权限模块', '3', '0', null, null), ('a:sys:role', null, '系统角色模块', '3', '0', null, null), ('a:sys:接口', null, '系统用户模块', '3', '1', null, null), ('a:test', null, '测试模块模块', '3', '0', null, null), ('b:user:add', 'm:sys:user', '添加用户', '2', null, '2018-06-02 11:00:37', null), ('b:user:delete', 'm:sys:user', '删除用户', '2', null, '2018-06-02 11:00:56', null), ('m:menu1', null, '菜单1', '1', '1', null, null), ('m:menu2', null, '菜单2', '1', '1', null, null), ('m:menu3', null, '菜单3', '1', '0', null, null), ('m:menu3:1', 'm:menu3', '菜单3-1', '1', '1', null, null), ('m:menu3:2', 'm:menu3', '菜单3-2', '1', '1', null, null), ('m:menu3:3', 'm:menu3', '菜单3-3', '1', '1', null, null), ('m:menu4', null, '菜单4', '1', '0', null, null), ('m:menu4:1', 'm:menu4', '菜单4-1', '1', '0', null, null), ('m:menu4:1:a', 'm:menu4:1', '菜单4-1-a', '1', '1', null, null), ('m:menu4:1:b', 'm:menu4:1', '菜单4-1-b', '1', '1', null, null), ('m:menu4:1:c', 'm:menu4:1', '菜单4-1-c', '1', '1', null, null), ('m:menu4:2', 'm:menu4', '菜单4-2', '1', '1', null, null), ('m:sys', null, '系统', '1', '0', null, null), ('m:sys:perm', 'm:sys', '权限管理', '1', '1', null, null), ('m:sys:role', 'm:sys', '角色管理', '1', '1', null, null), ('m:sys:user', 'm:sys', '用户管理', '1', '1', null, null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
