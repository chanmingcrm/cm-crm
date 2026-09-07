package com.platform.mesh.ai.biz.modules.ai.mcp.tool.service.impl;

import com.platform.mesh.ai.api.modules.mcp.domain.ro.McpToolDefinitionRO;
import com.platform.mesh.ai.biz.modules.ai.mcp.tool.domain.po.AiMcpTool;
import com.platform.mesh.ai.biz.modules.ai.mcp.tool.mapper.AiMcpToolMapper;
import com.platform.mesh.ai.biz.modules.ai.mcp.tool.service.AiMcpToolService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能描述:
 * 〈MCP 工具目录服务实现〉
 * @author qingfeng
 */
@Service
public class AiMcpToolServiceImpl implements AiMcpToolService {

    private final AiMcpToolMapper mapper;

    /**
     * 功能描述:
     * 〈创建 MCP 工具目录服务〉
     * @param mapper MCP 工具目录数据访问接口
     * @author qingfeng
     */
    public AiMcpToolServiceImpl(AiMcpToolMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 功能描述:
     * 〈查询全部启用的 MCP 工具〉
     * @return 启用的 MCP 工具列表
     * @author qingfeng
     */
    @Override
    public List<AiMcpTool> listEnabledTools() {
        return mapper.selectEnabledTools();
    }

    /**
     * 功能描述:
     * 〈查询全部启用的 MCP 工具定义〉
     * @return MCP 工具定义列表
     * @author qingfeng
     */
    @Override
    public List<McpToolDefinitionRO> listEnabledToolDefinitions() {
        return listEnabledTools().stream()
                .map(AiMcpToolServiceImpl::toDefinition)
                .toList();
    }

    /**
     * 功能描述:
     * 〈转换 MCP 工具目录对象〉
     * @param tool MCP 工具目录对象
     * @return MCP 工具定义
     * @author qingfeng
     */
    static McpToolDefinitionRO toDefinition(AiMcpTool tool) {
        return new McpToolDefinitionRO(tool.getMcpModule(), tool.getToolName(),
                tool.getDescription(), tool.getInputSchema(),
                tool.getExecutionConfig(),
                Integer.valueOf(1).equals(tool.getReadOnly()),
                Integer.valueOf(1).equals(tool.getReturnDirect()));
    }

}
