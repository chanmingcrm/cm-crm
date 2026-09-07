package com.platform.mesh.ai.biz.soa.model.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @description AI类型枚举
 * @author 蝉鸣
 */
@Schema(description = "AI类型枚举",enumAsRef = true)
public enum ModelFlagEnum implements BaseEnum<ModelFlagEnum, Integer> {

    // ========== 国外平台 ==========

    /**
     * OpenAI 官方
     */
    OPEN_AI(1,101, "OpenAI"),
    /**
     * 微软
     */
    AZURE_OPENAI(1,102, "AzureOpenAI"),
    /**
     * Ollama
     */
    OLLAMA(1,103, "Ollama"),

    // ========== 国内平台 ==========
    /**
     * 深度求索
     */
    DEEP_SEEK(2,201, "DeepSeek"),
    /**
     * 通义千问
     */
    QWEN(2,202, "通义千问"),
    /**
     * 文心一言
     */
    QIAN_FAN(2,203, "文心一言"),
    /**
     * 字节豆包
     */
    DOU_BAO(2,204, "豆包"),
    /**
     * 腾讯混元
     */
    HUN_YUAN(2,205, "混元"),

    ;

    @Getter
    private final Integer code;

    private final Integer value;

    private final String desc;

    ModelFlagEnum(Integer code, Integer value, String desc) {
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
