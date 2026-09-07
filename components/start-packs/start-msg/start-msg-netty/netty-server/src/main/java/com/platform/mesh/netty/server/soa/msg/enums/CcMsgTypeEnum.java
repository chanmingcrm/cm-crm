package com.platform.mesh.netty.server.soa.msg.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @description 客服消息类型枚举:用于客户端向服务端发送信息类型
 * @author 蝉鸣
 */
@Schema(description = "客服消息类型枚举",enumAsRef = true)
public enum CcMsgTypeEnum implements BaseEnum<CcMsgTypeEnum, Integer> {

    /**
     * 系统
     */
    SYS_PING(0,1,"心跳"),

    /**
     * 人员
     */
    USER_ONLINE(1,101,"人员上线"),

    USER_OFFLINE(1,102,"人员下线"),

    /**
     * 房间
     */
    GROUP_GET(2,200,"获取房间"),

    GROUP_CREATE(2,201,"创建房间"),

    GROUP_DELETE(2,202,"删除房间"),

    GROUP_JOIN(2,203,"加入房间"),

    GROUP_LEAVE(2,204,"离开房间"),

    /**
     * 消息
     */

    MSG_SEND(3,301,"消息发送"),

    MSG_RECEIVED(3,302,"消息接收"),


    ;

    @Getter
    private final Integer code;

    private final Integer value;

    private final String desc;

    CcMsgTypeEnum(Integer code, Integer value, String desc) {
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
