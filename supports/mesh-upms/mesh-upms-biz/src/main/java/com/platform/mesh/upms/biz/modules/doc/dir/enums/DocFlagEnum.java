package com.platform.mesh.upms.biz.modules.doc.dir.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 目录标识枚举
 * @author 蝉鸣
 */
@Schema(description = "文档来源标识枚举",enumAsRef = true)
public enum DocFlagEnum implements BaseEnum<DocFlagEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0, "元状态"),
    /**
     * 官网
     */
    HOME(1,  "官网"),
    /**
     * 平台
     */
    ADMIN(2, "平台"),
    ;

    private final Integer value;

    private final String desc;

    DocFlagEnum(Integer value, String desc) {
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
