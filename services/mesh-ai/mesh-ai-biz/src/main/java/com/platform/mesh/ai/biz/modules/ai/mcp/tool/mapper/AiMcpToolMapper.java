package com.platform.mesh.ai.biz.modules.ai.mcp.tool.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.ai.biz.modules.ai.mcp.tool.domain.po.AiMcpTool;

import java.util.List;

/**
 * 功能描述:
 * 〈MCP 工具目录数据访问接口〉
 *
 * @author qingfeng
 */
public interface AiMcpToolMapper extends BaseMapper<AiMcpTool> {

    /**
     * 功能描述:
     * 〈查询全部启用的 MCP 工具〉
     * @return 启用的 MCP 工具列表
     * @author qingfeng
     */
    @InterceptorIgnore(tenantLine = "true")
    List<AiMcpTool> selectEnabledTools();
}
