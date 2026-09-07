package com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.ro;

/**
 * 功能描述:
 * 〈MCP 访问密钥绑定身份〉
 * @author qingfeng
 */
public record McpKeyPrincipal(
        Long accessKeyId,
        Long accountId,
        Long userId) {
}
