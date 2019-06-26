/*
Navicat MySQL Data Transfer

Source Server         : 610服务器
Source Server Version : 50559
Source Host           : 192.168.49.228:3306
Source Database       : team

Target Server Type    : MYSQL
Target Server Version : 50559
File Encoding         : 65001

Date: 2019-06-26 17:30:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for contest
-- ----------------------------
DROP TABLE IF EXISTS `contest`;
CREATE TABLE `contest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for matches
-- ----------------------------
DROP TABLE IF EXISTS `matches`;
CREATE TABLE `matches` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `news_id` int(11) DEFAULT NULL,
  `title` varchar(50) DEFAULT '',
  `date` datetime DEFAULT NULL,
  `prize1` int(11) DEFAULT '0',
  `prize2` int(11) DEFAULT '0',
  `prize3` int(11) DEFAULT '0',
  `prize4` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT '',
  `content` text,
  `is_public` int(11) DEFAULT '0',
  `views` int(11) DEFAULT '0',
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for study_file
-- ----------------------------
DROP TABLE IF EXISTS `study_file`;
CREATE TABLE `study_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT '',
  `path` varchar(255) DEFAULT '' COMMENT '文件相对路径，相对于项目根目录',
  `created` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `folder_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `user_id` (`user_id`),
  KEY `folder_id` (`folder_id`),
  CONSTRAINT `study_file_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `study_file_ibfk_2` FOREIGN KEY (`folder_id`) REFERENCES `study_folder` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for study_folder
-- ----------------------------
DROP TABLE IF EXISTS `study_folder`;
CREATE TABLE `study_folder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `resume` varchar(255) DEFAULT NULL,
  `for_year` int(11) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `subject_id` (`subject_id`),
  CONSTRAINT `study_folder_ibfk_1` FOREIGN KEY (`subject_id`) REFERENCES `study_subject` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for study_subject
-- ----------------------------
DROP TABLE IF EXISTS `study_subject`;
CREATE TABLE `study_subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `priority` int(11) DEFAULT '10000',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL DEFAULT '',
  `password` varchar(24) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(20) DEFAULT NULL,
  `power` int(11) DEFAULT '0',
  `tag` int(11) DEFAULT '0' COMMENT '用户角色：0外部 1退役 2现役 3教师',
  `grade` int(11) DEFAULT '0',
  `major` varchar(16) DEFAULT '',
  `work` varchar(30) DEFAULT '',
  `blog_url` varchar(80) DEFAULT '',
  `resume` text,
  `photo_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`username`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `email` (`email`),
  KEY `grade` (`grade`),
  KEY `tag` (`tag`),
  KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
