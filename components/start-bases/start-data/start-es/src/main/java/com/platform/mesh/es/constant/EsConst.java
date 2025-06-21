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
    String MAPPING_MATCH_ID = "*id";

    /**
     *  icu_analyzer
     */
    String MAPPING_MATCH_NUM = "*_num";

    /**
     *  _num后缀
     */
    String MAPPING_SUFFIX_NUM = "_num";

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
     *  string_to_long 模板名称
     */
    String MAPPING_TEMP_LONG = "string_to_long";

    /**
     *  string_to_text 模板名称
     */
    String MAPPING_TEMP_STR = "string_to_text";

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

    /*=============================================响应信息 设置=============================================*/

    String BULK_RESPONSE_ERROR_VERSION = "version_conflict_engine_exception";
}
