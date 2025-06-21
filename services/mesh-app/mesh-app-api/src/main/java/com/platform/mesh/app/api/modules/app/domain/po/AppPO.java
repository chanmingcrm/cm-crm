package com.platform.mesh.app.api.modules.app.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 应用公共PO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class AppPO extends BasePO {


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
    * 数据类型
    */
    private Integer dataType;

    /**
    * 数据标识
    */
    private String dataMac;

    /**
    * 数据名称
    */
    private String dataName;

    /**
    * 数据描述
    */
    private String dataDesc;

    /**
     * 数据序列号
     */
    private Integer dataSerial;

    /**
    * 数据期数
    */
    private Integer dataPeriod;

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