package com.platform.mesh.ai.biz.modules.cc.group.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 会话群类型
 * @author 蝉鸣
 */
@Schema(description = "会话群类型",enumAsRef = true)
public enum GroupTypeEnum implements BaseEnum<GroupTypeEnum, Integer> {

    /**
     * 临时
     */
    TEMP(1,  "临时"),
    /**
     * 长久的
     */
    LONG(1,  "长久的"),
    ;


    private final Integer value;

    private final String desc;

    GroupTypeEnum(Integer value, String desc) {
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
