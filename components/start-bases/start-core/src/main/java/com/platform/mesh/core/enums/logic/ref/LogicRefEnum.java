package com.platform.mesh.core.enums.logic.ref;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "逻辑关系枚举",enumAsRef = true)
public enum LogicRefEnum implements BaseEnum<LogicRefEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,  "元状态"),
    /**
     * 等于
     */
    EQ(1,  "等于"),
    /**
     * 不等于
     */
    NE(2, "不等于"),
    /**
     * 大于
     */
    GT(3,  "大于"),
    /**
     * 大于等于
     */
    GE(4,  "大于等于"),
    /**
     * 小于
     */
    LT(5,  "小于"),
    /**
     * 小于等于
     */
    LE(6,  "小于等于"),
    /**
     * 区间
     */
    BETWEEN(7,  "区间"),
    /**
     * 不在区间
     */
    NOT_BETWEEN(8,  "不在区间"),
    /**
     * 模糊
     */
    LIKE(9,  "模糊"),
    /**
     * 非模糊"
     */
    NOT_LIKE(10,  "非模糊"),
    /**
     * 在条件之内
     */
    IN(11,  "在条件之内"),
    /**
     * 不在条件之内
     */
    NOT_IN(12,  "非条件之内"),
    ;

    private final Integer value;

    private final String desc;

    LogicRefEnum(Integer value, String desc) {
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
