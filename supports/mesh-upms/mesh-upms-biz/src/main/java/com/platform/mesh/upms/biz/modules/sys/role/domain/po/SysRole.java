package com.platform.mesh.upms.biz.modules.sys.role.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
                        
/**
 * @description 系统角色表(SysRole)实体类
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class SysRole extends BasePO {

    /**
    * 角色自增ID
    */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
    * 角色名
    */
    private String roleName;

    /**
    * (YesOrNoEnum)
    */
    private Integer actFlag;

    /**
    * (YesOrNoEnum)
    */
    private Integer delFlag;

    /**
    * (YesOrNoEnum)
    */
    private Integer initFlag;

    /**
    * 排序
    */
    private Integer sort;

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

