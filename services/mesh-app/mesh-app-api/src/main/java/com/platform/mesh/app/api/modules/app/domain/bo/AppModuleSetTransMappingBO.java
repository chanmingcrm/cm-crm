package com.platform.mesh.app.api.modules.app.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 模块转化设置字段映射BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块转化设置字段映射BO")
public class AppModuleSetTransMappingBO extends BaseBO {

    /**
     * 转化设置ID
     */
    @Schema(description = "转化设置ID")
    private Long transId;


    /**
     * 来源模块ID
     */
    @Schema(description = "来源模块ID")
    private Long fromModuleId;


    /**
     * 来源组件标识
     */
    @Schema(description = "来源组件标识")
    private String fromCompMac;


    /**
     * 来源字段标识
     */
    @Schema(description = "来源字段标识")
    private String fromColumnMac;


    /**
     * 目标模块ID
     */
    @Schema(description = "目标模块ID")
    private Long toModuleId;


    /**
     * 目标组件标识
     */
    @Schema(description = "目标组件标识")
    private String toCompMac;


    /**
     * 目标字段标识
     */
    @Schema(description = "目标字段标识")
    private String toColumnMac;
}