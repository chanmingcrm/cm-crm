package com.platform.mesh.app.api.modules.app.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.conf.enums.ConfSourceEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 第三方段映射设置BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="第三方段映射设置BO")
public class ThirdFormColumnMappingBO extends BaseBO {


    /**
     * 字段来源
     */
    @SchemaEnum(value = ConfSourceEnum.class, description = "字段来源")
    private Integer sourceFlag;


    /**
     * 第三方字段标识
     */
    @Schema(description = "第三方字段标识")
    private String relColumnMac;


    /**
     * 第三方字段名称
     */
    @Schema(description = "第三方字段名称")
    private String relColumnName;


    /**
     * 模块Id
     */
    @Schema(description = "模块Id")
    private Long moduleId;


    /**
     * 表单Id
     */
    @Schema(description = "表单Id")
    private Long formId;


    /**
     * 系统组件标识
     */
    @Schema(description = "系统组件标识")
    private String compMac;


    /**
     * 系统字段标识
     */
    @Schema(description = "系统字段标识")
    private String columnMac;


    /**
     * 系统字段名称
     */
    @Schema(description = "系统字段名称")
    private String columnName;

}