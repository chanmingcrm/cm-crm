package com.platform.mesh.upms.biz.modules.doc.dir.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import com.platform.mesh.mybatis.plus.annotation.IgnoreDataScope;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 文件目录DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "doc_dir", autoResultMap = true)
public class DocDir extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 父ID
    */
    private Long parentId;


    /**
    * 模块ID
    */
    private Long moduleId;


    /**
    * 数据ID
    */
    private Long dataId;


    /**
    * 目录标识
    */
    private Integer dirFlag;


    /**
    * 目录编码
    */
    private String dirMac;


    /**
    * 目录名称
    */
    private String dirName;


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