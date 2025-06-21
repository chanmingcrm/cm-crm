package com.platform.mesh.bpm.biz.data.noderel.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 业务数据模板流程节点表单关系DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "bpm_data_form_node_rel", autoResultMap = true)
public class BpmDataFormNodeRel extends BasePO {


    /**
    * 
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
    * 应用ID
    */
    private Long appId;

    /**
    * 父模块ID
    */
    private Long parentModuleId;

    /**
    * 模块ID
    */
    private Long moduleId;

    /**
    * 表单ID
    */
    private Long formId;

    /**
    * 流程模板ID
    */
    private Long tempProcessId;

    /**
    * 流程名称
    */
    private String processName;

    /**
    * 流程版本
    */
    private String processVersion;

    /**
    * 流程模板节点ID
    */
    private Long tempNodeId;

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