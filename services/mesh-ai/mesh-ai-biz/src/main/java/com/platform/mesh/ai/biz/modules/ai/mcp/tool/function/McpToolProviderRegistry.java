package com.platform.mesh.ai.biz.modules.ai.mcp.tool.function;

import com.platform.mesh.ai.api.modules.mcp.feign.RemoteMcpToolProviderService;
import com.platform.mesh.ai.biz.modules.ai.mcp.tool.constant.McpToolConst;
import com.platform.mesh.ai.biz.modules.ai.mcp.tool.exception.McpToolExceptionEnum;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能描述:
 * 〈MCP 工具提供方注册表〉
 *
 * @author qingfeng
 */
@Component
public class McpToolProviderRegistry {

    private final McpToolProviderClientFactory clientFactory;
    private final Map<String, RemoteMcpToolProviderService> providerMap = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈创建 MCP 工具提供方注册表〉
     *
     * @param clientFactory 工具提供方客户端工厂
     * @author qingfeng
     */
    public McpToolProviderRegistry(McpToolProviderClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    /**
     * 功能描述:
     * 〈按业务模块获取工具提供方客户端〉
     *
     * @param mcpModule MCP 业务模块编码
     * @return MCP 工具提供方客户端
     * @author qingfeng
     */
    public RemoteMcpToolProviderService getProvider(String mcpModule) {
        String normalizedModule = normalize(mcpModule);
        return providerMap.computeIfAbsent(normalizedModule,
                module -> clientFactory.create(serviceName(module)));
    }

    /**
     * 功能描述:
     * 〈校验并规范化 MCP 业务模块编码〉
     *
     * @param mcpModule MCP 业务模块编码
     * @return 规范化后的业务模块编码
     * @author qingfeng
     */
    private String normalize(String mcpModule) {
        if (mcpModule == null || mcpModule.isBlank()) {
            throw McpToolExceptionEnum.MCP_MODULE_EMPTY.getBaseException();
        }
        String normalizedModule = mcpModule.trim().toLowerCase(Locale.ROOT);
        if (!normalizedModule.matches(McpToolConst.MODULE_PATTERN)) {
            throw McpToolExceptionEnum.MCP_MODULE_INVALID.getBaseException();
        }
        return normalizedModule;
    }

    /**
     * 功能描述:
     * 〈根据业务模块编码生成服务发现名称〉
     *
     * @param mcpModule 规范化后的业务模块编码
     * @return 服务发现名称
     * @author qingfeng
     */
    private String serviceName(String mcpModule) {
        return McpToolConst.PROVIDER_SERVICE_PREFIX + mcpModule
                + McpToolConst.PROVIDER_SERVICE_SUFFIX;
    }
}
