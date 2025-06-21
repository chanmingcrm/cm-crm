package com.platform.mesh.upms.biz.modules.msg.notice.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 消息提醒循环枚举
 * @author 蝉鸣
 */
@Schema(description = "消息提醒循环枚举",enumAsRef = true)
public enum NoticeTypeEnum implements BaseEnum<NoticeTypeEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,  "元状态"),
    /**
     * 日程
     */
    OA(1,  "日程"),
    /**
     * 跟进
     */
    FOLLOW(2,  "跟进"),
    ;


    private final Integer value;

    private final String desc;

    NoticeTypeEnum(Integer value, String desc) {
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
