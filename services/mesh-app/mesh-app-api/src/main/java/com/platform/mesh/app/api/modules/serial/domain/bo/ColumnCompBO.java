package com.platform.mesh.app.api.modules.serial.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
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
public class ColumnCompBO extends BaseBO {


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
     * 默认值数据类型
     */
    @Schema(description = "关联数据类型标识DataTypeEnum")
    private Integer defaultDataType;


    /**
     * 默认值
     */
    @Schema(description = "默认值")
    private Object defaultDataValue;


    /**
     * 关联数据类型标识DataTypeEnum
     */
    @Schema(description = "关联数据类型标识DataTypeEnum")
    private Integer setDataType;


    /**
     * 关联数据值
     */
    @Schema(description = "关联数据值")
    private Object setDataValue;


    /**
     * 关联数据类型标识DataTypeEnum
     */
    @Schema(description = "关联数据类型标识DataTypeEnum")
    private Integer relDataType;


    /**
     * 关联数据值
     */
    @Schema(description = "关联数据值")
    private Object relDataValue;


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

}