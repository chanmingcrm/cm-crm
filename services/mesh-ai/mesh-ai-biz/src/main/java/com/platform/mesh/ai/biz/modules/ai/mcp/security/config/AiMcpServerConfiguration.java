package com.platform.mesh.ai.biz.modules.ai.mcp.security.config;

import com.platform.mesh.ai.api.modules.mcp.domain.ro.McpToolDefinitionRO;
import com.platform.mesh.ai.api.modules.mcp.constants.McpConst;
import com.platform.mesh.ai.biz.modules.ai.mcp.tool.service.manual.AiMcpToolDispatcher;
import com.platform.mesh.ai.biz.modules.ai.mcp.tool.service.manual.AiMcpToolCallback;
import com.platform.mesh.ai.biz.modules.ai.mcp.tool.service.AiMcpToolService;
import com.platform.mesh.ai.biz.modules.ai.mcp.tool.exception.McpToolExceptionEnum;
import com.platform.mesh.feign.interceptor.FeignRequestInterceptor;
import com.platform.mesh.security.constants.SecurityConstant;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import io.modelcontextprotocol.common.McpTransportContext;
import io.modelcontextprotocol.json.jackson3.JacksonMcpJsonMapper;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.mcp.server.common.autoconfigure.properties.McpServerStreamableHttpProperties;
import org.springframework.ai.mcp.server.webmvc.transport.WebMvcStreamableServerTransportProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.json.JsonMapper;

import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 功能描述:
 * 〈AI MCP 服务配置〉
 *
 * @author qingfeng
 */
@Configuration
public class AiMcpServerConfiguration {

    /**
     * 功能描述:
     * 〈创建可跨异步线程传递可信租户身份的 MCP WebMVC 传输器〉
     * @param properties MCP Streamable HTTP 配置
     * @return MCP WebMVC 传输器
     * @author qingfeng
     */
    @Bean
    public WebMvcStreamableServerTransportProvider webMvcStreamableServerTransportProvider(
            JsonMapper mcpServerJsonMapper,
            McpServerStreamableHttpProperties properties) {
        return WebMvcStreamableServerTransportProvider.builder()
                .jsonMapper(new JacksonMcpJsonMapper(mcpServerJsonMapper))
                .mcpEndpoint(properties.getMcpEndpoint())
                .keepAliveInterval(properties.getKeepAliveInterval())
                .disallowDelete(properties.isDisallowDelete())
                .contextExtractor(request -> {
                    Object principal = request.servletRequest().getAttribute(
                            SecurityConstant.AUTHENTICATION_PRINCIPAL_ATTRIBUTE);
                    if (!(principal instanceof LoginUserBO)) {
                        return McpTransportContext.EMPTY;
                    }
                    java.util.Map<String, Object> context = new HashMap<>();
                    context.put(McpConst.CONTEXT_PRINCIPAL, principal);
                    context.put(McpConst.CONTEXT_ACCESS_KEY, Boolean.TRUE.equals(
                            request.servletRequest().getAttribute(
                                    SecurityConstant.AUTHENTICATION_ACCESS_KEY_ATTRIBUTE)));
                    return McpTransportContext.create(context);
                })
                .build();
    }

    /**
     * 功能描述:
     * 〈注册由 AI 统一发布的远程业务工具〉
     * @param dispatcher MCP 远程工具调度器
     * @return MCP 工具回调提供者
     * @author qingfeng
     */
    @Bean
    public ToolCallbackProvider aiMcpToolCallbackProvider(AiMcpToolDispatcher dispatcher,
            AiMcpToolService toolService) {
        // 工具元数据由 AI 数据库统一维护，业务模块仅负责实现和执行工具。
        List<McpToolDefinitionRO> definitions = toolService.listEnabledToolDefinitions();
        validateUniqueNames(definitions);
        List<ToolCallback> callbacks = definitions.stream()
                .<ToolCallback>map(definition -> new AiMcpToolCallback(definition,
                        arguments -> dispatcher.invoke(definition.mcpModule(), definition.name(), arguments,
                                definition.executionConfig())))
                .toList();
        return ToolCallbackProvider.from(callbacks);
    }

    /**
     * 功能描述:
     * 〈校验工具名称全局唯一〉
     * @param definitions 工具定义列表
     * @author qingfeng
     */
    private void validateUniqueNames(List<McpToolDefinitionRO> definitions) {
        Set<String> names = new HashSet<>();
        for (McpToolDefinitionRO definition : definitions) {
            if (definition == null || definition.name() == null || !names.add(definition.name())) {
                throw McpToolExceptionEnum.MCP_TOOL_NAME_INVALID.getBaseException();
            }
        }
    }
}
