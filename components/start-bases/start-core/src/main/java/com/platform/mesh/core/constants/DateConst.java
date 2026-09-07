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
	 * 年
	 */
	String YYYY_QUARTER = "yyyy-q";

	/**
	 * 年-月
	 */
	String YYYY_MM = "yyyy-MM";

	/**
	 * 年
	 */
	String YYYY_WEEK = "yyyy-ww";

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
	String ES_YYYY_MM__DD__HH_MM_SS = "strict_date_optional_time || epoch_millis";

	/**
	 * 日期类型
	 */
	String[] PARSE_PATTERNS = {
			// 纯月份
			"yyyy/MM",
			"yyyy-MM",
			"yyyy.MM",

			// 日期（单数字日）
			"yyyy/MM/d",
			"yyyy-MM-d",
			"yyyy.MM.d",

			// 日期（双数字日）
			"yyyy/MM/dd",
			"yyyy-MM-dd",
			"yyyy.MM.dd",

			// 单数字日 + 时间（有时分秒）
			"yyyy/MM/d HH:mm:ss",
			"yyyy-MM-d HH:mm:ss",
			"yyyy.MM.d HH:mm:ss",

			// 单数字日 + 时间（只有时分）
			"yyyy/MM/d HH:mm",
			"yyyy-MM-d HH:mm",
			"yyyy.MM.d HH:mm",

			// 双数字日 + 时间（有时分秒）
			"yyyy/MM/dd HH:mm:ss",
			"yyyy-MM-dd HH:mm:ss",
			"yyyy.MM.dd HH:mm:ss",

			// 双数字日 + 时间（只有时分）
			"yyyy/MM/dd HH:mm",
			"yyyy-MM-dd HH:mm",
			"yyyy.MM.dd HH:mm",

			// 纯时间
			"HH:mm:ss"};

}
