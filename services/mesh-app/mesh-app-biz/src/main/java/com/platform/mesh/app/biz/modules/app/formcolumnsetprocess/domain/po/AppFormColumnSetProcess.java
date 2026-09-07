package com.platform.mesh.app.biz.modules.app.formcolumnsetprocess.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 字段事件实体对象
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "app_form_column_set_process", autoResultMap = true)
public class AppFormColumnSetProcess extends BasePO {


    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
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
     * 动作ID
     */
    private Long actionId;


    /**
     * 动作名称
     */
    private String actionName;


    /**
     * 事件Id
     */
    private Long eventId;


    /**
     * 事件Hash
     */
    private String eventHash;


    /**
     * 事件名称
     */
    private String eventName;


    /**
     * 事件类型
     */
    private String eventType;
    ;


    /**
     * 流程模板Id
     */
    private Long tempProcessId;


    /**
     * 流程模板版本
     */
    private String tempProcessVersion;

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