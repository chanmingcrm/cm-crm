package com.platform.mesh.ai.biz.soa.model.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 模型支持类型枚举
 * @author 蝉鸣
 */
@Schema(description = "模型支持类型枚举",enumAsRef = true)
public enum ModelTypeEnum implements BaseEnum<ModelTypeEnum, Integer> {

    /**
     * 向量模型
     */
    EMBEDDING(1, "向量模型"),
    /**
     * 排序模型
     */
    RERANKER(2, "排序模型"),
    /**
     * 大语言模型
     */
    LLM(3, "大语言模型"),
    /**
     * 文字转语音模型
     */
    TTS(4, "文字转语音模型"),
    /**
     * 语音转文字模型
     */
    STT(5, "语音转文字模型"),
    /**
     * 图片模型
     */
    IMAGE(6, "图片模型"),
    /**
     * 视频模型
     */
    VIDEO(7, "视频模型"),

    ;

    private final Integer value;

    private final String desc;

    ModelTypeEnum(Integer value, String desc) {
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
