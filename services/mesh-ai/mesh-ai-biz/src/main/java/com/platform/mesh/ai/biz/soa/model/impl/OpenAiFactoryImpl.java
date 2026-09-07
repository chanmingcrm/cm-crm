package com.platform.mesh.ai.biz.soa.model.impl;

import com.openai.client.OpenAIClient;
import com.openai.client.OpenAIClientAsync;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.client.okhttp.OpenAIOkHttpClientAsync;
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
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.openai.*;
import org.springframework.stereotype.Service;

/**
 * @description AI平台工厂实现
 * @author 蝉鸣
 */
@Service
public class OpenAiFactoryImpl implements AiModelService {

    /**
     * 功能描述:
     * 〈AI平台类型〉
     * @return 正常返回:{@link ModelFlagEnum}
     * @author 蝉鸣
     */
    @Override
    public ModelFlagEnum aiModel() {
        return ModelFlagEnum.OPEN_AI;
    }

    /**
     * 功能描述:
     * 〈获取对象〉
     * @return 正常返回:{@link ModelFlagEnum}
     * @author 蝉鸣
     */
    @Override
    public <T extends AiBaseProperties> ChatModel getChatModel(T aiProperties) {
        OpenAIClient openAiClient = this.getOpenAiClient(aiProperties);
        // 创建 OpenAi 对象
        return OpenAiChatModel.builder()
                .openAiClient(openAiClient)
                .openAiClientAsync(this.getOpenAiAsyncClient(aiProperties))
                .options(OpenAiChatOptions.builder()
                        .model(aiProperties.getModelName())
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
        return OpenAiChatOptions.builder()
                .model(aiModel.getModelName())
                .temperature(aiModel.getTemperature().doubleValue())
                .maxTokens(aiModel.getMaxTokens())
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
     * 〈构建 ChatOptions 对象〉
     * @return 正常返回:{@link ChatOptions}
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
        OpenAIClient openAiClient = this.getOpenAiClient(aiProperties);
        OpenAiEmbeddingOptions options = OpenAiEmbeddingOptions.builder()
                .model(aiProperties.getModelName())
                .build();
        return OpenAiEmbeddingModel.builder()
                .openAiClient(openAiClient)
                .metadataMode(MetadataMode.EMBED)
                .options(options)
                .build();
    }

    /**
     * 获得 文字转语音 对象
     * @return TextToSpeechModel 对象
     * @author 蝉鸣
     */
    @Override
    public <T extends AiBaseProperties> TextToSpeechModel getSpeechSynthesisModel(T properties) {
        OpenAIClient openAiClient = this.getOpenAiClient(properties);
        OpenAiAudioSpeechOptions options = OpenAiAudioSpeechOptions.builder()
                .model(properties.getModelName())
                .build();
        return OpenAiAudioSpeechModel.builder()
                .openAiClient(openAiClient)
                .options(options)
                .build();
    }

    /**
     * 获得 语音转文字 对象
     * @return TranscriptionModel 对象
     * @author 蝉鸣
     */
    @Override
    public <T extends AiBaseProperties> TranscriptionModel getDashScopeAudioTranscriptionModel(T properties) {
        OpenAIClient openAiClient = this.getOpenAiClient(properties);
        OpenAiAudioTranscriptionOptions options = OpenAiAudioTranscriptionOptions.builder()
                .model(properties.getModelName())
                .build();
        return OpenAiAudioTranscriptionModel.builder()
                .openAiClient(openAiClient)
                .options(options)
                .build();
    }

    private <T extends AiBaseProperties> OpenAIClient getOpenAiClient(T aiProperties) {
        return OpenAIOkHttpClient.builder()
                .baseUrl(aiProperties.getBaseUrl())
                .apiKey(aiProperties.getApiKey())
                .build();
    }

    private <T extends AiBaseProperties> OpenAIClientAsync getOpenAiAsyncClient(T aiProperties) {
        return OpenAIOkHttpClientAsync.builder()
                .baseUrl(aiProperties.getBaseUrl())
                .apiKey(aiProperties.getApiKey())
                .build();
    }
}
