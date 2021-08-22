/*
 Navicat Premium Data Transfer

 Source Server         : Peng'sDB
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : localhost:3306
 Source Schema         : x-admin

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 22/08/2021 16:12:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '演出名称',
  `show_start_time` datetime NOT NULL COMMENT '演出起始时间',
  `show_end_time` datetime NOT NULL COMMENT '演出结束时间',
  `venue_city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '场地城市',
  `venue_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '场地名称',
  `venue_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '场地地址',
  `poster_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '海报图片',
  `source_channel` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '数据来源渠道（秀动：SHOWSTART）',
  `source_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '数据来源渠道-对象id',
  `source_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '来源url',
  `online` int(11) NOT NULL DEFAULT 0 COMMENT '线上活动',
  `artist_id` bigint(20) NOT NULL COMMENT '演出者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '演出表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (3, '1', '2021-08-21 21:55:06', '2021-08-21 21:55:05', '1', '1', '1', '1', '1', '1', '1', 1, 1, '2021-08-21 21:54:58', '2021-08-21 21:54:59');

-- ----------------------------
-- Table structure for activity_copy1
-- ----------------------------
DROP TABLE IF EXISTS `activity_copy1`;
CREATE TABLE `activity_copy1`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '演出名称',
  `show_start_time` datetime NOT NULL COMMENT '演出起始时间',
  `show_end_time` datetime NOT NULL COMMENT '演出结束时间',
  `venue_city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '场地城市',
  `venue_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `venue_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `poster_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '海报图片',
  `source_channel` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '数据来源渠道（秀动：SHOWSTART）',
  `source_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '数据来源渠道-对象id',
  `source_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '来源url',
  `online` int(11) NOT NULL DEFAULT 0 COMMENT '线上活动',
  `artist_id` bigint(20) NOT NULL COMMENT '演出者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '演出表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of activity_copy1
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `moment_id` bigint(20) NOT NULL COMMENT '动态id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父评论id',
  `reply_to_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '@用户id',
  `like_count` int(11) NOT NULL DEFAULT 0 COMMENT '点赞数量',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态（0：已屏蔽，1：正常，2：已删除）',
  `artist_id` bigint(20) NOT NULL COMMENT '演出者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (48, 1, 1, '1', 1, 1, 1, 1, 1, '2021-08-21 22:01:33', '2021-08-21 22:01:34');

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '内容',
  `contact_info` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '联系方式',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `artist_id` bigint(20) NOT NULL COMMENT '演出者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '意见反馈' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES (22, '1', '1', '1', 1, 1, '2021-08-21 22:04:26', '2021-08-21 22:04:27');

-- ----------------------------
-- Table structure for follow_relation
-- ----------------------------
DROP TABLE IF EXISTS `follow_relation`;
CREATE TABLE `follow_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `target_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '目标类型（USER：用户，TAG：标签）',
  `target_id` bigint(20) NOT NULL COMMENT '目标id',
  `artist_id` bigint(20) NOT NULL COMMENT '演出者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_user_id`(`user_id`, `target_type`, `target_id`, `artist_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '关注-关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of follow_relation
-- ----------------------------
INSERT INTO `follow_relation` VALUES (29, 1, '1', 1, 1, '2021-08-21 22:06:55', '2021-08-21 22:06:56');

-- ----------------------------
-- Table structure for moment
-- ----------------------------
DROP TABLE IF EXISTS `moment`;
CREATE TABLE `moment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '发送者id',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型（TEXT：纯文本，IMAGE：图片，VIDEO：视频）',
  `text_content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文本内容',
  `activity_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '活动id',
  `like_count` int(11) NOT NULL DEFAULT 0 COMMENT '点赞数量',
  `comment_count` int(11) NOT NULL DEFAULT 0 COMMENT '评论数量',
  `repost_count` int(11) NOT NULL DEFAULT 0 COMMENT '转发数量',
  `share_count` int(11) NOT NULL DEFAULT 0 COMMENT '分享数量',
  `view_count` int(11) NOT NULL DEFAULT 0 COMMENT '查看次数',
  `repost_moment_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '转发的动态id',
  `status` int(20) NOT NULL DEFAULT 1 COMMENT '状态（0：已屏蔽，1：正常，2：已删除）',
  `artist_id` bigint(20) NOT NULL COMMENT '演出者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 376 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '动态表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of moment
-- ----------------------------
INSERT INTO `moment` VALUES (375, 1, '1', '1', 1, 1, 1, 1, 1, 1, 1, 1, 1, '2021-08-21 22:10:32', '2021-08-21 22:10:33');

-- ----------------------------
-- Table structure for moment_image
-- ----------------------------
DROP TABLE IF EXISTS `moment_image`;
CREATE TABLE `moment_image`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `moment_id` bigint(20) NOT NULL COMMENT '动态id',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图片地址',
  `width` int(11) NOT NULL DEFAULT 0 COMMENT '宽',
  `height` int(11) NOT NULL DEFAULT 0 COMMENT '高',
  `artist_id` bigint(20) NOT NULL COMMENT '演出者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 220 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '动态-图片表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of moment_image
-- ----------------------------
INSERT INTO `moment_image` VALUES (219, 1, '1', 1, 1, 1, '2021-08-21 22:12:48', '2021-08-21 22:12:50');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `description` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '个人简介',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '0不使用1滚动2发现公告',
  `artist_id` bigint(20) NOT NULL COMMENT '演出者id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (3, '1', '1', 1, 1, '2021-08-21 22:16:32', '2021-08-21 22:16:33');

-- ----------------------------
-- Table structure for region_university
-- ----------------------------
DROP TABLE IF EXISTS `region_university`;
CREATE TABLE `region_university`  (
  `id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id用来唯一指示该组织',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `uni_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前几位表示城市+学校，后两位表示学校内的不同组织',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of region_university
-- ----------------------------

-- ----------------------------
-- Table structure for share
-- ----------------------------
DROP TABLE IF EXISTS `share`;
CREATE TABLE `share`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `page_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '页面类型',
  `moment_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '动态id',
  `profile_user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT 'profile用户id',
  `artist_id` bigint(20) NOT NULL COMMENT '演出者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分享表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of share
-- ----------------------------
INSERT INTO `share` VALUES (43, 1, '1', 1, 1, 1, '2021-08-21 22:19:05', '2021-08-21 22:19:06');

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作内容',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作时间',
  `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作人',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 190 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_log
-- ----------------------------
INSERT INTO `t_log` VALUES (80, '用户 admin 登录系统', '2021-05-25 16:42:07', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (81, '更新用户：admin ', '2021-05-25 16:42:19', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (82, '用户 admin 退出系统', '2021-05-25 16:42:29', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (83, '用户 admin 登录系统', '2021-05-25 16:42:31', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (84, '更新用户：jerry ', '2021-05-25 16:49:14', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (85, '用户 admin 登录系统', '2021-05-25 16:49:30', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (86, '用户 admin 登录系统', '2021-05-25 16:50:07', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (87, '更新用户：jerry ', '2021-05-25 16:50:21', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (88, '用户 admin 登录系统', '2021-05-26 16:35:40', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (89, '更新用户：jerry ', '2021-05-26 16:36:16', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (90, '更新用户：jack ', '2021-05-26 16:36:18', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (91, '删除用户 tom ', '2021-05-26 16:36:29', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (92, '删除用户 hello ', '2021-05-26 16:36:30', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (93, '删除用户 jack ', '2021-05-26 16:36:31', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (94, '删除用户 jerry ', '2021-05-26 16:36:32', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (95, '用户 admin 登录系统', '2021-05-31 12:18:16', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (96, '更新用户：admin ', '2021-05-31 12:19:48', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (97, '更新用户：admin ', '2021-05-31 12:19:57', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (98, '用户 admin 登录系统', '2021-06-01 16:46:09', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (99, '新增用户：zhang ', '2021-06-01 16:46:46', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (100, '新增用户：li ', '2021-06-01 16:47:12', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (101, '更新用户：admin ', '2021-06-01 16:47:19', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (102, '用户 admin 退出系统', '2021-06-01 16:47:22', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (103, '用户 admin 登录系统', '2021-06-01 16:47:24', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (104, '更新用户：li ', '2021-06-01 16:47:34', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (105, '更新用户：zhang ', '2021-06-01 16:47:35', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (106, '用户 admin 登录系统', '2021-06-07 12:32:17', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (107, '用户 admin 登录系统', '2021-06-07 14:16:00', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (108, '用户 admin 登录系统', '2021-06-07 14:34:26', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (109, '更新用户：li ', '2021-06-07 14:35:21', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (110, '更新用户：li ', '2021-06-07 14:35:23', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (111, '更新角色：超级管理员', '2021-06-07 14:35:41', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (112, '更新角色：超级管理员', '2021-06-07 14:35:45', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (113, '更新权限菜单：用户管理', '2021-06-07 14:37:58', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (114, '更新权限菜单：用户管理', '2021-06-07 14:38:08', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (115, '更新权限菜单：用户管理', '2021-06-07 14:38:13', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (116, '更新权限菜单：用户管理', '2021-06-07 14:38:21', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (117, '用户 admin 登录系统', '2021-06-07 14:57:29', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (118, '更新角色：超级管理员', '2021-06-07 14:57:43', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (119, '用户 admin 登录系统', '2021-06-09 23:19:38', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (120, '删除权限菜单：学生管理', '2021-06-09 23:19:50', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (121, '用户 admin 登录系统', '2021-06-12 10:18:54', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (122, '更新角色：超级管理员', '2021-06-12 10:30:15', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (123, '用户 admin 登录系统', '2021-06-12 13:55:01', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (124, '用户 admin 登录系统', '2021-06-27 10:44:12', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (125, '更新角色：超级管理员', '2021-06-27 10:44:26', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (126, '用户 admin 登录系统', '2021-06-27 11:15:11', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (127, '更新角色：超级管理员', '2021-06-27 11:15:17', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (128, '用户 admin 登录系统', '2021-06-27 11:18:36', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (129, '用户 admin 登录系统', '2021-06-27 11:41:59', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (130, '更新角色：超级管理员', '2021-06-27 11:42:04', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (131, '用户 admin 登录系统', '2021-06-27 11:49:27', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (132, '用户 admin 登录系统', '2021-08-21 12:02:30', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (133, '用户 admin 登录系统', '2021-08-21 16:54:29', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (134, '用户 admin 登录系统', '2021-08-21 18:17:47', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (135, '删除权限菜单：图书管理', '2021-08-21 18:19:18', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (136, '更新角色：超级管理员', '2021-08-21 18:19:31', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (137, '更新角色：超级管理员', '2021-08-21 18:19:39', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (138, '更新角色：超级管理员', '2021-08-21 18:19:42', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (139, '新增用户：Peng ', '2021-08-21 18:25:46', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (140, '更新用户：Peng ', '2021-08-21 18:26:12', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (141, '用户 admin 登录系统', '2021-08-21 18:43:25', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (142, '更新角色：超级管理员', '2021-08-21 18:43:34', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (143, '用户 admin 登录系统', '2021-08-21 20:25:50', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (144, '更新权限菜单：角色管理', '2021-08-21 20:26:38', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (145, '更新权限菜单：微信端用户管理管理', '2021-08-21 20:26:48', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (146, '更新权限菜单：微信端用户管理', '2021-08-21 20:26:54', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (147, '更新用户：Peng ', '2021-08-21 20:28:19', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (148, '用户 admin 登录系统', '2021-08-21 21:00:14', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (149, '更新角色：超级管理员', '2021-08-21 21:00:48', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (150, '更新权限菜单：微信端用户管理', '2021-08-21 21:00:55', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (151, '用户 admin 登录系统', '2021-08-21 21:24:40', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (152, '用户 admin 登录系统', '2021-08-21 21:24:40', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (153, '用户 admin 登录系统', '2021-08-21 21:28:42', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (154, '用户 admin 登录系统', '2021-08-21 21:29:49', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (155, '用户 admin 登录系统', '2021-08-21 21:37:01', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (156, '用户 admin 登录系统', '2021-08-21 21:52:23', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (157, '更新角色：超级管理员', '2021-08-21 21:52:42', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (158, '用户 admin 登录系统', '2021-08-21 21:55:46', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (159, '用户 admin 登录系统', '2021-08-21 22:01:12', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (160, '更新角色：超级管理员', '2021-08-21 22:01:26', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (161, '用户 admin 登录系统', '2021-08-21 22:04:16', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (162, '更新角色：超级管理员', '2021-08-21 22:04:21', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (163, '用户 admin 登录系统', '2021-08-21 22:06:44', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (164, '更新角色：超级管理员', '2021-08-21 22:06:50', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (165, '用户 admin 登录系统', '2021-08-21 22:10:15', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (166, '更新角色：超级管理员', '2021-08-21 22:10:21', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (167, '用户 admin 登录系统', '2021-08-21 22:12:24', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (168, '更新角色：超级管理员', '2021-08-21 22:12:42', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (169, '用户 admin 登录系统', '2021-08-21 22:16:21', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (170, '用户 admin 登录系统', '2021-08-21 22:16:21', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (171, '更新角色：超级管理员', '2021-08-21 22:16:26', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (172, '用户 admin 登录系统', '2021-08-21 22:18:53', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (173, '更新角色：超级管理员', '2021-08-21 22:18:59', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (174, '用户 admin 登录系统', '2021-08-21 22:20:40', 'admin', '127.0.0.1');
INSERT INTO `t_log` VALUES (175, '更新角色：超级管理员', '2021-08-21 22:20:46', 'admin', '127.0.0.1');
INSERT INTO `t_log` VALUES (176, '用户 admin 登录系统', '2021-08-21 22:22:30', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (177, '更新角色：超级管理员', '2021-08-21 22:23:01', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (178, '用户 admin 登录系统', '2021-08-21 22:25:58', 'admin', '127.0.0.1');
INSERT INTO `t_log` VALUES (179, '更新角色：超级管理员', '2021-08-21 22:26:14', 'admin', '127.0.0.1');
INSERT INTO `t_log` VALUES (180, '更新角色：超级管理员', '2021-08-21 22:27:18', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (181, '用户 admin 登录系统', '2021-08-21 22:28:39', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (182, '更新角色：超级管理员', '2021-08-21 22:28:46', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (183, '用户 admin 登录系统', '2021-08-21 22:36:28', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (184, '更新角色：超级管理员', '2021-08-21 22:36:38', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (185, '用户 admin 登录系统', '2021-08-21 22:42:45', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (186, '更新角色：超级管理员', '2021-08-21 22:42:57', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (187, '用户 admin 登录系统', '2021-08-21 22:45:55', 'admin', '127.0.0.1');
INSERT INTO `t_log` VALUES (188, '更新角色：超级管理员', '2021-08-21 22:46:30', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `t_log` VALUES (189, '用户 admin 登录系统', '2021-08-22 10:09:07', 'admin', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for t_message
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评论人',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论时间',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父ID',
  `foreign_id` bigint(20) NULL DEFAULT 0 COMMENT '关联id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '留言表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_message
-- ----------------------------
INSERT INTO `t_message` VALUES (20, '哈哈哈哈哈！！！微信端用户搞定啦！！', 'admin', '2021-08-21 21:40:36', NULL, 0);

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES (7, '后端开发', '争取8月23日之前弄完！！', '2021-08-21 21:44:12');
INSERT INTO `t_notice` VALUES (8, '哈哈哈哈！！', '终于修复完bug了', '2021-08-21 22:36:48');

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 's-data' COMMENT '图标',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES (1, '用户管理', '用户管理', 'user', 'user-solid');
INSERT INTO `t_permission` VALUES (2, '角色管理', '角色管理', 'role', 's-cooperation');
INSERT INTO `t_permission` VALUES (3, '权限管理', '权限管理', 'permission', 'menu');
INSERT INTO `t_permission` VALUES (24, '日志管理', '日志管理', 'log', 'notebook-2');
INSERT INTO `t_permission` VALUES (25, '在线留言', '在线留言', 'message', 'message');
INSERT INTO `t_permission` VALUES (34, '微信端用户管理', '微信端用户管理', 'wxUser', 's-data');
INSERT INTO `t_permission` VALUES (36, '演出管理', '演出管理', 'activity', 's-data');
INSERT INTO `t_permission` VALUES (37, '评论管理', '评论管理', 'comment', 's-data');
INSERT INTO `t_permission` VALUES (39, '意见反馈管理', '意见反馈管理', 'feedback', 's-data');
INSERT INTO `t_permission` VALUES (40, '关注-关联管理', '关注-关联管理', 'follow_relation', 's-data');
INSERT INTO `t_permission` VALUES (41, '动态管理', '动态管理', 'moment', 's-data');
INSERT INTO `t_permission` VALUES (42, '动态-图片管理', '动态-图片管理', 'moment_image', 's-data');
INSERT INTO `t_permission` VALUES (44, '分享管理', '分享管理', 'share', 's-data');
INSERT INTO `t_permission` VALUES (45, '标签管理', '标签管理', 'tag', 's-data');
INSERT INTO `t_permission` VALUES (46, '标签-关联管理', '标签-关联管理', 'tag_relation', 's-data');
INSERT INTO `t_permission` VALUES (47, '微信端公告管理', '微信端公告管理', 'notice', 's-data');
INSERT INTO `t_permission` VALUES (48, '系统公告管理', '系统公告管理', 't_notice', 's-data');
INSERT INTO `t_permission` VALUES (50, '话题-动态-关联管理', '话题-动态-关联管理', 'topic_relation', 's-data');
INSERT INTO `t_permission` VALUES (51, '置顶管理', '置顶管理', 'zhiding_moment', 's-data');
INSERT INTO `t_permission` VALUES (52, '话题管理', '话题管理', 'topic', 's-data');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `permission` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限列表',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, '超级管理员', '所有权限', '[1,2,27,30,24,32,33,34,36,37,39,40,41,42,43,44,45,46,47,48,49,50,51,52]');
INSERT INTO `t_role` VALUES (2, '普通用户', '部分权限', '[]');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', 'admin', '管理员', '111124444', '13978786565', '1622537239707', '[1]', '北京', NULL);
INSERT INTO `t_user` VALUES (2, 'zhang', '123456', '张三', 'zhang@qq.com', '13089897878', NULL, '[2]', '北京', 24);
INSERT INTO `t_user` VALUES (3, 'li', '123456', '李四', 'li@qq.com', '13989897898', NULL, '[2]', '南京', 22);
INSERT INTO `t_user` VALUES (4, 'Peng', '123456', 'Rika', '201250214@smail.nju.edu.cn', '18672585215', NULL, '[2]', '南京', 18);

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签名称',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人id',
  `fans_count` int(11) NOT NULL DEFAULT 0 COMMENT '粉丝人数',
  `artist_id` bigint(20) NOT NULL COMMENT '演出者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (1, '1', 1, 1, 1, '2021-08-21 22:20:53', '2021-08-21 22:20:54');

-- ----------------------------
-- Table structure for tag_relation
-- ----------------------------
DROP TABLE IF EXISTS `tag_relation`;
CREATE TABLE `tag_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tag_id` bigint(20) NOT NULL COMMENT '标签id',
  `entity_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '目标类型（MOMENT：动态）',
  `entity_id` bigint(20) NOT NULL COMMENT '目标id',
  `artist_id` bigint(20) NOT NULL COMMENT '演出者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签-关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tag_relation
-- ----------------------------
INSERT INTO `tag_relation` VALUES (1, 1, '1', 1, 1, '2021-08-21 22:24:56', '2021-08-21 22:24:57');

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '话题名称',
  `cover_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '封面url',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人id',
  `last_update_user_id` bigint(20) NOT NULL COMMENT '最新更新者id',
  `artist_id` bigint(20) NOT NULL COMMENT '演出者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_name`(`name`, `artist_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '话题表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of topic
-- ----------------------------
INSERT INTO `topic` VALUES (3, '1', '1', 1, 1, 1, '2021-08-21 22:43:06', '2021-08-21 22:43:08');

-- ----------------------------
-- Table structure for topic_relation
-- ----------------------------
DROP TABLE IF EXISTS `topic_relation`;
CREATE TABLE `topic_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `topic_id` bigint(20) NOT NULL COMMENT '话题id',
  `moment_id` bigint(20) NOT NULL COMMENT '动态id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `artist_id` bigint(20) NOT NULL COMMENT '演出者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '话题-动态-关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of topic_relation
-- ----------------------------
INSERT INTO `topic_relation` VALUES (3, 1, 1, 1, 1, '2021-08-21 22:43:15', '2021-08-21 22:43:16');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `gender` int(11) NOT NULL DEFAULT 1 COMMENT '性别（0：女，1：男）',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '头像url',
  `description` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '个人简介',
  `mini_weixin_union_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '微信小程序unionId',
  `mini_weixin_open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '微信小程序openId',
  `mini_qq_union_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'QQ小程序unionId',
  `mini_qq_open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'QQ小程序openId',
  `country` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '国家',
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '省',
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '市',
  `university` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '未认证' COMMENT '大学',
  `follow_count` int(11) NOT NULL DEFAULT 0 COMMENT '关注人数',
  `fans_count` int(11) NOT NULL DEFAULT 0 COMMENT '粉丝人数',
  `letter_unread` int(11) NOT NULL DEFAULT 0 COMMENT '私信未读',
  `comment_unread` int(11) NOT NULL DEFAULT 0 COMMENT '评论未读',
  `at_unread` int(11) NOT NULL DEFAULT 0 COMMENT '@未读',
  `notification_unread` int(11) NOT NULL DEFAULT 0 COMMENT '通知未读',
  `artist_id` bigint(20) NOT NULL DEFAULT 100000000 COMMENT '演出者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `uni_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '大学id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100000029 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (100000027, '1', 1, '1', '1', '1213', '123', '123', '4124', '1', '1', '1', '1', 0, 0, 0, 0, 0, 0, 100000000, '2021-08-21 21:23:42', '2021-08-21 21:23:42', '1');
INSERT INTO `user` VALUES (100000028, 'Peng', 1, '图片', 'hello world！', '123', '123', '321', '321', '中国', '江苏省', '南京市', '南京大学', 0, 0, 0, 0, 0, 0, 100000000, '2021-08-21 21:25:02', '2021-08-21 21:25:02', '1');

-- ----------------------------
-- Table structure for zhiding_moment
-- ----------------------------
DROP TABLE IF EXISTS `zhiding_moment`;
CREATE TABLE `zhiding_moment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '发送者id',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型（TEXT：纯文本，IMAGE：图片，VIDEO：视频）',
  `text_content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文本内容',
  `activity_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '活动id',
  `like_count` int(11) NOT NULL DEFAULT 0 COMMENT '点赞数量',
  `comment_count` int(11) NOT NULL DEFAULT 0 COMMENT '评论数量',
  `repost_count` int(11) NOT NULL DEFAULT 0 COMMENT '转发数量',
  `share_count` int(11) NOT NULL DEFAULT 0 COMMENT '分享数量',
  `view_count` int(11) NOT NULL DEFAULT 0 COMMENT '查看次数',
  `repost_moment_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '转发的动态id',
  `status` int(20) NOT NULL DEFAULT 1 COMMENT '状态（0：已屏蔽，1：正常，2：已删除）',
  `artist_id` bigint(20) NOT NULL COMMENT '演出者id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '动态表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of zhiding_moment
-- ----------------------------
INSERT INTO `zhiding_moment` VALUES (1, 1, '1', '1', 1, 1, 1, 1, 1, 1, 1, 1, 1, '2021-08-21 22:43:28', '2021-08-21 22:43:29');

SET FOREIGN_KEY_CHECKS = 1;
