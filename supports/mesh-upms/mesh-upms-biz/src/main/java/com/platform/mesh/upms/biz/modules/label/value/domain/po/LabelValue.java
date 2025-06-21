package com.platform.mesh.upms.biz.modules.label.value.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 标签值DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "label_value", autoResultMap = true)
public class LabelValue extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 字典ID
    */
    private Long labelId;


    /**
    * 字典标识
    */
    private Integer labelFlag;


    /**
    * 字典编码
    */
    private String labelMac;


    /**
    * 字典名称
    */
    private String labelName;


    /**
    * 字典值
    */
    private String labelValue;


    /**
    * 字典颜色
    */
    private String labelColor;


    /**
    * 数据类型
    */
    private Integer dataType;


    /**
    * 默认标识
    */
    private Integer defaultFlag;


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