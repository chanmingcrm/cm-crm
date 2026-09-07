package com.platform.mesh.ai.biz.modules.ai.mcp.tool.exception;

import com.platform.mesh.ai.biz.modules.ai.mcp.tool.constant.McpToolConst;
import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * @description MCP 工具异常枚举
 * @author 蝉鸣
 */
@Schema(description = "MCP 工具异常枚举", enumAsRef = true)
public enum McpToolExceptionEnum implements BaseExceptionEnum<McpToolExceptionEnum, String> {

    /**
     * MCP 工具提供方编码为空
     */
    MCP_MODULE_EMPTY(McpToolConst.MODULE, 500, null, "MCP 工具提供方编码不能为空"),

    /**
     * 远程 MCP 工具调用失败
     */
    MCP_REMOTE_INVOKE_FAILED(McpToolConst.MODULE, 503, null, "远程 MCP 工具调用失败"),

    /**
     * MCP 工具调用缺少交换上下文
     */
    MCP_EXCHANGE_CONTEXT_MISSING(McpToolConst.MODULE, 504, null, "MCP 工具调用缺少交换上下文"),

    /**
     * MCP 工具调用缺少可信租户身份
     */
    MCP_TRUSTED_IDENTITY_MISSING(McpToolConst.MODULE, 505, null, "MCP 工具调用缺少可信租户身份"),

    /**
     * 当前租户或账号无权调用 MCP 工具
     */
    MCP_TOOL_ACCESS_DENIED(McpToolConst.MODULE, 506, null, "当前租户或账号无权调用该 MCP 工具"),

    /**
     * MCP 工具名称为空或重复
     */
    MCP_TOOL_NAME_INVALID(McpToolConst.MODULE, 507, null, "MCP 工具名称为空或重复"),

    /**
     * MCP 工具提供方编码格式错误
     */
    MCP_MODULE_INVALID(McpToolConst.MODULE, 508, null, "MCP 工具提供方编码格式错误"),
    ;

    /**
     * 所属模块
     */
    private final String module;

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误码对应的参数
     */
    private final Object[] args;

    /**
     * 错误消息
     */
    private final String desc;

    McpToolExceptionEnum(String module, Integer code, Object[] args, String desc) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.desc = desc;
    }

    @Override
    public String getModule() {
        return module;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    @Override
    public String getDesc() {
        return desc;
    }

}
