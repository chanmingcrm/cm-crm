package com.platform.mesh.app.api.modules.app.domain.bo;

import com.platform.mesh.app.api.modules.app.enums.comp.ModuleTypeEnum;
import com.platform.mesh.core.application.domain.bo.BaseBO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 模块VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块BO")
public class AppModuleBaseBO extends BaseBO {



    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;


    /**
     * 应用ID
     */
    @Schema(description = "应用ID")
    private Long appId;


    /**
     * 父ID
     */
    @Schema(description = "父ID")
    private Long parentId;


    /**
     * 模块类型ModuleTypeEnum
     */
    @SchemaEnum(value = ModuleTypeEnum.class,description = "模块类型")
    private Integer moduleType;


    /**
     * 模块标识
     */
    @Schema(description = "模块标识")
    private String moduleMac;


    /**
     * 模块名称
     */
    @Schema(description = "模块名称")
    private String moduleName;


    /**
     * 模块存储
     */
    @Schema(description = "模块存储")
    private String moduleSchema;


    /**
     * 模块索引
     */
    @Schema(description = "模块索引")
    private String moduleIndex;

    /**
     * 模块描述
     */
    @Schema(description = "模块描述")
    private String moduleDesc;


    /**
     * 模块logo
     */
    @Schema(description = "模块logo")
    private String moduleLogo;


    /**
     * 模块版本
     */
    @Schema(description = "模块版本")
    private String moduleVersion;


    /**
     * 初始化ES
     */
    @SchemaEnum(value = YesOrNoEnum.class,description = "初始化ES")
    private Integer initEsFlag;

    /**
     * 开放标识
     */
    @SchemaEnum(value = YesOrNoEnum.class,description = "开放标识")
    private Integer openFlag;
}