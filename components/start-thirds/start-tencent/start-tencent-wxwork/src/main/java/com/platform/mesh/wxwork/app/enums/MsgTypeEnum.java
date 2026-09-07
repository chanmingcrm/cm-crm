package com.platform.mesh.wxwork.app.enums;


import com.platform.mesh.core.enums.base.BaseEnum;

/**
 * @description 消息类型
 * 当前自定义机器人支持
 * 文本（text）、
 * markdown（markdown）、
 * 图片（image）、
 * 图文（news）、
 * 文件（file）、
 * 语音（voice）、
 * 模板卡片（template_card）
 * 七种消息类型
 * @author 蝉鸣
 */
public enum MsgTypeEnum implements BaseEnum<MsgTypeEnum, String> {

    /**
     * 文本
     */
    TEXT("text",  "文本"),
    /**
     * markdown
     */
    MARKDOWN("markdown",  "markdown"),
    /**
     * 图片
     */
    IMAGE("image",  "图片"),
    /**
     * 图文
     */
    NEWS("news",  "图文"),
    /**
     * 文件
     */
    FILE("file",  "文件"),
    /**
     * 语音
     */
    VOICE("voice",  "语音"),
    /**
     * 模板卡片
     */
    TEMPLATE_CARD("template_card",  "模板卡片"),

    ;


    private final String value;

    private final String desc;

    MsgTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return this.value;
    }
    @Override
    public String getDesc() {
        return this.desc;
    }
}
