package com.platform.mesh.app.biz.modules.app.formcolumn.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.platform.mesh.core.application.domain.po.BasePO;
import com.platform.mesh.mybatis.plus.annotation.TableParentId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 单字段关联DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "app_form_column", autoResultMap = true)
public class AppFormColumn extends BasePO {


    /**
    * 字段ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 字段ID
     */
    @TableParentId(value = "parent_id")
    private Long parentId;

    /**
    * 模块ID
    */
    private Long moduleId;


    /**
    * 表单ID
    */
    private Long formId;


    /**
    * 组件类型
    */
    private Integer compType;


    /**
    * 组件标识
    */
    private String compMac;


    /**
    * 是否初始化ES
    */
    private Integer esInit;


    /**
    * ES kind类型 {@link co.elastic.clients.elasticsearch._types.mapping.Property.Kind}
    */
    private String esKind;


    /**
    * 字段hash
    */
    private String columnHash;


    /**
    * 字段标识
    */
    private String columnMac;


    /**
    * 字段名称
    */
    private String columnName;


    /**
    * 字段描述
    */
    private String columnDesc;


    /**
    * 字段类型ColumnTypeEnum
    */
    private Integer columnType;


    /**
    * 输入提示
    */
    private String columnTips;


    /**
    * 前缀值
    */
    private String prefixValue;


    /**
    * 后缀值
    */
    private String suffixValue;


    /**
    * 最大值
    */
    private Integer maxValue;


    /**
    * 最小值
    */
    private Integer minValue;


    /**
    * 精度值
    */
    private Integer degreeValue;


    /**
     * 默认值数据类型
     */
    private Integer defaultDataType;


    /**
     * 默认值
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Object defaultDataValue;


    /**
    * 关联数据类型标识DataTypeEnum
    */
    private Integer setDataType;


    /**
    * 配置数据值
    */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Object setDataValue;


    /**
    * 关联数据类型标识DataTypeEnum
    */
    private Integer relDataType;


    /**
    * 关联数据值
    */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Object relDataValue;


    /**
    * 关联数据转换标识DataTypeEnum
    */
    private Integer relTransDataType;


    /**
    * 关联数据转换目标值
    */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Object relTransDataValue;


    /**
    * 唯一标识UniqueFlagEnum
    */
    private Integer uniqueFlag;


    /**
    * 空值标识EmptyFlagEnum
    */
    private Integer emptyFlag;


    /**
    * 多选标识MutiFlag
    */
    private Integer multiFlag;


    /**
    * 隐藏标识HiddenFlagEnum
    */
    private Integer hiddenFlag;


    /**
    * 可修改标识YesOrNoEnum
    */
    private Integer editFlag;


    /**
    * 删除标识YesOrNoEnum
    */
    private Integer deleteFlag;


    /**
    * 横坐标
    */
    private Integer xAddr;


    /**
    * 纵坐标
    */
    private Integer yAddr;


    /**
    * 样式百分比%
    */
    private Integer stylePercent;


    /**
    * 样式脚本
    */
    private String styleCss;


    /**
    * 样式svg
    */
    private String styleSvg;


    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}