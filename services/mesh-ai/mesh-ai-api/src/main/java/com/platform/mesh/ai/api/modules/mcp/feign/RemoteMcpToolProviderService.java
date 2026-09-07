package com.platform.mesh.ai.api.modules.mcp.feign;

import com.platform.mesh.ai.api.modules.mcp.constants.McpConst;
import com.platform.mesh.ai.api.modules.mcp.domain.ro.McpToolInvokeRO;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.utils.result.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 功能描述:
 * 〈远程 MCP 工具提供方服务契约〉
 *
 * @author qingfeng
 */
public interface RemoteMcpToolProviderService {

    /**
     * 功能描述:
     * 〈调用当前业务服务提供的工具〉
     *
     * @param dto 工具调用参数
     * @return 正常返回:{@link Result}，数据为工具调用结果
     * @author qingfeng
     */
    @PostMapping(value = McpConst.TOOL_INVOKE_PATH, headers = HttpConst.HEADER_FROM_IN)
    Result<String> invoke(@RequestBody McpToolInvokeRO dto);

    /**
     * 功能描述:
     * 〈使用当前登录身份调用业务服务提供的工具〉
     *
     * @param dto 工具调用参数
     * @return 正常返回:{@link Result}，数据为工具调用结果
     * @author qingfeng
     */
    @PostMapping(McpConst.TOOL_SESSION_INVOKE_PATH)
    Result<String> invokeFromSession(@RequestBody McpToolInvokeRO dto);
}
