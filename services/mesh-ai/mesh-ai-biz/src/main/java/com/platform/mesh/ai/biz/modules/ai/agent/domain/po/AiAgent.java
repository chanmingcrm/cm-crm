package com.platform.mesh.ai.biz.modules.ai.agent.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description AiAgent
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "ai_agent", autoResultMap = true)
public class AiAgent extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
     * 智能体名称
     */
    private String agentName;

    /**
     * 智能体图标
     */
    private String agentLogo;

    /**
     * 智能体昵称
     */
    private String agentNick;

    /**
     * 智能体描述
     */
    private String agentDesc;

    /**
     * 智能体空间
     */
    private String agentSpace;

    /**
     * 智能体回复逻辑
     */
    private String agentPrompt;

    /**
     * agent地址
     */
    private String agentUrl;

    /**
     * 智能体ID
     */
    private String agentKey;

    /**
    * agentSecret
    */
    private String agentSecret;

    /**
    * 智能体类型
    */
    private Integer agentFlag;

    /**
    * 是否删除
    */
    private Integer delFlag;

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