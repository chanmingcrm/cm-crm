package com.platform.mesh.bpm.biz.data.form.domain.po;


import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 数据表单绑定DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("bpm_data_form_rel")
public class BpmDataFormRel extends BasePO {

    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 模块ID
     */
    private Long moduleId;

    /**
     * 父模块ID
     */
    private Long parentModuleId;

    /**
     * 表单ID
     */
    private Long formId;

    /**
     * 顶层流程模板ID
     */
    private Long tempProcessId;

    /**
     * 流程模板名称
     */
    private String processName;

    /**
     * 流程模板ID
     */
    private String processVersion;

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

