package com.platform.mesh.ai.biz.soa.agent.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.coze.openapi.client.bots.*;
import com.coze.openapi.client.bots.model.BotPromptInfo;
import com.coze.openapi.client.chat.CreateChatReq;
import com.coze.openapi.client.chat.model.Chat;
import com.coze.openapi.client.chat.model.ChatError;
import com.coze.openapi.client.chat.model.ChatEvent;
import com.coze.openapi.client.chat.model.ChatEventType;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.connversations.message.model.MessageContentType;
import com.coze.openapi.client.connversations.message.model.MessageRole;
import com.coze.openapi.client.connversations.message.model.MessageType;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.config.Consts;
import com.coze.openapi.service.service.CozeAPI;
import com.platform.mesh.ai.biz.modules.ai.agent.domain.po.AiAgent;
import com.platform.mesh.ai.biz.soa.agent.AiAgentService;
import com.platform.mesh.ai.biz.soa.agent.constant.AiConst;
import com.platform.mesh.ai.biz.soa.agent.domain.bo.AgentMsgBO;
import com.platform.mesh.ai.biz.soa.agent.enums.AgentFlagEnum;
import com.platform.mesh.ai.biz.soa.agent.exception.AiAgentSoaExceptionEnum;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.utils.result.Result;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @description AI智能体工厂实现
 * @author 蝉鸣
 */
@Service
public class CozeFactoryImpl implements AiAgentService {

    private static final Logger log = LoggerFactory.getLogger(CozeFactoryImpl.class);

    static {
        RxJavaPlugins.setErrorHandler(throwable -> {
            if (isUndeliverableInterrupted(throwable)) {
                log.debug("AI bot coze stream closed");
                return;
            }
            log.error("AI bot rxjava undeliverable error", throwable);
        });
    }

    /**
     * 功能描述:
     * 〈AI智能体类型〉
     * @return 正常返回:{@link AgentFlagEnum}
     * @author 蝉鸣
     */
    @Override
    public AgentFlagEnum aiAgent() {
        return AgentFlagEnum.COZE;
    }

    /**
     * 功能描述:
     * 〈创建智能体〉
     * @param aiAgent aiAgent
     * @author 蝉鸣
     */
    @Override
    public void addOrEditAgent(AiAgent aiAgent){
        // 创建 DashScope 对象
        String token = aiAgent.getAgentSecret();
        String baseUrl = Consts.COZE_CN_BASE_URL;
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze =
                new CozeAPI.Builder()
                        .baseURL(baseUrl)
                        .auth(authCli)
                        .readTimeout(NumberConst.NUM_1000000)
                        .build();
        //智能体的人设与回复逻辑
        BotPromptInfo promptInfo = BotPromptInfo.builder()
                .prompt(aiAgent.getAgentPrompt())
                .build();
        //校验智能体是否存在
        Boolean isExist;
        try {
            RetrieveBotReq retrieveBotReq = RetrieveBotReq.of(aiAgent.getAgentKey());
            RetrieveBotResp retrieve = coze.bots().retrieve(retrieveBotReq);
            String botID = retrieve.getBot().getBotID();
            aiAgent.setAgentKey(botID);
            isExist = Boolean.TRUE;
        }catch (Exception e){
            isExist = Boolean.FALSE;
        }
        if(!isExist){
            CreateBotReq botReq = CreateBotReq.builder()
                    .spaceID(aiAgent.getAgentSpace())
                    .name(aiAgent.getAgentName())
                    .description(aiAgent.getAgentDesc())
                    .promptInfo(promptInfo)
                    .build();
            CreateBotResp botResp = coze.bots().create(botReq);
            String botID = botResp.getBotID();
            aiAgent.setAgentKey(botID);
        }else{
            UpdateBotReq botReq = UpdateBotReq.builder()
                    .botID(aiAgent.getAgentKey())
                    .name(aiAgent.getAgentName())
                    .description(aiAgent.getAgentDesc())
                    .promptInfo(promptInfo)
                    .build();
            coze.bots().update(botReq);
        }
        //发布应用
        //组装渠道
        List<String> connectorIDs = CollUtil.newArrayList(AiConst.COZE_CONNECTOR_API,AiConst.COZE_CONNECTOR_CHAT_SDK);
        PublishBotReq botReq = PublishBotReq.builder()
                .botID(aiAgent.getAgentKey())
                .connectorIDs(connectorIDs)
                .build();
        coze.bots().publish(botReq);
    }

    /**
     * 功能描述:
     * 〈请求智能体并获取结果〉
     * @param aiAgent aiAgent
     * @return 正常返回:{@link Object}
     * @author 蝉鸣
     */
    @Override
    public String chatByAiAgent(AiAgent aiAgent, AgentMsgBO agentMsgBO) {
        // 创建 DashScope 对象
        String token = aiAgent.getAgentSecret();
        String baseUrl = Consts.COZE_CN_BASE_URL;
        String botId = aiAgent.getAgentKey();
        String uid = agentMsgBO.getUserHash();
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze =
                new CozeAPI.Builder()
                        .baseURL(baseUrl)
                        .auth(authCli)
                        .readTimeout(NumberConst.NUM_1000000)
                        .build();
        CreateChatReq req =
                CreateChatReq.builder()
                        .botID(botId)
                        .userID(uid)
                        .stream(true) // 关键：启用流式输出
                        .messages(getMessage(agentMsgBO))
                        .build();
        // 创建流式消费者来处理实时消息
        Flowable<ChatEvent> resp = coze.chat().stream(req);
        StrBuilder strBuilder = StrBuilder.create();
        CountDownLatch latch = new CountDownLatch(NumberConst.NUM_1);
        AtomicBoolean hasDelta = new AtomicBoolean(Boolean.FALSE);
        AtomicBoolean streamCompleted = new AtomicBoolean(Boolean.FALSE);
        AtomicReference<Throwable> errorRef = new AtomicReference<>();
        Disposable subscribe = null;
        try {
            subscribe = resp.subscribeOn(Schedulers.io())
                    .subscribe(
                            event -> {
                                if (event == null) {
                                    return;
                                }
                                ChatEventType eventType = event.getEvent();
                                if (ChatEventType.DONE.equals(eventType)) {
                                    streamCompleted.set(Boolean.TRUE);
                                    latch.countDown();
                                    return;
                                }
                                if (ChatEventType.ERROR.equals(eventType)) {
                                    Message errorMessage = event.getMessage();
                                    String errorContent = ObjectUtil.isNull(errorMessage) ? null : errorMessage.getContent();
                                    errorRef.set(new RuntimeException(errorContent));
                                    log.error("AI bot coze event error: botId={}, userId={}, content={}", botId, uid, errorContent);
                                    latch.countDown();
                                    return;
                                }
                                if (ChatEventType.CONVERSATION_CHAT_FAILED.equals(eventType)) {
                                    String errorContent = getChatErrorContent(event);
                                    errorRef.set(new RuntimeException(errorContent));
                                    log.error("AI bot coze chat failed: botId={}, userId={}, content={}", botId, uid, errorContent);
                                    latch.countDown();
                                    return;
                                }
                                if (ChatEventType.CONVERSATION_CHAT_REQUIRES_ACTION.equals(eventType)) {
                                    String errorContent = "AI bot coze requires action";
                                    errorRef.set(new RuntimeException(errorContent));
                                    log.error("AI bot coze requires action: botId={}, userId={}", botId, uid);
                                    latch.countDown();
                                    return;
                                }
                                if (ChatEventType.CONVERSATION_CHAT_COMPLETED.equals(eventType)) {
                                    Message message = event.getMessage();
                                    if (ObjectUtil.isNotNull(message) && ObjectUtil.isNotEmpty(message.getContent())) {
                                        //累积补充内容
                                        strBuilder.append(message.getContent());
                                    }
                                    streamCompleted.set(Boolean.TRUE);
                                    latch.countDown();
                                    return;
                                }
                                // 先检查消息是否为null
                                Message message = event.getMessage();
                                if (message == null) {
                                    return;
                                }
                                if (ChatEventType.CONVERSATION_MESSAGE_DELTA.equals(eventType)) {
                                    String content = message.getContent();
                                    if (ObjectUtil.isNotEmpty(content)) {
                                        //将分块内容添加到结果中
                                        hasDelta.set(Boolean.TRUE);
                                        strBuilder.append(content);
                                    }
                                } else if (ChatEventType.CONVERSATION_MESSAGE_COMPLETED.equals(eventType)) {
                                    String content = message.getContent();
                                    if (!hasDelta.get() && isAssistantAnswer(message) && ObjectUtil.isNotEmpty(content)) {
                                        //没有收到分块时，从消息完成事件兜底获取完整回答
                                        strBuilder.append(content);
                                    }
                                }
                            },
                            throwable -> {
                                errorRef.set(throwable);
                                log.error("AI bot coze subscribe error: botId={}, userId={}", botId, uid, throwable);
                                latch.countDown();
                            },
                            () -> {
                                streamCompleted.set(Boolean.TRUE);
                                latch.countDown();
                            });
            // 为了防止程序立即退出，添加一个简单的等待
            boolean completed = latch.await(NumberConst.NUM_480, TimeUnit.SECONDS);
            if (!completed) {
                log.warn(AiAgentSoaExceptionEnum.AI_BOT_TIMEOUT.getDesc());
            }
            if (ObjectUtil.isNotNull(errorRef.get())) {
                log.error("Ai bot error: {}", errorRef.get().toString());
                throw AiAgentSoaExceptionEnum.AI_BOT_ERROR.getBaseException();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Ai bot: {}", e.getMessage());
            throw AiAgentSoaExceptionEnum.AI_BOT_ERROR.getBaseException();
        } finally {
            // 统一清理资源
            if (ObjectUtil.isNotNull(subscribe) && !subscribe.isDisposed() && !streamCompleted.get()) {
                subscribe.dispose();
            }
            coze.shutdownExecutor();
        }
        return strBuilder.toString();
    }

    /**
     * 功能描述:
     * 〈请求智能体并获取结果〉
     * @param aiAgent aiAgent
     * @return 正常返回:{@link Object}
     * @author 蝉鸣
     */
    @Override
    public Flux<Result<String>> chatByAiAgentStream(AiAgent aiAgent, AgentMsgBO agentMsgBO) {
        String token = aiAgent.getAgentSecret();
        String baseUrl = Consts.COZE_CN_BASE_URL;
        String botId = aiAgent.getAgentKey();
        String uid = resolveStreamUserId(agentMsgBO);
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze =
                new CozeAPI.Builder()
                        .baseURL(baseUrl)
                        .auth(authCli)
                        .readTimeout(NumberConst.NUM_1000000)
                        .build();
        CreateChatReq req =
                CreateChatReq.builder()
                        .botID(botId)
                        .userID(uid)
                        .stream(true) // 关键：启用流式输出
                        .parameters(getParameters())
                        .messages(this.getMessage(agentMsgBO))
                        .build();
        Flowable<ChatEvent> resp = coze.chat().stream(req);
        return Flux.create(sink -> {
            AtomicBoolean hasDelta = new AtomicBoolean(Boolean.FALSE);
            AtomicReference<Disposable> subscriptionRef = new AtomicReference<>();
            Runnable cleanup = runOnce(() -> {
                Disposable subscription = subscriptionRef.get();
                if (ObjectUtil.isNotNull(subscription) && !subscription.isDisposed()) {
                    subscription.dispose();
                }
                coze.shutdownExecutor();
            });
            sink.onDispose(cleanup::run);

            Disposable subscription = resp.subscribeOn(Schedulers.io()).subscribe(
                    event -> {
                        if (event == null) {
                            return;
                        }
                        ChatEventType eventType = event.getEvent();
                        if (ChatEventType.DONE.equals(eventType)) {
                            sink.complete();
                            return;
                        }
                        if (ChatEventType.ERROR.equals(eventType)) {
                            log.error("AI stream coze event error: botId={}", botId);
                            sink.error(new RuntimeException("Coze stream event error"));
                            return;
                        }
                        if (ChatEventType.CONVERSATION_CHAT_FAILED.equals(eventType)) {
                            log.error("AI stream coze chat failed: botId={}", botId);
                            sink.error(new RuntimeException(getChatErrorContent(event)));
                            return;
                        }
                        if (ChatEventType.CONVERSATION_CHAT_REQUIRES_ACTION.equals(eventType)) {
                            log.error("AI stream coze requires action: botId={}", botId);
                            sink.error(new RuntimeException("AI stream coze requires action"));
                            return;
                        }
                        String content = contentFromStreamEvent(event, hasDelta);
                        if (ObjectUtil.isNotEmpty(content)) {
                            sink.next(streamResult(content));
                        }
                    },
                    throwable -> {
                        log.error("AI stream error: botId={}, type={}",
                                botId, throwable.getClass().getSimpleName());
                        sink.error(throwable);
                    },
                    sink::complete);
            subscriptionRef.set(subscription);
            if (sink.isCancelled() && !subscription.isDisposed()) {
                subscription.dispose();
            }
        });
    }

    static String resolveStreamUserId(AgentMsgBO agentMsgBO) {
        return StrUtil.blankToDefault(agentMsgBO.getUserHash(), IdUtil.fastSimpleUUID());
    }

    static String contentFromStreamEvent(ChatEvent event, AtomicBoolean hasDelta) {
        Message message = event.getMessage();
        if (ObjectUtil.isNull(message)) {
            return null;
        }
        String content = message.getContent();
        if (ChatEventType.CONVERSATION_MESSAGE_DELTA.equals(event.getEvent())) {
            if (ObjectUtil.isNotEmpty(content)) {
                hasDelta.set(Boolean.TRUE);
                return content;
            }
            return null;
        }
        if (ChatEventType.CONVERSATION_MESSAGE_COMPLETED.equals(event.getEvent())
                && !hasDelta.get()
                && isAssistantAnswer(message)
                && ObjectUtil.isNotEmpty(content)) {
            return content;
        }
        return null;
    }

    static Runnable runOnce(Runnable action) {
        AtomicBoolean ran = new AtomicBoolean(Boolean.FALSE);
        return () -> {
            if (ran.compareAndSet(Boolean.FALSE, Boolean.TRUE)) {
                action.run();
            }
        };
    }

    static Result<String> streamResult(String content) {
        return Result.success("操作成功", content);
    }

    /**
     * 功能描述:
     * 〈转换消息类型〉
     * @param agentMsgBO agentMsgBO
     * @return 正常返回:{@link Object}
     * @author 蝉鸣
     */
    public List<Message> getMessage(AgentMsgBO agentMsgBO){
        List<Message>  messages = new ArrayList<>();
        Message currentMsg = Message.buildUserQuestionText(agentMsgBO.getContent());
        if(CollUtil.isEmpty(agentMsgBO.getMessages())){
            messages.add(currentMsg);
            return messages;
        }
        List<Message> hisList = agentMsgBO.getMessages().stream().map(message -> {
            if (MessageRole.USER.getValue().equals(message.getMessageType().getValue())) {
                return Message.buildUserQuestionText(message.getText());
            }
            if (MessageRole.ASSISTANT.getValue().equals(message.getMessageType().getValue())) {
                return Message.buildAssistantAnswer(message.getText());
            }
            return Message.builder()
                    .role(MessageRole.UNKNOWN)
                    .type(MessageType.UNKNOWN)
                    .content(message.getText())
                    .contentType(MessageContentType.TEXT)
                    .build();
        }).toList();
        messages.addAll(hisList);
        //当前消息放最后
        messages.add(currentMsg);
        return messages;
    }

    private static boolean isUndeliverableInterrupted(Throwable throwable) {
        if (!(throwable instanceof UndeliverableException) || ObjectUtil.isNull(throwable.getCause())) {
            return Boolean.FALSE;
        }
        return throwable.getCause() instanceof InterruptedIOException;
    }

    /**
     * 功能描述:
     * 〈是否辅助回答〉
     * @param message message
     * @return 正常返回:{@link boolean}
     * @author 蝉鸣
     */
    private static boolean isAssistantAnswer(Message message) {
        return MessageRole.ASSISTANT.equals(message.getRole())
                && (ObjectUtil.isNull(message.getType()) || MessageType.ANSWER.equals(message.getType()));
    }

    /**
     * 功能描述:
     * 〈获取错误信息〉
     * @param event event
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    private String getChatErrorContent(ChatEvent event) {
        Chat chat = event.getChat();
        if (ObjectUtil.isNull(chat) || ObjectUtil.isNull(chat.getLastError())) {
            return "AI bot coze chat failed";
        }
        ChatError lastError = chat.getLastError();
        return lastError.getCode() + ":" + lastError.getMsg();
    }

}
