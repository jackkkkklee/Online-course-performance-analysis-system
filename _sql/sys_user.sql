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

 Date: 12/20/2020 15:46:55 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `uid` varchar(25) NOT NULL COMMENT '用户id',
  `uname` varchar(50) DEFAULT NULL COMMENT '登录名，不可改',
  `nick` varchar(50) DEFAULT NULL COMMENT '用户昵称，可改',
  `pwd` varchar(200) DEFAULT NULL COMMENT '已加密的登录密码',
  `salt` varchar(200) DEFAULT NULL COMMENT '加密盐值',
  `lock` tinyint(1) DEFAULT NULL COMMENT '是否锁定',
  `created` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updated` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uname` (`uname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1002748017179541505', 'guanyu', '关羽', 'n2Wd7JramFVrHcijY4KW1rNTGKnwyYPJ0RDYvy2BdK0=', 'aem4EsAFae5rObEdZP4Xlw==', null, '2018-06-02 11:05:32', '2018-06-02 14:40:01'), ('1002748102537822209', 'zhangfei', '张飞', 'g+aRBmgVTTPkNLNwJfM64D8rwH94WEgDgckQ4fuQp6w=', 'Sqhvxsnc0HZSQEFKjBB9zQ==', null, '2018-06-02 11:05:52', null), ('986177923098808322', 'admin', '刘备', 'J/ms7qTJtqmysekuY8/v1TAS+VKqXdH5sB7ulXZOWho=', 'wxKYXuTPST5SG0jMQzVPsg==', '0', '2018-04-17 17:41:53', '2018-04-19 17:08:15'), ('stu-001', '张三', null, 'n2Wd7JramFVrHcijY4KW1rNTGKnwyYPJ0RDYvy2BdK0=', 'aem4EsAFae5rObEdZP4Xlw', null, '2020-12-11 19:26:51', '2020-12-11 19:26:54'), ('stu-002', '李四', null, 'g+aRBmgVTTPkNLNwJfM64D8rwH94WEgDgckQ', 'Sqhvxsnc0HZSQEFKjBB9zQ==', null, '2020-12-11 19:27:31', '2020-12-11 19:27:33'), ('stu-003', '王五', null, 'J/ms7qTJtqmysekuY8/v1TAS+VKqXdH5sB7ulXZOWho=', 'wxKYXuTPST5SG0jMQzVPsg==', null, '2020-12-11 19:28:02', '2020-12-11 19:28:04'), ('tea-001', '赵六', null, 'n2Wd7JramFVrHcijY4KW1rNTGKnwyYPJ0RDYvy2BdK0=', 'aem4EsAFae5rObEdZP4Xlw', null, '2020-12-11 19:29:46', '2020-12-11 19:29:51'), ('tea-002', '李二', null, 'g+aRBmgVTTPkNLNwJfM64D8rwH94WEgDgckQ', 'aem4EsAFae5rObEdZP4Xlw', null, '2020-12-11 19:30:48', '2020-12-11 19:30:49');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
