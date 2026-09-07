package com.platform.mesh.ai.biz.soa.agent.impl;

import cn.hutool.core.util.StrUtil;
import com.baidubce.appbuilder.console.appbuilderclient.AppBuilderClient;
import com.baidubce.appbuilder.model.appbuilderclient.AppBuilderClientIterator;
import com.baidubce.appbuilder.model.appbuilderclient.AppBuilderClientResult;
import com.platform.mesh.ai.biz.modules.ai.agent.domain.po.AiAgent;
import com.platform.mesh.ai.biz.soa.agent.AiAgentService;
import com.platform.mesh.ai.biz.soa.agent.domain.bo.AgentMsgBO;
import com.platform.mesh.ai.biz.soa.agent.enums.AgentFlagEnum;
import com.platform.mesh.ai.biz.soa.agent.exception.AiAgentSoaExceptionEnum;
import com.platform.mesh.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * @description AI智能体工厂实现
 * @author 蝉鸣
 */
@Service
public class QianfanFactoryImpl implements AiAgentService {

    private static final Logger log = LoggerFactory.getLogger(QianfanFactoryImpl.class);

    /**
     * 功能描述:
     * 〈AI智能体类型〉
     * @return 正常返回:{@link AgentFlagEnum}
     * @author 蝉鸣
     */
    @Override
    public AgentFlagEnum aiAgent() {
        return AgentFlagEnum.QIAN_FAN;
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
        // 从AppBuilder控制台【个人空间】-【应用】网页获取已发布应用的ID
        String appId = aiAgent.getAgentKey();
        StringBuilder strBuilder = StrUtil.builder();
        try {
            AppBuilderClient builder = new AppBuilderClient(appId,aiAgent.getAgentSecret());
            String conversationId = builder.createConversation();

            AppBuilderClientIterator result = builder.run(agentMsgBO.getContent(), conversationId, new String[] {}, Boolean.TRUE);
            while (result.hasNext()) {
                AppBuilderClientResult response = result.next();
                strBuilder.append(response.getAnswer());
            }
        }catch (Exception e){
            Thread.currentThread().interrupt();
            log.error("Ai agent: {}", e.getMessage());
            throw AiAgentSoaExceptionEnum.AI_BOT_ERROR.getBaseException();
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
        String appId = aiAgent.getAgentKey();
        String appSecret = aiAgent.getAgentSecret();

        // 使用 unicast sink（适合单订阅者，如 SSE）
        Sinks.Many<Result<String>> sink = Sinks.many().unicast().onBackpressureBuffer();

        // 在异步线程中执行阻塞式 SDK 调用（避免阻塞 Netty IO 线程）
        Thread thread = new Thread(() -> {
            AppBuilderClient builder = null;
            String conversationId = null;
            try {
                builder = new AppBuilderClient(appId, appSecret);
                conversationId = builder.createConversation();

                // 调用流式接口
                AppBuilderClientIterator result = builder.run(agentMsgBO.getContent(), conversationId, new String[]{}, Boolean.TRUE);
                while (result.hasNext()) {
                    AppBuilderClientResult response = result.next();
                    String answer = response.getAnswer();
                    if (StrUtil.isNotBlank(answer)) {
                        // 推送 chunk 到响应流
                        sink.tryEmitNext(Result.success(answer));
                    }
                }
                // 正常结束
                sink.tryEmitComplete();
            } catch (Exception e) {
                log.error("AI agent stream error", e);
                sink.tryEmitError(e);
            }
        });

        thread.setDaemon(true); // 避免阻塞 JVM 退出
        thread.start();

        // 当客户端取消订阅（如关闭浏览器）时，中断线程（若 SDK 支持中断）
        return sink.asFlux().doOnCancel(thread::interrupt).doFinally(signalType->{
            thread.interrupt();
        });
    }

}
