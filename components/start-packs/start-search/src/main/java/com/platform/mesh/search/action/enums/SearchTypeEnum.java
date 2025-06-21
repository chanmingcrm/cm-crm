package com.platform.mesh.search.action.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "搜索来源类型枚举",enumAsRef = true)
public enum SearchTypeEnum implements BaseEnum<SearchTypeEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,  "元状态"),
    /**
     * 数据库
     */
    SQL_DB(1,  "数据库"),
    /**
     * elasticsearch
     */
    ELASTICSEARCH(2, "elasticsearch"),
    /**
     * clickhouse
     */
    CLICKHOUSE(3, "clickhouse"),
    /**
     * Doris
     */
    DORIS(4, "Doris")
    ;

    private final Integer value;

    private final String desc;

    SearchTypeEnum(Integer value, String desc) {
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

}
