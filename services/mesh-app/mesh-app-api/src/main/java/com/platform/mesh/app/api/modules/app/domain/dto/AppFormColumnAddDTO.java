package com.platform.mesh.app.api.modules.app.domain.dto;

import com.platform.mesh.app.api.modules.app.enums.comp.CompMacEnum;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 单字段关联VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="表单字段关联BO")
public class AppFormColumnAddDTO extends BaseVO {

    /**
     * 字段ID
     */
    @Schema(description = "字段ID")
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
     * 组件类型
     */
    @Schema(description = "组件类型")
    private Integer compType;


    /**
     * 组件标识
     */
    @Schema(description = "组件标识")
    private String compMac;


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
     * 字段描述
     */
    @Schema(description = "字段描述")
    private String columnDesc;


    /**
     * 字段类型ColumnTypeEnum
     */
    @SchemaEnum(value = CompMacEnum.class,description = "字段类型ColumnTypeEnum")
    private Integer columnType;


    /**
     * 前缀值
     */
    @Schema(description = "前缀值")
    private String prefixValue;


    /**
     * 后缀值
     */
    @Schema(description = "后缀值")
    private String suffixValue;


    /**
     * 默认值
     */
    @Schema(description = "默认值")
    private Object defaultValue;


    /**
     * 默认值数据类型
     */
    @Schema(description = "默认值数据类型")
    private Integer defaultDataType;


    /**
     * 最大值
     */
    @Schema(description = "最大值")
    private Integer maxValue;


    /**
     * 最小值
     */
    @Schema(description = "最小值")
    private Integer minValue;


    /**
     * 精度值
     */
    @Schema(description = "精度值")
    private Integer degreeValue;


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
     * 关联数据转换目标值
     */
    @Schema(description = "关联数据转换目标值")
    private String relTransDataValue;


    /**
     * 唯一标识UniqueFlagEnum
     */
    @Schema(description = "唯一标识UniqueFlagEnum")
    private Integer uniqueFlag;


    /**
     * 字段值
     */
    @Schema(description = "字段值")
    private Object value;

}