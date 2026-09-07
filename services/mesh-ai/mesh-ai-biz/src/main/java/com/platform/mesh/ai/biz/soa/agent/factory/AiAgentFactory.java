package com.platform.mesh.ai.biz.soa.agent.factory;

import com.platform.mesh.ai.biz.soa.agent.AiAgentService;
import com.platform.mesh.ai.biz.soa.agent.enums.AgentFlagEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description AI智能体工厂
 * @author 蝉鸣
 */
@Service
public class AiAgentFactory implements InitializingBean {

    @Autowired
    private List<AiAgentService> aiAgentServiceList;

    private final Map<AgentFlagEnum, AiAgentService> aiAgentMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根据类型获取对应的AI智能体实现〉
     * @param botFlagEnum aiModelEnum
     * @return 正常返回:{@link AiAgentService}
     * @author 蝉鸣
     */
    public AiAgentService getAiAgentService(AgentFlagEnum botFlagEnum){
        return aiAgentMaps.get(botFlagEnum);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (AiAgentService service : aiAgentServiceList){
            aiAgentMaps.put(service.aiAgent(), service);
        }
    }
}
