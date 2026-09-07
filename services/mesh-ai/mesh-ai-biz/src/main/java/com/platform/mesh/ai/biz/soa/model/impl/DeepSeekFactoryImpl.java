package com.platform.mesh.ai.biz.soa.model.impl;

import com.platform.mesh.ai.biz.chat.domain.dto.AiImageDTO;
import com.platform.mesh.ai.biz.chat.domain.dto.AiMsgDTO;
import com.platform.mesh.ai.biz.modules.ai.model.domain.po.AiModel;
import com.platform.mesh.ai.biz.soa.base.properties.AiBaseProperties;
import com.platform.mesh.ai.biz.soa.model.AiModelService;
import com.platform.mesh.ai.biz.soa.model.enums.ModelFlagEnum;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.deepseek.DeepSeekChatOptions;
import org.springframework.ai.deepseek.api.DeepSeekApi;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptions;
import org.springframework.stereotype.Service;

/**
 * @description AI平台工厂实现
 * @author 蝉鸣
 */
@Service
public class DeepSeekFactoryImpl implements AiModelService {

    /**
     * 功能描述:
     * 〈AI平台类型〉
     * @return 正常返回:{@link ModelFlagEnum}
     * @author 蝉鸣
     */
    @Override
    public ModelFlagEnum aiModel() {
        return ModelFlagEnum.DEEP_SEEK;
    }

    /**
     * 功能描述:
     * 〈获得 会话模型 对象〉
     * @return 正常返回:{@link ChatModel}
     * @author 蝉鸣
     */
    @Override
    public <T extends AiBaseProperties> ChatModel getChatModel(T aiProperties) {
        DeepSeekApi.ChatModel chatModel = DeepSeekApi.ChatModel.DEEPSEEK_V4_PRO;
        if (DeepSeekApi.ChatModel.DEEPSEEK_V4_FLASH.getValue().equals(aiProperties.getModelName())) {
            chatModel = DeepSeekApi.ChatModel.DEEPSEEK_V4_FLASH;
        }
        // 创建 DeepSeekChatModel对象
        return DeepSeekChatModel.builder()
                .deepSeekApi(DeepSeekApi.builder()
                        .baseUrl(aiProperties.getBaseUrl())
                        .apiKey(aiProperties.getApiKey())
                        .build())
                .options(DeepSeekChatOptions.builder()
                        .model(chatModel)
                        .temperature(aiProperties.getTemperature())
                        .maxTokens(aiProperties.getMaxTokens())
                        .topP(aiProperties.getTopP())
                        .build())
                .build();
    }

    /**
     * 功能描述:
     * 〈构建 ChatOptions 对象〉
     * @return 正常返回:{@link ChatOptions}
     * @author 蝉鸣
     */
    @Override
    public ChatOptions getChatOptions(AiModel aiModel, AiMsgDTO msgDTO) {
        // 使用模型配置构建原生会话参数，为后续装配 MCP 工具保留扩展能力。
        return DeepSeekChatOptions.builder()
                .model(aiModel.getModelName())
                .temperature(aiModel.getTemperature().doubleValue())
                .maxTokens(aiModel.getMaxTokens())
                .topP(aiModel.getTopP().doubleValue())
                .build();
    }

    /**
     * 获得 生成图片模型 对象
     * @return ImageModel 对象
     * @author 蝉鸣
     */
    @Override
    public <T extends AiBaseProperties> ImageModel getImageModel(T aiProperties) {
        return null;
    }

    /**
     * 功能描述:
     * 〈构建 ImageOptions 对象〉
     * @return 正常返回:{@link ImageOptions}
     * @author 蝉鸣
     */
    @Override
    public ImageOptions getImageOptions(AiModel aiModel, AiImageDTO imageDTO) {
        return null;
    }

    /**
     * 获得 嵌入模型 对象
     * @return EmbeddingModel 对象
     * @author 蝉鸣
     */
    @Override
    public <T extends AiBaseProperties> EmbeddingModel getEmbeddingModel(T aiProperties) {
        return null;
    }

    /**
     * 获得 文字转语音 对象
     * @return TextToSpeechModel 对象
     * @author 蝉鸣
     */
    @Override
    public <T extends AiBaseProperties> TextToSpeechModel getSpeechSynthesisModel(T properties) {
        return null;
    }

    /**
     * 获得 语音转文字 对象
     * @return TranscriptionModel 对象
     * @author 蝉鸣
     */
    @Override
    public <T extends AiBaseProperties> TranscriptionModel getDashScopeAudioTranscriptionModel(T properties) {
        return null;
    }
}
