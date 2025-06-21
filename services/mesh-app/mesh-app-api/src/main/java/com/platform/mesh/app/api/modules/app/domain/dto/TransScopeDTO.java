package com.platform.mesh.app.api.modules.app.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 转移数据所属DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="转移数据所属DTO")
public class TransScopeDTO extends BaseDTO {


    /**
     * 索引名称
     */
    @Schema(description = "索引名称")
    private String indexName;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private List<Long> ids;

    /**
     * 员工ID
     */
    @Schema(description = "员工ID")
    private Long memberId;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID",hidden = true)
    private Long scopeUserId;

    /**
     * 组织ID
     */
    @Schema(description = "组织ID")
    private Long scopeOrgId;

}