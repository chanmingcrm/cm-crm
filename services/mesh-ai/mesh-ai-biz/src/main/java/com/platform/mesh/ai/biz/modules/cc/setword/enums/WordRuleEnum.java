package com.platform.mesh.ai.biz.modules.cc.setword.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 提示语类型
 * @author 蝉鸣
 */
@Schema(description = "提示语类型",enumAsRef = true)
public enum WordRuleEnum implements BaseEnum<WordRuleEnum, Integer> {

    /**
     * 随机
     */
    RANDOM(1,  "随机"),
    /**
     * 顺序
     */
    SERIA(2,  "顺序"),
    ;


    private final Integer value;

    private final String desc;

    WordRuleEnum(Integer value, String desc) {
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
