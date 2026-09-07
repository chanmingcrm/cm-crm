package com.platform.mesh.crm.biz.mcp.tool.crm.impl;

import cn.hutool.json.JSONObject;
import com.platform.mesh.crm.biz.mcp.tool.crm.manual.ContractMcpManual;
import com.platform.mesh.crm.biz.mcp.tool.factory.CrmMcpTool;
import org.springframework.stereotype.Service;

/**
 * 功能描述:
 * 〈提供合同场景的 MCP 工具入口〉
 * @author qingfeng
 */
@Service
public class ContractMcpTool {

    private final ContractMcpManual handler;

    /**
     * 功能描述:
     * 〈提供合同场景的 MCP 工具入口〉
     * @param handler 对应场景的业务处理器
     * @author qingfeng
     */
    public ContractMcpTool(ContractMcpManual handler) {
        this.handler = handler;
    }

    /**
     * 功能描述:
     * 〈查询即将到期的合同列表〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    @CrmMcpTool("crm_list_expiring_contracts")
    public Object listExpiringContracts(JSONObject arguments, JSONObject executionConfig) {
        return handler.listExpiringContracts(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈查询合同异常并生成处理建议〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    @CrmMcpTool("crm_list_contract_exceptions")
    public Object listContractExceptions(JSONObject arguments, JSONObject executionConfig) {
        return handler.listContractExceptions(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈分页查询当前账号可见合同〉
     * @param arguments MCP 工具参数
     * @param executionConfig 数据库维护的工具执行配置
     * @return 业务处理结果
     * @author qingfeng
     */
    @CrmMcpTool("crm_search_contracts")
    public Object searchContracts(JSONObject arguments, JSONObject executionConfig) {
        return handler.searchContracts(arguments, executionConfig);
    }

    /**
     * 功能描述:
     * 〈获取当前账号可见的合同详情〉
     * @param arguments MCP 工具参数
     * @return 业务处理结果
     * @author qingfeng
     */
    @CrmMcpTool("crm_get_contract")
    public Object getContract(JSONObject arguments) {
        return handler.getContract(arguments);
    }
}