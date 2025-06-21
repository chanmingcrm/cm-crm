package com.platform.mesh.app.biz.modules.app.base.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 应用DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="应用DTO")
public class AppBaseDTO extends BaseDTO {



    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;


    /**
     * APP类型AppTypeEnum
     */
    @Schema(description = "APP类型AppTypeEnum")
    private Integer appType;


    /**
     * APP标识
     */
    @Schema(description = "APP标识")
    private String appMac;


    /**
     * APP名称
     */
    @Schema(description = "APP名称")
    private String appName;


    /**
     * APP描述
     */
    @Schema(description = "APP描述")
    private String appDesc;


    /**
     * APPlogo
     */
    @Schema(description = "APPlogo")
    private String appLogo;


    /**
     * APP版本
     */
    @Schema(description = "APP版本")
    private String appVersion;


}