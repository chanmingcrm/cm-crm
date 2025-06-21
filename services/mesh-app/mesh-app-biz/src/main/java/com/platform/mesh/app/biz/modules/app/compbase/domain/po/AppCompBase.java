package com.platform.mesh.app.biz.modules.app.compbase.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 页面组件DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "app_comp_base", autoResultMap = true)
public class AppCompBase extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 组件类型CompTypeEnum
    */
    private Integer compType;


    /**
    * 组件标识
    */
    private String compMac;


    /**
    * 组件名称
    */
    private String compName;


    /**
    * 组件图标
    */
    private String compSvg;


    /**
     * 是否初始化ES
     */
    private Integer esInit;


    /**
     * ES kind类型 {@link co.elastic.clients.elasticsearch._types.mapping.Property.Kind}
     */
    private String esKind;


    /**
    * 字段标识ColumnFlagEnum
    */
    private Integer columnFlag;


    /**
    * 初始标识InitFlagEnum
    */
    private Integer initFlag;


    /**
    * 隐藏标识HiddenFlagEnum
    */
    private Integer hiddenFlag;


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