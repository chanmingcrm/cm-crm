package com.platform.mesh.redis.service.constants;

/**
 * @description 缓存key 常量
 * @author 蝉鸣
 */
public interface CacheConstants {

	/**
	 * oauth token缓存前缀
	 */
	String OAUTH_TOKEN_PREFIX = "token";

	/**
	 * oauth 缓存前缀
	 */
	String OAUTH_ACCESS_PREFIX = "token:access_token";

	/**
	 * oauth 客户端信息
	 */
	String CLIENT_DETAILS_KEY = "oauth:client:details";

	/**
	 * 用户帐户信息缓存
	 */
	String USER_ACCOUNT_DETAILS = "user_account_details";

	/**
	 * 用户角色信息缓存
	 */
	String USER_ROLE_DETAILS = "user_role_details:{}";

	/**
	 * 用户菜单信息缓存
	 */
	String USER_MENU_DETAILS = "user_menu_details:{}";

	/**
	 * 用户组织信息缓存
	 */
	String USER_ORG_DETAILS = "user_org_details:{}";

	/**
	 * 用户下属信息缓存
	 */
	String USER_CHILD_DETAILS = "user_child_details:{}";

	/**
	 * 系统用户信息缓存
	 */
	String SYS_USER_DETAILS = "sys_user_details";

	/**
	 * 系统组织信息缓存
	 */
	String SYS_ORG_DETAILS = "sys_org_details";

	/**
	 * 字典管理（默认缓存十分钟） cache key
	 */
	String SYS_DICT_KEY = "sys_dict";

	/**
	 * 字典管理（默认缓存十分钟） cache NAME
	 */
	String SYS_DICT_NAME = "sys_dict";

	/**
	 * 租户下组织信息缓存
	 */
	String SYS_TENANT_LEVEL_NAME = "sys_tenant_level_name";

	/**
	 * 应用表单
	 */
	String SYS_MSG_LEAVE = "sys_msg_leave";

	/**
	 * 租户下成员信息缓存
	 */
	String SYS_TENANT_MEMBER_NAME = "sys_tenant_member_name";

	/**
	 * 租户下应用模块信息缓存
	 */
	String SYS_APP_MODULE = "sys_app_module";


	/**
	 * 账户下数据权限缓存
	 */
	String SYS_ACCOUNT_SCOPE = "sys_account_scope";

	/**
	 * 应用表单
	 */
	String APP_MODULE_FORM = "APP_MODULE_FORM";

	/**
	 * BI搜索条件缓存
	 */
	String BI_SEARCH_PARAM = "BI_SEARCH_PARAM";

	/**
	 * 短信缓存
	 */
	String SMS_PHONE_CACHE = "SMS_PHONE";

	/**
	 * 字典管理（默认缓存十分钟） cache key
	 */
	Long TTL_KEY_DEFAULT = 1800L;

	/**
	 * 字典管理（默认缓存十分钟） cache key
	 */
	Long TTL_KEY_FIX = 1800L;
}
