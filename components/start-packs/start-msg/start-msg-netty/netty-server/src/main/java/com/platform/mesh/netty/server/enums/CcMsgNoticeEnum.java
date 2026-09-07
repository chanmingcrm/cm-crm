package com.platform.mesh.netty.server.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @description 客服提醒类型枚举:用于向客户端返回响应信息类型
 * @author 蝉鸣
 */
@Schema(description = "客服提醒类型枚举",enumAsRef = true)
public enum CcMsgNoticeEnum implements BaseEnum<CcMsgNoticeEnum, Integer> {

    /**
     * 提醒
     */
    SYSTEM_PING(0,1,"心跳"),

    SYSTEM_TIP(1,101,"系统提醒"),

    SYSTEM_GROUP(1,102,"群更新"),

    SYSTEM_JOIN(1,103,"加入群"),

    MSG_BACK(2,201,"消息"),

    MSG_HUMAN(2,202,"需要人工联系"),

    MSG_LEAVE(2,203,"留言"),

    MSG_ONLINE(2,204,"在线提示"),

    AI_STREAM_START(2,205,"AI流开始"),

    AI_STREAM_CHUNK(2,206,"AI流分段"),

    AI_STREAM_END(2,207,"AI流结束"),


    ;

    @Getter
    private final Integer code;

    private final Integer value;

    private final String desc;

    CcMsgNoticeEnum(Integer code, Integer value, String desc) {
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
