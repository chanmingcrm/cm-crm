package com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.controller;

import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.ro.McpKeyCreateRO;
import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.ro.McpAccessInfoRO;
import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.ro.McpKeyCreatedVO;
import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.ro.McpKeySummaryVO;
import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.service.IAiMcpAccessKeyService;
import com.platform.mesh.utils.result.Result;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能描述:
 * 〈MCP 访问密钥管理接口〉
 * @author qingfeng
 */
@RestController
public class AiMcpAccessKeyController {

    private final IAiMcpAccessKeyService accessKeyService;

    public AiMcpAccessKeyController(IAiMcpAccessKeyService accessKeyService) {
        this.accessKeyService = accessKeyService;
    }

    /**
     * 功能描述:
     * 〈获取当前账号的 MCP 访问密钥列表〉
     * @return MCP 访问密钥列表
     * @author qingfeng
     */
    @GetMapping("/ai/mcp/access/key/list")
    public Result<List<McpKeySummaryVO>> list() {
        return Result.success(accessKeyService.listMine());
    }

    /**
     * 功能描述:
     * 〈获取当前租户的 MCP 访问密钥列表〉
     * @return MCP 访问密钥列表
     * @author qingfeng
     */
    @GetMapping("/ai/mcp/access/key/list/admin")
    public Result<List<McpKeySummaryVO>> listAdmin() {
        return Result.success(accessKeyService.listTenantForAdmin());
    }

    /**
     * 功能描述:
     * 〈创建 MCP 访问密钥〉
     * @param request 创建参数
     * @return 新创建的 MCP 访问密钥
     * @author qingfeng
     */
    @PostMapping("/ai/mcp/access/key/create")
    public Result<McpKeyCreatedVO> create(@Valid @RequestBody McpKeyCreateRO request) {
        return Result.success(accessKeyService.create(request.name(), request.expiresAt()));
    }

    /**
     * 功能描述:
     * 〈撤销当前账号的 MCP 访问密钥〉
     * @param id 访问密钥 ID
     * @return 操作结果
     * @author qingfeng
     */
    @PostMapping("/ai/mcp/access/key/revoke/{id}")
    public Result<Void> revoke(@PathVariable("id") long id) {
        accessKeyService.revokeMine(id);
        return Result.success();
    }

    /**
     * 功能描述:
     * 〈撤销当前租户的 MCP 访问密钥〉
     * @param id 访问密钥 ID
     * @return 操作结果
     * @author qingfeng
     */
    @PostMapping("/ai/mcp/access/key/revoke/admin/{id}")
    public Result<Void> revokeAdmin(@PathVariable("id") long id) {
        accessKeyService.revokeForAdmin(id);
        return Result.success();
    }

    /**
     * 功能描述:
     * 〈获取当前账号的 MCP 接入信息〉
     * @return MCP 接入信息
     * @author qingfeng
     */
    @GetMapping("/ai/mcp/access/info")
    public Result<McpAccessInfoRO> accessInfo() {
        return Result.success(accessKeyService.getAccessInfo());
    }

}
