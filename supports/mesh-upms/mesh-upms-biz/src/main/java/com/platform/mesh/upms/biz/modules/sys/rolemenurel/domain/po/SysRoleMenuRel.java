package com.platform.mesh.upms.biz.modules.sys.rolemenurel.domain.po;

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
@TableName("sys_role_menu_rel")
public class SysRoleMenuRel extends BasePO {

    /**
    * 自增ID
    */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
    * 角色ID
    */
    private Long roleId;
    /**
     * 根菜单ID
     */
    private Long rootMenuId;
    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}

