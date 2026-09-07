package com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.service;

import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.ro.McpKeyPrincipal;
import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.ro.McpAccessInfoRO;
import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.ro.McpKeyCreatedVO;
import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.ro.McpKeySummaryVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 功能描述:
 * 〈MCP 访问密钥服务〉
 * @author qingfeng
 */
public interface IAiMcpAccessKeyService {

    /**
     * 功能描述:
     * 〈创建 MCP 访问密钥〉
     * @param name 密钥名称
     * @param expiresAt 过期时间
     * @return 创建结果
     * @author qingfeng
     */
    McpKeyCreatedVO create(String name, LocalDateTime expiresAt);

    /**
     * 功能描述:
     * 〈获取当前账号的 MCP 访问密钥〉
     * @return 访问密钥列表
     * @author qingfeng
     */
    List<McpKeySummaryVO> listMine();

    /**
     * 功能描述:
     * 〈获取当前租户的 MCP 访问密钥〉
     * @return 访问密钥列表
     * @author qingfeng
     */
    List<McpKeySummaryVO> listTenantForAdmin();

    /**
     * 功能描述:
     * 〈撤销当前账号的 MCP 访问密钥〉
     * @param id 访问密钥 ID
     * @author qingfeng
     */
    void revokeMine(long id);

    /**
     * 功能描述:
     * 〈撤销当前租户的 MCP 访问密钥〉
     * @param id 访问密钥 ID
     * @author qingfeng
     */
    void revokeForAdmin(long id);

    /**
     * 功能描述:
     * 〈校验 MCP Access Key〉
     * @param accessKeySecret Access Key 明文
     * @return 密钥绑定身份
     * @author qingfeng
     */
    McpKeyPrincipal verify(String accessKeySecret);

    /**
     * 功能描述:
     * 〈更新访问密钥最近使用时间〉
     * @param accessKeyId 访问密钥 ID
     * @author qingfeng
     */
    void markLastUsed(long accessKeyId);

    /**
     * 功能描述:
     * 〈获取当前账号的 MCP 接入信息〉
     * @return MCP 接入信息
     * @author qingfeng
     */
    McpAccessInfoRO getAccessInfo();
}
