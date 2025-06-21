package com.platform.mesh.app.api.modules.app.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
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
     * 模块来源
     */
    @Schema(description = "模块来源")
    private AppModuleBaseBO moduleFrom;


    /**
     * 模块查询
     */
    @Schema(description = "模块查询")
    private AppModuleBaseBO moduleSearch;


    /**
     * 模块目标
     */
    @Schema(description = "模块目标")
    private AppModuleBaseBO moduleTo;


    /**
     * 模块搜索字段
     */
    @Schema(description = "模块搜索字段")
    private String moduleSearchRelColumn;


    /**
     * 规则字段标识
     */
    @Schema(description = "规则字段标识")
    private String ruleMac;


    /**
     * 规则数据类型
     */
    @Schema(description = "规则数据类型")
    private String roleDataType;


    /**
     * 规则数据值
     */
    @Schema(description = "规则数据值")
    private String roleDataValue;

    /**
     * 模块目标字段映射BO
     */
    @Schema(description = "模块目标字段映射BO")
    private List<AppModuleSetTransMappingBO> mappingBOList;
}