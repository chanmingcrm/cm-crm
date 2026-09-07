package com.platform.mesh.ai.biz.modules.ai.mcp.tool.service.manual;

import com.platform.mesh.ai.api.modules.mcp.domain.ro.McpToolDefinitionRO;
import com.platform.mesh.ai.api.modules.mcp.constants.McpConst;
import com.platform.mesh.ai.biz.modules.ai.mcp.tool.exception.McpToolExceptionEnum;
import com.platform.mesh.security.context.SecurityContextScope;
import com.platform.mesh.resource.authentication.UserAuthenticationToken;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.feign.context.FeignIdentityContext;
import cn.hutool.json.JSONUtil;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.metadata.ToolMetadata;

import java.util.function.Function;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 功能描述:
 * 〈远程业务工具回调〉
 * @author qingfeng
 */
public class AiMcpToolCallback implements ToolCallback {

    private final ToolDefinition definition;
    private final ToolMetadata metadata;
    private final Function<String, String> executor;

    /**
     * 功能描述:
     * 〈创建远程业务工具回调〉
     * @param toolDefinition 远程工具定义
     * @param executor 远程工具执行函数
     * @author qingfeng
     */
    public AiMcpToolCallback(McpToolDefinitionRO toolDefinition, Function<String, String> executor) {
        this.definition = ToolDefinition.builder()
                .name(toolDefinition.name())
                .description(toolDefinition.description())
                .inputSchema(toolDefinition.inputSchema())
                .build();
        this.metadata = ToolMetadata.builder()
                .returnDirect(toolDefinition.returnDirect())
                .build();
        this.executor = executor;
    }

    /**
     * 功能描述:
     * 〈获取 Spring AI 工具定义〉
     * @return Spring AI 工具定义
     * @author qingfeng
     */
    @Override
    public @NonNull ToolDefinition getToolDefinition() {
        return definition;
    }

    /**
     * 功能描述:
     * 〈获取数据库配置的工具执行元数据〉
     * @return 工具执行元数据
     * @author qingfeng
     */
    @Override
    public @NonNull ToolMetadata getToolMetadata() {
        return metadata;
    }

    /**
     * 功能描述:
     * 〈执行远程业务工具〉
     * @param toolInput JSON 格式工具参数
     * @return JSON 格式工具结果
     * @author qingfeng
     */
    @Override
    public @NonNull String call(@NonNull String toolInput) {
        return executor.apply(toolInput);
    }

    /**
     * 功能描述:
     * 〈在 Spring AI 异步工具线程中恢复已认证的租户身份并执行远程工具〉
     * @param toolInput JSON 格式工具参数
     * @param toolContext Spring AI 工具上下文
     * @return JSON 格式工具结果
     * @author qingfeng
     */
    @Override
    public @NonNull String call(@NonNull String toolInput, ToolContext toolContext) {
        var exchange = McpToolUtils.getMcpExchange(toolContext)
                .orElseThrow(McpToolExceptionEnum.MCP_EXCHANGE_CONTEXT_MISSING::getBaseException);
        Object principal = exchange.transportContext().get(McpConst.CONTEXT_PRINCIPAL);
        Object accessKey = exchange.transportContext().get(McpConst.CONTEXT_ACCESS_KEY);
        if (!(principal instanceof LoginUserBO loginUser)) {
            throw McpToolExceptionEnum.MCP_TRUSTED_IDENTITY_MISSING.getBaseException();
        }
        // 工具数据权限直接使用已认证身份，不再叠加重复的租户策略开关。
        String encodedUser = URLEncoder.encode(JSONUtil.toJsonStr(loginUser), StandardCharsets.UTF_8);
        try (SecurityContextScope ignored = SecurityContextScope.open(
                    new UserAuthenticationToken(loginUser));
                FeignIdentityContext.Scope feignScope = FeignIdentityContext.open(
                        encodedUser, loginUser.getAccountId().toString(),
                        Boolean.TRUE.equals(accessKey))) {
            return executor.apply(toolInput);
        }
    }
}
