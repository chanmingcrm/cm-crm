package com.platform.mesh.crm.biz.modules.crm.allgroup.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 客户关系分组DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "crm_all_group", autoResultMap = true)
public class CrmAllGroup extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
    * 父主键ID
    */
    private Long parentId;

    /**
    * 表单ID
    */
    private Long moduleId;

    /**
    * 分组类型GroupTypeEnum
    */
    private Integer groupType;

    /**
    * 分组标识
    */
    private String groupMac;

    /**
    * 分组名称
    */
    private String groupName;

    /**
    * 分组描述
    */
    private String groupDesc;

    /**
    * 创建时间
    */
    private LocalDateTime createTime;

    /**
    * 修改时间
    */
    private LocalDateTime updateTime;

    /**
    * 用户ID
    */
    private Long scopeUserId;

    /**
    * 组织ID
    */
    private Long scopeOrgId;

}