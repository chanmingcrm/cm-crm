package com.platform.mesh.ai.biz.modules.ai.agent.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.ai.biz.modules.ai.agent.domain.po.AiAgent;
import com.platform.mesh.ai.biz.modules.ai.agent.domain.vo.AiAgentVO;
import com.platform.mesh.ai.biz.modules.ai.agent.exception.AiAgentExceptionEnum;
import com.platform.mesh.ai.biz.modules.ai.agentklrel.domain.po.AiAgentKlRel;
import com.platform.mesh.ai.biz.modules.ai.agentklrel.service.IAiAgentKlRelService;
import com.platform.mesh.ai.biz.soa.agent.AiAgentService;
import com.platform.mesh.ai.biz.soa.agent.enums.AgentFlagEnum;
import com.platform.mesh.ai.biz.soa.agent.factory.AiAgentFactory;
import com.platform.mesh.core.enums.base.BaseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description AI生成
 * @author 蝉鸣
 */
@Service
public class AiAgentServiceManual {

    private static final Logger log = LoggerFactory.getLogger(AiAgentServiceManual.class);

    @Autowired
    private AiAgentFactory aiAgentFactory;

    @Autowired
    private IAiAgentKlRelService aiAgentKlRelService;

    public void addAiAgent(AiAgent aiAgent) {
        if(ObjectUtil.isEmpty(aiAgent.getAgentPrompt())){
            throw AiAgentExceptionEnum.ADD_NO_PROMPT.getBaseException();
        }
        //智能体问答
        AgentFlagEnum enumByValue = BaseEnum.getEnumByValue(AgentFlagEnum.class, aiAgent.getAgentFlag());
        if(ObjectUtil.isEmpty(enumByValue)){
            //如果没有智能体需要人工介入
            throw AiAgentExceptionEnum.ADD_NO_ARGS.getBaseException();
        }
        AiAgentService aiAgentService = aiAgentFactory.getAiAgentService(enumByValue);
        aiAgentService.addOrEditAgent(aiAgent);
    }

    /**
     * 功能描述:
     * 〈保存知识库关系〉
     * @param aiAgent aiAgent
     * @param klIds klIds
     * @author 蝉鸣
     */
    public void saveKlRel(AiAgent aiAgent, List<Long> klIds) {
        if(CollUtil.isEmpty(klIds)){
            return;
        }
        //先删除旧数据
        aiAgentKlRelService.lambdaUpdate()
                .eq(AiAgentKlRel::getAgentId,aiAgent.getId())
                .remove();
        List<AiAgentKlRel> list = CollUtil.newArrayList();
        for (Long klId : klIds) {
            AiAgentKlRel agentKlRel = new AiAgentKlRel();
            agentKlRel.setAgentId(aiAgent.getId());
            agentKlRel.setKlId(klId);
            list.add(agentKlRel);
        }
        //保存新数据
        aiAgentKlRelService.saveBatch(list);
    }
}