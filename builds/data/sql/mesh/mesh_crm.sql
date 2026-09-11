/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.84
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : 192.168.0.84:3306
 Source Schema         : mesh_crm

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 07/09/2026 10:46:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for crm_all_goal
-- ----------------------------
DROP TABLE IF EXISTS `crm_all_goal`;
CREATE TABLE `crm_all_goal`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块',
  `module_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模块名称',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '数据名称',
  `data_flag` tinyint NULL DEFAULT NULL COMMENT '数据类型(DataFlagEnum)',
  `year_time` year NULL DEFAULT NULL COMMENT '年度',
  `year_goal` decimal(10, 2) NULL DEFAULT NULL COMMENT '年度目标',
  `day_goal` decimal(10, 2) NULL DEFAULT NULL COMMENT '天目标',
  `jan_goal` decimal(10, 2) NULL DEFAULT NULL COMMENT '一月目标',
  `feb_goal` decimal(10, 2) NULL DEFAULT NULL COMMENT '二月目标',
  `mar_goal` decimal(10, 2) NULL DEFAULT NULL COMMENT '三月目标',
  `apr_goal` decimal(10, 2) NULL DEFAULT NULL COMMENT '四月目标',
  `may_goal` decimal(10, 2) NULL DEFAULT NULL COMMENT '五月目标',
  `jun_goal` decimal(10, 2) NULL DEFAULT NULL COMMENT '六月目标',
  `jul_goal` decimal(10, 2) NULL DEFAULT NULL COMMENT '七月目标',
  `aug_goal` decimal(10, 2) NULL DEFAULT NULL COMMENT '八月目标',
  `sep_goal` decimal(10, 2) NULL DEFAULT NULL COMMENT '九月目标',
  `oct_goal` decimal(10, 2) NULL DEFAULT NULL COMMENT '十月目标',
  `nov_goal` decimal(10, 2) NULL DEFAULT NULL COMMENT '十一月目标',
  `dec_goal` decimal(10, 2) NULL DEFAULT NULL COMMENT '十二月目标',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系目标表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_all_group
-- ----------------------------
DROP TABLE IF EXISTS `crm_all_group`;
CREATE TABLE `crm_all_group`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父主键ID',
  `module_id` bigint NOT NULL COMMENT '表单ID',
  `group_type` int NOT NULL DEFAULT 1 COMMENT '分组类型GroupTypeEnum',
  `group_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分组标识',
  `group_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '分组名称',
  `group_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '分组描述',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系分组表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_all_group_rel
-- ----------------------------
DROP TABLE IF EXISTS `crm_all_group_rel`;
CREATE TABLE `crm_all_group_rel`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `group_id` bigint NULL DEFAULT NULL COMMENT '分组ID',
  `group_type` int NULL DEFAULT NULL COMMENT '分组类型GroupTypeEnum',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系分组关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_business
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_business`;
CREATE TABLE `crm_on_business`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `customer_id` bigint NULL DEFAULT NULL COMMENT '客户ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '商机类型BusinessTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商机标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '商机名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '商机描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '商机期数',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除标识',
  `total_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '总计金额',
  `discount_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '折扣金额',
  `real_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '实际金额',
  `inst_process_id` bigint NULL DEFAULT NULL COMMENT '流程实例ID',
  `process_pass` int NULL DEFAULT NULL COMMENT '通过状态',
  `process_stage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '流程阶段',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系商机跟进表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_business_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_business_data`;
CREATE TABLE `crm_on_business_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系商机跟进数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_contract
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_contract`;
CREATE TABLE `crm_on_contract`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `customer_id` bigint NULL DEFAULT NULL COMMENT '客户ID',
  `business_id` bigint NULL DEFAULT NULL COMMENT '商机ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '合同类型ContractTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '合同标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '合同名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '合同描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '合同期数',
  `total_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '总计金额',
  `discount_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '折扣金额',
  `real_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '实际金额',
  `cost_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '成本金额',
  `received_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '已收金额',
  `unreceived_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '未收金额',
  `invoice_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '开票金额',
  `profit_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '利润金额',
  `start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `inst_process_id` bigint NULL DEFAULT NULL COMMENT '流程实例ID',
  `process_pass` int NULL DEFAULT NULL COMMENT '通过状态',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `CREATE_TIME`(`create_time` ASC) USING BTREE COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系合同签订表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_contract_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_contract_data`;
CREATE TABLE `crm_on_contract_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系合同签订数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_contract_rel
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_contract_rel`;
CREATE TABLE `crm_on_contract_rel`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `rel_module_id` bigint NULL DEFAULT NULL COMMENT '关联模块ID',
  `rel_data_id` bigint NULL DEFAULT NULL COMMENT '关联数据ID',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系合同签订关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_demand
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_demand`;
CREATE TABLE `crm_on_demand`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `customer_id` bigint NULL DEFAULT NULL COMMENT '客户ID',
  `order_id` bigint NULL DEFAULT NULL COMMENT '订单ID',
  `contract_id` bigint NULL DEFAULT NULL COMMENT '合同ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '需求类型DemandTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '需求标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '需求名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '需求描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '需求期数',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系需求整理表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_demand_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_demand_data`;
CREATE TABLE `crm_on_demand_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系需求整理数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_follow
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_follow`;
CREATE TABLE `crm_on_follow`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '跟进类型FollowTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '跟进标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '跟进名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '跟进描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '跟进期数',
  `rel_module_id` bigint NULL DEFAULT NULL COMMENT '关联数据模块ID',
  `rel_data_id` bigint NULL DEFAULT NULL COMMENT '关联数据ID',
  `next_time` datetime NULL DEFAULT NULL COMMENT '下次跟进时间',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系跟进拜访表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_follow_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_follow_data`;
CREATE TABLE `crm_on_follow_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系跟进拜访数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_follow_link
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_follow_link`;
CREATE TABLE `crm_on_follow_link`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `init_flag` int NULL DEFAULT NULL COMMENT '初始化标识',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系跟进拜访共享表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_follow_rel
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_follow_rel`;
CREATE TABLE `crm_on_follow_rel`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `rel_module_id` bigint NULL DEFAULT NULL COMMENT '关联模块ID',
  `rel_data_id` bigint NULL DEFAULT NULL COMMENT '关联数据ID',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系跟进拜访关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_invoice
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_invoice`;
CREATE TABLE `crm_on_invoice`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `customer_id` bigint NULL DEFAULT NULL COMMENT '客户ID',
  `contract_id` bigint NULL DEFAULT NULL COMMENT '合同ID',
  `order_id` bigint NULL DEFAULT NULL COMMENT '订单ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '发票类型InvoiceTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发票标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '发票名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '发票描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '发票期数',
  `total_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '总计金额',
  `discount_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '折扣金额',
  `real_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '实际金额',
  `invoice_time` datetime NULL DEFAULT NULL COMMENT '开票时间',
  `inst_process_id` bigint NULL DEFAULT NULL COMMENT '流程实例ID',
  `process_pass` int NULL DEFAULT NULL COMMENT '通过状态',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系发票回执表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_invoice_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_invoice_data`;
CREATE TABLE `crm_on_invoice_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系发票回执数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_invoice_rel
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_invoice_rel`;
CREATE TABLE `crm_on_invoice_rel`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `rel_module_id` bigint NULL DEFAULT NULL COMMENT '关联模块ID',
  `rel_data_id` bigint NULL DEFAULT NULL COMMENT '关联数据ID',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系发票回执关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_order
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_order`;
CREATE TABLE `crm_on_order`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `customer_id` bigint NULL DEFAULT NULL COMMENT '客户ID',
  `business_id` bigint NULL DEFAULT NULL COMMENT '商机ID',
  `contract_id` bigint NULL DEFAULT NULL COMMENT '合同ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '合同类型ContractTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '合同标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '合同名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '合同描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '合同期数',
  `total_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '总计金额',
  `discount_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '折扣金额',
  `real_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '实际金额',
  `cost_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '成本金额',
  `received_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '已收金额',
  `unreceived_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '未收金额',
  `invoice_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '开票金额',
  `profit_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '利润金额',
  `start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `inst_process_id` bigint NULL DEFAULT NULL COMMENT '流程实例ID',
  `process_pass` int NULL DEFAULT NULL COMMENT '通过状态',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `CREATE_TIME`(`create_time` ASC) USING BTREE COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_order_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_order_data`;
CREATE TABLE `crm_on_order_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系订单数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_payment
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_payment`;
CREATE TABLE `crm_on_payment`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `customer_id` bigint NULL DEFAULT NULL COMMENT '客户ID',
  `contract_id` bigint NULL DEFAULT NULL COMMENT '合同ID',
  `order_id` bigint NULL DEFAULT NULL COMMENT '订单ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '款项类型PaymentTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '款项标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '款项名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '款项描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '款项期数',
  `payment_time` datetime NULL DEFAULT NULL COMMENT '款项时间',
  `total_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '总计金额',
  `discount_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '折扣金额',
  `real_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '实际金额',
  `verify_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '核销金额',
  `verify_status` int NULL DEFAULT NULL COMMENT '核销状态',
  `inst_process_id` bigint NULL DEFAULT NULL COMMENT '流程实例ID',
  `process_pass` int NULL DEFAULT NULL COMMENT '通过状态',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系款项记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_payment_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_payment_data`;
CREATE TABLE `crm_on_payment_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系款项记录数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_programme
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_programme`;
CREATE TABLE `crm_on_programme`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `customer_id` bigint NULL DEFAULT NULL COMMENT '客户ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '方案类型ProgrammeTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '方案标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '方案名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '方案描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '方案期数',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系方案输出表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_programme_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_programme_data`;
CREATE TABLE `crm_on_programme_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系方案输出数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_sub_product
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_sub_product`;
CREATE TABLE `crm_on_sub_product`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `rel_module_id` bigint NULL DEFAULT NULL COMMENT '关联模块ID',
  `rel_data_id` bigint NULL DEFAULT NULL COMMENT '关联数据ID',
  `customer_id` bigint NULL DEFAULT NULL COMMENT '客户ID',
  `business_id` bigint NULL DEFAULT NULL COMMENT '商机ID',
  `proposal_id` bigint NULL DEFAULT NULL COMMENT '报价ID',
  `contract_id` bigint NULL DEFAULT NULL COMMENT '合同ID',
  `product_id` bigint NULL DEFAULT NULL COMMENT '产品ID',
  `data_type` int NULL DEFAULT 1 COMMENT '报价类型',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报价标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '报价名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '报价描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '报价序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '报价期数',
  `total_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '总计金额',
  `discount_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '折扣金额',
  `real_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '实际金额',
  `cost_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '成本价',
  `sale_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '销售价',
  `profit_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '利润价',
  `order_num` decimal(10, 2) NULL DEFAULT NULL COMMENT '数量',
  `order_unit` bigint NULL DEFAULT NULL COMMENT '单位',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `CREATE_TIME`(`create_time` ASC) USING BTREE COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系关联子产品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_on_sub_product_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_on_sub_product_data`;
CREATE TABLE `crm_on_sub_product_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `rel_module_id` bigint NULL DEFAULT NULL COMMENT '关联模块ID',
  `rel_data_id` bigint NULL DEFAULT NULL COMMENT '关联数据ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系关联子产品数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_pre_competitor
-- ----------------------------
DROP TABLE IF EXISTS `crm_pre_competitor`;
CREATE TABLE `crm_pre_competitor`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `customer_id` bigint NULL DEFAULT NULL COMMENT '客户ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '竞品类型CompetitorTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '竞品标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '竞品名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '竞品描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '竞品期数',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系竞品分析表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_pre_competitor_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_pre_competitor_data`;
CREATE TABLE `crm_pre_competitor_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系竞品分析数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_pre_contacts
-- ----------------------------
DROP TABLE IF EXISTS `crm_pre_contacts`;
CREATE TABLE `crm_pre_contacts`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `customer_id` bigint NULL DEFAULT NULL COMMENT '客户ID',
  `data_type` int NULL DEFAULT 1 COMMENT '联系人类型',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '联系人名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '联系人描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '联系人序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '联系人期数',
  `phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号码',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系联系人对象表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_pre_contacts_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_pre_contacts_data`;
CREATE TABLE `crm_pre_contacts_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系联系人对象数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_pre_customer
-- ----------------------------
DROP TABLE IF EXISTS `crm_pre_customer`;
CREATE TABLE `crm_pre_customer`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `data_type` int NULL DEFAULT 1 COMMENT '客户类型CustomerTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '客户名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '客户描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '客户期数',
  `phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号码',
  `total_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '总计金额',
  `received_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '已收金额',
  `unreceived_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '未收金额',
  `inst_process_id` bigint NULL DEFAULT NULL COMMENT '流程实例ID',
  `process_pass` int NULL DEFAULT NULL COMMENT '通过状态',
  `wx_work_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企微ID',
  `fei_shu_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '飞书ID',
  `ding_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '钉钉ID',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系客户对象表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_pre_customer_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_pre_customer_data`;
CREATE TABLE `crm_pre_customer_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系客户对象数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_pre_drainage
-- ----------------------------
DROP TABLE IF EXISTS `crm_pre_drainage`;
CREATE TABLE `crm_pre_drainage`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `data_type` int NULL DEFAULT 1 COMMENT '活动引流类型DrainageTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动引流标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '活动引流名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '活动引流描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '活动引流期数',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `inst_process_id` bigint NULL DEFAULT NULL COMMENT '流程实例ID',
  `process_pass` int NULL DEFAULT NULL COMMENT '通过状态',
  `wx_work_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企微ID',
  `fei_shu_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '飞书ID',
  `ding_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '钉钉ID',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系活动引流表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_pre_drainage_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_pre_drainage_data`;
CREATE TABLE `crm_pre_drainage_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系活动引流数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_pre_drainage_third
-- ----------------------------
DROP TABLE IF EXISTS `crm_pre_drainage_third`;
CREATE TABLE `crm_pre_drainage_third`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `source_flag` int NULL DEFAULT NULL COMMENT '数据来源',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `third_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '第三方ID',
  `third_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '第三方数据',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系活动引流表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_pre_materials
-- ----------------------------
DROP TABLE IF EXISTS `crm_pre_materials`;
CREATE TABLE `crm_pre_materials`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '活动物料类型MaterialsTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动物料标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '活动物料名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '活动物料描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '活动物料期数',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系活动物料表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_pre_materials_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_pre_materials_data`;
CREATE TABLE `crm_pre_materials_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系活动物料数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_pre_product
-- ----------------------------
DROP TABLE IF EXISTS `crm_pre_product`;
CREATE TABLE `crm_pre_product`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '产品类型ProductTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '产品名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '产品描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '产品期数',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系展示产品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_pre_product_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_pre_product_data`;
CREATE TABLE `crm_pre_product_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系展示产品数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_pre_proposal
-- ----------------------------
DROP TABLE IF EXISTS `crm_pre_proposal`;
CREATE TABLE `crm_pre_proposal`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `customer_id` bigint NULL DEFAULT NULL COMMENT '客户ID',
  `business_id` bigint NULL DEFAULT NULL COMMENT '商机ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '提案类型ProposalTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '提案标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '提案名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '提案描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '提案期数',
  `total_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '总计金额',
  `discount_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '折扣金额',
  `real_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '实际金额',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系提案报价表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_pre_proposal_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_pre_proposal_data`;
CREATE TABLE `crm_pre_proposal_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系提案报价数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_pre_research
-- ----------------------------
DROP TABLE IF EXISTS `crm_pre_research`;
CREATE TABLE `crm_pre_research`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '市场调研类型ResearchTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '市场调研标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '市场调研名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '市场调研描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '市场调研期数',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系市场调研表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_pre_research_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_pre_research_data`;
CREATE TABLE `crm_pre_research_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系市场调研数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_suf_deliver
-- ----------------------------
DROP TABLE IF EXISTS `crm_suf_deliver`;
CREATE TABLE `crm_suf_deliver`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `customer_id` bigint NULL DEFAULT NULL COMMENT '客户ID',
  `contract_id` bigint NULL DEFAULT NULL COMMENT '合同ID',
  `order_id` bigint NULL DEFAULT NULL COMMENT '订单ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '标的交付类型deliverTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标的交付标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标的交付名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标的交付描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '标的交付期数',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系标的交付表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_suf_deliver_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_suf_deliver_data`;
CREATE TABLE `crm_suf_deliver_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系标的交付数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_suf_feedback
-- ----------------------------
DROP TABLE IF EXISTS `crm_suf_feedback`;
CREATE TABLE `crm_suf_feedback`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `customer_id` bigint NULL DEFAULT NULL COMMENT '客户ID',
  `contract_id` bigint NULL DEFAULT NULL COMMENT '合同ID',
  `order_id` bigint NULL DEFAULT NULL COMMENT '订单ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '市场反馈类型FeedbackTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '市场反馈标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '市场反馈名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '市场反馈描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '市场反馈期数',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系市场反馈表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_suf_feedback_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_suf_feedback_data`;
CREATE TABLE `crm_suf_feedback_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系市场反馈数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_suf_opinion
-- ----------------------------
DROP TABLE IF EXISTS `crm_suf_opinion`;
CREATE TABLE `crm_suf_opinion`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `customer_id` bigint NULL DEFAULT NULL COMMENT '客户ID',
  `contract_id` bigint NULL DEFAULT NULL COMMENT '合同ID',
  `order_id` bigint NULL DEFAULT NULL COMMENT '订单ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '意见评价类型OpinionTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '意见评价标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '意见评价名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '意见评价描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '意见评价期数',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系意见评价表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_suf_opinion_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_suf_opinion_data`;
CREATE TABLE `crm_suf_opinion_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系意见评价数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_suf_review
-- ----------------------------
DROP TABLE IF EXISTS `crm_suf_review`;
CREATE TABLE `crm_suf_review`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `customer_id` bigint NULL DEFAULT NULL COMMENT '客户ID',
  `contract_id` bigint NULL DEFAULT NULL COMMENT '合同ID',
  `order_id` bigint NULL DEFAULT NULL COMMENT '订单ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '复盘总结类型ReviewTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '复盘总结标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '复盘总结名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '复盘总结描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '复盘总结期数',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系复盘总结表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for crm_suf_review_data
-- ----------------------------
DROP TABLE IF EXISTS `crm_suf_review_data`;
CREATE TABLE `crm_suf_review_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户关系复盘总结数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for plm_category
-- ----------------------------
DROP TABLE IF EXISTS `plm_category`;
CREATE TABLE `plm_category`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '产品类型',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '产品名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '产品描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '产品序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '产品期数',
  `product_id` bigint NULL DEFAULT NULL COMMENT '产品ID',
  `cost_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '成本金额',
  `sale_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '销售金额',
  `discount_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '折扣金额',
  `profit_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '利润金额',
  `on_off_flag` int NULL DEFAULT NULL COMMENT '上下架标识',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `CREATE_TIME`(`create_time` ASC) USING BTREE COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '产品分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for plm_category_data
-- ----------------------------
DROP TABLE IF EXISTS `plm_category_data`;
CREATE TABLE `plm_category_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '产品分类数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for plm_design_craft
-- ----------------------------
DROP TABLE IF EXISTS `plm_design_craft`;
CREATE TABLE `plm_design_craft`  (
  `id` bigint NOT NULL,
  `module_id` bigint NULL DEFAULT NULL,
  `data_type` int NOT NULL DEFAULT 1,
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `data_serial` int NULL DEFAULT NULL,
  `data_period` int NULL DEFAULT NULL,
  `del_flag` int NULL DEFAULT NULL,
  `create_user_id` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_user_id` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `scope_user_id` bigint NULL DEFAULT NULL,
  `scope_org_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `CREATE_TIME`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '产品工艺设计表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for plm_design_craft_data
-- ----------------------------
DROP TABLE IF EXISTS `plm_design_craft_data`;
CREATE TABLE `plm_design_craft_data`  (
  `id` bigint NOT NULL,
  `parent_module_id` bigint NULL DEFAULT NULL,
  `module_id` bigint NOT NULL,
  `add_form_id` bigint NULL DEFAULT NULL,
  `edit_form_id` bigint NULL DEFAULT NULL,
  `data_id` bigint NULL DEFAULT NULL,
  `column_id` bigint NULL DEFAULT NULL,
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `data_type` int NULL DEFAULT 1,
  `del_flag` int NULL DEFAULT NULL,
  `create_user_id` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_user_id` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `scope_user_id` bigint NULL DEFAULT NULL,
  `scope_org_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '产品工艺设计数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for plm_design_process
-- ----------------------------
DROP TABLE IF EXISTS `plm_design_process`;
CREATE TABLE `plm_design_process`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '产品类型',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '产品名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '产品描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '产品序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '产品期数',
  `supplier_id` bigint NULL DEFAULT NULL COMMENT '供应商ID',
  `product_id` bigint NULL DEFAULT NULL COMMENT '供应商产品ID',
  `cost_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '成本金额',
  `sale_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '销售金额',
  `discount_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '折扣金额',
  `profit_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '利润金额',
  `on_off_flag` int NULL DEFAULT NULL COMMENT '上下架标识',
  `product_num` bigint NULL DEFAULT NULL COMMENT '产品数量',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `CREATE_TIME`(`create_time` ASC) USING BTREE COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '工序设计表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for plm_design_process_data
-- ----------------------------
DROP TABLE IF EXISTS `plm_design_process_data`;
CREATE TABLE `plm_design_process_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '工序设计数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for plm_design_product
-- ----------------------------
DROP TABLE IF EXISTS `plm_design_product`;
CREATE TABLE `plm_design_product`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `data_type` int NULL DEFAULT 1 COMMENT '产品类型',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '产品名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '产品描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '产品序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '产品期数',
  `product_id` bigint NULL DEFAULT NULL COMMENT '产品ID',
  `category_id` bigint NULL DEFAULT NULL COMMENT '产品大类ID',
  `material_id` bigint NULL DEFAULT NULL COMMENT '物料ID',
  `supplier_id` bigint NULL DEFAULT NULL COMMENT '供应商ID',
  `cost_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '成本金额',
  `sale_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '销售金额',
  `discount_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '折扣金额',
  `profit_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '利润金额',
  `material_num` bigint NULL DEFAULT NULL COMMENT '物料数量',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `CREATE_TIME`(`create_time` ASC) USING BTREE COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '产品设计表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for plm_design_product_data
-- ----------------------------
DROP TABLE IF EXISTS `plm_design_product_data`;
CREATE TABLE `plm_design_product_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '产品设计数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for plm_design_struct
-- ----------------------------
DROP TABLE IF EXISTS `plm_design_struct`;
CREATE TABLE `plm_design_struct`  (
  `id` bigint NOT NULL,
  `module_id` bigint NULL DEFAULT NULL,
  `data_type` int NOT NULL DEFAULT 1,
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `data_serial` int NULL DEFAULT NULL,
  `data_period` int NULL DEFAULT NULL,
  `del_flag` int NULL DEFAULT NULL,
  `create_user_id` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_user_id` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `scope_user_id` bigint NULL DEFAULT NULL,
  `scope_org_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `CREATE_TIME`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '产品结构设计表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for plm_design_struct_data
-- ----------------------------
DROP TABLE IF EXISTS `plm_design_struct_data`;
CREATE TABLE `plm_design_struct_data`  (
  `id` bigint NOT NULL,
  `parent_module_id` bigint NULL DEFAULT NULL,
  `module_id` bigint NOT NULL,
  `add_form_id` bigint NULL DEFAULT NULL,
  `edit_form_id` bigint NULL DEFAULT NULL,
  `data_id` bigint NULL DEFAULT NULL,
  `column_id` bigint NULL DEFAULT NULL,
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `data_type` int NULL DEFAULT 1,
  `del_flag` int NULL DEFAULT NULL,
  `create_user_id` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_user_id` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `scope_user_id` bigint NULL DEFAULT NULL,
  `scope_org_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '产品结构设计数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for plm_material
-- ----------------------------
DROP TABLE IF EXISTS `plm_material`;
CREATE TABLE `plm_material`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '产品类型',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '产品名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '产品描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '产品序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '产品期数',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `CREATE_TIME`(`create_time` ASC) USING BTREE COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '产品物料表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for plm_material_data
-- ----------------------------
DROP TABLE IF EXISTS `plm_material_data`;
CREATE TABLE `plm_material_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '产品物料数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for plm_product
-- ----------------------------
DROP TABLE IF EXISTS `plm_product`;
CREATE TABLE `plm_product`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '产品类型',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '产品名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '产品描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '产品序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '产品期数',
  `cost_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '成本金额',
  `sale_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '销售金额',
  `discount_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '折扣金额',
  `profit_money` decimal(10, 2) NULL DEFAULT NULL COMMENT '利润金额',
  `on_off_flag` int NULL DEFAULT NULL COMMENT '上下架标识',
  `product_flag` int NULL DEFAULT NULL COMMENT '过程标识',
  `product_num` bigint NULL DEFAULT NULL COMMENT '产品数量',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `CREATE_TIME`(`create_time` ASC) USING BTREE COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '产品管理产品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for plm_product_data
-- ----------------------------
DROP TABLE IF EXISTS `plm_product_data`;
CREATE TABLE `plm_product_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除表示YesOrNoEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '产品管理产品数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tmp_calen_base
-- ----------------------------
DROP TABLE IF EXISTS `tmp_calen_base`;
CREATE TABLE `tmp_calen_base`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `work_flag` int NULL DEFAULT NULL COMMENT '工作日志类型',
  `work_done` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '已办',
  `work_todo` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '待办',
  `work_undo` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '未办',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '数据权限用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '数据权限层级ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1892842186382909443 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '日历' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tmp_oa_approval
-- ----------------------------
DROP TABLE IF EXISTS `tmp_oa_approval`;
CREATE TABLE `tmp_oa_approval`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `data_type` int NOT NULL DEFAULT 1 COMMENT '审批类型',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '复盘总结标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '复盘总结名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '复盘总结描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '客户序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '复盘总结期数',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除标识',
  `inst_process_id` bigint NULL DEFAULT NULL COMMENT '流程实例ID',
  `process_pass` int NULL DEFAULT NULL COMMENT '通过状态',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '审批表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tmp_oa_approval_data
-- ----------------------------
DROP TABLE IF EXISTS `tmp_oa_approval_data`;
CREATE TABLE `tmp_oa_approval_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `page_id` bigint NULL DEFAULT NULL COMMENT '页面ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '数据名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '数据描述',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '审批数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tmp_task_base
-- ----------------------------
DROP TABLE IF EXISTS `tmp_task_base`;
CREATE TABLE `tmp_task_base`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父任务ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `data_type` int NULL DEFAULT 1 COMMENT '任务类型TaskTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '任务名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '任务描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '任务序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '任务期数',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除标识',
  `source_flag` int NULL DEFAULT NULL COMMENT '任务来源',
  `est_rel_value` decimal(10, 2) NULL DEFAULT NULL COMMENT '预计数值',
  `act_rel_value` decimal(10, 2) NULL DEFAULT NULL COMMENT '实际数值',
  `est_progress` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '预计进度',
  `act_progress` decimal(10, 2) NULL DEFAULT NULL COMMENT '实际进度',
  `est_start_time` datetime NULL DEFAULT NULL COMMENT '预计开始时间',
  `est_end_time` datetime NULL DEFAULT NULL COMMENT '预计结束时间',
  `act_start_time` datetime NULL DEFAULT NULL COMMENT '实际开始时间',
  `act_end_time` datetime NULL DEFAULT NULL COMMENT '实际结束时间',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2052287535459295235 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '任务表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tmp_task_base_data
-- ----------------------------
DROP TABLE IF EXISTS `tmp_task_base_data`;
CREATE TABLE `tmp_task_base_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '任务数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tmp_task_base_rel
-- ----------------------------
DROP TABLE IF EXISTS `tmp_task_base_rel`;
CREATE TABLE `tmp_task_base_rel`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `module_id` bigint NULL DEFAULT 0 COMMENT '模块ID',
  `task_id` bigint NULL DEFAULT 1 COMMENT '任务ID',
  `rel_module_id` bigint NULL DEFAULT NULL COMMENT '关联模块ID',
  `rel_data_id` bigint NULL DEFAULT NULL COMMENT '关联数据ID',
  `version_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '版本编号',
  `version_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '版本描述',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1815331256046735361 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '任务数据关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tmp_work_log
-- ----------------------------
DROP TABLE IF EXISTS `tmp_work_log`;
CREATE TABLE `tmp_work_log`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `module_id` bigint NULL DEFAULT NULL COMMENT '模块ID',
  `data_type` int NULL DEFAULT 1 COMMENT '日志类型LogTypeEnum',
  `data_mac` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志标识',
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '日志名称',
  `data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '日志描述',
  `data_serial` int NULL DEFAULT NULL COMMENT '日志序列号',
  `data_period` int NULL DEFAULT NULL COMMENT '日志期数',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除标识',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '工作日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tmp_work_log_data
-- ----------------------------
DROP TABLE IF EXISTS `tmp_work_log_data`;
CREATE TABLE `tmp_work_log_data`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_module_id` bigint NULL DEFAULT NULL COMMENT '父模块ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `add_form_id` bigint NULL DEFAULT NULL COMMENT '新增表单ID',
  `edit_form_id` bigint NULL DEFAULT NULL COMMENT '编辑页面ID',
  `data_id` bigint NULL DEFAULT NULL COMMENT '数据ID',
  `column_id` bigint NULL DEFAULT NULL COMMENT '字段ID',
  `column_mac` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段标识',
  `column_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `data_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据值',
  `data_type` int NULL DEFAULT 1 COMMENT '数据类型DataTypeEnum',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '工作日志扩展数据表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
