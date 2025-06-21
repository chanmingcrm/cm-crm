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
			"oauth2_tenant_rel",
			"tenant_base",
			"tenant_client",
			"tenant_renew_rec",
			"log_sms",
			"doc_file",
			"sys_account",
			"sys_user",
			"sys_menu",
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
			"app_comp_base"
	);

}

