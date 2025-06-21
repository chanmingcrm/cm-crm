package com.platform.mesh.app.api.modules.app.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 应用公共DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="应用公共DTO")
public class AppDTO extends BaseDTO {

    
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
     * 数据类型
     */
    @Schema(description = "数据类型")
    private Integer dataType;

    /**
     * 数据标识
     */
    @Schema(description = "数据标识")
    private String dataMac;

    /**
     * 数据名称
     */
    @Schema(description = "数据名称")
    private String dataName;

    /**
     * 数据描述
     */
    @Schema(description = "数据描述")
    private String dataDesc;

    /**
     * 数据期数
     */
    @Schema(description = "数据期数")
    private Integer dataPeriod;

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