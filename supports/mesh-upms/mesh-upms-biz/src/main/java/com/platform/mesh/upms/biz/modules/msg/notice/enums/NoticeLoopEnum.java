package com.platform.mesh.upms.biz.modules.msg.notice.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 消息提醒循环枚举
 * @author 蝉鸣
 */
@Schema(description = "消息提醒循环枚举",enumAsRef = true)
public enum NoticeLoopEnum implements BaseEnum<NoticeLoopEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,  "元状态"),
    /**
     * 一次性
     */
    ONE(1,  "一次性"),
    /**
     * 多次
     */
    MORE(2,  "多次"),
    /**
     * 循环
     */
    LOOP(3,  "循环"),
    ;


    private final Integer value;

    private final String desc;

    NoticeLoopEnum(Integer value, String desc) {
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
