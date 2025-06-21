package com.platform.mesh.bpm.biz.modules.inst.line.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "线通过标识枚举",enumAsRef = true)
public enum InstLinePassEnum implements BaseEnum<InstLinePassEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,  "元状态"),
    /**
     * 通过
     */
    PASS(1,  "通过"),
    /**
     * 不通过
     */
    UN_PASS(2,  "不通过"),
    ;

    private final Integer value;

    private final String desc;

    InstLinePassEnum(Integer value, String desc) {
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
