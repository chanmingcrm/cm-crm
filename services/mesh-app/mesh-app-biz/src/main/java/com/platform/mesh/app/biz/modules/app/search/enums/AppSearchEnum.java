package com.platform.mesh.app.biz.modules.app.search.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "查询类型枚举",enumAsRef = true)
public enum AppSearchEnum implements BaseEnum<AppSearchEnum, Integer> {

    /**
     * 初始的
     */
    INIT(0,  "初始的"),
    /**
     * 全部的
     */
    ALL(1,  "全部"),
    /**
     * 自己的
     */
    SELF(2,  "自己的"),
    /**
     * 下属的
     */
    SUB(3,  "下属的"),
    ;


    private final Integer value;

    private final String desc;

    AppSearchEnum(Integer value, String desc) {
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
