package com.platform.mesh.crm.biz.mcp.tool.crm.impl;

import cn.hutool.json.JSONObject;
import com.platform.mesh.crm.biz.mcp.tool.crm.manual.Customer360McpManual;
import com.platform.mesh.crm.biz.mcp.tool.factory.CrmMcpTool;
import org.springframework.stereotype.Service;

/**
 * 功能描述:
 * 〈提供客户全景类场景的 MCP 工具入口〉
 * @author qingfeng
 */
@Service
public class Customer360McpTool {

    private final Customer360McpManual handler;

    /**
     * 功能描述:
     * 〈提供客户全景类场景的 MCP 工具入口〉
     * @param handler 对应场景的业务处理器
     * @author qingfeng
     */
    public Customer360McpTool(Customer360McpManual handler) {
        this.handler = handler;
    }

    /**
     * 功能描述:
     * 〈获取指定客户的三百六十度经营视图〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    @CrmMcpTool("crm_get_customer_360")
    public Object getCustomer360(JSONObject arguments, JSONObject executionConfig) {
        return handler.getCustomer360(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈分析指定客户的健康度和流失风险〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    @CrmMcpTool("crm_analyze_customer_health")
    public Object analyzeCustomerHealth(JSONObject arguments, JSONObject executionConfig) {
        return handler.analyzeCustomerHealth(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈生成指定客户的合同续签准备方案〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    @CrmMcpTool("crm_prepare_contract_renewal")
    public Object prepareContractRenewal(JSONObject arguments, JSONObject executionConfig) {
        return handler.prepareContractRenewal(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈生成指定客户的多维经营摘要〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    @CrmMcpTool("crm_generate_customer_summary")
    public Object generateCustomerSummary(JSONObject arguments, JSONObject executionConfig) {
        return handler.generateCustomerSummary(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈分析指定客户重点商机的成交风险〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    @CrmMcpTool("crm_analyze_opportunity_risk")
    public Object analyzeOpportunityRisk(JSONObject arguments, JSONObject executionConfig) {
        return handler.analyzeOpportunityRisk(arguments, executionConfig);
    }
}