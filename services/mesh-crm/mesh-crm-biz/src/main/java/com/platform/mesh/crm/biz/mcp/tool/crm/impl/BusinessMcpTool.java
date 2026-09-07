package com.platform.mesh.crm.biz.mcp.tool.crm.impl;

import cn.hutool.json.JSONObject;
import com.platform.mesh.crm.biz.mcp.tool.crm.manual.BusinessMcpManual;
import com.platform.mesh.crm.biz.mcp.tool.factory.CrmMcpTool;
import org.springframework.stereotype.Service;

/**
 * 功能描述:
 * 〈提供经营分析场景的 MCP 工具入口〉
 * @author qingfeng
 */
@Service
public class BusinessMcpTool {

    private final BusinessMcpManual handler;

    /**
     * 功能描述:
     * 〈提供经营分析场景的 MCP 工具入口〉
     * @param handler 对应场景的业务处理器
     * @author qingfeng
     */
    public BusinessMcpTool(BusinessMcpManual handler) {
        this.handler = handler;
    }

    /**
     * 功能描述:
     * 〈查询停滞商机并加工下一步动作〉
     * @param arguments MCP 工具参数
     * @return 业务处理结果
     * @author qingfeng
     */
    @CrmMcpTool("crm_list_stagnant_opportunities")
    public Object listStagnantOpportunities(JSONObject arguments) {
        return handler.listStagnantOpportunities(arguments);
    }
}