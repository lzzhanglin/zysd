/*
Navicat MySQL Data Transfer

Source Server         : local_server
Source Server Version : 80013
Source Host           : localhost:3306
Source Database       : database

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2019-01-11 14:23:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `test`
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `a1` decimal(18,6) DEFAULT NULL,
  `a2` decimal(18,6) DEFAULT NULL,
  `a3` decimal(18,6) DEFAULT NULL,
  `a4` decimal(18,6) DEFAULT NULL,
  `a5` decimal(18,6) DEFAULT NULL,
  `a6` decimal(18,6) DEFAULT NULL,
  `a7` decimal(18,6) DEFAULT NULL,
  `a8` decimal(18,6) DEFAULT NULL,
  `a9` decimal(18,6) DEFAULT NULL,
  `a10` decimal(18,6) DEFAULT NULL,
  `a11` decimal(18,6) DEFAULT NULL,
  `a12` decimal(18,6) DEFAULT NULL,
  `quality` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=570053 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test
-- ----------------------------
