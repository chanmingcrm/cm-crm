package com.platform.mesh.ai.biz.modules.ai.knowledgedoc.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 文件标识枚举
 * @author 蝉鸣
 */
@Schema(description = "知识库文档枚举",enumAsRef = true)
public enum ContentSourceEnum implements BaseEnum<ContentSourceEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,  "元状态"),
    /**
     * 文本
     */
    TEXT(1,  "TEXT"),
    /**
     * 本地文件
     */
    FILE(2,  "FILE"),
    /**
     * 网络资源
     */
    URL(3,  "URL"),
    /**
     * 系统在线文档
     */
    ONLINE(4,  "ONLINE"),
    ;


    private final Integer value;

    private final String desc;

    ContentSourceEnum(Integer value, String desc) {
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
