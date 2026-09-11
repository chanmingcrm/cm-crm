/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.84
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : 192.168.0.84:3306
 Source Schema         : mesh_uaa

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 07/09/2026 12:38:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth2_authorization
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_authorization`;
CREATE TABLE `oauth2_authorization`  (
  `id` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '授权记录的唯一标识符',
  `registered_client_id` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '注册的客户端 ID',
  `principal_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户主名称',
  `authorization_grant_type` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '授权类型',
  `authorized_scopes` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '授权范围',
  `attributes` blob NULL COMMENT '授权属性',
  `state` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '授权状态',
  `authorization_code_value` blob NULL COMMENT '授权码值',
  `authorization_code_issued_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '授权码发放时间',
  `authorization_code_expires_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '授权码过期时间',
  `authorization_code_metadata` blob NULL COMMENT '授权码元数据',
  `access_token_value` blob NULL COMMENT '访问令牌值',
  `access_token_issued_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '访问令牌发放时间',
  `access_token_expires_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '访问令牌过期时间',
  `access_token_metadata` blob NULL COMMENT '访问令牌元数据',
  `access_token_type` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '访问令牌类型',
  `access_token_scopes` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '访问令牌范围',
  `oidc_id_token_value` blob NULL COMMENT 'OIDC ID 令牌值',
  `oidc_id_token_issued_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'OIDC ID 令牌发放时间',
  `oidc_id_token_expires_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'OIDC ID 令牌过期时间',
  `oidc_id_token_metadata` blob NULL COMMENT 'OIDC ID 令牌元数据',
  `refresh_token_value` blob NULL COMMENT '刷新令牌值',
  `refresh_token_issued_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '刷新令牌发放时间',
  `refresh_token_expires_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '刷新令牌过期时间',
  `refresh_token_metadata` blob NULL COMMENT '刷新令牌元数据',
  `user_code_value` blob NULL COMMENT '用户代码值',
  `user_code_issued_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户代码发放时间',
  `user_code_expires_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户代码过期时间',
  `user_code_metadata` blob NULL COMMENT '用户代码元数据',
  `device_code_value` blob NULL COMMENT '设备代码值',
  `device_code_issued_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '设备代码发放时间',
  `device_code_expires_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '设备代码过期时间',
  `device_code_metadata` blob NULL COMMENT '设备代码元数据',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '授权记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oauth2_authorization
-- ----------------------------

-- ----------------------------
-- Table structure for oauth2_authorization_consent
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_authorization_consent`;
CREATE TABLE `oauth2_authorization_consent`  (
  `registered_client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '注册的客户端 ID',
  `principal_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户主名称',
  `authorities` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户权限',
  PRIMARY KEY (`registered_client_id`, `principal_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '授权确认信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oauth2_authorization_consent
-- ----------------------------

-- ----------------------------
-- Table structure for oauth2_registered_client
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_registered_client`;
CREATE TABLE `oauth2_registered_client`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '客户端主键id',
  `client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '客户端id',
  `client_id_issued_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '客户端签发时间',
  `client_secret` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '客户端加密后的秘钥',
  `client_secret_expires_at` datetime NULL DEFAULT NULL COMMENT '客户端秘钥过期时间',
  `client_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '客户端名称',
  `client_profile` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '客户端头像',
  `client_authentication_methods` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '认证客户端的方式',
  `authorization_grant_types` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '客户端支持的认证方式',
  `redirect_uris` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '回调地址',
  `scopes` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '客户端权限范围',
  `client_settings` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '客户端设置',
  `token_settings` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Token设置',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已删除 0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '授权客户端注册表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oauth2_registered_client
-- ----------------------------
INSERT INTO `oauth2_registered_client`  VALUES ('2096823007748706304', 'new_client', '2026-09-07 12:49:28', '{bcrypt}$2a$10$PPSSMd9QwvqIGg3OA2Q1ruY9vvLVQwSzPColn37b4wEwyWN9ob0PO', NULL, 'my_client_name', NULL, 'client_secret_basic', 'refresh_token,password,third,authorization_code,sms', 'http://www.baidu.com', 'server', '{\"settings\":{\"settings.client.require-proof-key\":true,\"settings.client.require-authorization-consent\":false}}', '{\"settings\":{\"settings.token.reuse-refresh-tokens\":true,\"settings.token.x509-certificate-bound-access-tokens\":false,\"settings.token.id-token-signature-algorithm\":\"RS256\",\"settings.token.access-token-time-to-live\":\"PT168H\",\"settings.token.access-token-format\":{\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":\"PT744H\",\"settings.token.authorization-code-time-to-live\":\"PT5M\",\"settings.token.device-code-time-to-live\":\"PT5M\"}}', 0);

-- ----------------------------
-- Table structure for oauth2_third_client
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_third_client`;
CREATE TABLE `oauth2_third_client`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户ID',
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应用ID',
  `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '代理ID',
  `client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '客户端id',
  `client_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '客户端密钥',
  `client_source` int NULL DEFAULT NULL COMMENT '客户端来源',
  `redirect_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '回调地址',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已删除 0-未删除，1-已删除',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间 ',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '授权客户端租户关系表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
