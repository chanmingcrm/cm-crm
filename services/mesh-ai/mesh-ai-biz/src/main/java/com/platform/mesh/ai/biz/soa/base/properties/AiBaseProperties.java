package com.platform.mesh.ai.biz.soa.base.properties;

import lombok.Data;

import java.util.List;

/**
 * @description AI基本变量
 * @author 蝉鸣
 */
@Data
public class AiBaseProperties {

    /**
     * 是否开启
     */
    private String enable;
    /**
     * API 基础地址
     */
    private String baseUrl;
    /**
     * 认证密钥
     */
    private String apiKey;
    /**
     * 模型名称
     */
    private String modelName;
    /**
     * 随机性(生成文本值越低，输出越确定（如 0.2 适合事实回答），值越高，输出越有创意（如 0.8 适合写作）)
     */
    private Double temperature;
    /**
     * 生成最大 token 数(1 token ≈ 1个英文单词或 2-3个中文字符)
     */
    private Integer maxTokens;
    /**
     * 核采样阈值(仅从概率质量最高的 token 中采样（如 0.9 表示保留前 90% 概率的 token）)
     */
    private Double topP;
    /**
     * 惩罚重复出现的 token（值越高，越避免重复）
     */
    private Double frequencyPenalty;
    /**
     * 惩罚新出现的 token（值越高，越倾向于新话题）
     */
    private Double presencePenalty;
    /**
     * 设置停止词（遇到这些词时停止生成）
     */
    private List<String> stop;
    /**
     * 是否启用流式响应（用于实时输出）
     */
    private Integer withStream;

}
