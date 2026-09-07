package com.platform.mesh.ai.biz.chat.service;

import com.platform.mesh.ai.biz.chat.domain.dto.AiAppDTO;
import com.platform.mesh.ai.biz.chat.domain.dto.AiFieldDTO;
import com.platform.mesh.ai.biz.chat.domain.dto.AiImageDTO;
import com.platform.mesh.ai.biz.chat.domain.dto.AiMsgDTO;
import com.platform.mesh.ai.biz.chat.domain.vo.AiMsgVO;
import com.platform.mesh.utils.result.Result;
import org.springframework.ai.audio.tts.Speech;
import reactor.core.publisher.Flux;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description AI生成
 * @author 蝉鸣
 */
public interface IAiChatService{

    /**
     * 功能描述:
     * 〈文本生成〉
     * @param msgDTO msgDTO
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    String genChat(AiMsgDTO msgDTO);

    /**
     * 功能描述:
     * 〈文本生成〉
     * @param msgDTO msgDTO
     * @return 正常返回:{@link Flux<Result<AiMsgVO>>}
     * @author 蝉鸣
     */
    Flux<Result<AiMsgVO>> genChatStream(AiMsgDTO msgDTO);

    /**
     * 功能描述:
     * 〈思维导图生成〉
     * @param msgDTO msgDTO
     * @return 正常返回:{@link Flux<Result<String>>}
     * @author 蝉鸣
     */
    Flux<Result<String>> genMindMapStream(AiMsgDTO msgDTO);

    /**
     * 功能描述:
     * 〈生成图片〉
     * @param imageDTO imageDTO
     * @return 正常返回:{@link Long}
     * @author 蝉鸣
     */
    String genChatImage(AiImageDTO imageDTO);

    /**
     * OCR格式化识别
     * @param msgDTO msgDTO
     * @return 正常返回:{@link Flux<AiMsgVO>}
     * @author 蝉鸣
     */
    Flux<AiMsgVO> ocrChatStream(AiMsgDTO msgDTO);

    /**
     * OCR格式化识别
     * @param msgDTO msgDTO
     * @return 正常返回:{@link Flux<AiMsgVO>}
     * @author 蝉鸣
     */
    <T> Flux<AiMsgVO> ocrChatStream(AiMsgDTO msgDTO,Class<T> clazz);

    /**
     * 文字转语音
     * @param msgDTO msgDTO
     * @return 正常返回:{@link Flux<Speech>}
     * @author 蝉鸣
     */
    Flux<Speech> textToSoundStream(AiMsgDTO msgDTO);

    /**
     * 语音转文字
     * @param msgDTO msgDTO
     * @return 正常返回:{@link Flux<String>}
     * @author 蝉鸣
     */
    Flux<String> soundToTextStream(AiMsgDTO msgDTO);

    /**
     * 智能体问答
     * @param msgDTO msgDTO
     * @return 正常返回:{@link Flux<String>}
     * @author 蝉鸣
     */
    Flux<Result<String>> callByAgentStream(AiMsgDTO msgDTO);

    /**
     * 智能体问答
     * @param msgDTO msgDTO
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    String callByAgent(AiMsgDTO msgDTO);

    /**
     * 智能体智能创建
     * @param appDTO appDTO
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    String callByAgentAppAdd(AiAppDTO appDTO);

    /**
     * 识别信息
     * @param fieldDTO fieldDTO
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    Object ocrChatWithField(AiFieldDTO fieldDTO);
}
