package com.platform.mesh.upms.biz.modules.doc.file.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
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
@TableName(value = "doc_file", autoResultMap = true)
public class DocFile extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 文件标识
    */
    private Integer fileFlag;


    /**
    * 文件编码
    */
    private String fileMac;


    /**
    * 文件名称
    */
    private String fileName;


    /**
    * 文件别名
    */
    private String fileAlias;


    /**
    * 文件类型
    */
    private String fileType;


    /**
    * 文件来源
    */
    private String fileSource;


    /**
     * 文件路径
     */
    private String fileEndpoint;


    /**
     * 文件桶
     */
    private String fileBucket;


    /**
     * 文件地址
     */
    private String fileAddr;


    /**
    * 文件批次
    */
    private String fileBatch;


    /**
    * 文件大小
    */
    private Integer fileSize;


    /**
    * 新增标识
    */
    private Integer addFlag;


    /**
    * 开放标识
    */
    private Integer openFlag;


    /**
    * 模块ID
    */
    private Long moduleId;


    /**
    * 数据ID
    */
    private Long dataId;


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