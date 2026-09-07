package com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.ro;

import java.time.LocalDateTime;

/**
 * 功能描述:
 * 〈MCP 访问密钥创建结果〉
 * @author qingfeng
 */
public record McpKeyCreatedVO(
        Long id,
        String name,
        String secret,
        LocalDateTime expiresAt,
        LocalDateTime createTime) {
}
