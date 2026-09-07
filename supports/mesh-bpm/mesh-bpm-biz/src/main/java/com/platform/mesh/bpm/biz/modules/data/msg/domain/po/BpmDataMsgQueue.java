package com.platform.mesh.bpm.biz.modules.data.msg.domain.po;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 数据模板绑定DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("bpm_data_msg_queue")
public class BpmDataMsgQueue extends BasePO {

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
     * 模块空间
     */
    private String moduleSchema;

    /**
     * 字段类型
     */
    private Integer columnType;

    /**
     * 数据ID
     */
    private Long dataId;

    /**
     * 顶层流程实例ID
     */
    private Long instProcessId;

    /**
     * 流程类型
     */
    private Integer processPass;

    /**
     * 经办人
     */
    private Long handleUserId;

    /**
     * 扩展参数
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Object extendJson;

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

