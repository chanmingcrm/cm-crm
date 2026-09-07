package com.platform.mesh.ai.biz.modules.cc.group.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 会话状态
 * @author Codex
 */
@Schema(description = "会话状态", enumAsRef = true)
public enum CcGroupStatusEnum implements BaseEnum<CcGroupStatusEnum, Integer> {

    /**
     * 会话中
     */
    CHATTING(1, "会话中"),

    /**
     * 已离线
     */
    OFFLINE(2, "已离线"),

    /**
     * 已结束
     */
    ENDED(3, "已结束"),
    ;

    private final Integer value;

    private final String desc;

    CcGroupStatusEnum(Integer value, String desc) {
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
