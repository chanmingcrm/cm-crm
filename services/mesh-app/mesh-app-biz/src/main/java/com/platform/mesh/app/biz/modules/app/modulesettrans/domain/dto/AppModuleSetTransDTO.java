package com.platform.mesh.app.biz.modules.app.modulesettrans.domain.dto;

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
public class AppModuleSetTransDTO extends BaseDTO {


    /**
     * id
     */
    @Schema(description = "id")
    private Long id;


    /**
     * 模块来源ID
     */
    @Schema(description = "模块来源ID")
    private Long moduleFromId;


    /**
     * 模块搜索ID
     */
    @Schema(description = "模块搜索ID")
    private Long moduleSearchId;


    /**
     * 模块搜索字段
     */
    @Schema(description = "模块搜索字段")
    private String moduleSearchRelColumn;


    /**
     * 模块目标ID
     */
    @Schema(description = "模块目标ID")
    private Long moduleToId;



    /**
     * 规则字段标识
     */
    @Schema(description = "规则字段标识")
    private String ruleMac;


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