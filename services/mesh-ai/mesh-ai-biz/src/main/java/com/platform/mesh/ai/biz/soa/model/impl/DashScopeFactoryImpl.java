package com.platform.mesh.ai.biz.soa.model.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.api.DashScopeAudioSpeechApi;
import com.alibaba.cloud.ai.dashscope.api.DashScopeAudioTranscriptionApi;
import com.alibaba.cloud.ai.dashscope.api.DashScopeImageApi;
import com.alibaba.cloud.ai.dashscope.audio.transcription.DashScopeAudioTranscriptionModel;
import com.alibaba.cloud.ai.dashscope.audio.transcription.DashScopeAudioTranscriptionOptions;
import com.alibaba.cloud.ai.dashscope.audio.tts.DashScopeAudioSpeechModel;
import com.alibaba.cloud.ai.dashscope.audio.tts.DashScopeAudioSpeechOptions;
import com.alibaba.cloud.ai.dashscope.embedding.text.DashScopeEmbeddingModel;
import com.alibaba.cloud.ai.dashscope.embedding.text.DashScopeEmbeddingOptions;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import com.platform.mesh.ai.biz.chat.domain.dto.AiImageDTO;
import com.platform.mesh.ai.biz.chat.domain.dto.AiMsgDTO;
import com.platform.mesh.ai.biz.modules.ai.model.domain.po.AiModel;
import com.platform.mesh.ai.biz.soa.base.properties.AiBaseProperties;
import com.platform.mesh.ai.biz.soa.model.AiModelService;
import com.platform.mesh.ai.biz.soa.model.enums.ModelFlagEnum;
import com.platform.mesh.core.constants.NumberConst;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.model.SimpleApiKey;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

/**
 * @description AI平台工厂实现
 * @author 蝉鸣
 */
@Service
public class DashScopeFactoryImpl implements AiModelService {

    /**
     * 功能描述:
     * 〈AI平台类型〉
     * @return 正常返回:{@link ModelFlagEnum}
     * @author 蝉鸣
     */
    @Override
    public ModelFlagEnum aiModel() {
        return ModelFlagEnum.QWEN;
    }

    /**
     * 功能描述:
     * 〈获取对象〉
     * @return 正常返回:{@link ModelFlagEnum}
     * @author 蝉鸣
     */
    @Override
    public <T extends AiBaseProperties> ChatModel getChatModel(T aiProperties) {
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .baseUrl(aiProperties.getBaseUrl())
                .apiKey(aiProperties.getApiKey())
                .model(ObjectUtil.defaultIfEmpty(aiProperties.getModelName(), DashScopeApi.DEFAULT_CHAT_MODEL))
                .temperature(aiProperties.getTemperature())
                .build();
        return OpenAiChatModel.builder()
                .options(options)
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
                .build();
    }

    /**
     * 获得 生成图片模型 对象
     * @return ImageModel 对象
     * @author 蝉鸣
     */
    @Override
    public <T extends AiBaseProperties> ImageModel getImageModel(T aiProperties) {
        DashScopeImageApi dashScopeImageApi = DashScopeImageApi.builder()
                .apiKey(aiProperties.getApiKey())
                .build();
        return DashScopeImageModel.builder()
                .dashScopeApi(dashScopeImageApi)
                .build();
    }

    /**
     * 功能描述:
     * 〈构建 ImageOptions 对象〉
     * @return 正常返回:{@link ImageOptions}
     * @author 蝉鸣
     */
    @Override
    public ImageOptions getImageOptions(AiModel aiModel, AiImageDTO imageDTO) {
        return DashScopeImageOptions.builder()
                .model(aiModel.getModelName())
                .n(NumberConst.NUM_1)
                .height(imageDTO.getHeight())
                .width(imageDTO.getWidth())
                .build();
    }

    /**
     * 获得 嵌入模型 对象
     * @return EmbeddingModel 对象
     * @author 蝉鸣
     */
    @Override
    public <T extends AiBaseProperties> EmbeddingModel getEmbeddingModel(T aiProperties) {
        DashScopeApi dashScopeApi = DashScopeApi.builder()
                .apiKey(aiProperties.getApiKey())
                .build();
        DashScopeEmbeddingOptions dashScopeEmbeddingOptions = DashScopeEmbeddingOptions.builder()
                .model(DashScopeApi.DEFAULT_EMBEDDING_MODEL)
                .textType(DashScopeApi.DEFAULT_EMBEDDING_TEXT_TYPE)
                .build();
        return new DashScopeEmbeddingModel(dashScopeApi, MetadataMode.EMBED, dashScopeEmbeddingOptions);
    }

    /**
     * 获得 文字转语音 对象
     * @return TextToSpeechModel 对象
     * @author 蝉鸣
     */
    @Override
    public <T extends AiBaseProperties> TextToSpeechModel getSpeechSynthesisModel(T properties) {
        DashScopeAudioSpeechApi dashScopeAudioSpeechApi = DashScopeAudioSpeechApi.builder()
                .apiKey(new SimpleApiKey(properties.getApiKey()))
                .build();
        DashScopeAudioSpeechOptions dashScopeAudioSpeechOptions = DashScopeAudioSpeechOptions.builder()
                // 不同模型可能不支持字级别音素边界
                .model(properties.getModelName())
                .format("wav")
                .wordTimestampEnabled(Boolean.TRUE)
                .phonemeTimestampEnabled(Boolean.TRUE)
                .build();
        return DashScopeAudioSpeechModel.builder()
                .audioSpeechApi(dashScopeAudioSpeechApi)
                .defaultOptions(dashScopeAudioSpeechOptions)
                .build();
    }

    /**
     * 获得 语音转文字 对象
     * @return TranscriptionModel 对象
     * @author 蝉鸣
     */
    @Override
    public <T extends AiBaseProperties> TranscriptionModel getDashScopeAudioTranscriptionModel(T properties) {
        DashScopeAudioTranscriptionApi dashScopeAudioTranscriptionApi = DashScopeAudioTranscriptionApi.builder()
                .apiKey(new SimpleApiKey(properties.getApiKey()))
                .build();
        DashScopeAudioTranscriptionOptions dashScopeAudioTranscriptionOptions = DashScopeAudioTranscriptionOptions
                .builder()
                .model(properties.getModelName())
                .build();
        return DashScopeAudioTranscriptionModel.builder()
                .audioTranscriptionApi(dashScopeAudioTranscriptionApi)
                .defaultOptions(dashScopeAudioTranscriptionOptions)
                .build();
    }
}
