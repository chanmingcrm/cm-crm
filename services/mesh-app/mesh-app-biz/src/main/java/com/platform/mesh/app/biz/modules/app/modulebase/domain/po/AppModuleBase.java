package com.platform.mesh.app.biz.modules.app.modulebase.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import com.platform.mesh.mybatis.plus.annotation.IgnoreDataScope;
import com.platform.mesh.mybatis.plus.annotation.TableParentId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 模块DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "app_module_base", autoResultMap = true)
public class AppModuleBase extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 应用ID
    */
    private Long appId;


    /**
    * 父ID
    */
    @TableParentId(value = "parent_id")
    private Long parentId;


    /**
    * 模块类型ModuleTypeEnum
    */
    private Integer moduleType;


    /**
    * 模块标识
    */
    private String moduleMac;


    /**
    * 模块名称
    */
    private String moduleName;


    /**
    * 模块存储
    */
    private String moduleSchema;


    /**
    * 模块索引
    */
    private String moduleIndex;


    /**
    * 模块描述
    */
    private String moduleDesc;


    /**
    * 模块logo
    */
    private String moduleLogo;


    /**
    * 模块版本
    */
    private String moduleVersion;


    /**
    * 初始化ES
    */
    private Integer initEsFlag;


    /**
     * 开放标识
     */
    private Integer openFlag;


    /**
     * 删除标识
     */
    private Integer delFlag;

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
    @IgnoreDataScope()
    @TableField(fill = FieldFill.INSERT)
    private Long scopeUserId;

    /**
     * 组织ID
     */
    @IgnoreDataScope()
    @TableField(fill = FieldFill.INSERT)
    private Long scopeOrgId;

}