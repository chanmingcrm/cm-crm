package com.platform.mesh.ai.biz.modules.ai.mcp.tool.function;

import com.platform.mesh.ai.api.modules.mcp.feign.RemoteMcpToolProviderService;
import com.platform.mesh.ai.biz.modules.ai.mcp.tool.constant.McpToolConst;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 功能描述:
 * 〈基于服务发现创建 MCP 工具提供方客户端〉
 *
 * @author qingfeng
 */
@Component
public class FeignMcpToolProviderClientFactory implements McpToolProviderClientFactory {

    private final FeignClientBuilder feignClientBuilder;

    /**
     * 功能描述:
     * 〈创建 MCP 工具提供方客户端工厂〉
     *
     * @param applicationContext Spring 应用上下文
     * @author qingfeng
     */
    public FeignMcpToolProviderClientFactory(ApplicationContext applicationContext) {
        this.feignClientBuilder = new FeignClientBuilder(applicationContext);
    }

    /**
     * 功能描述:
     * 〈创建指定服务的 MCP 工具提供方客户端〉
     *
     * @param serviceName 服务名称
     * @return MCP 工具提供方客户端
     * @author qingfeng
     */
    @Override
    public RemoteMcpToolProviderService create(String serviceName) {
        return feignClientBuilder.forType(RemoteMcpToolProviderService.class, serviceName)
                .contextId(McpToolConst.PROVIDER_CONTEXT_PREFIX + serviceName)
                .build();
    }
}
