/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : novel

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-05-14 12:21:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for crawler
-- ----------------------------
DROP TABLE IF EXISTS `crawler`;
CREATE TABLE `crawler` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `crawlerName` varchar(255) DEFAULT '',
  `crawlerUrl` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of crawler
-- ----------------------------
INSERT INTO `crawler` VALUES ('1', '笔趣阁爬虫', 'https://www.biquge5200.cc/');

-- ----------------------------
-- Table structure for crawlerconfig
-- ----------------------------
DROP TABLE IF EXISTS `crawlerconfig`;
CREATE TABLE `crawlerconfig` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `configId` varchar(255) DEFAULT '',
  `configName` varchar(255) DEFAULT '',
  `select` varchar(255) DEFAULT '',
  `num` varchar(255) DEFAULT '',
  `attrName` varchar(255) DEFAULT '',
  `reg` varchar(255) DEFAULT '',
  `appendResult` varchar(255) DEFAULT '',
  `regGroupNum` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of crawlerconfig
-- ----------------------------
INSERT INTO `crawlerconfig` VALUES ('1', '1', '小说列表页', 'a', 'all', 'href', 'www.biquge5200.cc/[A-Za-z]+/$', 'https://', '0');
INSERT INTO `crawlerconfig` VALUES ('2', '1', '小说页', 'a', 'all', 'href', 'www.biquge5200.cc/\\d+_\\d+/$', 'https://', '0');
INSERT INTO `crawlerconfig` VALUES ('3', '1', '小说名称', '#info h1', '0', 'html', '[\\s\\S]*', '', '0');
INSERT INTO `crawlerconfig` VALUES ('4', '1', '小说作者', '#info p', '0', 'html', '([^：]+)$', '', '0');
INSERT INTO `crawlerconfig` VALUES ('5', '1', '小说类型', '.con_top a', '2', 'html', '[\\s\\S]*', '', '0');
INSERT INTO `crawlerconfig` VALUES ('6', '1', '小说描述', '#intro p', '0', 'html', '[\\s\\S]*', '', '0');
INSERT INTO `crawlerconfig` VALUES ('7', '1', '小说主图地址', '#fmimg img', '0', 'src', '[\\s\\S]*', '', '0');
INSERT INTO `crawlerconfig` VALUES ('8', '1', '小说章节链接', '#list dd', 'all', 'html', '[\\s\\S]*', '', '0');
INSERT INTO `crawlerconfig` VALUES ('9', '1', '小说章节地址', 'a', '0', 'html', '[\\s\\S]*', '', '0');
INSERT INTO `crawlerconfig` VALUES ('10', '1', '小说章节编号', 'a', '0', 'html', '第(.*)章', '', '1');
INSERT INTO `crawlerconfig` VALUES ('11', '1', '小说章节名称', 'a', '0', 'html', '[\\s\\S]*', '', '0');

-- ----------------------------
-- Table structure for novel
-- ----------------------------
DROP TABLE IF EXISTS `novel`;
CREATE TABLE `novel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT '',
  `name` varchar(255) DEFAULT '',
  `author` varchar(255) DEFAULT '',
  `typeName` varchar(255) DEFAULT '',
  `description` varchar(255) DEFAULT '',
  `mainImage` varchar(255) DEFAULT '',
  `createTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '',
  `updateTime` varchar(255) DEFAULT '',
  `status` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of novel
-- ----------------------------

-- ----------------------------
-- Table structure for novelchaperdetail
-- ----------------------------
DROP TABLE IF EXISTS `novelchaperdetail`;
CREATE TABLE `novelchaperdetail` (
  `id` int(11) NOT NULL,
  `novelChapterListId` varchar(255) DEFAULT '',
  `content` longtext,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of novelchaperdetail
-- ----------------------------

-- ----------------------------
-- Table structure for novelchapterlist
-- ----------------------------
DROP TABLE IF EXISTS `novelchapterlist`;
CREATE TABLE `novelchapterlist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `novelId` varchar(255) DEFAULT '',
  `chapterName` varchar(255) DEFAULT '',
  `chapterNum` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of novelchapterlist
-- ----------------------------

-- ----------------------------
-- Table structure for noveltype
-- ----------------------------
DROP TABLE IF EXISTS `noveltype`;
CREATE TABLE `noveltype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `typename` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of noveltype
-- ----------------------------
