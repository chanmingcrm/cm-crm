package com.platform.mesh.upms.biz.modules.org.level.domain.po;


import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import com.platform.mesh.mybatis.plus.annotation.TableParentId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 组织层级DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("org_level")
public class OrgLevel extends BasePO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 根层级ID:用于公司/顶层组织类型ID
     */
    private Long rootId;

    /**
     * 父层级ID
     */
    @TableParentId(value = "parent_id")
    private Long parentId;

    /**
    * 层级标识
    */
    private Integer levelFlag;

    /**
    * 名称
    */
    private String levelName;

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

