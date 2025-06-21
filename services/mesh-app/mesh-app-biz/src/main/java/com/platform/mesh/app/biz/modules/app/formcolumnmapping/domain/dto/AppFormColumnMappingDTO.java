package com.platform.mesh.app.biz.modules.app.formcolumnmapping.domain.dto;

import com.platform.mesh.app.api.modules.app.enums.comp.CompMacEnum;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 单字段映射DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="单字段映射DTO")
public class AppFormColumnMappingDTO extends BaseDTO {



    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;


    /**
     * 表单ID
     */
    @Schema(description = "表单ID")
    private Long formId;


    /**
     * 事件ID
     */
    @Schema(description = "事件ID")
    private Long eventId;


    /**
     * 字段ID
     */
    @Schema(description = "字段ID")
    private Long columnId;


    /**
     * 字段标识
     */
    @Schema(description = "字段标识")
    private String columnMac;


    /**
     * 字段名称
     */
    @Schema(description = "字段名称")
    private String columnName;


    /**
     * 字段类型ColumnTypeEnum
     */
    @SchemaEnum(value = CompMacEnum.class,description = "字段类型ColumnTypeEnum")
    private Integer columnType;


    /**
     * 默认值
     */
    @Schema(description = "默认值")
    private String defaultValue;


    /**
     * 关联数据类型标识DataTypeEnum
     */
    @Schema(description = "关联数据类型标识DataTypeEnum")
    private Integer relDataType;


    /**
     * 关联数据值
     */
    @Schema(description = "关联数据值")
    private String relDataValue;


    /**
     * 关联数据转换标识DataTypeEnum
     */
    @Schema(description = "关联数据转换标识DataTypeEnum")
    private Integer relTransDataType;


    /**
     * 关联数据转换公式
     */
    @Schema(description = "关联数据转换公式")
    private String relDataTransFormula;


    /**
     * 关联数据转换目标值
     */
    @Schema(description = "关联数据转换目标值")
    private String relTransDataValue;


}