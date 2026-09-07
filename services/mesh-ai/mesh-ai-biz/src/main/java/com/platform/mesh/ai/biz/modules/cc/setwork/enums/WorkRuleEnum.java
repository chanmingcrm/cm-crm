package com.platform.mesh.ai.biz.modules.cc.setwork.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 提示语类型
 * @author 蝉鸣
 */
@Schema(description = "提示语类型",enumAsRef = true)
public enum WorkRuleEnum implements BaseEnum<WorkRuleEnum, Integer> {

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

    WorkRuleEnum(Integer value, String desc) {
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
