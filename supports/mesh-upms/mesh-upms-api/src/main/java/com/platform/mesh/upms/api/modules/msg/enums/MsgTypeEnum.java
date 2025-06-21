package com.platform.mesh.upms.api.modules.msg.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "消息类型枚举",enumAsRef = true)
public enum MsgTypeEnum implements BaseEnum<MsgTypeEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,0,  "元状态"),
    ;


    @Getter
    private final Integer code;

    private final Integer value;

    private final String desc;

    MsgTypeEnum(Integer code, Integer value, String desc) {
        this.code = code;
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
