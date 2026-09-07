package com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 第三方字段字段映射设置DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "third_form_column_mapping", autoResultMap = true)
public class ThirdFormColumnMapping extends BasePO {


    /**
    * ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
     * 字段来源
     */
    private Integer sourceFlag;


    /**
     * 第三方字段标识
     */
    private String relColumnMac;


    /**
     * 第三方字段名称
     */
    private String relColumnName;


    /**
     * 模块Id
     */
    private Long moduleId;


    /**
     * 表单Id
     */
    private Long formId;


    /**
     * 系统字段Id
     */
    private Long columnId;


    /**
     * 系统字段标识
     */
    private String columnMac;


    /**
     * 系统字段名称
     */
    private String columnName;


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

}
