package com.platform.mesh.core.enums.logic.type;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "逻辑类型枚举",enumAsRef = true)
public enum LogicTypeEnum implements BaseEnum<LogicTypeEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,  "元状态"),
    /**
     * 与
     */
    AND(1,  "与"),
    /**
     * 或
     */
    OR(2, "或"),
    /**
     * 非
     */
    NOT(3,  "非"),
    ;

    private final Integer value;

    private final String desc;

    LogicTypeEnum(Integer value, String desc) {
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
