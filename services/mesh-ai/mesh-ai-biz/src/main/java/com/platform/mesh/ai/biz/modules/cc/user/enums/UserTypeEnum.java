package com.platform.mesh.ai.biz.modules.cc.user.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 客服人员类型
 * @author 蝉鸣
 */
@Schema(description = "客服人员类型",enumAsRef = true)
public enum UserTypeEnum implements BaseEnum<UserTypeEnum, Integer> {

    /**
     * 佚名游客
     */
    INIT(0,  "佚名游客"),
    /**
     * AI
     */
    AI(1,  "AI"),
    /**
     * 人工
     */
    HUMAN(2,  "人工"),
    ;


    private final Integer value;

    private final String desc;

    UserTypeEnum(Integer value, String desc) {
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
