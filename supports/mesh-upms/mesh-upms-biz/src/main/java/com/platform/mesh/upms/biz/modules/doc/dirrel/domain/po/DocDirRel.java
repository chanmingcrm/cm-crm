package com.platform.mesh.upms.biz.modules.doc.dirrel.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import com.platform.mesh.mybatis.plus.annotation.IgnoreDataScope;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 文件DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "doc_dir_rel", autoResultMap = true)
public class DocDirRel extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 目录ID
    */
    private Long dirId;


    /**
    * 文件ID
    */
    private Long fileId;


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