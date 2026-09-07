package com.platform.mesh.app.biz.modules.app.modulesettransmapping.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 模块转化字段映射设置DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "app_module_set_trans_mapping", autoResultMap = true)
public class AppModuleSetTransMapping extends BasePO {


    /**
    * ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 转化ID
    */
    private Long transId;


    /**
    * 来源模块ID
    */
    private Long fromModuleId;


    /**
    * 来源组件标识
    */
    private String fromCompMac;


    /**
    * 来源字段标识
    */
    private String fromColumnMac;


    /**
     * 目标模块ID
     */
    private Long toModuleId;


    /**
     * 目标组件标识
     */
    private String toCompMac;


    /**
     * 目标字段标识
     */
    private String toColumnMac;

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