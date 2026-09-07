package com.platform.mesh.crm.biz.mcp.constant;

/**
 * 功能描述:
 * 〈CRM MCP 工具常量〉
 *
 * @author qingfeng
 */
public final class CrmMcpToolConst {

    /** MCP 模块及工具名称。 */
    public static final String MODULE_NAME = "CRM MCP";
    public static final String CONNECTION_INFO = "crm_connection_info";
    public static final String SEARCH_CUSTOMERS = "crm_search_customers";
    public static final String GET_CUSTOMER = "crm_get_customer";
    public static final String GET_CUSTOMER_RELATIONS = "crm_get_customer_relations";
    public static final String GET_CUSTOMER_360 = "crm_get_customer_360";
    public static final String SEARCH_CUSTOMER_PRIORITIES = "crm_search_customer_priorities";
    public static final String CREATE_CUSTOMER = "crm_create_customer";
    public static final String CREATE_CUSTOMER_FOLLOW = "crm_create_customer_follow";
    public static final String SEARCH_CONTRACTS = "crm_search_contracts";
    public static final String GET_CONTRACT = "crm_get_contract";

    /** 通用查询参数。 */
    public static final String ARGUMENT_CUSTOMER_ID = "customerId";
    public static final String ARGUMENT_CONTRACT_ID = "contractId";
    public static final String ARGUMENT_KEYWORD = "keyword";
    public static final String ARGUMENT_PAGE = "page";
    public static final String ARGUMENT_PAGE_SIZE = "pageSize";
    public static final String ARGUMENT_CONTENT = "content";

    /** 客户及联系人创建参数。 */
    public static final String ARGUMENT_CUSTOMER_NAME = "customerName";
    public static final String ARGUMENT_CUSTOMER_DESCRIPTION = "customerDescription";
    public static final String ARGUMENT_PHONE = "phone";
    public static final String ARGUMENT_EMAIL = "email";
    public static final String ARGUMENT_ADDRESS = "address";
    public static final String ARGUMENT_CONTACT_NAME = "contactName";
    public static final String ARGUMENT_CONTACT_JOB_TITLE = "contactJobTitle";
    public static final String ARGUMENT_CONTACT_PHONE = "contactPhone";
    public static final String ARGUMENT_CONTACT_EMAIL = "contactEmail";
    public static final String ARGUMENT_CONTACT_ADDRESS = "contactAddress";
    public static final String ARGUMENT_MODULE_ID = "moduleId";

    /** 两阶段写入及经营分析参数。 */
    public static final String ARGUMENT_NEXT_TIME = "nextTime";
    public static final String ARGUMENT_CONFIRM = "confirm";
    public static final String ARGUMENT_CONFIRM_TOKEN = "confirmToken";
    public static final String ARGUMENT_INACTIVE_DAYS = "inactiveDays";
    public static final String ARGUMENT_ENDING_WITHIN_DAYS = "endingWithinDays";

    /** 默认值、结果字段及访问模式。 */
    public static final String EMPTY_ARGUMENTS = "{}";
    public static final String TRACE_ID = "traceId";
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 50;
    public static final String RESULT_ID = "id";
    public static final String RESULT_NAME = "name";
    public static final String RESULT_DESCRIPTION = "description";
    public static final String RESULT_UPDATED_AT = "updatedAt";
    public static final int MAX_RELATIONS = 20;
    public static final String ACCESS_MODE_READ_ONLY = "READ_ONLY";

    /**
     * 功能描述:
     * 〈禁止创建 CRM MCP 工具常量实例〉
     * @author qingfeng
     */
    private CrmMcpToolConst() {
    }
}
