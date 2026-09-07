package com.platform.mesh.bpm.biz.soa.event.type.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 事件类型枚举
 * @author 蝉鸣
 */
@Schema(description = "事件类型枚举",enumAsRef = true)
public enum EventTypeEnum implements BaseEnum<EventTypeEnum, Integer> {

    /**
     * 站内消息
     */
    SYS(1, "SYS"),
    /**
     * 邮件
     */
    EMAIL(2, "EMAIL"),
    /**
     * 钉钉
     */
    DING_TALK(3,"DING_TALK"),

    ;


    private final Integer value;

    private final String desc;

    EventTypeEnum(Integer value, String desc) {
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
