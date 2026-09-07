package com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 功能描述:
 * 〈统一维护 AI MCP Access Key 业务异常编码和描述〉
 *
 * @author qingfeng
 */
@Schema(description = "AI MCP Access Key 异常枚举", enumAsRef = true)
public enum AiMcpAccessKeyExceptionEnum
        implements BaseExceptionEnum<AiMcpAccessKeyExceptionEnum, String> {

    CREATE_FAILED("ai-mcp-access-key", 500, null, "创建 MCP 访问密钥失败"),
    UPDATE_LAST_USED_FAILED("ai-mcp-access-key", 501, null, "更新 MCP 访问密钥使用时间失败"),
    TENANT_ADMIN_REQUIRED("ai-mcp-access-key", 502, null, "仅租户管理员可以执行该操作"),
    IDENTITY_INVALID("ai-mcp-access-key", 503, null, "AI MCP Access Key 身份无效"),
    ACCOUNT_TENANT_UNAVAILABLE("ai-mcp-access-key", 504, null, "账号租户绑定服务不可用"),
    ACCOUNT_TENANT_INACTIVE("ai-mcp-access-key", 505, null, "账号租户绑定未生效"),
    RATE_LIMIT_EXCEEDED("ai-mcp-access-key", 506, null, "AI MCP Access Key 请求频率超限"),
    HASH_ALGORITHM_UNAVAILABLE("ai-mcp-access-key", 507, null, "当前运行环境不支持 SHA-256 算法"),
    UNAUTHORIZED("ai-mcp-access-key", 508, null, "MCP 访问密钥无效");

    /** 异常所属模块。 */
    private final String module;
    /** 模块内异常编码。 */
    private final Integer code;
    /** 国际化消息参数。 */
    private final Object[] args;
    /** 默认异常描述。 */
    private final String desc;

    /**
     * 功能描述:
     * 〈创建 AI MCP Access Key 业务异常定义〉
     * @param module 异常所属模块
     * @param code 模块内异常编码
     * @param args 国际化消息参数
     * @param desc 默认异常描述
     * @author qingfeng
     */
    AiMcpAccessKeyExceptionEnum(String module, Integer code, Object[] args, String desc) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.desc = desc;
    }

    /**
     * 功能描述:
     * 〈获取异常所属模块〉
     * @return 异常所属模块
     * @author qingfeng
     */
    @Override
    public String getModule() {
        return module;
    }

    /**
     * 功能描述:
     * 〈获取模块内异常编码〉
     * @return 异常编码
     * @author qingfeng
     */
    @Override
    public Integer getCode() {
        return code;
    }

    /**
     * 功能描述:
     * 〈获取国际化消息参数〉
     * @return 国际化消息参数
     * @author qingfeng
     */
    @Override
    public Object[] getArgs() {
        return args;
    }

    /**
     * 功能描述:
     * 〈获取默认异常描述〉
     * @return 默认异常描述
     * @author qingfeng
     */
    @Override
    public String getDesc() {
        return desc;
    }
}
