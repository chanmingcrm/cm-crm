package com.platform.mesh.ai.biz.modules.ai.mcp.tool.service;

import com.platform.mesh.ai.biz.modules.ai.mcp.tool.domain.po.AiMcpTool;
import com.platform.mesh.ai.api.modules.mcp.domain.ro.McpToolDefinitionRO;

import java.util.List;

/**
 * 功能描述:
 * 〈MCP 工具目录服务〉
 *
 * @author qingfeng
 */
public interface AiMcpToolService {

    /**
     * 功能描述:
     * 〈查询全部启用的 MCP 工具〉
     * @return 启用的 MCP 工具列表
     * @author qingfeng
     */
    List<AiMcpTool> listEnabledTools();

    /**
     * 功能描述:
     * 〈查询全部启用的 MCP 工具定义〉
     * @return MCP 工具定义列表
     * @author qingfeng
     */
    List<McpToolDefinitionRO> listEnabledToolDefinitions();
}
