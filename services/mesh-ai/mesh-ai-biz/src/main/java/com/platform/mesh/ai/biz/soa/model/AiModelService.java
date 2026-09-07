package com.platform.mesh.ai.biz.soa.model;

import com.platform.mesh.ai.biz.chat.domain.dto.AiImageDTO;
import com.platform.mesh.ai.biz.chat.domain.dto.AiMsgDTO;
import com.platform.mesh.ai.biz.modules.ai.model.domain.po.AiModel;
import com.platform.mesh.ai.biz.soa.base.properties.AiBaseProperties;
import com.platform.mesh.ai.biz.soa.model.enums.ModelFlagEnum;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptions;

/**
 * @description 动作工厂
 * @author 蝉鸣
 */
public interface AiModelService {

    /**
     * 功能描述:
     * 〈模型类型〉
     * @return 正常返回:{@link ModelFlagEnum}
     * @author 蝉鸣
     */
    ModelFlagEnum aiModel();

    /**
     * 功能描述:
     * 〈获得 会话模型 对象〉
     * @return 正常返回:{@link ChatModel}
     * @author 蝉鸣
     */
    <T extends AiBaseProperties> ChatModel getChatModel(T aiProperties);

    /**
     * 功能描述:
     * 〈构建 ChatOptions 对象〉
     * @return 正常返回:{@link ChatModel}
     * @author 蝉鸣
     */
    ChatOptions getChatOptions(AiModel aiModel, AiMsgDTO msgDTO);

    /**
     * 获得 生成图片模型 对象
     * @return ImageModel 对象
     * @author 蝉鸣
     */
    <T extends AiBaseProperties> ImageModel getImageModel(T aiProperties);

    /**
     * 功能描述:
     * 〈构建 ImageOptions 对象〉
     * @return 正常返回:{@link ImageOptions}
     * @author 蝉鸣
     */
    ImageOptions getImageOptions(AiModel aiModel, AiImageDTO imageDTO);

    /**
     * 获得 嵌入模型 对象
     * @return EmbeddingModel 对象
     * @author 蝉鸣
     */
    <T extends AiBaseProperties> EmbeddingModel getEmbeddingModel(T aiProperties);

    /**
     * 获得 文字转语音 对象
     * @return TextToSpeechModel 对象
     * @author 蝉鸣
     */
    <T extends AiBaseProperties> TextToSpeechModel getSpeechSynthesisModel(T properties);

    /**
     * 获得 语音转 文字对象
     * @return TranscriptionModel 对象
     * @author 蝉鸣
     */
    <T extends AiBaseProperties> TranscriptionModel getDashScopeAudioTranscriptionModel(T properties);
}
