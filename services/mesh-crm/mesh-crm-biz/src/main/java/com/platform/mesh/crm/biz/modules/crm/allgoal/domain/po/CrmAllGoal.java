package com.platform.mesh.crm.biz.modules.crm.allgoal.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;

/**
 * @description 客户关系目标
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "crm_all_goal", autoResultMap = true)
public class CrmAllGoal extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
    * 表单ID
    */
    private Long moduleId;

    /**
    * 数据ID
    */
    private Long dataId;

    /**
    * 数据标识
    */
    private Integer dataFlag;

    /**
    * 数据标识
    */
    private Year yearTime;

    /**
    * 年度目标
    */
    private BigDecimal yearGoal;

    /**
    * 天目标
    */
    private BigDecimal dayGoal;

    /**
    * 一月目标
    */
    private BigDecimal janGoal;

    /**
    * 二月目标
    */
    private BigDecimal febGoal;

    /**
    * 三月目标
    */
    private BigDecimal marGoal;

    /**
    * 四月目标
    */
    private BigDecimal aprGoal;

    /**
    * 五月目标
    */
    private BigDecimal mayGoal;

    /**
    * 六月目标
    */
    private BigDecimal junGoal;

    /**
    * 七月目标
    */
    private BigDecimal julGoal;

    /**
    * 八月目标
    */
    private BigDecimal augGoal;

    /**
    * 九月目标
    */
    private BigDecimal sepGoal;

    /**
    * 十月目标
    */
    private BigDecimal octGoal;

    /**
    * 十一月目标
    */
    private BigDecimal novGoal;

    /**
    * 十二月目标
    */
    private BigDecimal decGoal;

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