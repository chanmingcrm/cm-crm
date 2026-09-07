package com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description AIMcpVO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AIMcpVO")
public class AiMcpServerVO extends BaseVO {


    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 数据所属租户，0 表示系统数据
     */
    @Schema(description = "数据所属租户，0 表示系统数据")
    private Long tenantId;

    /**
     * 服务类型：1平台 MCP 服务，2租户外部 MCP 服务
     */
    @Schema(description = "服务类型：1平台 MCP 服务，2租户外部 MCP 服务")
    private Integer serverType;

    /**
     * MCP服务名称
     */
    @Schema(description = "MCP服务名称")
    private String mcpName;

    /**
     * 请求类型
     */
    @Schema(description = "请求类型")
    private String mcpType;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String mcpDes;

    /**
     * MCP 服务完整地址
     */
    @Schema(description = "MCP 服务完整地址")
    private String serverUrl;

    /**
     * 连接超时秒数
     */
    @Schema(description = "连接超时秒数")
    private Integer connectTimeoutSeconds;

    /**
     * 请求超时秒数
     */
    @Schema(description = "请求超时秒数")
    private Integer requestTimeoutSeconds;

    /**
     * 是否启用
     */
    @Schema(description = "是否启用")
    private Integer statusFlag;

}
