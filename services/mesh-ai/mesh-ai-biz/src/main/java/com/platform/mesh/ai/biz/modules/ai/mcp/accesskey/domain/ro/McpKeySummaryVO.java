package com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.ro;

import java.time.LocalDateTime;

/**
 * 功能描述:
 * 〈MCP 访问密钥摘要〉
 * @author qingfeng
 */
public record McpKeySummaryVO(
        Long id,
        String name,
        String publicId,
        Long accountId,
        Long userId,
        String ownerName,
        Integer status,
        LocalDateTime expiresAt,
        LocalDateTime lastUsedAt,
        LocalDateTime revokedAt,
        LocalDateTime createTime) {
}
