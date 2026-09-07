package com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import com.platform.mesh.mybatis.plus.annotation.IgnoreDataScope;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 外部MCP服务配置PO
 * @author qingfeng
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "ai_mcp_server", autoResultMap = true)
@IgnoreDataScope
public class AiMcpServer extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 服务类型：1平台 MCP 服务，2租户外部 MCP 服务
     */
    private Integer serverType;

    /**
    * 外部MCP服务名称
    */
    private String mcpName;

    /**
    * MCP传输类型
    */
    private String mcpType;

    /**
    * 外部MCP服务描述
    */
    private String mcpDes;

    /**
    * JSON格式请求头，仅服务端使用
    */
    private String requestHeaders;

    /**
    * MCP 服务完整地址
    */
    private String serverUrl;

    /**
     * 连接超时秒数
     */
    private Integer connectTimeoutSeconds;

    /**
     * 请求超时秒数
     */
    private Integer requestTimeoutSeconds;

    /**
    * 状态：1启用，0停用
    */
    private Integer statusFlag;

    /**
    * 删除标识：1正常，0删除
    */
    private Integer delFlag;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 租户ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long tenantId;

}
