package com.platform.mesh.core.enums.bpm;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "流程通过状态枚举",enumAsRef = true)
public enum ProcessPassEnum implements BaseEnum<ProcessPassEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,0,  "元状态"),
    /**
     * 通过
     */
    PASS(1,101,  "通过"),
    /**
     * 驳回
     */
    UN_PASS(2,201,  "驳回"),
    /**
     * 无效
     */
    UN_VALID(3,301,  "无效"),
    ;


    @Getter
    private final Integer code;

    private final Integer value;

    private final String desc;

    ProcessPassEnum(Integer code, Integer value, String desc) {
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
