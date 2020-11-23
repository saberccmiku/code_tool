/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.243
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : 192.168.0.243:3306
 Source Schema         : code_gen_tool

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 15/05/2020 13:36:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for 项目表
-- ----------------------------
DROP TABLE IF EXISTS `项目表`;
CREATE TABLE `项目表`  (
  `唯一码` bigint(255) NOT NULL COMMENT '主键',
  `名称` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `地址` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`唯一码`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of 项目表
-- ----------------------------
INSERT INTO `项目表` VALUES (1, '项目一', '地址');
INSERT INTO `项目表` VALUES (2, '项目二', '地址二');

-- ----------------------------
-- Table structure for t_control_lib
-- ----------------------------
DROP TABLE IF EXISTS `t_control_lib`;
CREATE TABLE `t_control_lib`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `img` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '缩略图',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件类别',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件名称',
  `props` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '可编辑属性',
  `events` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '可编辑事件',
  `code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '组件代码',
  `add_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '新增人',
  `add_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '新增时间',
  `edit_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '修改人',
  `edit_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '组件库表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_css_lib
-- ----------------------------
DROP TABLE IF EXISTS `t_css_lib`;
CREATE TABLE `t_css_lib`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '样式名称',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '样式描述',
  `code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '代码',
  `add_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '新增人',
  `add_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '新增时间',
  `edit_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '修改人',
  `edit_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '样式文件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_css_lib
-- ----------------------------
INSERT INTO `t_css_lib` VALUES (5, '222', '22', '22', '222', '2020-04-16 08:00:00', NULL, NULL);
INSERT INTO `t_css_lib` VALUES (6, '111', '111', '111', '111', '2020-04-16 08:00:00', NULL, NULL);
INSERT INTO `t_css_lib` VALUES (7, '3333', '333', '3333', '33333', '2020-04-16 08:00:00', NULL, NULL);
INSERT INTO `t_css_lib` VALUES (8, '白色样式', '白色', 'body{\n  padding: 0;\n}', '张三', '2020-04-20 08:00:00', NULL, NULL);

-- ----------------------------
-- Table structure for t_db_info
-- ----------------------------
DROP TABLE IF EXISTS `t_db_info`;
CREATE TABLE `t_db_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_id` int(11) NULL DEFAULT NULL COMMENT '数据库类型id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数据库名称',
  `sort` smallint(3) NULL DEFAULT NULL COMMENT '排序',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `port` int(5) NULL DEFAULT NULL COMMENT '端口',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `add_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '新增人',
  `add_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '新增时间',
  `edit_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '修改人',
  `edit_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '数据库信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_db_info
-- ----------------------------
INSERT INTO `t_db_info` VALUES (5, 1, 'code_gen_tool', 1, '192.168.0.243', 3306, 'sa', 'tianheng2018', '黄为', '2020-04-16 08:00:00', '', NULL);
INSERT INTO `t_db_info` VALUES (6, 3, 'YZCH', 2, '192.168.0.243', 1521, 'YZCH', 'YZCH', '黄为', '2020-05-12 10:49:59', '', '2020-05-12 10:49:59');
INSERT INTO `t_db_info` VALUES (7, 1, 'test', 1, '127.0.0.1', 3306, 'root', '123456', 'hw', '2020-05-11 08:00:00', 'hh', '2020-05-12 08:00:00');
INSERT INTO `t_db_info` VALUES (9, 1, 'tang4j', 3, '192.168.0.243', 3306, 'sa', 'tianheng2018', '黄为', '2020-05-11 08:00:00', '黄为', '2020-05-11 08:00:00');

-- ----------------------------
-- Table structure for t_db_type
-- ----------------------------
DROP TABLE IF EXISTS `t_db_type`;
CREATE TABLE `t_db_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数据库类型名称',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数据库种类',
  `driver` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '驱动包名称',
  `add_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '新增人',
  `add_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '新增时间',
  `edit_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '修改人',
  `edit_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '数据库类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_db_type
-- ----------------------------
INSERT INTO `t_db_type` VALUES (1, 'Mysql', '关系型数据库', 'com.mysql.jdbc.Driver', '张三', '2020-04-15 08:00:00', NULL, NULL);
INSERT INTO `t_db_type` VALUES (2, 'Sql Server', '非关系型数据库', 'com.microsoft.sqlserver.jdbc.SQLServerDriver', '王五', '2020-04-15 08:00:00', '李四', '2020-04-15 08:00:00');
INSERT INTO `t_db_type` VALUES (3, 'Oracle', '关系型数据库', 'oracle.jdbc.OracleDriver', '张三', '2020-04-15 09:10:10', NULL, '2020-04-15 09:10:11');
INSERT INTO `t_db_type` VALUES (4, 'PostgreSQL', '关系型数据库', 'org.postgresql.Driver', '老刘', '2020-04-08 09:10:58', NULL, '2020-04-15 09:11:01');

-- ----------------------------
-- Table structure for t_form_model
-- ----------------------------
DROP TABLE IF EXISTS `t_form_model`;
CREATE TABLE `t_form_model`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '模型类别',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '模型名称',
  `props` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '属性',
  `add_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '新增人',
  `add_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '新增时间',
  `edit_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '修改人',
  `edit_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '表单模型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_function_lib
-- ----------------------------
DROP TABLE IF EXISTS `t_function_lib`;
CREATE TABLE `t_function_lib`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '函数类别',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '函数名',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '函数代码',
  `add_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '新增人',
  `add_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '新增时间',
  `edit_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '修改人',
  `edit_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '函数库表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_function_lib
-- ----------------------------
INSERT INTO `t_function_lib` VALUES (4, '校验函数', '3333', '3333', '33333', '3333', '2020-04-16 08:00:00', 'yyyyy', '2020-04-16 08:00:00');
INSERT INTO `t_function_lib` VALUES (5, '获取接口数据函数', '1111', '111', '111', '111', '2020-04-16 08:00:00', NULL, NULL);
INSERT INTO `t_function_lib` VALUES (6, '获取接口数据函数', '222', '22', '222', '222', '2020-04-16 08:00:00', NULL, NULL);

-- ----------------------------
-- Table structure for t_inter_record
-- ----------------------------
DROP TABLE IF EXISTS `t_inter_record`;
CREATE TABLE `t_inter_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `db_id` int(11) NULL DEFAULT NULL COMMENT '数据库id',
  `table_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '表',
  `add_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '添加人',
  `add_time` timestamp(0) NULL DEFAULT NULL COMMENT '添加时间',
  `svn_submit` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'svn提交状态(已提交、未提交)',
  `package_config` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件存储路径，json数组格式',
  `inter_config` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '接口描述，json数组',
  `project_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 194 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '已生成的接口记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_inter_record
-- ----------------------------
INSERT INTO `t_inter_record` VALUES (193, 5, 'ts_test_user', 'fjy', '2020-05-15 11:38:26', '未提交', '{\"controller\":\"controller\",\"entity\":\"model\",\"mapper\":\"dao\",\"outputDir\":\"D:\\\\\\\\IdeaProjects\\\\\\\\code_gen_tool\\\\\\\\codetool\\\\\",\"packageName\":\"com.tianheng.codetool\",\"requestEntity\":\"model.request\",\"service\":\"service\",\"xml\":\"dao\"}', '[{\"interName\":\"新增\",\"interUri\":\"/tsTestUser/insert\"},{\"interName\":\"批量新增\",\"interUri\":\"/tsTestUser/insertBatch\"},{\"interName\":\"修改\",\"interUri\":\"/tsTestUser/update\"},{\"interName\":\"批量修改\",\"interUri\":\"/tsTestUser/updateBatch\"},{\"interName\":\"删除\",\"interUri\":\"/tsTestUser/delete/{id}\"},{\"interName\":\"批量删除\",\"interUri\":\"/tsTestUser/deleteBatch\"},{\"interName\":\"详情\",\"interUri\":\"/tsTestUser/detail/{id}\"},{\"interName\":\"查询\",\"interUri\":\"/tsTestUser/list\"},{\"interName\":\"分页查询\",\"interUri\":\"/tsTestUser/pageList\"},{\"interName\":\"条件修改\",\"interUri\":\"/tsTestUser/editByCrit\"},{\"interName\":\"条件删除\",\"interUri\":\"/tsTestUser/delByCrit\"}]', NULL);

-- ----------------------------
-- Table structure for t_inter_rule
-- ----------------------------
DROP TABLE IF EXISTS `t_inter_rule`;
CREATE TABLE `t_inter_rule`  (
  `id` int(11) NOT NULL,
  `type` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型唯一',
  `regex` varchar(355) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '正则表达式',
  `abbreviation` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '简称',
  `message` varchar(355) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '提示信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ts_service_fee
-- ----------------------------
DROP TABLE IF EXISTS `ts_service_fee`;
CREATE TABLE `ts_service_fee`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `date` datetime(0) NULL DEFAULT NULL COMMENT '日期',
  `money` decimal(10, 4) NULL DEFAULT NULL COMMENT '金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '服务费表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ts_test_table
-- ----------------------------
DROP TABLE IF EXISTS `ts_test_table`;
CREATE TABLE `ts_test_table`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '编码',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '详细信息',
  `number` decimal(11, 4) NULL DEFAULT NULL COMMENT '数字类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '测试表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ts_test_table
-- ----------------------------
INSERT INTO `ts_test_table` VALUES (1, '测试一', 'test1', 'test1@qq.com', 4.0000);
INSERT INTO `ts_test_table` VALUES (2, '测试二', 'test2', 'test2@qq.com', 5.0000);
INSERT INTO `ts_test_table` VALUES (3, '测试三', 'test3', 'test3@qq.com', 6.0000);
INSERT INTO `ts_test_table` VALUES (38, '测试新增二', 'csxz2', 'csxz2@qq.com', 1.0000);
INSERT INTO `ts_test_table` VALUES (39, '测试新增三', 'csxz3', 'csxz3@qq.com', 2.0000);

-- ----------------------------
-- Table structure for ts_test_user
-- ----------------------------
DROP TABLE IF EXISTS `ts_test_user`;
CREATE TABLE `ts_test_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '编码',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '详细信息',
  `number` decimal(11, 4) NULL DEFAULT NULL COMMENT '数字类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '测试表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ts_test_user
-- ----------------------------
INSERT INTO `ts_test_user` VALUES (1, '测试1', 'test', '测试信息', NULL);
INSERT INTO `ts_test_user` VALUES (2, '测试2', 'test2', '测试信息2', NULL);
INSERT INTO `ts_test_user` VALUES (3, '测试3', 'test3', '测试信息3', NULL);
INSERT INTO `ts_test_user` VALUES (4, '测试1', 'test1', '123456@qq.com', NULL);
INSERT INTO `ts_test_user` VALUES (5, '测试22', 'test22', '123456@qq.com', NULL);

SET FOREIGN_KEY_CHECKS = 1;
