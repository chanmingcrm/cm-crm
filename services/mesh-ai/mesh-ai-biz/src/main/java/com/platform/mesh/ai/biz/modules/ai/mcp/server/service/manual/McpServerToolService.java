package com.platform.mesh.ai.biz.modules.ai.mcp.server.service.manual;

import com.platform.mesh.ai.biz.modules.ai.mcp.server.constant.McpServerConst;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.po.AiMcpServer;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.ro.RegisteredMcpClient;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.mapper.AiMcpServerMapper;
import com.platform.mesh.security.context.SecurityContextScope;
import com.platform.mesh.resource.authentication.UserAuthenticationToken;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.utils.UserCacheUtil;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.model.ToolContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.SyncMcpToolCallback;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.metadata.ToolMetadata;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能描述:
 * 〈当前租户 MCP 工具装配服务〉
 * @author qingfeng
 */
@Service
public class McpServerToolService {

    private static final Logger log = LoggerFactory.getLogger(McpServerToolService.class);
    private static final Pattern REQUIRED_TOOL_PATTERN = Pattern.compile(
            "(?:必须调用|调用)\\s+([a-zA-Z][a-zA-Z0-9_]*)");
    private static final Pattern TOOL_ARGUMENTS_PATTERN = Pattern.compile(
            "工具参数JSON=(\\{.*?})。");
    /** 外部 MCP 客户端注册表。 */
    private final McpClientRegistry registry;
    /** MCP 服务配置查询组件。 */
    private final AiMcpServerMapper serverMapper;
    /** 系统内置 MCP 工具提供者。 */
    private final ToolCallbackProvider platformToolProvider;

    /**
     * 功能描述:
     * 〈创建当前租户 MCP 工具装配服务〉
     * @param registry 外部 MCP 客户端注册表
     * @param serverMapper MCP 服务配置查询组件
     * @param platformToolProvider 系统内置 MCP 工具提供者
     * @author qingfeng
     */
    public McpServerToolService(McpClientRegistry registry, AiMcpServerMapper serverMapper,
            @Qualifier("aiMcpToolCallbackProvider") ToolCallbackProvider platformToolProvider) {
        this.registry = registry;
        this.serverMapper = serverMapper;
        this.platformToolProvider = platformToolProvider;
    }

    /**
     * 功能描述:
     * 〈查询当前租户可用的 MCP 工具回调〉
     * @return MCP 工具回调列表
     * @author qingfeng
     */
    public List<ToolCallback> toolCallbacks() {
        List<ToolCallback> callbacks = new ArrayList<>();
        // 先装配本地平台工具，再发现外部工具；名称冲突时保持现有的平台优先顺序。
        addPlatformTools(callbacks);
        addExternalTools(callbacks);
        return List.copyOf(callbacks);
    }

    /**
     * 功能描述:
     * 〈装配系统内置 MCP 工具〉
     * @param callbacks MCP 工具回调集合
     * @param tenantId 当前租户标识
     * @author qingfeng
     */
    private void addPlatformTools(List<ToolCallback> callbacks) {
        AiMcpServer platformServer = serverMapper.selectPlatformServer();
        if (platformServer == null) return;
        LoginUserBO loginUser = UserCacheUtil.getLoginUser();
        String prefix = toolPrefix(platformServer.getId());
        for (ToolCallback callback : platformToolProvider.getToolCallbacks()) {
            callbacks.add(authenticatedPlatformCallback(callback, prefix, loginUser));
        }
    }

    /**
     * 功能描述:
     * 〈装配系统共享及当前租户的外部 MCP 工具〉
     * @param callbacks MCP 工具回调集合
     * @param tenantId 当前租户标识
     * @author qingfeng
     */
    private void addExternalTools(List<ToolCallback> callbacks) {
        for (RegisteredMcpClient registered : registry.clients()) {
            try {
                String prefix = toolPrefix(registered.server().getId());
                registered.client().listTools().tools().forEach(tool -> callbacks.add(
                        SyncMcpToolCallback.builder()
                                .mcpClient(registered.client())
                                .tool(tool)
                                .prefixedToolName(prefixedToolName(prefix, tool.name()))
                                .build()));
            }
            catch (RuntimeException exception) {
                // 单个外部服务故障不能阻断平台工具和其他外部服务的装配。
                log.warn("外部 MCP 工具发现失败，serverId={}", registered.server().getId(), exception);
            }
        }
    }

    /**
     * 功能描述:
     * 〈为系统内置工具增加统一名称、访问策略及登录身份〉
     * @param delegate 原始工具回调
     * @param prefix 系统 MCP 工具名称前缀
     * @param loginUser 当前登录用户
     * @param tenantId 当前租户标识
     * @return 可供模型调用的系统工具回调
     * @author qingfeng
     */
    private ToolCallback authenticatedPlatformCallback(ToolCallback delegate, String prefix,
            LoginUserBO loginUser) {
        // 保留原工具描述及参数结构，仅增加服务级名称前缀。
        ToolDefinition original = delegate.getToolDefinition();
        ToolDefinition definition = ToolDefinition.builder()
                .name(prefixedToolName(prefix, original.name()))
                .description(original.description())
                .inputSchema(original.inputSchema())
                .build();
        return new ToolCallback() {
            @Override
            public @NonNull ToolDefinition getToolDefinition() {
                return definition;
            }

            @Override
            public @NonNull ToolMetadata getToolMetadata() {
                return delegate.getToolMetadata();
            }

            @Override
            public @NonNull String call(@NonNull String toolInput) {
                // 系统 MCP 面向所有租户，实际业务数据权限仍使用当前登录身份。
                // 恢复页面登录身份，确保下游业务仍执行租户和数据权限校验。
                long startedAt = System.nanoTime();
                try (SecurityContextScope ignored = SecurityContextScope.open(
                        new UserAuthenticationToken(loginUser))) {
                    return delegate.call(toolInput);
                }
                finally {
                    log.info("AI MCP工具执行完成，tool={}, durationMs={}", original.name(),
                            (System.nanoTime() - startedAt) / 1_000_000);
                }
            }

            @Override
            public @NonNull String call(@NonNull String toolInput, ToolContext toolContext) {
                return call(toolInput);
            }
        };
    }

    /**
     * 功能描述:
     * 〈将当前租户外部 MCP 工具加入聊天参数〉
     * @param options 原聊天参数
     * @return 包含外部 MCP 工具的聊天参数
     * @author qingfeng
     */
    public ChatOptions withExternalTools(ChatOptions options) {
        return withExternalTools(options, null);
    }

    /**
     * 功能描述:
     * 〈按当前请求装配 MCP 工具，明确指定工具时只暴露目标工具〉
     * @param options 原聊天参数
     * @param content 当前请求内容
     * @return 包含 MCP 工具的聊天参数
     * @author qingfeng
     */
    public ChatOptions withExternalTools(ChatOptions options, String content) {
        // 步骤一：收集系统内置工具和当前账号可访问的外部工具。
        List<ToolCallback> callbacks = this.toolCallbacks();

        // 步骤二：场景中心明确指定工具时缩小工具集合，减少模型选错工具的概率。
        String requestedTool = requestedToolName(content);
        if (requestedTool != null) {
            List<ToolCallback> matchedCallbacks = filterRequestedTool(callbacks, requestedTool);
            if (!matchedCallbacks.isEmpty()) {
                callbacks = matchedCallbacks;
                log.debug("AI MCP请求仅装配指定工具，tool={}", requestedTool);
            }
            else {
                log.warn("AI MCP请求指定工具不存在，继续装配可用工具，tool={}", requestedTool);
            }
        }
        if (callbacks.isEmpty()) {
            return options;
        }

        // 步骤三：仅支持工具调用的 ChatOptions 才写入回调，其他模型配置保持原样。
        ChatOptions.Builder<?> builder = options.mutate();
        if (!(builder instanceof ToolCallingChatOptions.Builder<?> toolBuilder)) {
            return options;
        }
        return toolBuilder.toolCallbacks(callbacks).build();
    }

    /**
     * 功能描述:
     * 〈从场景指令中提取明确指定的工具名称〉
     * @param content 当前请求内容
     * @return 工具名称，未明确指定时返回 null
     * @author qingfeng
     */
    static String requestedToolName(String content) {
        if (content == null || content.isBlank()) {
            return null;
        }
        Matcher matcher = REQUIRED_TOOL_PATTERN.matcher(content);
        return matcher.find() ? matcher.group(1) : null;
    }

    /**
     * 提取场景中心提供的可信工具参数；普通自然语言请求不会命中。
     * @param content 当前请求内容
     * @return JSON 工具参数，未提供时返回 null
     */
    public static String requestedToolArguments(String content) {
        if (content == null || content.isBlank()) {
            return null;
        }
        Matcher matcher = TOOL_ARGUMENTS_PATTERN.matcher(content);
        return matcher.find() ? matcher.group(1) : null;
    }

    /**
     * 功能描述:
     * 〈从候选工具中筛选场景明确指定的唯一工具〉
     * @param callbacks 当前账号可用工具
     * @param requestedTool 场景指定工具名称
     * @return 名称匹配的工具列表
     * @author qingfeng
     */
    private static List<ToolCallback> filterRequestedTool(List<ToolCallback> callbacks,
            String requestedTool) {
        return callbacks.stream()
                .filter(callback -> matchesToolName(
                        callback.getToolDefinition().name(), requestedTool))
                .toList();
    }

    /**
     * 功能描述:
     * 〈兼容原始工具名和带 MCP 连接前缀的工具名〉
     * @param actualName 已装配的实际工具名称
     * @param requestedName 场景指定的原始工具名称
     * @return 是否为同一个工具
     * @author qingfeng
     */
    private static boolean matchesToolName(String actualName, String requestedName) {
        return actualName.equals(requestedName)
                || actualName.endsWith(McpServerConst.TOOL_NAME_SEPARATOR + requestedName);
    }

    /**
     * 功能描述:
     * 〈生成连接级工具名称前缀〉
     * @param connectionId 外部 MCP 连接 ID
     * @return 工具名称前缀
     * @author qingfeng
     */
    private static String toolPrefix(Long connectionId) {
        return McpServerConst.TOOL_NAME_PREFIX + connectionId;
    }

    /**
     * 功能描述:
     * 〈生成带连接前缀的工具名称〉
     * @param prefix 连接级工具名称前缀
     * @param toolName 原工具名称
     * @return 带连接前缀的工具名称
     * @author qingfeng
     */
    private static String prefixedToolName(String prefix, String toolName) {
        return prefix + McpServerConst.TOOL_NAME_SEPARATOR + toolName;
    }
}
