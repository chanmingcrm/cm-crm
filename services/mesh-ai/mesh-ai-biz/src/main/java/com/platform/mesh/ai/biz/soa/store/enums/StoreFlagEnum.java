package com.platform.mesh.ai.biz.soa.store.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description AI向量枚举
 * @author 蝉鸣
 */
@Schema(description = "AI向量枚举",enumAsRef = true)
public enum StoreFlagEnum implements BaseEnum<StoreFlagEnum, Integer> {

    /**
     * FILE
     */
    FILE(1,"FILE"),
    /**
     * REDIS
     */
    REDIS(2,"REDIS"),
    /**
     * ES
     */
    ELASTICSEARCH(3,"ES"),
    /**
     * MILVUS
     */
    MILVUS(4, "MILVUS"),

    ;

    private final Integer value;

    private final String desc;

    StoreFlagEnum(Integer value, String desc) {
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
