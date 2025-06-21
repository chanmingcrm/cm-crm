package com.platform.mesh.bpm.biz.modules.inst.event.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "时间执行标识枚举",enumAsRef = true)
public enum InstEventHandleEnum implements BaseEnum<InstEventHandleEnum, Integer> {

    /**
     * 未执行
     */
    UNDO(1,  "未执行"),
    /**
     * 已执行
     */
    DONE(2,  "已执行"),
    ;

    private final Integer value;

    private final String desc;

    InstEventHandleEnum(Integer value, String desc) {
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
