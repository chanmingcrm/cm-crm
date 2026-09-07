package com.platform.mesh.app.biz.modules.app.formbase.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 单DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "app_form_base", autoResultMap = true)
public class AppFormBase extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 模块ID
    */
    private Long moduleId;


    /**
    * 表单标识
    */
    private String formMac;


    /**
    * 表单名称
    */
    private String formName;


    /**
    * 表单类型FormTypeEnum
    */
    private Integer formType;


    /**
    * 表单请求地址
    */
    private String formUrl;


    /**
    * 表单logo
    */
    private String formLogo;


    /**
    * 表单排序
    */
    private Integer formSort;


    /**
    * 表单版本
    */
    private String formVersion;


    /**
    * 表单背景
    */
    private String styleBackground;


    /**
    * 表单css脚本
    */
    private String styleCss;


    /**
    * 默认标识YesOrNoEnum
    */
    private Integer defaultFlag;


    /**
    * 删除标识YesOrNoEnum
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

}