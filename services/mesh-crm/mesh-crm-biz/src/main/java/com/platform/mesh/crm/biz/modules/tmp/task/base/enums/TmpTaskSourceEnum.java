package com.platform.mesh.crm.biz.modules.tmp.task.base.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 任务来源类型枚举
 * @author 蝉鸣
 */
@Schema(description = "任务来源类型枚举",enumAsRef = true)
public enum TmpTaskSourceEnum implements BaseEnum<TmpTaskSourceEnum, Integer> {

    /**
     * 任务
     */
    TASK(1,  "task"),

    /**
     * APP业务
     */
    APP(2,  "app"),
    ;


    private final Integer value;

    private final String desc;

    TmpTaskSourceEnum(Integer value, String desc) {
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
