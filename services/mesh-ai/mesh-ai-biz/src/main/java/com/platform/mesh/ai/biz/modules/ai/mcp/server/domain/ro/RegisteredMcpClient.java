package com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.ro;

import com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.po.AiMcpServer;
import io.modelcontextprotocol.client.McpSyncClient;

/**
 * 功能描述:
 * 〈已连接的外部 MCP Server 及其协议客户端〉
 *
 * @param server 外部 MCP Server 配置
 * @param client MCP 同步客户端
 * @author qingfeng
 */
public record RegisteredMcpClient(AiMcpServer server, McpSyncClient client) {
}
