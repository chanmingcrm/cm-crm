package com.platform.mesh.crm.biz.mcp.query.controller;

import com.platform.mesh.ai.api.modules.mcp.domain.ro.McpToolInvokeRO;
import com.platform.mesh.ai.api.modules.mcp.feign.RemoteMcpToolProviderService;
import com.platform.mesh.crm.biz.mcp.query.service.CrmMcpToolProviderService;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.utils.SecurityUtils;
import com.platform.mesh.utils.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述:
 * 〈CRM MCP 内部工具接口〉
 *
 * @author qingfeng
 */
@RestController
public class CrmMcpToolProviderController implements RemoteMcpToolProviderService {

    private final CrmMcpToolProviderService providerService;

    /**
     * 功能描述:
     * 〈创建 CRM MCP 内部工具接口〉
     *
     * @param providerService CRM MCP 工具提供服务
     * @author qingfeng
     */
    public CrmMcpToolProviderController(CrmMcpToolProviderService providerService) {
        this.providerService = providerService;
    }

    /**
     * 功能描述:
     * 〈调用 CRM 只读工具〉
     *
     * @param dto 工具调用参数
     * @return 正常返回:{@link Result}，数据为工具调用结果
     * @author qingfeng
     */
    @Override
    @AuthIgnore
    public Result<String> invoke(McpToolInvokeRO dto) {
        Authentication authentication = SecurityUtils.getAuthentication();
        if (authentication == null
                || !(authentication.getPrincipal() instanceof LoginUserBO loginUser)
                || loginUser.getAccountId() == null
                || loginUser.getUserId() == null) {
            return Result.error(HttpStatus.UNAUTHORIZED.value(), "MCP 调用身份未恢复");
        }
        return providerService.invoke(dto);
    }

    /**
     * 功能描述:
     * 〈处理会话链路发起的 CRM MCP 工具调用〉
     *
     * @param dto MCP 工具调用参数
     * @return MCP 工具执行结果
     * @author qingfeng
     */
    @Override
    public Result<String> invokeFromSession(McpToolInvokeRO dto) {
        return providerService.invoke(dto);
    }
}
