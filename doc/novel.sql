/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : novel

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 19/05/2018 16:48:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for crawler
-- ----------------------------
DROP TABLE IF EXISTS `crawler`;
CREATE TABLE `crawler`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `crawlerName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `crawlerUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `crawlerStatus` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of crawler
-- ----------------------------
INSERT INTO `crawler` VALUES (1, '笔趣阁爬虫', 'https://www.biquge5200.cc/', '1');

-- ----------------------------
-- Table structure for crawlerconfig
-- ----------------------------
DROP TABLE IF EXISTS `crawlerconfig`;
CREATE TABLE `crawlerconfig`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `configId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `configName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `select` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `attrName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `reg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `regGroupNum` int(11) NULL DEFAULT NULL,
  `appendResult` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `sort` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of crawlerconfig
-- ----------------------------
INSERT INTO `crawlerconfig` VALUES (1, '1', '小说列表页', 'a', 'all', 'href', 'www.biquge5200.cc/[A-Za-z]+/$', 0, 'https://', 1);
INSERT INTO `crawlerconfig` VALUES (2, '1', '小说页', 'a', 'all', 'href', 'www.biquge5200.cc/\\d+_\\d+/$', 0, 'https://', 2);
INSERT INTO `crawlerconfig` VALUES (3, '1', '小说名称', '#info h1', '0', 'html', '[\\s\\S]*', 0, '', 3);
INSERT INTO `crawlerconfig` VALUES (4, '1', '小说作者', '#info p', '0', 'html', '([^：]+)$', 0, '', 4);
INSERT INTO `crawlerconfig` VALUES (5, '1', '小说类型', '.con_top a', '2', 'html', '[\\s\\S]*', 0, '', 5);
INSERT INTO `crawlerconfig` VALUES (6, '1', '小说描述', '#intro p', '0', 'html', '[\\s\\S]*', 0, '', 6);
INSERT INTO `crawlerconfig` VALUES (7, '1', '小说主图地址', '#fmimg img', '0', 'src', '[\\s\\S]*', 0, '', 7);
INSERT INTO `crawlerconfig` VALUES (8, '1', '小说章节标签', '#list dd', 'all', 'html', '[\\s\\S]*', 0, '', 8);
INSERT INTO `crawlerconfig` VALUES (9, '1', '小说章节地址', 'a', '0', 'href', '[\\s\\S]*', 0, '', 9);
INSERT INTO `crawlerconfig` VALUES (10, '1', '小说章节名称', 'a', '0', 'html', '[\\s\\S]*', 0, '', 10);

-- ----------------------------
-- Table structure for novel
-- ----------------------------
DROP TABLE IF EXISTS `novel`;
CREATE TABLE `novel`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `typeName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `mainImage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `createTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  `updateTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `clickView` int(11) NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for novelchaperdetail
-- ----------------------------
DROP TABLE IF EXISTS `novelchaperdetail`;
CREATE TABLE `novelchaperdetail`  (
  `id` int(11) NOT NULL,
  `novelChapterListId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for novelchapterlist
-- ----------------------------
DROP TABLE IF EXISTS `novelchapterlist`;
CREATE TABLE `novelchapterlist`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `novelId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `chapterName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `chapterLink` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20213 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for noveltype
-- ----------------------------
DROP TABLE IF EXISTS `noveltype`;
CREATE TABLE `noveltype`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
