package com.platform.mesh.app.biz.modules.app.formcolumnrole.domain.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 表单字段权限DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "app_form_column_role", autoResultMap = true)
public class AppFormColumnRole extends BasePO {

    /**
     * 字段ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 父ID
     */
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
     * 组件ID
     */
    private Long columnId;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 批次ID
     */
    private Long batchId;

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
