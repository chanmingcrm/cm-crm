package com.platform.mesh.crm.biz.mcp.query.service;

import cn.hutool.json.JSONUtil;
import com.platform.mesh.ai.api.modules.mcp.domain.ro.McpToolInvokeRO;
import com.platform.mesh.crm.biz.mcp.tool.factory.CrmMcpToolFactory;
import com.platform.mesh.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 功能描述:
 * 〈接收 MCP 调用请求并转发到对应 CRM 工具〉
 * @author qingfeng
 */
@Service
public class CrmMcpToolProviderService {

    private static final Logger log = LoggerFactory.getLogger(CrmMcpToolProviderService.class);

    private final CrmMcpToolFactory toolFactory;

    /**
     * 功能描述:
     * 〈接收 MCP 调用请求并转发到对应 CRM 工具〉
     * @param toolFactory CRM MCP 工具工厂
     * @author qingfeng
     */
    public CrmMcpToolProviderService(CrmMcpToolFactory toolFactory) {
        this.toolFactory = toolFactory;
    }

    /**
     * 功能描述:
     * 〈调用数据库工具名称对应的 CRM 业务方法〉
     * @param dto MCP 工具调用参数
     * @return 业务处理结果
     * @author qingfeng
     */
    public Result<String> invoke(McpToolInvokeRO dto) {
        // 第一步：校验调用对象和数据库工具名称，避免无效请求进入工具路由。
        if (dto == null || dto.toolName() == null) {
            return Result.error(400, "工具调用参数不能为空");
        }
        try {
            // 第二步：将参数和执行配置交给工具工厂，通过 toolName 精确调用业务入口。
            Object result = toolFactory.invoke(dto.toolName(), dto.arguments(),
                    dto.executionConfig());
            return Result.success("操作成功", JSONUtil.toJsonStr(result));
        } catch (Exception exception) {
            // 第三步：仅记录工具名称和异常堆栈，不记录可能包含客户数据的调用参数。
            log.error("CRM MCP 工具执行失败，tool={}", dto.toolName(), exception);
            return Result.error(404, "工具不存在或不可用");
        }
    }

}