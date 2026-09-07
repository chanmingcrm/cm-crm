package com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description AIMcpDTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AIMcpDTO")
public class AiMcpServerDTO extends BaseDTO {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 服务类型：新增和修改外部连接时由服务端固定为租户外部 MCP 服务
     */
    @Schema(description = "服务类型：1平台 MCP 服务，2租户外部 MCP 服务", accessMode = Schema.AccessMode.READ_ONLY)
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
     * JSON 格式请求头，仅写入不回显
     */
    @Schema(description = "JSON 格式请求头，仅写入不回显")
    private String requestHeaders;

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
