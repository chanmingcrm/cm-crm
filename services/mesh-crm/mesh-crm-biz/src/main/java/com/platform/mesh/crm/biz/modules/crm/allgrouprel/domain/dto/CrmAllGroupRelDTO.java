package com.platform.mesh.crm.biz.modules.crm.allgrouprel.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 客户关系分组关联DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客户关系分组关联DTO")
public class CrmAllGroupRelDTO extends BaseDTO {



    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     * 分组ID
     */
    @Schema(description = "分组ID")
    private Long groupId;

    /**
     * 分组类型GroupTypeEnum
     */
    @Schema(description = "分组类型GroupTypeEnum")
    private Integer groupType;

    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long dataId;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long scopeUserId;

    /**
     * 组织ID
     */
    @Schema(description = "组织ID")
    private Long scopeOrgId;

}