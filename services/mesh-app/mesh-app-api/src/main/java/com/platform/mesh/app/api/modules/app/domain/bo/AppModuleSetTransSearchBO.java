package com.platform.mesh.app.api.modules.app.domain.bo;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 模块转化设置BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块转化设置检索BO")
public class AppModuleSetTransSearchBO extends PageDTO {

    /**
     * 来源模块
     */
    @Schema(description = "来源模块")
    private AppModuleBaseBO moduleFrom;


    /**
     * 查询模块
     */
    @Schema(description = "查询模块")
    private AppModuleBaseBO moduleSearch;

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
     * 规则数据类型
     */
    @Schema(description = "规则数据类型")
    private Integer ruleDataType;


    /**
     * 规则数据值
     */
    @Schema(description = "规则数据值")
    private String ruleDataValue;


}