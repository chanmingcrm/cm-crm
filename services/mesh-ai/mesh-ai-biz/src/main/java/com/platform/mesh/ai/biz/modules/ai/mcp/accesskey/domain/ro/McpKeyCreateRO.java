package com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.ro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * 功能描述:
 * 〈MCP 访问密钥创建参数〉
 * @author qingfeng
 */
public record McpKeyCreateRO(
        @NotBlank @Size(max = 64) String name,
        LocalDateTime expiresAt) {
}
