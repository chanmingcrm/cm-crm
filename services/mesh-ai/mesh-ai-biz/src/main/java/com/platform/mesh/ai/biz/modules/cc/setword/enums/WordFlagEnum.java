package com.platform.mesh.ai.biz.modules.cc.setword.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 提示语类型
 * @author 蝉鸣
 */
@Schema(description = "提示语类型",enumAsRef = true)
public enum WordFlagEnum implements BaseEnum<WordFlagEnum, Integer> {

    /**
     * 进入聊天室
     */
    IN_GROUP(1,  "进入聊天室"),
    /**
     * 欢迎语
     */
    HELLO_WORD(2,  "欢迎语"),
    /**
     * 安抚语
     */
    COMFORT_WORD(3,  "安抚语"),
    /**
     * 留言语
     */
    LEAVE_WORD(4,  "留言语"),
    /**
     * 人工关键词
     */
    HUMAN_WORD(5,  "人工关键词"),
    /**
     * 离开聊天室
     */
    OUT_GROUP(6,  "离开聊天室"),
    ;


    private final Integer value;

    private final String desc;

    WordFlagEnum(Integer value, String desc) {
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
