package com.platform.mesh.ai.biz.modules.ai.agent.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.ai.biz.modules.ai.agent.domain.po.AiAgent;
import org.apache.ibatis.annotations.Param;

/**
 * @description AiAgent
 * @author 蝉鸣
 */
public interface AiAgentMapper extends BaseMapper<AiAgent> {

    @InterceptorIgnore(tenantLine = "true")
    AiAgent getAiAgentBOById(@Param("agentId") Long agentId);

    @InterceptorIgnore(tenantLine = "true")
    AiAgent getAiAgentBOByGroupHash(@Param("groupHash") String groupHash);

    @InterceptorIgnore(tenantLine = "true")
    AiAgent getDefaultAgent(@Param("agentFlag") Integer agentFlag);
}