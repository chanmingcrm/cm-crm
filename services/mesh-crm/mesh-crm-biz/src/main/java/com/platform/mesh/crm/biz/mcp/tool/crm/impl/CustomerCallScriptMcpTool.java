package com.platform.mesh.crm.biz.mcp.tool.crm.impl;

import cn.hutool.json.JSONObject;
import com.platform.mesh.crm.biz.mcp.tool.crm.manual.CustomerCallScriptMcpManual;
import com.platform.mesh.crm.biz.mcp.tool.factory.CrmMcpTool;
import org.springframework.stereotype.Service;

/**
 * 功能描述:
 * 〈提供客户沟通话术场景的 MCP 工具入口〉
 * @author qingfeng
 */
@Service
public class CustomerCallScriptMcpTool {

    private final CustomerCallScriptMcpManual handler;

    /**
     * 功能描述:
     * 〈提供客户沟通话术场景的 MCP 工具入口〉
     * @param handler 对应场景的业务处理器
     * @author qingfeng
     */
    public CustomerCallScriptMcpTool(CustomerCallScriptMcpManual handler) {
        this.handler = handler;
    }

    /**
     * 功能描述:
     * 〈生成指定客户的电话沟通话术〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    @CrmMcpTool("crm_generate_customer_call_script")
    public Object generateCustomerCallScript(JSONObject arguments, JSONObject executionConfig) {
        return handler.generateCustomerCallScript(arguments, executionConfig);
    }
}