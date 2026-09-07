package com.platform.mesh.ai.biz.modules.ai.mcp.tool.function;

import com.platform.mesh.ai.api.modules.mcp.feign.RemoteMcpToolProviderService;

/**
 * 功能描述:
 * 〈MCP 工具提供方客户端工厂〉
 *
 * @author qingfeng
 */
@FunctionalInterface
public interface McpToolProviderClientFactory {

    /**
     * 功能描述:
     * 〈按服务名称创建 MCP 工具提供方客户端〉
     *
     * @param serviceName 服务名称
     * @return MCP 工具提供方客户端
     * @author qingfeng
     */
    RemoteMcpToolProviderService create(String serviceName);
}
