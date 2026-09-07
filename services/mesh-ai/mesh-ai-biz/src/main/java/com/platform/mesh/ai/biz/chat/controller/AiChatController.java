package com.platform.mesh.ai.biz.chat.controller;

import com.platform.mesh.ai.biz.chat.domain.dto.AiAppDTO;
import com.platform.mesh.ai.biz.chat.domain.dto.AiFieldDTO;
import com.platform.mesh.ai.biz.chat.domain.dto.AiImageDTO;
import com.platform.mesh.ai.biz.chat.domain.dto.AiMsgDTO;
import com.platform.mesh.ai.biz.chat.domain.vo.AiMsgVO;
import com.platform.mesh.ai.biz.chat.service.IAiChatService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.ai.audio.tts.Speech;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 约定当前controller 只引入当前service
 * @description AI生成
 * @author 蝉鸣
 */
@Tag(description = "AiChatController", name = "AI生成")
@RestController
@RequestMapping
public class AiChatController extends BaseController{

    @Autowired
    private IAiChatService aiChatService;

    /**
	 * 功能描述:
	 * 〈文本生成〉
	 * @param msgDTO msgDTO
	 * @return 正常返回:{@link Flux<Result<AiMsgVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "生成文本")
	@PostMapping(value = "/ai/chat/gen/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Result<AiMsgVO>> genChatStream(@RequestBody AiMsgDTO msgDTO) {
		return aiChatService.genChatStream(msgDTO);
	}

	/**
	 * 功能描述:
	 * 〈文思维导图生成〉
	 * @param msgDTO msgDTO
	 * @return 正常返回:{@link Flux<Result<String>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "导图生成（流式）")
	@PostMapping(value = "/ai/chat/gen/mind/map/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Result<String>> genMindMapStream(@RequestBody @Valid AiMsgDTO msgDTO) {
		return aiChatService.genMindMapStream(msgDTO);
	}

	/**
	 * 功能描述:
	 * 〈生成图片〉
	 * @param imageDTO imageDTO
	 * @return 正常返回:{@link Result<Long>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "生成图片")
	@PostMapping("/ai/chat/gen/image")
	public Result<String> genChatImage(@Valid @RequestBody AiImageDTO imageDTO) {
		return Result.success(aiChatService.genChatImage(imageDTO));
	}

	/**
	 * OCR格式化识别
	 * @param msgDTO msgDTO
	 * @return 正常返回:{@link Flux<AiMsgVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "OCR格式化识别")
	@PostMapping(value = "/ai/chat/ocr/image", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<AiMsgVO> ocrChatStream(@RequestBody AiMsgDTO msgDTO) {
		return aiChatService.ocrChatStream(msgDTO);
	}

	/**
	 * 文字转语音
	 * @param msgDTO msgDTO
	 * @return 正常返回:{@link Flux<Speech>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "文字转语音")
	@PostMapping(value = "/ai/chat/text/to/sound", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Speech> textToSoundStream(@RequestBody AiMsgDTO msgDTO) {
		return aiChatService.textToSoundStream(msgDTO);
	}

	/**
	 * 语音转文字
	 * @param msgDTO msgDTO
	 * @return 正常返回:{@link Flux<String>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "语音转文字")
	@PostMapping(value = "/ai/chat/sound/to/text", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> soundToTextStream(@RequestBody AiMsgDTO msgDTO) {
		return aiChatService.soundToTextStream(msgDTO);
	}

    /**
     * 语音转文字
     * @param msgDTO msgDTO
     * @return 正常返回:{@link Flux<AiMsgVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "智能体问答")
    @PostMapping(value = "/ai/chat/by/agent/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Result<String>> callByAgentStream(@RequestBody AiMsgDTO msgDTO) {
        return aiChatService.callByAgentStream(msgDTO);
    }

    /**
     * 语音转文字
     * @param msgDTO msgDTO
     * @return 正常返回:{@link Result<String>}
     * @author 蝉鸣
     */
    @Operation(summary = "智能体问答")
    @PostMapping(value = "/ai/chat/by/agent")
    public Result<String> callByAgent(@RequestBody AiMsgDTO msgDTO) {
        String result = aiChatService.callByAgent(msgDTO);
        return Result.success(result);
    }

    /**
     * 智能体智能创建
     * @param appDTO appDTO
     * @return 正常返回:{@link Result<String>}
     * @author 蝉鸣
     */
    @Operation(summary = "智能体智能创建")
    @PostMapping(value = "/ai/chat/by/agent/app/add")
    public Result<String> callByAgentAppAdd(@RequestBody AiAppDTO appDTO) {
        String result = aiChatService.callByAgentAppAdd(appDTO);
        return Result.success(result);
    }

	/**
	 * OCR格式化识别
	 * @param fieldDTO fieldDTO
	 * @return 正常返回:{@link Flux<String>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "OCR格式化识别")
	@PostMapping(value = "/ai/chat/ocr/with/field")
	public Result<Object> ocrChatWithField(@Valid @RequestBody AiFieldDTO fieldDTO) {
		return Result.success("操作成功", aiChatService.ocrChatWithField(fieldDTO));
	}
}
