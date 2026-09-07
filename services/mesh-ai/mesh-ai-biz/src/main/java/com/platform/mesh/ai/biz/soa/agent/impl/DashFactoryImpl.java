package com.platform.mesh.ai.biz.soa.agent.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.dashscope.app.Application;
import com.alibaba.dashscope.app.ApplicationOutput;
import com.alibaba.dashscope.app.ApplicationParam;
import com.alibaba.dashscope.app.ApplicationResult;
import com.alibaba.dashscope.common.Message;
import com.platform.mesh.ai.biz.modules.ai.agent.domain.po.AiAgent;
import com.platform.mesh.ai.biz.soa.agent.AiAgentService;
import com.platform.mesh.ai.biz.soa.agent.domain.bo.AgentMsgBO;
import com.platform.mesh.ai.biz.soa.agent.enums.AgentFlagEnum;
import com.platform.mesh.ai.biz.soa.agent.exception.AiAgentSoaExceptionEnum;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.utils.result.Result;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @description AI智能体工厂实现
 * @author 蝉鸣
 */
@Service
public class DashFactoryImpl implements AiAgentService {

    private static final Logger log = LoggerFactory.getLogger(DashFactoryImpl.class);

    /**
     * 功能描述:
     * 〈AI智能体类型〉
     * @return 正常返回:{@link AgentFlagEnum}
     * @author 蝉鸣
     */
    @Override
    public AgentFlagEnum aiAgent() {
        return AgentFlagEnum.DASH_SCOPE;
    }

    /**
     * 功能描述:
     * 〈创建智能体〉
     * @param aiAgent aiAgent
     * @author 蝉鸣
     */
    @Override
    public void addOrEditAgent(AiAgent aiAgent){
        throw AiAgentSoaExceptionEnum.AI_BOT_PLATFORM_NOT_SUPPORTS.getBaseException();
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
        ApplicationParam param = ApplicationParam.builder()
                .apiKey(aiAgent.getAgentSecret())
                .appId(aiAgent.getAgentKey())
                .parameters(getParameters())
                .messages(this.getMessage(agentMsgBO))
                .incrementalOutput(true)
                .build();

        StrBuilder strBuilder = StrBuilder.create();
        CountDownLatch latch = new CountDownLatch(NumberConst.NUM_1);
        Disposable subscribe= null;
        try {
            Application application = new Application();
            Flowable<ApplicationResult> result = application.streamCall(param);
            result.blockingForEach(data -> {
                strBuilder.append(data.getOutput().getText());
            });
            subscribe = result.subscribeOn(Schedulers.io())
                    .subscribe(
                            event -> {
                                if (ObjectUtil.isNull(event)) {
                                    return;
                                }
                                // 先检查消息是否为null
                                ApplicationOutput output = event.getOutput();
                                if (ObjectUtil.isNull(output)) {
                                    return;
                                }
                                String content = event.getOutput().getText();
                                // 累积补充内容
                                strBuilder.append(content);
                            },
                            throwable -> {
                                // 发生错误也需要通知
                                latch.countDown();
                            },
                            latch::countDown);
            boolean completed = latch.await(NumberConst.NUM_32, TimeUnit.SECONDS);
            if (!completed) {
                log.warn(AiAgentSoaExceptionEnum.AI_BOT_TIMEOUT.getDesc());
            }
        }catch (Exception e){
            Thread.currentThread().interrupt();
            log.error("Ai bot: {}", e.getMessage());
            throw AiAgentSoaExceptionEnum.AI_BOT_ERROR.getBaseException();
        }finally {
            // 统一清理资源
            if (ObjectUtil.isNotNull(subscribe) && !subscribe.isDisposed()) {
                subscribe.dispose();
            }
            latch.countDown();
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

        ApplicationParam param = ApplicationParam.builder()
                .apiKey(aiAgent.getAgentSecret())
                .appId(aiAgent.getAgentKey())
                .parameters(getParameters())
                .messages(this.getMessage(agentMsgBO))
                .incrementalOutput(true)
                .build();

        // 创建单播 sink（适合 SSE 单订阅者场景）
        Sinks.Many<Result<String>> sink = Sinks.many().unicast().onBackpressureBuffer();

        Application application = new Application();
        try {
            Flowable<ApplicationResult> result = application.streamCall(param);
            Disposable disposable = result
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            event -> {
                                if (ObjectUtil.isNull(event) || ObjectUtil.isNull(event.getOutput())) {
                                    return;
                                }
                                String content = event.getOutput().getText();
                                if (ObjectUtil.isNotEmpty(content)) {
                                    sink.tryEmitNext(Result.success(content));
                                }
                            },
                            throwable -> {
                                log.error("AI stream error", throwable);
                                sink.tryEmitError(throwable);
                            },
                            sink::tryEmitComplete
                    );
            // 资源清理：当 Flux 被取消时，释放 RxJava 订阅
            return sink.asFlux()
                    .doOnCancel(() -> {
                        if (!disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    })
                    .doFinally(signalType -> {
                        if (!disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    });
        }catch (Exception e){
            log.error("AI stream error:{}", e.getMessage());
        }
        return sink.asFlux();
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
        Message currentMsg = Message.builder()
                .role(MessageType.USER.getValue())
                .content(agentMsgBO.getContent())
                .build();
         if(CollUtil.isEmpty(agentMsgBO.getMessages())){
             messages.add(currentMsg);
             return messages;
         }
        List<Message> hisMsg = agentMsgBO.getMessages().stream().map(message -> {
            Message build = Message.builder().build();
            build.setRole(message.getMessageType().getValue());
            build.setContent(message.getText());
            return build;
        }).toList();
        messages.addAll(hisMsg);
        messages.add(currentMsg);
        return messages;
    }
}
