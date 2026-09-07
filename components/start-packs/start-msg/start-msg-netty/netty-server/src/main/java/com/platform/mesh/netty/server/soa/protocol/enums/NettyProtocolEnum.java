package com.platform.mesh.netty.server.soa.protocol.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description Netty消息协议类型
 * @author 蝉鸣
 */
@Schema(description = "Netty消息协议类型",enumAsRef = true)
public enum NettyProtocolEnum implements BaseEnum<NettyProtocolEnum, Integer> {

    /**
     * websocket
     */
    WEBSOCKET(1,"websocket"),

    ;

    private final Integer value;

    private final String desc;

    NettyProtocolEnum(Integer value, String desc) {
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
