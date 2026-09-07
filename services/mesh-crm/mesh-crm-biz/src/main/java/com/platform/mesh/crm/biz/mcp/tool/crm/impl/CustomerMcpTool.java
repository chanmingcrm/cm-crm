package com.platform.mesh.crm.biz.mcp.tool.crm.impl;

import cn.hutool.json.JSONObject;
import com.platform.mesh.crm.biz.mcp.constant.CrmMcpToolConst;
import com.platform.mesh.crm.biz.mcp.tool.crm.manual.CustomerMcpManual;
import com.platform.mesh.crm.biz.mcp.tool.factory.CrmMcpTool;
import org.springframework.stereotype.Service;

/**
 * 功能描述:
 * 〈提供客户基础能力的 MCP 工具入口〉
 * @author qingfeng
 */
@Service
public class CustomerMcpTool {

    private final CustomerMcpManual handler;

    /**
     * 功能描述:
     * 〈提供客户基础能力的 MCP 工具入口〉
     * @param handler 对应场景的业务处理器
     * @author qingfeng
     */
    public CustomerMcpTool(CustomerMcpManual handler) {
        this.handler = handler;
    }

    /**
     * 功能描述:
     * 〈获取当前 CRM 连接和账号信息〉
     * @return 业务处理结果
     * @author qingfeng
     */
    @CrmMcpTool("crm_connection_info")
    public Object connectionInfo() {
        return handler.connectionInfo();
    }

    /**
     * 功能描述:
     * 〈分页搜索当前账号可见客户〉
     * @param arguments MCP 工具参数
     * @return 业务处理结果
     * @author qingfeng
     */
    @CrmMcpTool("crm_search_customers")
    public Object searchCustomers(JSONObject arguments) {
        return handler.searchCustomers(arguments);
    }

    /**
     * 功能描述:
     * 〈获取当前账号可见的客户摘要〉
     * @param arguments MCP 工具参数
     * @return 业务处理结果
     * @author qingfeng
     */
    @CrmMcpTool("crm_get_customer")
    public Object getCustomer(JSONObject arguments) {
        return handler.getCustomer(arguments);
    }

    /**
     * 功能描述:
     * 〈获取指定客户的关联业务数据〉
     * @param arguments MCP 工具参数
     * @return 业务处理结果
     * @author qingfeng
     */
    @CrmMcpTool("crm_get_customer_relations")
    public Object getCustomerRelations(JSONObject arguments) {
        return handler.getCustomerRelations(arguments);
    }

    /**
     * 功能描述:
     * 〈预览或确认创建客户，未指定模块时使用默认客户模块〉
     * @param arguments MCP 工具参数
     * @return 业务处理结果
     * @author qingfeng
     */
    @CrmMcpTool(CrmMcpToolConst.CREATE_CUSTOMER)
    public Object createCustomer(JSONObject arguments) {
        return handler.createCustomer(arguments);
    }

    /**
     * 功能描述:
     * 〈预览或确认创建客户跟进记录〉
     * @param arguments MCP 工具参数
     * @return 业务处理结果
     * @author qingfeng
     */
    @CrmMcpTool("crm_create_customer_follow")
    public Object createCustomerFollow(JSONObject arguments) {
        return handler.createCustomerFollow(arguments);
    }
}
