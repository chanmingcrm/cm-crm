package com.platform.mesh.crm.biz.mcp.tool.crm.impl;

import cn.hutool.json.JSONObject;
import com.platform.mesh.crm.biz.mcp.tool.crm.manual.CustomerVisitMcpManual;
import com.platform.mesh.crm.biz.mcp.tool.factory.CrmMcpTool;
import org.springframework.stereotype.Service;

/**
 * 功能描述:
 * 〈提供客户拜访准备场景的 MCP 工具入口〉
 * @author qingfeng
 */
@Service
public class CustomerVisitMcpTool {

    private final CustomerVisitMcpManual handler;

    /**
     * 功能描述:
     * 〈提供客户拜访准备场景的 MCP 工具入口〉
     * @param handler 对应场景的业务处理器
     * @author qingfeng
     */
    public CustomerVisitMcpTool(CustomerVisitMcpManual handler) {
        this.handler = handler;
    }

    /**
     * 功能描述:
     * 〈生成指定客户的拜访前准备内容〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    @CrmMcpTool("crm_prepare_customer_visit")
    public Object prepareCustomerVisit(JSONObject arguments, JSONObject executionConfig) {
        return handler.prepareCustomerVisit(arguments, executionConfig);
    }
}