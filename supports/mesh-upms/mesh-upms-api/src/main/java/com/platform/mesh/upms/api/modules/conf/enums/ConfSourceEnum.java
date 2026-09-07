package com.platform.mesh.upms.api.modules.conf.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 目录标识枚举
 * @author 蝉鸣
 */
@Schema(description = "文档来源标识枚举",enumAsRef = true)
public enum ConfSourceEnum implements BaseEnum<ConfSourceEnum, Integer> {

    /**
     * 系统
     */
    SYS(0, "系统"),
    /**
     * 抖音
     */
    DOU_YIN(1,  "抖音"),
    /**
     * 巨量
     */
    OCEAN_ENGINE(2, "巨量"),
    /**
     * 企微
     */
    WX_WORK(3, "企微"),
    ;

    private final Integer value;

    private final String desc;

    ConfSourceEnum(Integer value, String desc) {
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
