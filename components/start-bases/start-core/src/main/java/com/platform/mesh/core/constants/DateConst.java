package com.platform.mesh.core.constants;

/**
 * @description 字符常量
 * @author 蝉鸣
 */
public interface DateConst {

	/**
	 * 年
	 */
	String YYYY = "yyyy";

	/**
	 * 年-月
	 */
	String YYYY_MM = "yyyy-MM";

	/**
	 * 年-月-日
	 */
	String YYYY_MM_DD = "yyyy-MM-dd";

	/**
	 * 年-月-日
	 */
	String PATH_YYYY_MM_DD = "yyyy/MM/dd";

	/**
	 * 时:分:秒
	 */
	String HH_MM_SS = "HH:mm:ss";

	/**
	 * 年-月-日 时:分:秒
	 */
	String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 年-月-日 时:分:秒
	 */
	String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss SSS";

	/**
	 * 年-月-日 时:分:秒 ::  strict_date_optional_time：Elasticsearch 内置的 ISO 8601 格式。epoch_millis：时间戳格式。
	 */
	String ES_YYYY_MM__DD__HH_MM_SS = "yyyy-MM || yyyy-MM-dd || HH:mm:ss || yyyy-MM-dd HH:mm:ss || strict_date_optional_time || epoch_millis";

	/**
	 * 日期类型
	 */
	String[] PARSE_PATTERNS = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
			"yyyy.MM.dd HH:mm", "yyyy.MM", "HH:mm:ss" };

}
