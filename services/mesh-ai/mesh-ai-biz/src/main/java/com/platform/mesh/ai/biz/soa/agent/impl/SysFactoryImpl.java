package com.platform.mesh.ai.biz.soa.agent.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.ai.biz.chat.domain.dto.AiMsgDTO;
import com.platform.mesh.ai.biz.chat.domain.vo.AiMsgVO;
import com.platform.mesh.ai.biz.chat.service.IAiChatService;
import com.platform.mesh.ai.biz.modules.ai.agent.domain.po.AiAgent;
import com.platform.mesh.ai.biz.modules.ai.agentklrel.domain.po.AiAgentKlRel;
import com.platform.mesh.ai.biz.modules.ai.agentklrel.service.IAiAgentKlRelService;
import com.platform.mesh.ai.biz.modules.ai.model.domain.po.AiModel;
import com.platform.mesh.ai.biz.modules.ai.model.service.IAiModelService;
import com.platform.mesh.ai.biz.modules.ai.session.domain.po.AiSession;
import com.platform.mesh.ai.biz.modules.ai.session.service.IAiSessionService;
import com.platform.mesh.ai.biz.soa.agent.AiAgentService;
import com.platform.mesh.ai.biz.soa.agent.domain.bo.AgentMsgBO;
import com.platform.mesh.ai.biz.soa.agent.enums.AgentFlagEnum;
import com.platform.mesh.ai.biz.soa.agent.exception.AiAgentSoaExceptionEnum;
import com.platform.mesh.utils.result.Result;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @description AI智能体工厂实现
 * @author 蝉鸣
 */
@Service
public class SysFactoryImpl implements AiAgentService {

    private static final Logger log = LoggerFactory.getLogger(SysFactoryImpl.class);

    @Autowired
    private IAiModelService aiModelService;

    @Autowired
    private IAiSessionService aiSessionService;

    @Autowired
    private IAiAgentKlRelService aiAgentKlRelService;

    /**
     * 功能描述:
     * 〈AI智能体类型〉
     * @return 正常返回:{@link AgentFlagEnum}
     * @author 蝉鸣
     */
    @Override
    public AgentFlagEnum aiAgent() {
        return AgentFlagEnum.INIT;
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
        AiMsgDTO msgDTO = getMsgDTO(aiAgent, agentMsgBO);
        IAiChatService aiChatService = SpringContextHolderUtil.getBean(IAiChatService.class);
        return aiChatService.genChat(msgDTO);
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
        AiMsgDTO msgDTO = getMsgDTO(aiAgent, agentMsgBO);
        IAiChatService aiChatService = SpringContextHolderUtil.getBean(IAiChatService.class);
        Flux<Result<AiMsgVO>> resultFlux = aiChatService.genChatStream(msgDTO);
        // 转换：提取 AiMsgVO 中的 content 字段
        return resultFlux.map(result -> {
            if (result.getCode().equals(HttpStatus.OK.value()) && result.getData() != null) {
                // 成功时，提取 content
                return Result.success(result.getData().getContent());
            } else {
                // 失败时，保留错误信息
                return Result.error(result.getCode(), result.getMsg());
            }
        });
    }


    /**
     * 功能描述:
     * 〈转换消息〉
     * @param aiAgent aiAgent
     * @param agentMsgBO agentMsgBO
     * @return 正常返回:{@link AiMsgDTO}
     * @author 蝉鸣
     */
    public AiMsgDTO getMsgDTO(AiAgent aiAgent, AgentMsgBO agentMsgBO){
        //获取sessionId
        AiSession aiSession;
        if(ObjectUtil.isEmpty(agentMsgBO.getUserHash())){
            aiSession = new AiSession();
            Long modelId = Long.parseLong(aiAgent.getAgentSpace());
            //获取模型
            AiModel aiModel = aiModelService.getById(modelId);
            aiSession.setAgentId(aiAgent.getId());
            aiSession.setModelId(modelId);
            aiSession.setModelName(aiModel.getModelName());
            aiSession.setModelFlag(aiModel.getModelFlag());
            aiSession.setModelType(aiModel.getModelType());
            aiSessionService.save(aiSession);
        }else{
            Long sessionId = Long.parseLong(agentMsgBO.getUserHash());
            aiSession = aiSessionService.getById(sessionId);
        }
        AiMsgDTO msgDTO = new AiMsgDTO();
        msgDTO.setContent(agentMsgBO.getContent());
        msgDTO.setPrompt(aiAgent.getAgentPrompt());
        //保存session
        msgDTO.setSessionId(aiSession.getId());
        //查询知识库
        List<AiAgentKlRel> klRels = aiAgentKlRelService.lambdaQuery().eq(AiAgentKlRel::getAgentId, aiAgent.getId()).list();
        if(CollUtil.isNotEmpty(klRels)){
            List<Long> klIds = klRels.stream().map(AiAgentKlRel::getKlId).distinct().toList();
            msgDTO.setKnowledgeIds(klIds);
        }
        return msgDTO;
    }
}
