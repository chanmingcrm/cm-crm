package com.platform.mesh.app.biz.modules.app.formcolumnsetaction.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 单字段动作DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "app_form_column_set_action", autoResultMap = true)
public class AppFormColumnSetAction extends BasePO {


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
    * 组件类型
    */
    private Integer compType;


    /**
    * 组件Mac
    */
    private String compMac;


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
    * 动作Hash
    */
    private String actionHash;


    /**
    * 动作名称
    */
    private String actionName;


    /**
    * 动作类型
    */
    private String actionType;


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