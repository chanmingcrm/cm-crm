package com.platform.mesh.ai.biz.modules.ai.mcp.tool.constant;

/**
 * 功能描述:
 * 〈MCP 工具常量〉
 *
 * @author qingfeng
 */
public interface McpToolConst {

    /**
     * MCP 工具模块编码
     */
    String MODULE = "ai_mcp_tool";

    /**
     * 远程 MCP 工具调用失败日志模板
     */
    String LOG_REMOTE_INVOKE_FAILED = "远程 MCP 工具调用失败，module={}，tool={}，code={}";

    /**
     * MCP 工具提供方服务名称前缀
     */
    String PROVIDER_SERVICE_PREFIX = "mesh-";

    /**
     * MCP 工具提供方服务名称后缀
     */
    String PROVIDER_SERVICE_SUFFIX = "-biz";

    /**
     * 动态 Feign 客户端上下文前缀
     */
    String PROVIDER_CONTEXT_PREFIX = "mcp-tool-provider-";

    /**
     * MCP 业务模块编码格式
     */
    String MODULE_PATTERN = "^[a-z][a-z0-9-]*$";
}
