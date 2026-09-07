package com.platform.mesh.ai.biz.soa.agent;


import com.platform.mesh.ai.biz.modules.ai.agent.domain.po.AiAgent;
import com.platform.mesh.ai.biz.soa.agent.domain.bo.AgentMsgBO;
import com.platform.mesh.ai.biz.soa.agent.enums.AgentFlagEnum;
import com.platform.mesh.utils.result.Result;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 智能体工厂
 * @author 蝉鸣
 */
public interface AiAgentService {

    /**
     * 功能描述:
     * 〈智能体类型〉
     * @return 正常返回:{@link AgentFlagEnum}
     * @author 蝉鸣
     */
    AgentFlagEnum aiAgent();

    /**
     * 功能描述:
     * 〈创建智能体〉
     * @param aiAgent aiAgent
     * @author 蝉鸣
     */
    void addOrEditAgent(AiAgent aiAgent);


    /**
     * 功能描述:
     * 〈请求智能体并获取结果〉
     * @param aiAgent aiAgent
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    String chatByAiAgent(AiAgent aiAgent, AgentMsgBO agentMsgBO);


    /**
     * 功能描述:
     * 〈请求智能体并获取结果〉
     * @param aiAgent aiAgent
     * @return 正常返回:{@link Object}
     * @author 蝉鸣
     */
    Flux<Result<String>> chatByAiAgentStream(AiAgent aiAgent, AgentMsgBO agentMsgBO);

    default Map<String,Object> getParameters() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("key1", "value1");
        parameters.put("temperature", 0.7);
        parameters.put("top_p", 0.9);
        return parameters;
    }

}
