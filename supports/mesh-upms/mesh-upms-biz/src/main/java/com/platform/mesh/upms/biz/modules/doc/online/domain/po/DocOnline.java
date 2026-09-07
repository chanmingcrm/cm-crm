package com.platform.mesh.upms.biz.modules.doc.online.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 在线文档DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "doc_online", autoResultMap = true)
public class DocOnline extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 文档标识
    */
    private Integer docFlag;


    /**
    * 文档标题
    */
    private String docTitle;


    /**
    * 文档关键字
    */
    private String docKeyword;


    /**
    * 文档描述
    */
    private String docDesc;


    /**
    * 文档文本
    */
    private String docContext;


    /**
    * 移动端文档文本
    */
    private String docAppContext;


    /**
    * 星级
    */
    private Integer starLevel;


    /**
    * 发布标识
    */
    private Integer pubFlag;


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