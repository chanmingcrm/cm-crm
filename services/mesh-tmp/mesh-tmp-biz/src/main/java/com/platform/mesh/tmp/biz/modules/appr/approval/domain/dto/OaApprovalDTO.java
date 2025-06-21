package com.platform.mesh.tmp.biz.modules.appr.approval.domain.dto;

import java.time.LocalDateTime;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description OA办公审批DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="OA办公审批DTO")
public class OaApprovalDTO extends BaseDTO {


    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 模块ID
     */
    @Schema(description = "父模块ID")
    private Long parentModuleId;

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     * 商机类型BusinessTypeEnum
     */
    @Schema(description = "商机类型BusinessTypeEnum")
    private Integer dataType;

    /**
     * 商机标识
     */
    @Schema(description = "商机标识")
    private String dataMac;

    /**
     * 商机名称
     */
    @Schema(description = "商机名称")
    private String dataName;

    /**
     * 商机描述
     */
    @Schema(description = "商机描述")
    private String dataDesc;

    /**
     * 商机期数
     */
    @Schema(description = "商机期数")
    private Integer dataPeriod;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    private Long createUserId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @Schema(description = "修改人ID")
    private Long updateUserId;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

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