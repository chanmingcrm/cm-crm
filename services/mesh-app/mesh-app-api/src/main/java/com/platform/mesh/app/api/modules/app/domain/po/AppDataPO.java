package com.platform.mesh.app.api.modules.app.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 应用公共PO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class AppDataPO extends BasePO {

    
    /**
     * 主键ID
     */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 父模块ID
     */
    private Long parentModuleId;

    /**
     * 模块ID
     */
    private Long moduleId;

    /**
     * 新增表单ID
     */
    private Long addFormId;

    /**
     * 编辑表单ID
     */
    private Long editFormId;

    /**
     * 数据ID
     */
    private Long dataId;

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
     * 数据值
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Object dataValue;

    /**
     * 数据类型DataTypeEnum
     */
    private Integer dataType;

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

    /**
     * 用户ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long scopeUserId;

    /**
     * 组织ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long scopeOrgId;

}