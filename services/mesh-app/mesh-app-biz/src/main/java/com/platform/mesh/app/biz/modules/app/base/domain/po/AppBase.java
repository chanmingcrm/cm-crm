package com.platform.mesh.app.biz.modules.app.base.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 应用DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "app_base", autoResultMap = true)
public class AppBase extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * APP类型AppTypeEnum
    */
    private Integer appType;


    /**
    * APP标识
    */
    private String appMac;


    /**
    * APP名称
    */
    private String appName;


    /**
    * APP描述
    */
    private String appDesc;


    /**
    * APPlogo
    */
    private String appLogo;


    /**
    * APP版本
    */
    private String appVersion;


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

}