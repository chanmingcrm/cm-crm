package com.platform.mesh.app.biz.modules.app.formcolumnsetting.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 单字段配置DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "app_form_column_setting", autoResultMap = true)
public class AppFormColumnSetting extends BasePO {


    /**
    * ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 模块ID
    */
    private Long moduleId;


    /**
    * 表单ID
    */
    private Long formId;


    /**
    * 事件ID
    */
    private Long eventId;


    /**
    * 字段ID
    */
    private Long columnId;


    /**
    * 字段标识
    */
    private String columnMac;


    /**
    * 字段名称
    */
    private String columnName;


    /**
    * 字段类型ColumnTypeEnum
    */
    private Integer columnType;


    /**
    * 默认值
    */
    private String defaultValue;


    /**
    * 关联数据类型标识DataTypeEnum
    */
    private Integer relDataType;


    /**
    * 关联数据值
    */
    private String relDataValue;


    /**
    * 关联数据转换标识DataTypeEnum
    */
    private Integer relTransDataType;


    /**
    * 关联数据转换公式
    */
    private String relDataTransFormula;


    /**
    * 关联数据转换目标值
    */
    private String relTransDataValue;


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