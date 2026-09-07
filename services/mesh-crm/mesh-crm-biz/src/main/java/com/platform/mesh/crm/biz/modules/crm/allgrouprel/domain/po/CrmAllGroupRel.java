package com.platform.mesh.crm.biz.modules.crm.allgrouprel.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 客户关系分组关联DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "crm_all_group_rel", autoResultMap = true)
public class CrmAllGroupRel extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
    * 模块ID
    */
    private Long moduleId;

    /**
    * 分组ID
    */
    private Long groupId;

    /**
    * 分组类型GroupTypeEnum
    */
    private Integer groupType;

    /**
    * 数据ID
    */
    private Long dataId;

    /**
    * 用户ID
    */
    private Long scopeUserId;

    /**
    * 组织ID
    */
    private Long scopeOrgId;


}