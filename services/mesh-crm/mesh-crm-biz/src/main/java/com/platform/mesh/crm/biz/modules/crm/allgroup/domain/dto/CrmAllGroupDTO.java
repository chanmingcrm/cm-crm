package com.platform.mesh.crm.biz.modules.crm.allgroup.domain.dto;

import java.time.LocalDateTime;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 客户关系分组DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客户关系分组DTO")
public class CrmAllGroupDTO extends BaseDTO {



    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 父主键ID
     */
    @Schema(description = "父主键ID")
    private Long parentId;

    /**
     * 表单ID
     */
    @Schema(description = "表单ID")
    private Long moduleId;

    /**
     * 分组类型GroupTypeEnum
     */
    @Schema(description = "分组类型GroupTypeEnum")
    private Integer groupType;

    /**
     * 分组标识
     */
    @Schema(description = "分组标识")
    private String groupMac;

    /**
     * 分组名称
     */
    @Schema(description = "分组名称")
    private String groupName;

    /**
     * 分组描述
     */
    @Schema(description = "分组描述")
    private String groupDesc;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

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