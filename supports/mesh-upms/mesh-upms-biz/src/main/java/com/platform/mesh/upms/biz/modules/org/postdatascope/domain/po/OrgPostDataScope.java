package com.platform.mesh.upms.biz.modules.org.postdatascope.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 岗位权限
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("org_post_data_scope")
public class OrgPostDataScope extends BasePO {
    
    /**
    * 职位ID
    */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
    * 职位ID
    */
    private Long postId;
    /**
     * 数据权限类型
     */
    private Integer dataScope;
    /**
     * 数据关联类型
     */
    private Integer dataFlag;
    /**
     * 数据关联ID
     */
    private Long dataId;
    /**
     * 数据关联名称
     */
    private String dataName;

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

