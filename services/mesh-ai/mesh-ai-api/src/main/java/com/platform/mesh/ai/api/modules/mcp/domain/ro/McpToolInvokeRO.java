package com.platform.mesh.ai.api.modules.mcp.domain.ro;

import java.io.Serializable;

/**
 * 功能描述:
 * 〈MCP 工具调用请求〉
 *
 * @param toolName 工具名称
 * @param arguments JSON 格式调用参数
 * @param requestId 请求标识
 * @param executionConfig 工具可信 JSON 配置
 * @author qingfeng
 */
public record McpToolInvokeRO(
        String toolName,
        String arguments,
        String requestId,
        String executionConfig) implements Serializable {
}
