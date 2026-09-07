package com.platform.mesh.mybatis.plus.constant;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.core.constants.NumberConst;

import java.util.List;

/**
 * @description 数据库常量
 * @author 蝉鸣
 */
public interface MybatisPlusConst {


	/**
	 * split_num
	 */
	Integer SPLIT_NUM = NumberConst.NUM_100;


	/**
	 * DEFAULT_DB_TYPE
	 */
	String DEFAULT_DB_TYPE = "mysql";

	/**
	 * LIMIT 1
	 */
	String LIMIT_1 = "LIMIT 1";

	/**
	 * 忽略数据权限table
	 */
	List<String> IGNORE_TABLES_SCOPE = CollUtil.newArrayList(
			"oauth2_authorization",
			"oauth2_authorization_consent",
			"oauth2_registered_client",
			"oauth2_third_client",
			"sys_account",
			"sys_user",
			"log_sms",
			"log_update",
			"app_comp_base",
			"doc_file",
			"log_login",
			"log_modify",
			"log_operate",
			"sys_user_role_rel",
			"sys_role",
			"sys_role_menu_rel",
			"org_level",
			"org_level_post_rel",
			"org_post",
			"org_post_data_scope",
			"org_member",
			"org_member_post_rel",
			"org_member",
			"org_member_user_rel",
			"sys_menu",
			"dict_base",
			"dict_value",
			"label_base",
			"label_value",
			"app_base",
			"app_form_base",
			"app_form_column",
			"app_form_column_set_action",
			"app_form_column_set_event",
			"app_form_column_set_process",
			"app_form_column_set_require",
			"app_form_column_setting",
			"app_form_column_sorting",
			"app_module_base",
			"app_module_set_open",
			"app_module_set_pick",
			"app_module_set_trans",
			"app_module_set_trans_mapping",
			"ai_agent",
			"ai_prompt_temp"
	);
}

