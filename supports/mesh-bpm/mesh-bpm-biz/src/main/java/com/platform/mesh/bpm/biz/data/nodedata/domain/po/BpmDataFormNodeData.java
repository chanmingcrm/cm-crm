package com.platform.mesh.bpm.biz.data.nodedata.domain.po;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 业务数据实例流程节点表单数据DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "bpm_data_form_node_data", autoResultMap = true)
public class BpmDataFormNodeData extends BasePO {


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
    * 模块ID
    */
    private Long moduleId;

    /**
    * 父模块ID
    */
    private Long parentModuleId;

    /**
    * 表单ID
    */
    private Long formId;

    /**
    * 顶层流程模板ID
    */
    private Long tempProcessId;

    /**
    * 流程实例ID
    */
    private Long instProcessId;

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
    * 流程实例节点ID
    */
    private Long instNodeId;

    /**
    * 表单数据
    */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private JSONArray formData;

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