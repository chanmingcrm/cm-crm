package com.platform.mesh.ai.api.modules.mcp.domain.ro;

import java.io.Serializable;

/**
 * 功能描述:
 * 〈MCP 工具定义〉
 * @param mcpModule 工具提供方编码
 * @param name 工具名称
 * @param description 工具说明
 * @param inputSchema 输入参数 JSON Schema
 * @param executionConfig 工具可信 JSON 配置
 * @param readOnly 是否为只读工具
 * @param returnDirect 工具结果是否直接返回调用方
 * @author qingfeng
 */
public record McpToolDefinitionRO(
        String mcpModule,
        String name,
        String description,
        String inputSchema,
        String executionConfig,
        boolean readOnly,
        boolean returnDirect) implements Serializable {
}
