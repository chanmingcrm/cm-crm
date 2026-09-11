/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.84
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : 192.168.0.84:3306
 Source Schema         : mesh_ai

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 07/09/2026 12:35:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ai_agent
-- ----------------------------
DROP TABLE IF EXISTS `ai_agent`;
CREATE TABLE `ai_agent`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `agent_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Agent名称',
  `agent_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Agent图标',
  `agent_nick` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Agent昵称',
  `agent_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Agent描述',
  `agent_space` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Agent空间ID',
  `agent_prompt` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT 'Agent提示词',
  `agent_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Agent地址',
  `agent_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '智能体ID',
  `agent_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Agent密钥',
  `agent_flag` int NULL DEFAULT NULL COMMENT '智能体类型',
  `default_flag` int NULL DEFAULT NULL COMMENT '默认标识',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除标识',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'AI Agent表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_agent
-- ----------------------------

-- ----------------------------
-- Table structure for ai_agent_kl_rel
-- ----------------------------
DROP TABLE IF EXISTS `ai_agent_kl_rel`;
CREATE TABLE `ai_agent_kl_rel`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `agent_id` bigint NULL DEFAULT NULL COMMENT '智能体ID',
  `kl_id` bigint NULL DEFAULT NULL COMMENT '知识库ID',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'AI Agent知识库关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_agent_kl_rel
-- ----------------------------

-- ----------------------------
-- Table structure for ai_job
-- ----------------------------
DROP TABLE IF EXISTS `ai_job`;
CREATE TABLE `ai_job`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `job_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务名称',
  `job_flag` int NULL DEFAULT NULL COMMENT '任务类型',
  `agent_id` bigint NULL DEFAULT NULL COMMENT '智能体ID',
  `params` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '任务参数',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除标识',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'AI 任务表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_job
-- ----------------------------

-- ----------------------------
-- Table structure for ai_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `ai_knowledge`;
CREATE TABLE `ai_knowledge`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `model_id` bigint NULL DEFAULT NULL COMMENT '向量模型ID',
  `store_flag` int NULL DEFAULT NULL COMMENT '存储类型',
  `knowledge_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '知识库名称',
  `knowledge_welcome` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '欢迎语',
  `knowledge_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `knowledge_sort` int NULL DEFAULT 0 COMMENT '知识库排序',
  `knowledge_share` tinyint NULL DEFAULT NULL COMMENT '是否公开知识库（1 是 2否）',
  `default_flag` tinyint NULL DEFAULT NULL COMMENT '默认标识',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 1：正常，2：删除',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1951103316582371330 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识库' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_knowledge
-- ----------------------------

-- ----------------------------
-- Table structure for ai_knowledge_doc
-- ----------------------------
DROP TABLE IF EXISTS `ai_knowledge_doc`;
CREATE TABLE `ai_knowledge_doc`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `knowledge_id` bigint NOT NULL COMMENT '知识库ID',
  `doc_id` bigint NULL DEFAULT NULL COMMENT '存储文件ID',
  `doc_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文档名称',
  `content_source` int NOT NULL COMMENT '文档类型',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文档内容',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_kname`(`knowledge_id` ASC, `doc_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1953272743834873858 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识库附件' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_knowledge_doc
-- ----------------------------

-- ----------------------------
-- Table structure for ai_knowledge_slice
-- ----------------------------
DROP TABLE IF EXISTS `ai_knowledge_slice`;
CREATE TABLE `ai_knowledge_slice`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `knowledge_id` bigint NOT NULL COMMENT '知识库ID',
  `knowledge_doc_id` bigint NULL DEFAULT NULL COMMENT '文档ID',
  `vector_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '向量ID',
  `vector_index` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '向量索引',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文档内容',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1953272746842189838 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识片段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_knowledge_slice
-- ----------------------------

-- ----------------------------
-- Table structure for ai_mcp_access_key
-- ----------------------------
DROP TABLE IF EXISTS `ai_mcp_access_key`;
CREATE TABLE `ai_mcp_access_key`  (
  `id` bigint NOT NULL,
  `key_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `public_id` varchar(16) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `secret_hash` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `account_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `status` tinyint NOT NULL DEFAULT 1,
  `expires_at` datetime NULL DEFAULT NULL,
  `last_used_at` datetime NULL DEFAULT NULL,
  `revoked_at` datetime NULL DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_ai_mcp_public_id`(`public_id` ASC) USING BTREE,
  INDEX `idx_ai_mcp_owner`(`account_id` ASC, `status` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'AI MCP 访问密钥' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_mcp_access_key
-- ----------------------------

-- ----------------------------
-- Table structure for ai_mcp_server
-- ----------------------------
DROP TABLE IF EXISTS `ai_mcp_server`;
CREATE TABLE `ai_mcp_server`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'MCP ID',
  `server_type` tinyint NOT NULL DEFAULT 2 COMMENT '服务类型：1平台MCP服务，2租户外部MCP服务',
  `mcp_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'MCP服务名称',
  `mcp_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求类型',
  `mcp_des` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `mcp_command` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '命令',
  `mcp_args` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数',
  `request_headers` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '环境变量',
  `server_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MCP服务完整地址',
  `connect_timeout_seconds` int NOT NULL DEFAULT 10 COMMENT '连接超时秒数',
  `request_timeout_seconds` int NOT NULL DEFAULT 20 COMMENT '请求超时秒数',
  `status_flag` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用  1：启用，0：关闭',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0：正常，1：删除',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_name`(`mcp_name` ASC) USING BTREE,
  INDEX `idx_ai_mcp_server_type_status`(`server_type` ASC, `status_flag` ASC, `del_flag` ASC) USING BTREE,
  INDEX `idx_ai_mcp_server_tenant_status`(`server_type` ASC, `status_flag` ASC, `del_flag` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1950868335385845763 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'AI MCP服务' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_mcp_server
-- ----------------------------

-- ----------------------------
-- Table structure for ai_mcp_tool
-- ----------------------------
DROP TABLE IF EXISTS `ai_mcp_tool`;
CREATE TABLE `ai_mcp_tool`  (
  `id` bigint NOT NULL COMMENT '主键',
  `mcp_module` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工具提供方编码',
  `tool_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工具名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工具说明',
  `input_schema` json NOT NULL COMMENT '输入参数 JSON Schema',
  `executor_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '后端业务执行器编码',
  `execution_config` json NULL COMMENT '执行器与响应加工配置',
  `read_only` tinyint NOT NULL DEFAULT 1 COMMENT '是否只读',
  `return_direct` tinyint NOT NULL DEFAULT 0 COMMENT '是否直接返回工具结果：1是，0否',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态：1 启用，0 停用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_ai_mcp_tool_name`(`tool_name` ASC) USING BTREE,
  INDEX `idx_ai_mcp_tool_provider`(`mcp_module` ASC, `status` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'AI MCP 工具目录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_mcp_tool
-- ----------------------------

-- ----------------------------
-- Table structure for ai_model
-- ----------------------------
DROP TABLE IF EXISTS `ai_model`;
CREATE TABLE `ai_model`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `model_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模型名称',
  `model_flag` int NULL DEFAULT NULL COMMENT '模型标识',
  `model_type` int NULL DEFAULT NULL COMMENT '模型类别',
  `model_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模型描述',
  `base_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模型基础地址',
  `api_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模型密钥',
  `temperature` decimal(10, 2) NULL DEFAULT NULL COMMENT '随机性',
  `max_tokens` int NULL DEFAULT NULL COMMENT '最大token数',
  `top_p` decimal(10, 2) NULL DEFAULT NULL COMMENT '核采样阈值',
  `frequency_penalty` decimal(10, 2) NULL DEFAULT NULL COMMENT '惩罚重复出现的',
  `presence_penalty` decimal(10, 2) NULL DEFAULT NULL COMMENT '惩罚新出现的',
  `stop_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '设置停止词',
  `with_stream` tinyint(1) NULL DEFAULT NULL COMMENT '是否启用流式响应',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除标识',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'AI模型Api表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_model
-- ----------------------------

-- ----------------------------
-- Table structure for ai_prompt_temp
-- ----------------------------
DROP TABLE IF EXISTS `ai_prompt_temp`;
CREATE TABLE `ai_prompt_temp`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `temp_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提示词模板名称',
  `temp_flag` tinyint(1) NULL DEFAULT NULL COMMENT '提示词分类，knowledge 知识库类型，chat 对话类型，draw绘画类型 ...',
  `temp_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '提示词模板内容',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1950841248822272003 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '提示词模板表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_prompt_temp
-- ----------------------------

-- ----------------------------
-- Table structure for ai_session
-- ----------------------------
DROP TABLE IF EXISTS `ai_session`;
CREATE TABLE `ai_session`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `agent_id` bigint NULL DEFAULT NULL COMMENT '智能体ID',
  `model_id` bigint NULL DEFAULT NULL COMMENT '模型ID',
  `model_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模型名称',
  `model_flag` int NULL DEFAULT NULL COMMENT '模型标识',
  `model_type` int NULL DEFAULT NULL COMMENT '模型类别',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '会话表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_session
-- ----------------------------

-- ----------------------------
-- Table structure for ai_session_his
-- ----------------------------
DROP TABLE IF EXISTS `ai_session_his`;
CREATE TABLE `ai_session_his`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `session_id` bigint NULL DEFAULT NULL COMMENT '会话ID',
  `model_id` bigint NULL DEFAULT NULL COMMENT '模型ID',
  `model_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模型名称',
  `model_flag` int NULL DEFAULT NULL COMMENT '模型标识',
  `model_type` int NULL DEFAULT NULL COMMENT '模型类别',
  `require_role` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对话角色',
  `require_prompt` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '请求提示词',
  `require_params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求参数',
  `response_code` int NULL DEFAULT NULL COMMENT '响应码',
  `response_msg` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '响应内容',
  `total_tokens` int NULL DEFAULT 0 COMMENT '累计 Tokens',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `scope_user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `scope_org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '会话历史表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_session_his
-- ----------------------------

-- ----------------------------
-- Table structure for cc_group
-- ----------------------------
DROP TABLE IF EXISTS `cc_group`;
CREATE TABLE `cc_group`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `group_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '会话群Hash',
  `group_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '会话群名称',
  `group_type` int NULL DEFAULT NULL COMMENT '会话群类型',
  `reply_type` int NULL DEFAULT NULL COMMENT '群消息回复类型',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `status` int NULL DEFAULT 1 COMMENT 'session status',
  `source` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'visitor source',
  `source_page` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'source page',
  `visitor_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'visitor name',
  `visitor_contact` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'visitor contact',
  `visitor_company` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'visitor company',
  `visitor_demand` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT 'visitor demand',
  `first_response_at` datetime NULL DEFAULT NULL COMMENT 'first response time',
  `last_msg_at` datetime NULL DEFAULT NULL COMMENT 'last message time',
  `closed_at` datetime NULL DEFAULT NULL COMMENT 'closed time',
  `assignee_user_hash` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'assignee user hash',
  `tags` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT 'session tags',
  `summary` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT 'session summary',
  `lead_status` int NULL DEFAULT 0 COMMENT 'lead status',
  `lead_id` bigint NULL DEFAULT NULL COMMENT 'CRM lead id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '会话组表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cc_group
-- ----------------------------

-- ----------------------------
-- Table structure for cc_group_user_rel
-- ----------------------------
DROP TABLE IF EXISTS `cc_group_user_rel`;
CREATE TABLE `cc_group_user_rel`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `group_id` bigint NULL DEFAULT NULL COMMENT '会话群ID',
  `group_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '会话群Hash',
  `group_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '会话群名称',
  `group_type` int NULL DEFAULT NULL COMMENT '会话群类型',
  `user_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户Hash',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名称',
  `user_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户类型',
  `star_level` int NULL DEFAULT NULL COMMENT '服务星级',
  `last_visit_time` datetime NULL DEFAULT NULL COMMENT '上次访问时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '会话组表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cc_group_user_rel
-- ----------------------------

-- ----------------------------
-- Table structure for cc_group_user_server
-- ----------------------------
DROP TABLE IF EXISTS `cc_group_user_server`;
CREATE TABLE `cc_group_user_server`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `group_id` bigint NULL DEFAULT NULL COMMENT '会话群ID',
  `group_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '会话群Hash',
  `group_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '会话群名称',
  `group_type` int NULL DEFAULT NULL COMMENT '会话群类型',
  `user_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户Hash',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名称',
  `user_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户类型',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '会话组表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cc_group_user_server
-- ----------------------------

-- ----------------------------
-- Table structure for cc_session_msg
-- ----------------------------
DROP TABLE IF EXISTS `cc_session_msg`;
CREATE TABLE `cc_session_msg`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `group_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '群Hash',
  `group_type` int NULL DEFAULT NULL COMMENT '群类型',
  `user_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户Hash',
  `user_type` int NULL DEFAULT NULL COMMENT '用户类型',
  `msg_type` int NULL DEFAULT NULL COMMENT '消息类型',
  `msg_content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '消息内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '会话表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cc_session_msg
-- ----------------------------

-- ----------------------------
-- Table structure for cc_set_word
-- ----------------------------
DROP TABLE IF EXISTS `cc_set_word`;
CREATE TABLE `cc_set_word`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `word_flag` int NULL DEFAULT NULL COMMENT '提示语类型',
  `word_rate` int NULL DEFAULT NULL COMMENT '提示语频率',
  `word_interval` int NULL DEFAULT NULL COMMENT '提示语间隔（秒为单位）',
  `word_rule` int NULL DEFAULT NULL COMMENT '提示语规则',
  `word_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '提示语内容',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客服提示语配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cc_set_word
-- ----------------------------

-- ----------------------------
-- Table structure for cc_set_work
-- ----------------------------
DROP TABLE IF EXISTS `cc_set_work`;
CREATE TABLE `cc_set_work`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `set_work_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '排班名称',
  `include_week_days` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '包含的周天',
  `exclude_week_days` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '排除的周天',
  `work_rule` int NULL DEFAULT NULL COMMENT '接待规则',
  `start_time` time NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` time NULL DEFAULT NULL COMMENT '结束时间',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客服排班配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cc_set_work
-- ----------------------------

-- ----------------------------
-- Table structure for cc_user
-- ----------------------------
DROP TABLE IF EXISTS `cc_user`;
CREATE TABLE `cc_user`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `user_type` int NULL DEFAULT NULL COMMENT '人员类型',
  `user_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '人员Hash',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '人员名称',
  `user_flag` int NULL DEFAULT NULL COMMENT '客服状态',
  `agent_id` bigint NULL DEFAULT NULL COMMENT 'Agent ID',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `max_reception` int NULL DEFAULT 5 COMMENT 'max active receptions',
  `skill_group` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'skill group',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客服人员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cc_user
-- ----------------------------

-- ----------------------------
-- Table structure for cc_user_work_rel
-- ----------------------------
DROP TABLE IF EXISTS `cc_user_work_rel`;
CREATE TABLE `cc_user_work_rel`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `cc_user_id` bigint NULL DEFAULT NULL COMMENT '客服人员ID',
  `user_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '人员Hash',
  `user_type` int NULL DEFAULT NULL COMMENT '人员类型',
  `cc_work_id` bigint NULL DEFAULT NULL COMMENT '客服排班ID',
  `sort_num` int NULL DEFAULT NULL COMMENT '排序',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客服人员排班表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cc_user_work_rel
-- ----------------------------

-- ----------------------------
-- Table structure for cc_visitor
-- ----------------------------
DROP TABLE IF EXISTS `cc_visitor`;
CREATE TABLE `cc_visitor`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `visitor_hash` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访客Hash',
  `visitor_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访客名称',
  `visitor_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访客手机号码',
  `visitor_leave` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访客留言',
  `visitor_interest` int NULL DEFAULT NULL COMMENT '访客意向程度',
  `visitor_star` int NULL DEFAULT NULL COMMENT '访客评价星级',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客服人员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cc_visitor
-- ----------------------------

-- ----------------------------
-- Table structure for cc_web_set
-- ----------------------------
DROP TABLE IF EXISTS `cc_web_set`;
CREATE TABLE `cc_web_set`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `web_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '页面描述',
  `web_script` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '页面脚本',
  `consultation_guide` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '官网咨询引导配置JSON',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '会话组表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cc_web_set
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
