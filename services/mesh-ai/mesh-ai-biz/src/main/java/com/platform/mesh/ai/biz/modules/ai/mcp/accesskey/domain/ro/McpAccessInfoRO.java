package com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.ro;

import com.platform.mesh.ai.api.modules.mcp.domain.ro.McpToolDefinitionRO;

import java.util.List;

/**
 * 功能描述:
 * 〈MCP 接入信息〉
 * @param endpoint MCP 对外服务地址
 * @param protocol MCP 传输协议
 * @param enabled 当前账号是否允许使用 MCP
 * @param readOnly 是否仅允许读操作
 * @param rateLimitPerMinute 每分钟请求上限
 * @param tools 当前账号可用的工具列表
 * @author qingfeng
 */
public record McpAccessInfoRO(
        String endpoint,
        String protocol,
        boolean enabled,
        boolean readOnly,
        int rateLimitPerMinute,
        List<McpToolDefinitionRO> tools) {
}
