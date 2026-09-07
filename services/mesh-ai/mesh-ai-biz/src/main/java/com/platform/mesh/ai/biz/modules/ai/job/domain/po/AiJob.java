package com.platform.mesh.ai.biz.modules.ai.job.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description AI任务配置
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "ai_job", autoResultMap = true)
public class AiJob extends BasePO {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务类型
     */
    private Integer jobFlag;

    /**
     * 智能体ID
     */
    private Long agentId;

    /**
     * 任务参数
     */
    private Object params;

    /**
     * 任务参数
     */
    private Long tenantId;
}
