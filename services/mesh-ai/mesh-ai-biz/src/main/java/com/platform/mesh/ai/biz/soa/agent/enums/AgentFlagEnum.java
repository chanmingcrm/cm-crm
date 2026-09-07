package com.platform.mesh.ai.biz.soa.agent.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description AI智能体类型枚举
 * @author 蝉鸣
 */
@Schema(description = "AI智能体类型枚举",enumAsRef = true)
public enum AgentFlagEnum implements BaseEnum<AgentFlagEnum, Integer> {

    /**
     * 初始化
     */
    INIT(0, "初始化"),

    /**
     * 扣子
     */
    COZE(1, "coze"),

    /**
     * 百炼
     */
    DASH_SCOPE(2, "dash_scope"),

    /**
     * 千帆
     */
    QIAN_FAN(3, "qian_fan"),

    ;

    private final Integer value;

    private final String desc;

    AgentFlagEnum(Integer value, String desc) {
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
