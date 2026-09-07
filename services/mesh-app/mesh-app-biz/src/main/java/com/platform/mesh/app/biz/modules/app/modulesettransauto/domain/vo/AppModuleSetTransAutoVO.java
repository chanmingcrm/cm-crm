package com.platform.mesh.app.biz.modules.app.modulesettransauto.domain.vo;

import com.platform.mesh.app.biz.modules.app.modulebase.domain.vo.AppModuleBaseVO;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 模块转化自动化设置VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块转化自动化设置VO")
public class AppModuleSetTransAutoVO extends BaseVO {


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
     * 模块来源ID
     */
    @Schema(description = "模块来源")
    private AppModuleBaseVO moduleFrom;


    /**
     * 模块查询
     */
    @Schema(description = "模块查询")
    private AppModuleBaseVO moduleSearch;


    /**
     * 模块目标
     */
    @Schema(description = "模块目标")
    private AppModuleBaseVO moduleTo;


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