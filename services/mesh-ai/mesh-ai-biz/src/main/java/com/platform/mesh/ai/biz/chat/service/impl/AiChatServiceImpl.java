package com.platform.mesh.ai.biz.chat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.ai.biz.chat.domain.dto.AiAppDTO;
import com.platform.mesh.ai.biz.chat.domain.dto.AiFieldDTO;
import com.platform.mesh.ai.biz.chat.domain.dto.AiImageDTO;
import com.platform.mesh.ai.biz.chat.domain.dto.AiMsgDTO;
import com.platform.mesh.ai.biz.chat.domain.vo.AiMsgVO;
import com.platform.mesh.ai.biz.chat.exception.AiChatExceptionEnum;
import com.platform.mesh.ai.biz.chat.service.IAiChatService;
import com.platform.mesh.ai.biz.chat.service.manual.AiChatServiceManual;
import com.platform.mesh.ai.biz.chat.service.manual.AiOcrServiceManual;
import com.platform.mesh.ai.biz.modules.ai.agent.domain.po.AiAgent;
import com.platform.mesh.ai.biz.modules.ai.model.domain.po.AiModel;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.service.manual.McpServerToolService;
import com.platform.mesh.ai.biz.modules.ai.session.domain.po.AiSession;
import com.platform.mesh.ai.biz.soa.base.properties.AiBaseProperties;
import com.platform.mesh.ai.biz.soa.model.AiModelService;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.utils.file.DocFileUtil;
import com.platform.mesh.utils.context.ThreadContextSnapshot;
import com.platform.mesh.utils.result.Result;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.Speech;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.image.ImageMessage;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description AI生成
 * @author 蝉鸣
 */
@Service
public class AiChatServiceImpl implements IAiChatService {

    private static final Logger log = LoggerFactory.getLogger(AiChatServiceImpl.class);
    private static final Duration MCP_CALL_TIMEOUT = Duration.ofSeconds(45);
    private static final Scheduler MCP_CALL_SCHEDULER = Schedulers.newBoundedElastic(
            32, 256, "ai-mcp-call");

    @Autowired
    private AiChatServiceManual aiChatServiceManual;

    @Autowired
    private AiOcrServiceManual aiOcrServiceManual;

    @Autowired
    private McpServerToolService mcpServerToolService;

    /**
     * 功能描述:
     * 〈文本生成〉
     * @param msgDTO msgDTO
     * @return 正常返回:{@link Flux<AiMsgVO>}
     * @author 蝉鸣
     */
    @Override
    public String genChat(AiMsgDTO msgDTO) {
        //获取会话
        AiSession aiSession = aiChatServiceManual.getAiSession(msgDTO.getSessionId());
        //获取模型对象
        AiModel aiModel = aiChatServiceManual.getAiModel(aiSession.getModelId());
        //获取模型服务
        AiModelService modelService = aiChatServiceManual.getAiModelService(aiModel);
        //获取模型配置
        AiBaseProperties properties = BeanUtil.copyProperties(aiModel, AiBaseProperties.class);
        //获取模型实例
        ChatModel chatModel = modelService.getChatModel(properties);
        if(Objects.isNull(chatModel)){
            throw AiChatExceptionEnum.ADD_NO_MODEL_CHAT.getBaseException();
        }
        //获取消息
        List<Message> chatMessages = aiChatServiceManual.genChatMessages(msgDTO);
        //获取构建属性
        ChatOptions chatOptions = mcpServerToolService.withExternalTools(
                modelService.getChatOptions(aiModel, msgDTO), msgDTO.getContent());
        //返回提示词对象Prompt
        Prompt prompt = new Prompt(chatMessages, chatOptions);
        //请求响应
        String newContent = Objects.requireNonNull(chatModel.call(prompt).getResult()).getOutput().getText();
        aiChatServiceManual.addSessionHis(aiSession,msgDTO,newContent);
        return newContent;
    }

    /**
     * 功能描述:
     * 〈文本生成〉
     * @param msgDTO msgDTO
     * @return 正常返回:{@link Flux<AiMsgVO>}
     * @author 蝉鸣
     */
    @Override
    public Flux<Result<AiMsgVO>> genChatStream(AiMsgDTO msgDTO) {
        return Flux.defer(() -> {
            long requestStartedAt = System.nanoTime();
            //获取会话
            AiSession aiSession = aiChatServiceManual.getAiSession(msgDTO.getSessionId());
            //获取模型对象
            AiModel aiModel = aiChatServiceManual.getAiModel(aiSession.getModelId());
            //获取模型服务
            AiModelService modelService = aiChatServiceManual.getAiModelService(aiModel);
            //获取模型配置
            AiBaseProperties properties = BeanUtil.copyProperties(aiModel, AiBaseProperties.class);
            //获取模型实例
            ChatModel chatModel = modelService.getChatModel(properties);
            if(Objects.isNull(chatModel)){
                throw AiChatExceptionEnum.ADD_NO_MODEL_CHAT.getBaseException();
            }
            //获取消息
            List<Message> chatMessages = aiChatServiceManual.genChatMessages(msgDTO);
            //获取构建属性
            ChatOptions chatOptions = mcpServerToolService.withExternalTools(
                    modelService.getChatOptions(aiModel, msgDTO), msgDTO.getContent());
            //返回提示词对象Prompt
            Prompt prompt = new Prompt(chatMessages, chatOptions);
            // MCP 工具调用需要由会话客户端驱动完整的模型、工具及模型响应循环。
            if (chatOptions instanceof ToolCallingChatOptions toolOptions
                    && toolOptions.getToolCallbacks() != null
                    && !toolOptions.getToolCallbacks().isEmpty()) {
                // 步骤一：仅场景中心携带的显式 JSON 参数允许跳过模型参数推理。
                String directArguments = McpServerToolService.requestedToolArguments(
                        msgDTO.getContent());

                // 步骤二：异步线程必须恢复登录上下文，确保工具继续执行账号数据权限校验。
                ThreadContextSnapshot contextSnapshot = ThreadContextSnapshot.capture();
                return Mono.fromSupplier(contextSnapshot.wrap(() -> {
                    String content = executeMcpRequest(chatModel, prompt, toolOptions,
                            directArguments, msgDTO.getSessionId());
                    long modelCompletedAt = System.nanoTime();
                    if (ObjectUtil.isEmpty(content)) {
                        throw AiChatExceptionEnum.CHAT_NO_CONTENT.getBaseException();
                    }
                    // 步骤三：工具调用完成后保存最终回复，并以单条结果返回页面。
                    aiChatServiceManual.addSessionHis(aiSession, msgDTO, content);
                    log.info("AI MCP会话完成，sessionId={}, toolCount={}, modelAndToolMs={}, totalMs={}",
                            msgDTO.getSessionId(), toolOptions.getToolCallbacks().size(),
                            (modelCompletedAt - requestStartedAt) / 1_000_000,
                            (System.nanoTime() - requestStartedAt) / 1_000_000);
                    return Result.success(new AiMsgVO().setContent(content));
                }))
                // 步骤四：使用有界调度器和统一超时，避免高并发下无限创建线程或长期占用请求。
                .subscribeOn(MCP_CALL_SCHEDULER)
                .timeout(MCP_CALL_TIMEOUT)
                .doOnCancel(() -> log.warn("AI MCP会话已取消，sessionId={}", msgDTO.getSessionId()))
                .onErrorResume(exception -> {
                    // 记录完整异常链，页面仅返回可定位且不泄露内部实现的错误信息。
                    log.error("AI MCP 会话调用失败，sessionId={}", msgDTO.getSessionId(), exception);
                    String message = exception instanceof TimeoutException
                            ? "请求处理超时，请稍后重试"
                            : rootCauseMessage(exception);
                    return Mono.just(Result.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "AI MCP 调用失败：" + message));
                })
                .flux();
            }
            //请求响应
            Flux<ChatResponse> streamResponse = chatModel.stream(prompt);
            //收集信息
            StringBuffer contentBuffer = new StringBuffer();
            //流式返回
            return streamResponse
                    .map(chunk->{
                        // 响应结果
                        String newContent = ObjectUtil.isNotNull(chunk)
                                && ObjectUtil.isNotNull(chunk.getResult())
                                && ObjectUtil.isNotNull(chunk.getResult().getOutput()) ?
                                chunk.getResult().getOutput().getText() : StrUtil.EMPTY;
                        if(ObjectUtil.isNotEmpty(newContent)){
                            contentBuffer.append(newContent);
                        }
                        return Result.success(new AiMsgVO().setContent(newContent));
                    })
                    .filter(result -> ObjectUtil.isNotNull(result.getData())
                            && ObjectUtil.isNotEmpty(result.getData().getContent()))
                    .doOnComplete(()->{
                        // 响应结束后更新记忆
                        String newContent = contentBuffer.toString();
                        if(ObjectUtil.isNotEmpty(newContent)){
                            aiChatServiceManual.addSessionHis(aiSession,msgDTO,newContent);
                        }
                    })
                    .doOnCancel(() -> log.warn("AI chat stream canceled, sessionId: {}", msgDTO.getSessionId()))
                    .timeout(Duration.ofSeconds(NumberConst.NUM_480))
                    .switchIfEmpty(Flux.error(AiChatExceptionEnum.CHAT_NO_CONTENT.getBaseException()))
                    .retryWhen(Retry.backoff(NumberConst.NUM_1, Duration.ofMillis(NumberConst.NUM_256))
                            .filter(this::isChatNoContentException)
                            .doBeforeRetry(retrySignal -> log.warn("AI chat stream no content, retry: {}, sessionId: {}",
                                    retrySignal.totalRetries() + NumberConst.NUM_1, msgDTO.getSessionId())))
                    .doOnError(ex -> log.error("AI chat stream error, sessionId: {}", msgDTO.getSessionId(), ex))
                    .onErrorResume(this::isChatNoContentException,
                            ex -> Flux.just(Result.error(HttpStatus.NO_CONTENT.value(),
                                    AiChatExceptionEnum.CHAT_NO_CONTENT.getDesc())))
                    .onErrorResume(ex -> Flux.just(Result.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "AI生成失败：" + ex.getMessage())));
        });
    }

    /**
     * 功能描述:
     * 〈判断异常是否为模型未返回有效内容〉
     * @param exception 调用异常
     * @return 是否为模型空响应异常
     * @author qingfeng
     */
    private boolean isChatNoContentException(Throwable exception) {
        return exception instanceof BaseException baseException
                && Objects.equals(baseException.getModule(),
                        AiChatExceptionEnum.CHAT_NO_CONTENT.getModule())
                && Objects.equals(baseException.getCode(),
                        AiChatExceptionEnum.CHAT_NO_CONTENT.getCode());
    }

    /**
     * 功能描述:
     * 〈获取异常链中最底层的有效消息〉
     * @param exception 会话调用异常
     * @return 可展示的根因消息
     * @author qingfeng
     */
    private String rootCauseMessage(Throwable exception) {
        Throwable cause = exception;
        while (cause.getCause() != null && cause.getCause() != cause) {
            cause = cause.getCause();
        }
        return StrUtil.blankToDefault(cause.getMessage(), "下游服务调用异常");
    }

    /**
     * 功能描述:
     * 〈执行确定性 MCP 场景或普通模型工具调用循环〉
     *
     * <p>场景中心明确提供参数且仅匹配到一个工具时直接执行；其他自然语言请求仍交由
     * 模型完成参数理解、工具调用和最终回答，避免改变原有对话能力。</p>
     *
     * @param chatModel 当前会话模型
     * @param prompt 完整会话提示词
     * @param toolOptions 已装配工具的聊天参数
     * @param directArguments 场景中心显式工具参数
     * @param sessionId 当前会话 ID
     * @return 最终工具或模型响应内容
     * @author qingfeng
     */
    private String executeMcpRequest(ChatModel chatModel, Prompt prompt,
            ToolCallingChatOptions toolOptions, String directArguments, Long sessionId) {
        if (directArguments != null && toolOptions.getToolCallbacks().size() == 1) {
            var callback = toolOptions.getToolCallbacks().getFirst();
            String content = callback.call(directArguments);
            log.info("AI MCP场景工具直接执行，sessionId={}, tool={}",
                    sessionId, callback.getToolDefinition().name());
            return content;
        }
        return ChatClient.builder(chatModel)
                .build()
                .prompt(prompt)
                .call()
                .content();
    }

    /**
     * 功能描述:
     * 〈思维导图生成〉
     * @param msgDTO msgDTO
     * @return 正常返回:{@link Flux<Result<String>>}
     * @author 蝉鸣
     */
    @Override
    public Flux<Result<String>> genMindMapStream(AiMsgDTO msgDTO) {
        //获取会话
        AiSession aiSession = aiChatServiceManual.getAiSession(msgDTO.getSessionId());
        //获取模型对象
        AiModel aiModel = aiChatServiceManual.getAiModel(aiSession.getModelId());
        //获取模型服务
        AiModelService modelService = aiChatServiceManual.getAiModelService(aiModel);
        //获取模型配置
        AiBaseProperties properties = BeanUtil.copyProperties(aiModel, AiBaseProperties.class);
        //获取模型实例
        ChatModel chatModel = modelService.getChatModel(properties);
        if(Objects.isNull(chatModel)){
            throw AiChatExceptionEnum.ADD_NO_MODEL_CHAT.getBaseException();
        }
        //获取消息
        List<Message> chatMessages = aiChatServiceManual.genChatMessages(msgDTO);
        //获取构建属性
        ChatOptions chatOptions = modelService.getChatOptions(aiModel, msgDTO);
        //返回提示词对象Prompt
        Prompt prompt = new Prompt(chatMessages, chatOptions);
        //请求响应
        Flux<ChatResponse> streamResponse = chatModel.stream(prompt);
        //流式返回
        return streamResponse.map(chunk->{
            // 响应结果
            String newContent = ObjectUtil.isNotNull(chunk.getResult()) ? chunk.getResult().getOutput().getText() : StrUtil.EMPTY;
            return Result.success(newContent);
        });
    }

    /**
     * 功能描述:
     * 〈生成图片〉
     * @param imageDTO imageDTO
     * @return 正常返回:{@link Long}
     * @author 蝉鸣
     */
    @Override
    public String genChatImage(AiImageDTO imageDTO) {
        //获取模型对象
        AiModel aiModel = aiChatServiceManual.getAiModel(imageDTO.getModelId());
        //获取模型服务
        AiModelService modelService = aiChatServiceManual.getAiModelService(aiModel);
        //获取模型配置
        AiBaseProperties properties = BeanUtil.copyProperties(aiModel, AiBaseProperties.class);
        //获取模型实例
        ImageModel imageModel = modelService.getImageModel(properties);
        //获取消息体
        List<ImageMessage> imageMessage = aiChatServiceManual.genImageMessages(imageDTO);
        //获取构建属性
        ImageOptions imageOptions = modelService.getImageOptions(aiModel, imageDTO);
        //返回提示词对象Prompt
        ImagePrompt imagePrompt = new ImagePrompt(imageMessage, imageOptions);
        CompletableFuture<String> imageAsync = aiChatServiceManual.getImageAsync(imageModel, imagePrompt);
        return imageAsync.join();
    }

    /**
     * OCR格式化识别
     * @param msgDTO msgDTO
     * @return 正常返回:{@link Flux<AiMsgVO>}
     * @author 蝉鸣
     */
    @Override
    public Flux<AiMsgVO> ocrChatStream(AiMsgDTO msgDTO) {
        //获取会话
        AiSession aiSession = aiChatServiceManual.getAiSession(msgDTO.getSessionId());
        //获取模型对象
        AiModel aiModel = aiChatServiceManual.getAiModel(aiSession.getModelId());
        //获取模型服务
        AiModelService modelService = aiChatServiceManual.getAiModelService(aiModel);
        //获取模型配置
        AiBaseProperties properties = BeanUtil.copyProperties(aiModel, AiBaseProperties.class);
        //获取模型实例
        ChatModel ocrModel = modelService.getChatModel(properties);
        if(Objects.isNull(ocrModel)){
            throw AiChatExceptionEnum.ADD_NO_MODEL_CHAT.getBaseException();
        }
        //获取消息体
        List<Message> ocrMessage = aiChatServiceManual.genOcrMessages(msgDTO);
        //获取构建属性
        ChatOptions chatOptions = modelService.getChatOptions(aiModel, msgDTO);
        //返回提示词对象Prompt
        Prompt prompt = new Prompt(ocrMessage, chatOptions);
        //请求响应
        ChatResponse callResponse = ocrModel.call(prompt);
        //流式返回
        return Flux.just(new AiMsgVO().setContent(callResponse.getResult().getOutput().getText()));
    }

    /**
     * OCR格式化识别
     * @param msgDTO msgDTO
     * @return 正常返回:{@link Flux<AiMsgVO>}
     * @author 蝉鸣
     */
    @Override
    public <T> Flux<AiMsgVO> ocrChatStream(AiMsgDTO msgDTO,Class<T> clazz) {
        //获取会话
        AiSession aiSession = aiChatServiceManual.getAiSession(msgDTO.getSessionId());
        //获取模型对象
        AiModel aiModel = aiChatServiceManual.getAiModel(aiSession.getModelId());
        //获取模型服务
        AiModelService modelService = aiChatServiceManual.getAiModelService(aiModel);
        //获取模型配置
        AiBaseProperties properties = BeanUtil.copyProperties(aiModel, AiBaseProperties.class);
        //获取模型实例
        ChatModel ocrModel = modelService.getChatModel(properties);
        if(Objects.isNull(ocrModel)){
            throw AiChatExceptionEnum.ADD_NO_MODEL_CHAT.getBaseException();
        }
        //获取结果转换器
        BeanOutputConverter<T> converter = aiChatServiceManual.getOutputConverter();
        //设置响应转换
        msgDTO.setContent(msgDTO.getContent().concat(converter.getFormat()));
        //获取消息体
        List<Message> ocrMessage = aiChatServiceManual.genOcrMessages(msgDTO);
        //获取构建属性
        ChatOptions chatOptions = modelService.getChatOptions(aiModel, msgDTO);
        //返回提示词对象Prompt
        Prompt prompt = new Prompt(ocrMessage, chatOptions);
        //请求响应
        ChatResponse callResponse = ocrModel.call(prompt);
        //转换对象
        T convert = converter.convert(String.join(StrUtil.EMPTY,
                Objects.requireNonNull(Objects.requireNonNull(callResponse).getResult().getOutput().getText())));
        AiMsgVO messageVO = new AiMsgVO();
        messageVO.setContent(JSONUtil.toJsonStr(convert));
        return Flux.just(messageVO);
    }

    /**
     * 文字转语音
     * @param msgDTO msgDTO
     * @return 正常返回:{@link Flux<Speech>}
     * @author 蝉鸣
     */
    @Override
    public Flux<Speech> textToSoundStream(AiMsgDTO msgDTO) {
        //获取会话
        AiSession aiSession = aiChatServiceManual.getAiSession(msgDTO.getSessionId());
        //获取模型对象
        AiModel aiModel = aiChatServiceManual.getAiModel(aiSession.getModelId());
        //获取模型服务
        AiModelService modelService = aiChatServiceManual.getAiModelService(aiModel);
        //获取模型配置
        AiBaseProperties properties = BeanUtil.copyProperties(aiModel, AiBaseProperties.class);
        //获取模型实例
        TextToSpeechModel ssModel = modelService.getSpeechSynthesisModel(properties);
        if(Objects.isNull(ssModel)){
            throw AiChatExceptionEnum.ADD_NO_MODEL_CHAT.getBaseException();
        }
        //返回提示词对象Prompt
        TextToSpeechPrompt prompt = aiChatServiceManual.genTextToSpeechPrompt(msgDTO);
        //请求响应
        TextToSpeechResponse callResponse = ssModel.call(prompt);
        //流式返回
        return Flux.just(callResponse.getResult());
    }

    /**
     * 语音转文字
     * @param msgDTO msgDTO
     * @return 正常返回:{@link Flux<String>}
     * @author 蝉鸣
     */
    @Override
    public Flux<String> soundToTextStream(AiMsgDTO msgDTO) {
        //获取会话
        AiSession aiSession = aiChatServiceManual.getAiSession(msgDTO.getSessionId());
        //获取模型对象
        AiModel aiModel = aiChatServiceManual.getAiModel(aiSession.getModelId());
        //获取模型服务
        AiModelService modelService = aiChatServiceManual.getAiModelService(aiModel);
        //获取模型配置
        AiBaseProperties properties = BeanUtil.copyProperties(aiModel, AiBaseProperties.class);
        //获取模型实例
        TranscriptionModel audioModel = modelService.getDashScopeAudioTranscriptionModel(properties);
        if(Objects.isNull(audioModel)){
            throw AiChatExceptionEnum.ADD_NO_MODEL_CHAT.getBaseException();
        }
        URI uri = DocFileUtil.getUrlStrToUri(msgDTO.getContent());
        if(ObjectUtil.isNull(uri)){
            return Flux.just();
        }
        Resource resource = DocFileUtil.getUriToResource(uri);
        AudioTranscriptionPrompt audioTranscriptionPrompt = new AudioTranscriptionPrompt(
                // TODO 不支持直接的文件流，只能上传到OSS传入地址的方式
                resource);
        AudioTranscriptionResponse callResponse = audioModel.call(audioTranscriptionPrompt);
        //流式返回
        return Flux.just(callResponse.getResult().getOutput());
    }

    /**
     * 智能体问答
     * @param msgDTO msgDTO
     * @return 正常返回:{@link Flux<String>}
     * @author 蝉鸣
     */
    @Override
    public Flux<Result<String>> callByAgentStream(AiMsgDTO msgDTO) {
        //获取智能体
        return aiChatServiceManual.callByAgentStream(msgDTO);
    }

    /**
     * 智能体问答
     * @param msgDTO msgDTO
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    @Override
    public String callByAgent(AiMsgDTO msgDTO) {
        //获取智能体
        return aiChatServiceManual.callByAgent(msgDTO);
    }

    /**
     * 智能体智能创建
     * @param appDTO appDTO
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    @Override
    public String callByAgentAppAdd(AiAppDTO appDTO) {
        Message sysMessages = aiChatServiceManual.getSysMessages(appDTO.getColumnMap());
        appDTO.setMessages(CollUtil.newArrayList(sysMessages));
        //获取智能体
        return aiChatServiceManual.callByAgent(appDTO);
    }

    /**
     * 识别信息
     * @param fieldDTO fieldDTO
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    @Override
    public Object ocrChatWithField(AiFieldDTO fieldDTO) {
        //根据智能体获取模型ID
        AiAgent aiAgent = aiChatServiceManual.getAiAgentById(fieldDTO.getAgentId());
        if(ObjectUtil.isEmpty(aiAgent)){
            return null;
        }
        fieldDTO.setPrompt(aiAgent.getAgentPrompt());
        Long modelId = Long.parseLong(aiAgent.getAgentSpace());
        //获取模型对象
        AiModel aiModel = aiChatServiceManual.getAiModel(modelId);
        //获取模型服务
        AiModelService modelService = aiChatServiceManual.getAiModelService(aiModel);
        //获取模型配置
        AiBaseProperties properties = BeanUtil.copyProperties(aiModel, AiBaseProperties.class);
        //获取模型实例
        ChatModel ocrModel = modelService.getChatModel(properties);
        if(Objects.isNull(ocrModel)){
            throw AiChatExceptionEnum.ADD_NO_MODEL_CHAT.getBaseException();
        }
        //获取消息体
        List<Message> ocrMessage = aiOcrServiceManual.genOcrMessagesWithField(fieldDTO);
        //获取构建属性
        ChatOptions chatOptions = modelService.getChatOptions(aiModel, fieldDTO);
        //返回提示词对象Prompt
        Prompt prompt = new Prompt(ocrMessage, chatOptions);
        //请求响应
        ChatResponse callResponse = ocrModel.call(prompt);
        //流式返回
        String text = Objects.requireNonNull(callResponse.getResult()).getOutput().getText();
        if(JSONUtil.isTypeJSON(text)){
            return JSONUtil.parseObj(text);
        }
        return aiOcrServiceManual.parseOcrResult(text);
    }
}
