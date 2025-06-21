package com.platform.mesh.bpm.biz.modules.temp.process.domain.po;


import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 流程过程信息
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("bpm_temp_process")
public class BpmTempProcess extends BasePO {

    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 顶层流程ID
     */
    private Long tempRootId;

    /**
     * 顶层流程Hash
     */
    private String tempRootHash;

    /**
     * 流程Hash
     */
    private String processHash;

    /**
    * 流程名称
    */
    private String processName;

    /**
    * 流程版本
    */
    private String processVersion;

    /**
    * 流程图标
    */
    private String processSvg;

    /**
    * 流程类型
    */
    private Integer processFlag;

    /**
    * 流程主题
    */
    private Integer processTheme;

    /**
    * 运行标识
    */
    private Integer runFlag;

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

