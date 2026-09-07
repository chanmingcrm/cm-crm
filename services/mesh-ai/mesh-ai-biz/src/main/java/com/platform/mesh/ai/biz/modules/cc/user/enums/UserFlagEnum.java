package com.platform.mesh.ai.biz.modules.cc.user.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 提示语类型
 * @author 蝉鸣
 */
@Schema(description = "提示语类型",enumAsRef = true)
public enum UserFlagEnum implements BaseEnum<UserFlagEnum, Integer> {

    /**
     * 兼容历史正常状态
     */
    NORMAL(0,  "正常"),
    /**
     * 在线
     */
    ONLINE(1,  "在线"),
    /**
     * 忙碌
     */
    BUSY(2,  "忙碌"),
    /**
     * 离线
     */
    OFFLINE(3,  "离线"),
    ;


    private final Integer value;

    private final String desc;

    UserFlagEnum(Integer value, String desc) {
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
