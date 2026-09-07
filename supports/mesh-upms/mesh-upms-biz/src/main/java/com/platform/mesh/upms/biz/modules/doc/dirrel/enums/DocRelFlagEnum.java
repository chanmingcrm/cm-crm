package com.platform.mesh.upms.biz.modules.doc.dirrel.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 目录标识枚举
 * @author 蝉鸣
 */
@Schema(description = "文档来源标识枚举",enumAsRef = true)
public enum DocRelFlagEnum implements BaseEnum<DocRelFlagEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0, "元状态"),
    /**
     * 附件
     */
    FILE(1,  "附件"),
    /**
     * 在线文档
     */
    ONLINE(2, "在线文档"),
    ;

    private final Integer value;

    private final String desc;

    DocRelFlagEnum(Integer value, String desc) {
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
