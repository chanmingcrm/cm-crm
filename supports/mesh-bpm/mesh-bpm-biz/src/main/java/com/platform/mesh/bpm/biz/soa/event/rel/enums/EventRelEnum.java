package com.platform.mesh.bpm.biz.soa.event.rel.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @description 事件关联类型枚举
 * @author 蝉鸣
 */
@Schema(description = "事件关联类型枚举",enumAsRef = true)
public enum EventRelEnum implements BaseEnum<EventRelEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,0,  "元状态"),
    /**
     * 人员
     */
    USER_CUSTOM(1,100,  "自定义人员"),
    ;

    @Getter
    private final Integer code;

    private final Integer value;

    private final String desc;

    EventRelEnum(Integer code, Integer value, String desc) {
        this.code = code;
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
