package com.platform.mesh.app.biz.modules.app.modulesetpick.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import com.platform.mesh.mybatis.plus.annotation.IgnoreDataScope;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 模块分配设置DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "app_module_set_pick", autoResultMap = true)
public class AppModuleSetPick extends BasePO {


    /**
    * ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 模块ID
    */
    private Long moduleId;


    /**
    * 人员ID
    */
    private Long userId;


    /**
    * 成员ID
    */
    private Long memberId;


    /**
    * 成员名称
    */
    private String memberName;


    /**
    * 分配类型
    */
    private Integer pickType;


    /**
    * 分配参数值
    */
    private Integer pickValue;


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
    @IgnoreDataScope()
    @TableField(fill = FieldFill.INSERT)
    private Long scopeUserId;

    /**
     * 组织ID
     */
    @IgnoreDataScope()
    @TableField(fill = FieldFill.INSERT)
    private Long scopeOrgId;

}