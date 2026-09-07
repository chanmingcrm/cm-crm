package com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.ro;

import java.util.List;

/**
 * 功能描述:
 * 〈MCP 请求来源校验策略〉
 * @param allowedOrigins 允许的请求来源
 * @param allowedHosts 允许的请求主机
 * @author qingfeng
 */
public record McpOriginPolicy(List<String> allowedOrigins, List<String> allowedHosts) {
}
