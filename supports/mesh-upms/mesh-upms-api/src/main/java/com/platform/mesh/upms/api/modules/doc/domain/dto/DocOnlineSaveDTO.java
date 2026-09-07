package com.platform.mesh.upms.api.modules.doc.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 在线文档保存DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="在线文档保存DTO")
public class DocOnlineSaveDTO extends BaseDTO {

    /**
     * 文档标题
     */
    @Schema(description = "文档标题")
    private String title;

    /**
     * 文档关键字
     */
    @Schema(description = "文档关键字")
    private String keyword;

    /**
     * 文档描述
     */
    @Schema(description = "文档描述")
    private String desc;

    /**
     * 文档文本
     */
    @Schema(description = "文档文本")
    private String contentPc;

    /**
     * 移动端文档文本
     */
    @Schema(description = "移动端文档文本")
    private String contentApp;

    /**
     * 文档ID
     */
    @Schema(description = "文档ID")
    private Long dirId;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

}
