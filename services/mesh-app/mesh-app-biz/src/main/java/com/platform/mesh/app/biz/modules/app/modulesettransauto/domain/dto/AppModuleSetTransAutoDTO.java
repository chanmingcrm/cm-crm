package com.platform.mesh.app.biz.modules.app.modulesettransauto.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 模块转化设置DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块转化设置DTO")
public class AppModuleSetTransAutoDTO extends BaseDTO {


    /**
     * id
     */
    @Schema(description = "id")
    private Long id;


    /**
     * 转化配置ID
     */
    @Schema(description = "转化配置ID")
    private Long transId;


    /**
     * 模块搜索ID
     */
    @Schema(description = "模块搜索ID")
    private Long moduleSearchId;


    /**
     * 模块搜索字段
     */
    @Schema(description = "模块搜索字段")
    private String columnMac;


    /**
     * 规则字段标识
     */
    @Schema(description = "规则字段标识")
    private String ruleMac;


    /**
     * 规则字段标识名称
     */
    @Schema(description = "规则字段标识名称")
    private String ruleName;


    /**
     * 规则数据类型
     */
    @Schema(description = "规则数据类型")
    private String ruleDataType;


    /**
     * 规则数据值
     */
    @Schema(description = "规则数据值")
    private String ruleDataValue;

}