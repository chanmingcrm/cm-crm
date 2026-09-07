package com.platform.mesh.app.biz.modules.app.modulesettransauto.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 模块转化自动化设置DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "app_module_set_trans_auto", autoResultMap = true)
public class AppModuleSetTransAuto extends BasePO {


    /**
    * ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 转化配置ID
     */
    private Long transId;

    /**
    * 模块来源ID
    */
    private Long moduleFromId;


    /**
     * 模块目标ID
     */
    private Long moduleToId;


    /**
    * 模块搜索ID
    */
    private Long moduleSearchId;


    /**
     * 模块搜索字段
     */
    private String columnMac;


    /**
    * 规则字段标识
    */
    private String ruleMac;


    /**
     * 模块搜索字段名称
     */
    private String ruleName;


    /**
    * 规则数据类型
    */
    private String ruleDataType;


    /**
    * 规则数据值
    */
    private String ruleDataValue;


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