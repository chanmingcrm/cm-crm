package com.platform.mesh.ai.biz.modules.cc.user.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


/**
 * @description 客服人员PO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "cc_user", autoResultMap = true)
public class CcUser extends BasePO {

    /**
     * ID
     */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 人员类型
     */
    private Integer userType;

    /**
     * 人员Hash
     */
    private String userHash;

    /**
     * 人员名称
     */
    private String userName;

    /**
     * 客服状态
     */
    private Integer userFlag;

    /**
     * 最大接待数
     */
    private Integer maxReception;

    /**
     * 技能组
     */
    private String skillGroup;

    /**
     * 智能体ID
     */
    private Long agentId;

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

}
