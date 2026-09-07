package com.platform.mesh.ai.biz.modules.ai.mcp.tool.service.manual;

import com.platform.mesh.ai.api.modules.mcp.domain.ro.McpToolInvokeRO;
import com.platform.mesh.ai.biz.modules.ai.mcp.tool.constant.McpToolConst;
import com.platform.mesh.ai.biz.modules.ai.mcp.tool.exception.McpToolExceptionEnum;
import com.platform.mesh.ai.api.modules.mcp.feign.RemoteMcpToolProviderService;
import com.platform.mesh.ai.biz.modules.ai.mcp.tool.function.McpToolProviderRegistry;
import com.platform.mesh.feign.context.FeignIdentityContext;
import com.platform.mesh.utils.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * 功能描述:
 * 〈AI MCP 远程工具调度器〉
 *
 * @author qingfeng
 */
@Service
public class AiMcpToolDispatcher {

    private static final Logger log = LoggerFactory.getLogger(AiMcpToolDispatcher.class);

    private final McpToolProviderRegistry providerRegistry;

    /**
     * 功能描述:
     * 〈创建 AI MCP 远程工具调度器〉
     *
     * @param providerRegistry MCP 工具提供方注册表
     * @author qingfeng
     */
    public AiMcpToolDispatcher(McpToolProviderRegistry providerRegistry) {
        this.providerRegistry = providerRegistry;
    }

    /**
     * 功能描述:
     * 〈调用指定业务提供方的工具〉
     *
     * @param mcpModule 工具提供方编码
     * @param toolName 工具名称
     * @param arguments JSON 格式工具参数
     * @return JSON 格式工具结果
     * @author qingfeng
     */
    public String invoke(String mcpModule, String toolName, String arguments,
            String executionConfig) {
        RemoteMcpToolProviderService provider = providerRegistry.getProvider(mcpModule);
        McpToolInvokeRO dto = new McpToolInvokeRO(toolName, arguments,
                UUID.randomUUID().toString(), executionConfig);
        Result<String> result;
        try {
            // Access Key 使用内部签名接口，页面聊天沿用当前登录身份。
            result = accessKeyRequest()
                    ? provider.invoke(dto) : provider.invokeFromSession(dto);
        }
        catch (RuntimeException exception) {
            log.error(McpToolConst.LOG_REMOTE_INVOKE_FAILED,
                    mcpModule, toolName, null, exception);
            throw McpToolExceptionEnum.MCP_REMOTE_INVOKE_FAILED.getBaseException();
        }
        if (!successful(result) || result.getData() == null) {
            log.error(McpToolConst.LOG_REMOTE_INVOKE_FAILED,
                    mcpModule, toolName, result == null ? null : result.getCode());
            throw McpToolExceptionEnum.MCP_REMOTE_INVOKE_FAILED.getBaseException();
        }
        return result.getData();
    }

    /**
     * 功能描述:
     * 〈判断远程调用是否成功〉
     *
     * @param result 远程调用结果
     * @return 是否成功
     * @author qingfeng
     */
    private boolean successful(Result<?> result) {
        return result != null && Integer.valueOf(HttpStatus.OK.value()).equals(result.getCode());
    }

    /**
     * 功能描述:
     * 〈判断当前调用是否来自 MCP Access Key 链路〉
     *
     * @return 是否使用 Access Key 身份
     * @author qingfeng
     */
    private boolean accessKeyRequest() {
        return FeignIdentityContext.current()
                .map(FeignIdentityContext.Identity::isAccessKey)
                .orElse(false);
    }
}
