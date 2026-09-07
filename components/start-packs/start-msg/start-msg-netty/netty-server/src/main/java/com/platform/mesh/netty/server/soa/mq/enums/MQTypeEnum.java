package com.platform.mesh.netty.server.soa.mq.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 消息队列类型枚举
 * @author 蝉鸣
 */
@Schema(description = "消息队列类型枚举",enumAsRef = true)
public enum MQTypeEnum implements BaseEnum<MQTypeEnum, Integer> {

    /**
     * redis
     */
    REDIS(1,"redis"),
    ;

    private final Integer value;

    private final String desc;

    MQTypeEnum(Integer value, String desc) {
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
