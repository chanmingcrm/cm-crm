package com.platform.mesh.es.constant;

/**
 * @description 自定义常量
 * @author 蝉鸣
 */
public interface EsConst {


    /**
     * HOST
     */
    String CONFIG_PREFIX = "mesh.data.es";

    String ENABLE = "enable";

    String DEFAULT_ENABLE_VALUE = "true";

    /**
     * HOST
     */
    String DEFAULT_HOST = "127.0.0.1";

    /**
     *  PORT
     */
    Integer DEFAULT_PORT = 9200;

    /**
     * 默认节点地址
     */
    String DEFAULT_URI = "http://" + DEFAULT_HOST + ":" + DEFAULT_PORT;

    /**
     * HEADER_NAME
     */
    String URL_HEADER_NAME = "X-Elastic-Product";

    /**
     *  HEADER_VALUE
     */
    String URL_HEADER_VALUE = "Elasticsearch";

    /*=============================================ES 设置=============================================*/

    /**
     *  type
     */
    String MAPPING_TYPE = "type";

    /**
     *  keyword
     */
    String KEYWORD = "keyword";

    /**
     *  .keyword
     */
    String SUFFIX_KEYWORD = ".keyword";

    /**
     *  language
     */
    String LANGUAGE = "language";

    /**
     *  country
     */
    String COUNTRY = "country";

    /**
     *  zh
     */
    String ZH = "zh";

    /**
     *  CN
     */
    String CN = "CN";

    /**
     *  sort
     */
    String SORT = "sort";

    /**
     *  ignore_above
     */
    String IGNORE_ABOVE = "ignore_above";

    /**
     *  icu_collation_keyword
     */
    String ICU_COLLATION_KEYWORD = "icu_collation_keyword";

    /**
     *  lowercase_normalizer
     */
    String NORMALIZER_LOWERCASE = "lowercase_normalizer";

    /**
     *  icu_analyzer
     */
    String ANALYZER_ICU = "icu_analyzer";

    /*=============================================动态模板 设置=============================================*/

    /**
     *  string
     */
    String MAPPING_TYPE_STR = "string";

    /**
     *  icu_analyzer
     */
    String MAPPING_TYPE_OBJ = "object";

    /**
     *  icu_analyzer
     */
    String MAPPING_SUFFIX_ID = "_id";

    /**
     *  icu_analyzer
     */
    String MAPPING_MATCH_ID = "*id";

    /**
     *  icu_analyzer
     */
    String MAPPING_MATCH_STR = "*_str";

    /**
     *  _str后缀
     */
    String MAPPING_SUFFIX_STR = "_str";

    /**
     *  icu_analyzer
     */
    String MAPPING_MATCH_TEXT = "*_text";

    /**
     *  icu_analyzer
     */
    String MAPPING_MATCH_TEXTAREA = "textarea_*";

    /**
     *  icu_analyzer
     */
    String MAPPING_MATCH_TEXT_MULTI = "text_multi_*";

    /**
     *  icu_analyzer
     */
    String MAPPING_PREFIX_SIGN = "sign_*";

    /**
     *  _text后缀
     */
    String MAPPING_SUFFIX_TEXT = "_text";

    /**
     *  _text前缀
     */
    String MAPPING_PREFIX_TEXTAREA = "textarea_";

    /**
     *  _text前缀
     */
    String MAPPING_PREFIX_TEXT_MULTI = "text_multi_";

    /**
     *  icu_analyzer
     */
    String MAPPING_MATCH_NUM = "*_num";

    /**
     *  _num后缀
     */
    String MAPPING_SUFFIX_NUM = "_num";

    /**
     *  _money后缀
     */
    String MAPPING_MATCH_MONEY = "*_money";

    /**
     *  _money后缀
     */
    String MAPPING_SUFFIX_MONEY = "_money";

    /**
     *  icu_analyzer
     */
    String MAPPING_MATCH_DATE = "*_date";

    /**
     *  _date后缀
     */
    String MAPPING_SUFFIX_DATE = "_date";

    /**
     *  icu_analyzer
     */
    String MAPPING_MATCH_TIME = "*_time";

    /**
     *  _time后缀
     */
    String MAPPING_SUFFIX_TIME = "_time";

    /**
     *  icu_analyzer
     */
    String MAPPING_MATCH_JSON = "*_json";

    /**
     *  _json后缀
     */
    String MAPPING_SUFFIX_JSON = "_json";

    /**
     *  icu_analyzer
     */
    String MAPPING_MATCH_ARRAY = "*_array";

    /**
     *  _list后缀
     */
    String MAPPING_SUFFIX_ARRAY = "_array";

    /**
     *  string_to_long 模板名称
     */
    String MAPPING_TEMP_LONG = "string_to_long";

    /**
     *  string_to_key 模板名称
     */
    String MAPPING_TEMP_KEY = "string_to_key";

    /**
     *  string_to_text 模板名称
     */
    String MAPPING_TEMP_TEXT = "string_to_text";

    /**
     *  string_to_num 模板名称
     */
    String MAPPING_TEMP_NUM = "string_to_num";

    /**
     *  string_to_time 模板名称
     */
    String MAPPING_TEMP_TIME = "string_to_time";

    /**
     *  object_to_json 模板名称
     */
    String MAPPING_TEMP_JSON = "object_to_json";

    /**
     *  object_to_array 模板名称
     */
    String MAPPING_TEMP_ARRAY = "object_to_array";

    /*=============================================响应信息 设置=============================================*/

    String BULK_RESPONSE_ERROR_VERSION = "version_conflict_engine_exception";


    /*=============================================字段信息 设置=============================================*/

    String BPM_PROCESS_PASS = "process_pass";

    String BPM_PROCESS_PASS_JSON = "process_pass_json";

    String CRM_CONFIRM_FLAG = "confirm_flag";

    String CRM_CONFIRM_FLAG_JSON = "confirm_flag_json";

    String CRM_DRAINAGE_SOURCE_JSON = "drainage_source_json";
}
