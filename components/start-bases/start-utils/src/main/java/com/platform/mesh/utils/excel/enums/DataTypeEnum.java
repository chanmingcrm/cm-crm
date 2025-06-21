package com.platform.mesh.utils.excel.enums;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "字段标识枚举",enumAsRef = true)
public enum DataTypeEnum implements BaseEnum<DataTypeEnum, Integer> {

    /**
     * 存储数据类型
     */
    INIT(0,  "无数据"),
    NUMBER(1,  "数值"),
    STRING(2,  "字符串"),
    ARRAY(3,  "数组对象"),
    JSON_OBJECT(4,  "JsonObject对象"),
    JSON_ARRAY(5,  "JsonArray对象"),
    ;
    private final Integer value;

    private final String desc;

    DataTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
    @Override
    public String getDesc() {
        return this.desc;
    }

    public Object getDefaultValue(Object defaultValue) {
        return switch (this) {
            case INIT -> null;
            case NUMBER -> ObjectUtil.isNotEmpty(defaultValue) && NumberUtil.isNumber(StrUtil.toString(defaultValue))?defaultValue:NumberConst.NUM_0;
            case STRING -> toStr(defaultValue);
            case ARRAY, JSON_ARRAY -> ObjectUtil.isNotEmpty(defaultValue) && JSONUtil.isTypeJSONArray(JSONUtil.toJsonStr(defaultValue)) ? JSONUtil.parseArray(JSONUtil.toJsonStr(defaultValue)):JSONUtil.createArray();
            case JSON_OBJECT -> ObjectUtil.isNotEmpty(defaultValue) && JSONUtil.isTypeJSONObject(JSONUtil.toJsonStr(defaultValue)) ? JSONUtil.parseObj(JSONUtil.toJsonStr(defaultValue)):JSONUtil.createObj();
        };
    }

    public String getDefaultValueStr(Object defaultValue) {
        return switch (this) {
            case INIT -> StrUtil.EMPTY;
            case NUMBER -> ObjectUtil.isNotEmpty(defaultValue) && NumberUtil.isNumber(StrUtil.toString(defaultValue))?defaultValue.toString():NumberConst.NUM_0.toString();
            case STRING -> toStr(defaultValue);
            case ARRAY, JSON_ARRAY -> ObjectUtil.isNotEmpty(defaultValue) && JSONUtil.isTypeJSONArray(JSONUtil.toJsonStr(defaultValue)) ? JSONUtil.toJsonStr(defaultValue):JSONUtil.toJsonStr(JSONUtil.createArray());
            case JSON_OBJECT -> ObjectUtil.isNotEmpty(defaultValue) && JSONUtil.isTypeJSONObject(JSONUtil.toJsonStr(defaultValue)) ? JSONUtil.toJsonStr(defaultValue):JSONUtil.toJsonStr(JSONUtil.createObj());
        };
    }

    public String toStr(Object defaultValue) {
        try {
            String jsonStr = JSONUtil.toJsonStr(defaultValue);
            //如果多个引号则进行去除
            if(jsonStr.matches(SymbolConst.PATTERN_MORE_QUOTATION)){
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(jsonStr, String.class);
            }
            return jsonStr;
        }catch (Exception ignore) {}
        return StrUtil.toString(defaultValue);
    }

}
