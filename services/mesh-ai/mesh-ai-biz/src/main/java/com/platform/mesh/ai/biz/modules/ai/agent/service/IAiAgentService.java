package com.platform.mesh.ai.biz.modules.ai.agent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.ai.biz.modules.ai.agent.domain.dto.AiAgentDTO;
import com.platform.mesh.ai.biz.modules.ai.agent.domain.po.AiAgent;
import com.platform.mesh.ai.biz.modules.ai.agent.domain.vo.AiAgentVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description AiAgent信息
 * @author 蝉鸣
 */
public interface IAiAgentService extends IService<AiAgent> {

    /**
     * 功能描述:
     * 〈获取当前AIAgent信息〉
     * @param agentId agentId
     * @return 正常返回:{@link AiAgentVO}
     * @author 蝉鸣
     */
    AiAgentVO getAiAgentById(Long agentId);

    /**
     * 功能描述:
     * 〈获取当前AIAgent信息〉
     * @param agentId agentId
     * @return 正常返回:{@link AiAgent}
     * @author 蝉鸣
     */
    AiAgent getAiAgentBOById(Long agentId);

    /**
     * 功能描述:
     * 〈新增AIAgent〉
     * @param agentDTO agentDTO
     * @return 正常返回:{@link AiAgentVO}
     * @author 蝉鸣
     */
    AiAgentVO addAiAgent(AiAgentDTO agentDTO);

    /**
     * 功能描述:
     * 〈修改AIAgent〉
     * @param agentDTO agentDTO
     * @return 正常返回:{@link AiAgentVO}
     * @author 蝉鸣
     */
    AiAgentVO editAiAgent(AiAgentDTO agentDTO);

    /**
     * 功能描述:
     * 〈删除AIAgent〉
     * @param agentId agentId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteAiAgent(Long agentId);

    /**
     * 功能描述:
     * 〈根据组Hash 获取智能体客服〉
     * @param groupHash groupHash
     * @return 正常返回:{@link AiAgent}
     * @author 蝉鸣
     */
    AiAgent getAiAgentBOByGroupHash(String groupHash);
}
