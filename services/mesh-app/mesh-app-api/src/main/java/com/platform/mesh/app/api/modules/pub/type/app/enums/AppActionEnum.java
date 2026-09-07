package com.platform.mesh.app.api.modules.pub.type.app.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description  应用广播事件处理类型枚举
 * @author 蝉鸣
 */
@Schema(description = "应用广播事件处理类型枚举",enumAsRef = true)
public enum AppActionEnum implements BaseEnum<AppActionEnum, Integer> {

    /**
     * 自定义
     */
    CUSTOM(0,  "自定义"),

    /**
     * 同步名称
     */
    SYNC_NAME(1,  "同步名称"),

    ;


    private final Integer value;

    private final String desc;

    AppActionEnum(Integer value, String desc) {
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
