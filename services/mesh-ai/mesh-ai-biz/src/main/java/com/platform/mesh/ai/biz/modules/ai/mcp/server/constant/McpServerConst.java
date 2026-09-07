package com.platform.mesh.ai.biz.modules.ai.mcp.server.constant;

/**
 * 功能描述:
 * 〈外部 MCP 客户端常量〉
 *
 * @author qingfeng
 */
public interface McpServerConst {

    Integer SERVER_TYPE_PLATFORM = 1;
    Integer SERVER_TYPE_EXTERNAL = 2;
    String TYPE_STREAMABLE_HTTP = "STREAMABLE_HTTP";
    String DEFAULT_STREAMABLE_ENDPOINT = "/mcp";
    String HEADER_AUTHORIZATION = "Authorization";
    String TOOL_NAME_SEPARATOR = "__";
    String CLIENT_NAME_PREFIX = "mesh-ai-";
    String TOOL_NAME_PREFIX = "mcp";
    String CLIENT_VERSION = "1.0.0";
    Integer STATUS_ENABLED = 1;
    Integer DATA_ACTIVE = 1;
    int DEFAULT_CONNECT_TIMEOUT_SECONDS = 10;
    int DEFAULT_REQUEST_TIMEOUT_SECONDS = 20;
    int MIN_TIMEOUT_SECONDS = 1;
    int MAX_TIMEOUT_SECONDS = 120;
}
