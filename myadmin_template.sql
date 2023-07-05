/*
 Navicat Premium Data Transfer

 Source Server         : mysql_local
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : myadmin_template

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 05/07/2023 11:06:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_used` timestamp(0) NOT NULL,
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint(0) NULL DEFAULT NULL COMMENT '上级菜单ID',
  `sub_count` int(0) NULL DEFAULT 0 COMMENT '子菜单数目',
  `type` int(0) NULL DEFAULT NULL COMMENT '菜单类型',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单标题',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件名称',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件',
  `menu_sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接地址',
  `i_frame` int(0) NULL DEFAULT NULL COMMENT '是否外链',
  `cache` int(0) NULL DEFAULT 0 COMMENT '缓存',
  `hidden` int(0) NULL DEFAULT 0 COMMENT '隐藏',
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`menu_id`) USING BTREE,
  UNIQUE INDEX `uniq_title`(`title`) USING BTREE,
  UNIQUE INDEX `uniq_name`(`name`) USING BTREE,
  INDEX `inx_pid`(`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 116 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, NULL, 7, 0, '系统管理', NULL, NULL, 1, 'system', 'system', 0, 0, 0, NULL, NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (2, 1, 3, 1, '用户管理', 'User', 'system/user/index', 2, 'peoples', 'user', 0, 0, 0, 'user:list', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (3, 1, 3, 1, '角色管理', 'Role', 'system/role/index', 3, 'role', 'role', 0, 0, 0, 'roles:list', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (5, 1, 3, 1, '菜单管理', 'Menu', 'system/menu/index', 5, 'menu', 'menu', 0, 0, 0, 'menu:list', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (6, NULL, 5, 0, '系统监控', NULL, NULL, 10, 'monitor', 'monitor', 0, 0, 0, NULL, NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (7, 6, 0, 1, '操作日志', 'Log', 'monitor/log/index', 10, 'log', 'logs', 0, 0, 0, 'logs:list', NULL, 'admin', '2022-12-18 15:09:29', '2022-06-06 13:11:57');
INSERT INTO `sys_menu` VALUES (9, 6, 0, 1, 'SQL监控', 'Sql', 'monitor/sql/index', 18, 'sqlMonitor', 'druid', 0, 0, 0, NULL, NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (10, NULL, 5, 0, '组件管理', NULL, NULL, 50, 'zujian', 'components', 0, 0, 0, NULL, NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (11, 10, 0, 1, '图标库', 'Icons', 'components/icons/index', 51, 'icon', 'icon', 0, 0, 0, NULL, NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (15, 10, 0, 1, '富文本', 'Editor', 'components/Editor', 52, 'fwb', 'tinymce', 0, 0, 0, NULL, NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (19, 36, 0, 1, '支付宝工具', 'AliPay', 'tools/aliPay/index', 37, 'alipay', 'aliPay', 0, 0, 0, 'alipay', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (21, NULL, 2, 0, '多级菜单', NULL, '', 900, 'menu', 'nested', 0, 0, 0, NULL, NULL, 'admin', '2022-12-18 15:09:29', '2022-12-18 15:09:29');
INSERT INTO `sys_menu` VALUES (22, 21, 2, 1, '二级菜单1', NULL, '', 999, 'menu', 'menu1', 0, 0, 0, NULL, NULL, 'admin', '2022-12-18 15:09:29', '2022-12-18 15:09:29');
INSERT INTO `sys_menu` VALUES (23, 21, 0, 1, '二级菜单2', NULL, 'nested/menu2/index', 999, 'menu', 'menu2', 0, 0, 0, NULL, NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (24, 22, 1, 1, '三级菜单1', 'Test', '', 999, 'menu', 'menu1-1', 0, 0, 0, NULL, NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (25, 24, 0, 1, '四级菜单1', 'Menu1-1-1', '/nested/menu1/menu1-1/menu1-1-1/index', 999, 'menu', 'menu1-1-1', 0, 0, 0, NULL, NULL, NULL, '2022-12-18 15:10:29', NULL);
INSERT INTO `sys_menu` VALUES (27, 22, 0, 1, '三级菜单2', NULL, 'nested/menu1/menu1-2/index', 999, 'menu', 'menu1-2', 0, 0, 0, NULL, NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (28, 1, 3, 1, '任务调度', 'Timing', 'system/timing/index', 999, 'timing', 'timing', 0, 0, 0, 'timing:list', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (30, 36, 0, 1, '会议预约', 'MeetingIndex', 'meeting/index', 32, 'meeting', 'meeting', 0, 1, 0, 'meeting', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (32, 6, 0, 1, '异常日志', 'ErrorLog', 'monitor/log/errorLog', 11, 'error', 'errorLog', 0, 0, 0, 'logs:list', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (33, 10, 0, 1, 'Markdown', 'Markdown', 'components/MarkDown', 53, 'markdown', 'markdown', 0, 0, 0, NULL, NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (34, 36, 0, 1, 'HuTool工具文档', 'HuTool', '', 33, 'education', 'https://www.hutool.cn/docs/#/', 1, 0, 0, NULL, NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (35, 1, 3, 1, '部门管理', 'Dept', 'system/dept/index', 6, 'dept', 'dept', 0, 0, 1, 'dept:list', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (36, NULL, 7, 0, '系统工具', NULL, '', 30, 'sys-tools', 'sys-tools', 0, 0, 0, NULL, NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (37, 1, 3, 1, '岗位管理', 'Job', 'system/job/index', 7, 'Steve-Jobs', 'job', 0, 0, 1, 'job:list', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (38, 36, 0, 1, '接口文档', 'Swagger', '', 36, 'swagger', 'http://127.0.0.1:8088/swagger-ui.html', 1, 0, 0, NULL, NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (39, 1, 3, 1, '字典管理', 'Dict', 'system/dict/index', 8, 'dictionary', 'dict', 0, 0, 1, 'dict:list', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (40, 36, 0, 1, 'Kibana可视化', 'Kibana', '', 34, 'codeConsole', 'http://192.168.227.130:5601/', 1, 0, 0, NULL, NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (41, 7, 0, 2, '操作日志清空', NULL, '', 2, '', '', 0, 0, 0, 'infoLog:del', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (42, 32, 0, 2, '异常日志清空', NULL, '', 2, '', '', 0, 0, 0, 'errorLog:del', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (44, 2, 0, 2, '用户新增', NULL, '', 2, '', '', 0, 0, 0, 'user:add', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (45, 2, 0, 2, '用户编辑', NULL, '', 3, '', '', 0, 0, 0, 'user:edit', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (46, 2, 0, 2, '用户删除', NULL, '', 4, '', '', 0, 0, 0, 'user:del', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (48, 3, 0, 2, '角色创建', NULL, '', 2, '', '', 0, 0, 0, 'role:add', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (49, 3, 0, 2, '角色修改', NULL, '', 3, '', '', 0, 0, 0, 'role:edit', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (50, 3, 0, 2, '角色删除', NULL, '', 4, '', '', 0, 0, 0, 'role:del', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (52, 5, 0, 2, '菜单新增', NULL, '', 2, '', '', 0, 0, 0, 'menu:add', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (53, 5, 0, 2, '菜单编辑', NULL, '', 3, '', '', 0, 0, 0, 'menu:edit', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (54, 5, 0, 2, '菜单删除', NULL, '', 4, '', '', 0, 0, 0, 'menu:del', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (56, 35, 0, 2, '部门新增', NULL, '', 2, '', '', 0, 0, 0, 'dept:add', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (57, 35, 0, 2, '部门编辑', NULL, '', 3, '', '', 0, 0, 0, 'dept:edit', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (58, 35, 0, 2, '部门删除', NULL, '', 4, '', '', 0, 0, 0, 'dept:del', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (60, 37, 0, 2, '岗位新增', NULL, '', 2, '', '', 0, 0, 0, 'job:add', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (61, 37, 0, 2, '岗位编辑', NULL, '', 3, '', '', 0, 0, 0, 'job:edit', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (62, 37, 0, 2, '岗位删除', NULL, '', 4, '', '', 0, 0, 0, 'job:del', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (64, 39, 0, 2, '字典新增', NULL, '', 2, '', '', 0, 0, 0, 'dict:add', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (65, 39, 0, 2, '字典编辑', NULL, '', 3, '', '', 0, 0, 0, 'dict:edit', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (66, 39, 0, 2, '字典删除', NULL, '', 4, '', '', 0, 0, 0, 'dict:del', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (73, 28, 0, 2, '任务新增', NULL, '', 2, '', '', 0, 0, 0, 'timing:add', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (74, 28, 0, 2, '任务编辑', NULL, '', 3, '', '', 0, 0, 0, 'timing:edit', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (75, 28, 0, 2, '任务删除', NULL, '', 4, '', '', 0, 0, 0, 'timing:del', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (77, 18, 0, 2, '上传文件', NULL, '', 2, '', '', 0, 0, 0, 'storage:add', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (78, 18, 0, 2, '文件编辑', NULL, '', 3, '', '', 0, 0, 0, 'storage:edit', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (79, 18, 0, 2, '文件删除', NULL, '', 4, '', '', 0, 0, 0, 'storage:del', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (82, 36, 0, 1, '生成配置', 'GeneratorConfig', 'generator/config', 38, 'dev', 'generator/config/:tableName', 0, 1, 1, '', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (90, NULL, 5, 1, '运维管理', 'Mnt', '', 20, 'mnt', 'mnt', 0, 0, 1, NULL, NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (92, 90, 3, 1, '服务器', 'ServerDeploy', 'mnt/server/index', 22, 'server', 'mnt/serverDeploy', 0, 0, 0, 'serverDeploy:list', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (93, 90, 3, 1, '应用管理', 'App', 'mnt/app/index', 23, 'app', 'mnt/app', 0, 0, 0, 'app:list', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (94, 90, 3, 1, '部署管理', 'Deploy', 'mnt/deploy/index', 24, 'deploy', 'mnt/deploy', 0, 0, 0, 'deploy:list', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (97, 90, 1, 1, '部署备份', 'DeployHistory', 'mnt/deployHistory/index', 25, 'backup', 'mnt/deployHistory', 0, 0, 0, 'deployHistory:list', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (98, 90, 3, 1, '数据库管理', 'Database', 'mnt/database/index', 26, 'database', 'mnt/database', 0, 0, 0, 'database:list', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (102, 97, 0, 2, '删除', NULL, '', 999, '', '', 0, 0, 0, 'deployHistory:del', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (103, 92, 0, 2, '服务器新增', NULL, '', 999, '', '', 0, 0, 0, 'serverDeploy:add', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (104, 92, 0, 2, '服务器编辑', NULL, '', 999, '', '', 0, 0, 0, 'serverDeploy:edit', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (105, 92, 0, 2, '服务器删除', NULL, '', 999, '', '', 0, 0, 0, 'serverDeploy:del', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (106, 93, 0, 2, '应用新增', NULL, '', 999, '', '', 0, 0, 0, 'app:add', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (107, 93, 0, 2, '应用编辑', NULL, '', 999, '', '', 0, 0, 0, 'app:edit', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (108, 93, 0, 2, '应用删除', NULL, '', 999, '', '', 0, 0, 0, 'app:del', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (109, 94, 0, 2, '部署新增', NULL, '', 999, '', '', 0, 0, 0, 'deploy:add', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (110, 94, 0, 2, '部署编辑', NULL, '', 999, '', '', 0, 0, 0, 'deploy:edit', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (111, 94, 0, 2, '部署删除', NULL, '', 999, '', '', 0, 0, 0, 'deploy:del', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (112, 98, 0, 2, '数据库新增', NULL, '', 999, '', '', 0, 0, 0, 'database:add', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (113, 98, 0, 2, '数据库编辑', NULL, '', 999, '', '', 0, 0, 0, 'database:edit', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (114, 98, 0, 2, '数据库删除', NULL, '', 999, '', '', 0, 0, 0, 'database:del', NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (116, 36, 0, 1, '生成预览', 'Preview', 'generator/preview', 999, 'java', 'generator/preview/:tableName', 0, 1, 1, NULL, NULL, NULL, '2022-12-18 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (117, 2, 0, 2, '分配角色', NULL, '', 5, '', '', 0, 0, 0, 'user:allocRole', NULL, NULL, '2022-12-23 15:09:29', NULL);
INSERT INTO `sys_menu` VALUES (118, 3, 0, 2, '分配菜单', NULL, '', 5, '', '', 0, 0, 0, 'role:allocMenu', NULL, NULL, '2022-12-18 15:09:29', NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  `level` int(0) NULL DEFAULT 1 COMMENT '级别，越小权限越大',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '由谁创建',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '由谁更新',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `sys_role_name_uindex`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'admin', 1, '超级管理员角色', NULL, NULL, '2022-09-24 20:44:24', '2022-09-24 20:44:26');
INSERT INTO `sys_role` VALUES (2, 'normalUser1', 2, '普通用户', NULL, NULL, '2022-10-13 17:17:02', NULL);

-- ----------------------------
-- Table structure for sys_roles_menus
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_menus`;
CREATE TABLE `sys_roles_menus`  (
  `menu_id` bigint(0) NOT NULL COMMENT '菜单id',
  `role_id` bigint(0) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`menu_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_roles_menus
-- ----------------------------
INSERT INTO `sys_roles_menus` VALUES (1, 1);
INSERT INTO `sys_roles_menus` VALUES (1, 2);
INSERT INTO `sys_roles_menus` VALUES (2, 1);
INSERT INTO `sys_roles_menus` VALUES (2, 2);
INSERT INTO `sys_roles_menus` VALUES (3, 1);
INSERT INTO `sys_roles_menus` VALUES (3, 2);
INSERT INTO `sys_roles_menus` VALUES (5, 1);
INSERT INTO `sys_roles_menus` VALUES (5, 2);
INSERT INTO `sys_roles_menus` VALUES (6, 1);
INSERT INTO `sys_roles_menus` VALUES (7, 1);
INSERT INTO `sys_roles_menus` VALUES (9, 1);
INSERT INTO `sys_roles_menus` VALUES (10, 1);
INSERT INTO `sys_roles_menus` VALUES (11, 1);
INSERT INTO `sys_roles_menus` VALUES (14, 1);
INSERT INTO `sys_roles_menus` VALUES (15, 1);
INSERT INTO `sys_roles_menus` VALUES (18, 1);
INSERT INTO `sys_roles_menus` VALUES (19, 1);
INSERT INTO `sys_roles_menus` VALUES (21, 1);
INSERT INTO `sys_roles_menus` VALUES (22, 1);
INSERT INTO `sys_roles_menus` VALUES (23, 1);
INSERT INTO `sys_roles_menus` VALUES (24, 1);
INSERT INTO `sys_roles_menus` VALUES (27, 1);
INSERT INTO `sys_roles_menus` VALUES (28, 1);
INSERT INTO `sys_roles_menus` VALUES (30, 1);
INSERT INTO `sys_roles_menus` VALUES (32, 1);
INSERT INTO `sys_roles_menus` VALUES (33, 1);
INSERT INTO `sys_roles_menus` VALUES (34, 1);
INSERT INTO `sys_roles_menus` VALUES (35, 1);
INSERT INTO `sys_roles_menus` VALUES (36, 1);
INSERT INTO `sys_roles_menus` VALUES (37, 1);
INSERT INTO `sys_roles_menus` VALUES (38, 1);
INSERT INTO `sys_roles_menus` VALUES (39, 1);
INSERT INTO `sys_roles_menus` VALUES (40, 1);
INSERT INTO `sys_roles_menus` VALUES (41, 1);
INSERT INTO `sys_roles_menus` VALUES (42, 1);
INSERT INTO `sys_roles_menus` VALUES (44, 1);
INSERT INTO `sys_roles_menus` VALUES (44, 2);
INSERT INTO `sys_roles_menus` VALUES (45, 1);
INSERT INTO `sys_roles_menus` VALUES (46, 1);
INSERT INTO `sys_roles_menus` VALUES (48, 1);
INSERT INTO `sys_roles_menus` VALUES (49, 1);
INSERT INTO `sys_roles_menus` VALUES (50, 1);
INSERT INTO `sys_roles_menus` VALUES (52, 1);
INSERT INTO `sys_roles_menus` VALUES (53, 1);
INSERT INTO `sys_roles_menus` VALUES (54, 1);
INSERT INTO `sys_roles_menus` VALUES (56, 1);
INSERT INTO `sys_roles_menus` VALUES (57, 1);
INSERT INTO `sys_roles_menus` VALUES (58, 1);
INSERT INTO `sys_roles_menus` VALUES (60, 1);
INSERT INTO `sys_roles_menus` VALUES (61, 1);
INSERT INTO `sys_roles_menus` VALUES (62, 1);
INSERT INTO `sys_roles_menus` VALUES (64, 1);
INSERT INTO `sys_roles_menus` VALUES (65, 1);
INSERT INTO `sys_roles_menus` VALUES (66, 1);
INSERT INTO `sys_roles_menus` VALUES (73, 1);
INSERT INTO `sys_roles_menus` VALUES (74, 1);
INSERT INTO `sys_roles_menus` VALUES (75, 1);
INSERT INTO `sys_roles_menus` VALUES (77, 1);
INSERT INTO `sys_roles_menus` VALUES (78, 1);
INSERT INTO `sys_roles_menus` VALUES (79, 1);
INSERT INTO `sys_roles_menus` VALUES (80, 1);
INSERT INTO `sys_roles_menus` VALUES (82, 1);
INSERT INTO `sys_roles_menus` VALUES (90, 1);
INSERT INTO `sys_roles_menus` VALUES (92, 1);
INSERT INTO `sys_roles_menus` VALUES (93, 1);
INSERT INTO `sys_roles_menus` VALUES (94, 1);
INSERT INTO `sys_roles_menus` VALUES (97, 1);
INSERT INTO `sys_roles_menus` VALUES (98, 1);
INSERT INTO `sys_roles_menus` VALUES (102, 1);
INSERT INTO `sys_roles_menus` VALUES (103, 1);
INSERT INTO `sys_roles_menus` VALUES (104, 1);
INSERT INTO `sys_roles_menus` VALUES (105, 1);
INSERT INTO `sys_roles_menus` VALUES (106, 1);
INSERT INTO `sys_roles_menus` VALUES (107, 1);
INSERT INTO `sys_roles_menus` VALUES (108, 1);
INSERT INTO `sys_roles_menus` VALUES (109, 1);
INSERT INTO `sys_roles_menus` VALUES (110, 1);
INSERT INTO `sys_roles_menus` VALUES (111, 1);
INSERT INTO `sys_roles_menus` VALUES (112, 1);
INSERT INTO `sys_roles_menus` VALUES (113, 1);
INSERT INTO `sys_roles_menus` VALUES (114, 1);
INSERT INTO `sys_roles_menus` VALUES (116, 1);
INSERT INTO `sys_roles_menus` VALUES (117, 1);
INSERT INTO `sys_roles_menus` VALUES (118, 1);
INSERT INTO `sys_roles_menus` VALUES (120, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'TOM' COMMENT '昵称',
  `gender` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '性别',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `avatar_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'https://blogpic-1305209282.cos.ap-chengdu.myqcloud.com/img/blogphoto2.jpg' COMMENT '头像路径',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `is_admin` int(0) NULL DEFAULT 1 COMMENT '是否是管理员，1为是',
  `enabled` int(0) NOT NULL DEFAULT 1 COMMENT '是否可以启用，1为启用',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '由谁创建',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '由谁更新',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `sys_user_username_uindex`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '超级管理员', '1', '13219899632', '315850554@qq.com', 'https://blogpic-1305209282.cos.ap-chengdu.myqcloud.com/img/blogphoto2.jpg', '$2a$10$fIVaeBd33Y2fdsVmsoxKfe7t5oARWceWZbwE5bUotgD4rnuy6URY2', 1, 1, 'admin', 'admin', '2022-09-24 20:43:15', '2022-10-20 05:41:30');
INSERT INTO `sys_user` VALUES (2, 'tom', '普通用户', '1', '13219899655', '1369281736@qq.com', 'https://blogpic-1305209282.cos.ap-chengdu.myqcloud.com/img/QQ%E5%9B%BE%E7%89%8720221013171331.jpg', '$2a$10$fIVaeBd33Y2fdsVmsoxKfe7t5oARWceWZbwE5bUotgD4rnuy6URY2', 0, 1, 'admin', 'tom', '2022-10-13 17:15:24', '2022-10-14 07:48:26');
INSERT INTO `sys_user` VALUES (7, 'jerry', '耗子', '0', '13219899634', '1725453353@qq.com', 'https://blogpic-1305209282.cos.ap-chengdu.myqcloud.com/img/blogphoto2.jpg', '$2a$10$NJopxVc6Px6qcscu/sasiORU/cWJwzcDdhbu60pH2SnxvMixghKDK', 1, 1, 'admin', NULL, '2022-10-16 12:25:24', NULL);
INSERT INTO `sys_user` VALUES (12, '2', '3', '1', '13219899635', '13@qq.com', 'https://blogpic-1305209282.cos.ap-chengdu.myqcloud.com/img/blogphoto2.jpg', '$2a$10$zJE1NA/2Ezzgbt4wOwZpeuAQSvsUUglK/xW19rP/J8V1JsA26w/Di', 1, 1, 'admin', NULL, '2022-10-19 07:21:37', NULL);

-- ----------------------------
-- Table structure for sys_users_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_users_roles`;
CREATE TABLE `sys_users_roles`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `role_id` bigint(0) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_users_roles
-- ----------------------------
INSERT INTO `sys_users_roles` VALUES (1, 1);
INSERT INTO `sys_users_roles` VALUES (2, 2);
INSERT INTO `sys_users_roles` VALUES (7, 2);

-- ----------------------------
-- Table structure for trade
-- ----------------------------
DROP TABLE IF EXISTS `trade`;
CREATE TABLE `trade`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `subject` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `outTradeNo` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品订单号',
  `tradeNo` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方订单号',
  `totalAmount` decimal(10, 2) NOT NULL COMMENT '订单总金额',
  `state` int(0) NOT NULL DEFAULT 0 COMMENT '订单状态，未支付为0反之1',
  `createTime` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1584449199837741057 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of trade
-- ----------------------------
INSERT INTO `trade` VALUES (1584447158847430658, '可乐', '202210241530144183', '2022102422001447550501664398', 3.50, 1, '2022-10-24 15:30:14');
INSERT INTO `trade` VALUES (1584448389984747521, '可乐', '202210241535085628', '2022102422001447550501664186', 3.50, 1, '2022-10-24 15:35:08');
INSERT INTO `trade` VALUES (1584448833503035394, '可乐', '202210241536545006', '2022102422001447550501664302', 3.50, 1, '2022-10-24 15:36:54');
INSERT INTO `trade` VALUES (1584449199837741057, '可乐', '202210241538214277', '2022102422001447550501664303', 3.50, 1, '2022-10-24 15:38:21');

SET FOREIGN_KEY_CHECKS = 1;
