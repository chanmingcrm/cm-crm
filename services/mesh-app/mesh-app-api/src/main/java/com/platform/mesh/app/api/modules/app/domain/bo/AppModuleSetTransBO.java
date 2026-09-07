package com.platform.mesh.app.api.modules.app.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 模块转化设置BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块转化设置BO")
public class AppModuleSetTransBO extends BaseBO {


    /**
     * 转化配置ID
     */
    @Schema(description = "转化配置ID")
    private Long transId;

    /**
     * 模块来源
     */
    @Schema(description = "模块来源")
    private AppModuleBaseBO moduleFrom;


    /**
     * 模块目标
     */
    @Schema(description = "模块目标")
    private AppModuleBaseBO moduleTo;


    /**
     * 模块查询
     */
    @Schema(description = "模块查询")
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
    private String ruleDataType;


    /**
     * 规则数据值
     */
    @Schema(description = "规则数据值")
    private String ruleDataValue;


    /**
     * 模块目标字段映射BO
     */
    @Schema(description = "模块目标字段映射BO")
    private List<AppModuleSetTransMappingBO> mappingBOList;

    /**
     * 模块目标分配BO
     */
    @Schema(description = "模块目标分配BO")
    private List<AppModuleSetTransPickBO> pickBOList;

}