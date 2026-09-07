package com.platform.mesh.ai.biz.modules.ai.mcp.tool.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description MCP工具目录PO
 * @author qingfeng
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "ai_mcp_tool", autoResultMap = true)
public class AiMcpTool extends BasePO {

    /** 主键ID */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 工具提供方编码 */
    private String mcpModule;

    /** MCP工具名称 */
    private String toolName;

    /** MCP工具说明 */
    private String description;

    /** 输入参数JSON Schema */
    private String inputSchema;

    /** 工具可信JSON配置 */
    private String executionConfig;

    /** 是否只读：1是，0否 */
    private Integer readOnly;

    /** 是否直接返回工具结果：1是，0否 */
    private Integer returnDirect;

    /** 状态：1启用，0停用 */
    private Integer status;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 修改时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
