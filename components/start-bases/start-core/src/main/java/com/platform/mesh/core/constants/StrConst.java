package com.platform.mesh.core.constants;

import java.util.List;

/**
 * @description 字符常量
 * @author 蝉鸣
 */
public interface StrConst {

	/**
	 * Bearer
	 */
	String BEARER = "Bearer";

	/**
	 * Access Key 认证方案
	 */
	String ACCESS_KEY = "AccessKey";

	/**
	 *  枚举描述
	 */
	String DESC = "desc";

	/**
	 *  键变量
	 */
	String KEY = "key";

	/**
	 *  值变量
	 */
	String VALUE = "value";

	/**
	 *  子变量
	 */
	String CHILDREN = "children";

	/**
	 *  基础包属性
	 */
	String BASE_PACKAGES = "basePackages";

	/**
	 *  异步线程前缀
	 */
	String THREAD_PREFIX = "Mesh-Thread-";

	/**
	 *  配置文件开关
	 */
	String CONFIG_ENABLE = "enabled";

	/**
	 *  配置文件开关值
	 */
	String CONFIG_ENABLE_VALUE = "true";

	/**
	 * ID
	 */
	String ID = "id";

	/**
	 * NAME
	 */
	String NAME = "name";

	/**
	 * FROM_DATA_ID
	 */
	String FROM_DATA_ID = "from_data_id";

	/**
	 * DATA_ID
	 */
	String DATA_ID = "data_id";

	/**
	 * DATA_NAME
	 */
	String DATA_NAME = "data_name";

	/**
	 * DATA_MAC
	 */
	String DATA_MAC = "data_mac";

	/**
	 * DATA_TYPE
	 */
	String DATA_TYPE = "data_type";

	/**
	 * data_desc
	 */
	String DATA_DESC = "data_desc";

	/**
	 * data_value
	 */
	String DATA_VALUE = "data_value";

	/**
	 * DATA_SERIAL
	 */
	String DATA_SERIAL = "data_serial";

	/**
	 * DATA_PERIOD
	 */
	String DATA_PERIOD = "data_period";

	/**
	 * DATA_COLOR
	 */
	String DATA_COLOR = "color";

	/**
	 * DATA后缀
	 */
	String DATA_SUFFIX = "_data";

	/**
	 * Time后缀
	 */
	String TIME_SUFFIX = "_time";

	/**
	 * create_time
	 */
	String CREATE_TIME = "create_time";

	/**
	 * create_user_id
	 */
	String CREATE_USER_ID = "create_user_id";

	/**
	 * update_time
	 */
	String UPDATE_TIME = "update_time";

	/**
	 * update_user_id
	 */
	String UPDATE_USER_ID = "update_user_id";

	/**
	 * app_id
	 */
	String APP_ID = "app_id";

	/**
	 * module_id
	 */
	String MODULE_ID = "module_id";

	/**
	 * module_name
	 */
	String MODULE_NAME = "module_name";

	/**
	 * parent_module_id
	 */
	String PARENT_MODULE_ID = "parent_module_id";

	/**
	 * indexName
	 */
	String INDEX_NAME = "index_name";

	/**
	 * form_id
	 */
	String FORM_ID = "form_id";

	/**
	 * column_id
	 */
	String COLUMN_ID = "column_id";

	/**
	 * column_mac
	 */
	String COLUMN_MAC = "column_mac";

	/**
	 * column_hash
	 */
	String COLUMN_HASH = "column_hash";

	/**
	 * column_name
	 */
	String COLUMN_NAME = "column_name";

	/**
	 * del_flag
	 */
	String DEL_FLAG = "del_flag";

	/**
	 * 序列码名称
	 */
	String SE_CODE = "se_code";

	/**
	 * ES搜索关联字段ID后缀
	 */
	String ES_SUFFIX_ID = "@id";

	/**
	 * ES搜索关联字段名称后缀
	 */
	String ES_SUFFIX_NAME = "@name";

	/**
	 * scope_user
	 */
	String SCOPE_USER = "scope_user_json";

	/**
	 * scope_org
	 */
	String SCOPE_ORG = "scope_org_json";

	/**
	 * create_user
	 */
	String CREATE_USER = "create_user_json";

	/**
	 * update_user
	 */
	String UPDATE_USER = "update_user_json";

	/**
	 * member_user
	 */
	String MEMBER_USER = "member_user_json";

	/**
	 * uni_query
	 */
	String UNI_QUERY = "uni_query";

	/**
	 * bpm_msg_id
	 */
	String BPM_MSG_ID = "bpm_msg_id";

	/**
	 * process_stage_json
	 */
	String BPM_STAGE_COLUMN = "process_stage_json";

	/**
	 * process_stage
	 */
	String BPM_PROCESS_STAGE = "process_stage";

	/**
	 * app_data_column
	 */
	String APP_DATA_COLUMN = "app_data_column";

    /**
     * transId
     */
    String TRANS_ID = "transId";

    /**
     *  总计
     */
    String TOTAL = "total";

    /**
     *  所有
     */
    String ALL = "all";

    /**
     *  转移状态
     */
    String TRANS_JSON = "trans_flag_json";

	/**
	 * id_uni
	 */
	String ID_UNI = "id_uni";

	/**
	 * name_uni
	 */
	String NAME_UNI = "name_uni";

	/**
	 * sms_sign
	 */
	String SMS_SIGN = "SMS_SIGN";

    /**
	 * 功能描述:
	 * 〈联合查询返回固定字段〉
	 * @author 蝉鸣
	 */
	static List<String> getFixFiled() {
		return List.of(
				ID,
				MODULE_ID,
				DATA_TYPE,
				DATA_NAME,
				DATA_MAC,
				DATA_DESC,
				DATA_PERIOD,
				CREATE_USER,
				CREATE_TIME,
				UPDATE_USER,
				UPDATE_TIME,
				SCOPE_USER,
				SCOPE_ORG
		);
	}
}
