package com.platform.mesh.ai.biz.modules.ai.agent.domain.vo;

import com.platform.mesh.ai.biz.soa.agent.enums.AgentFlagEnum;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description AiAgentVO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AiAgentVO")
public class AiAgentVO extends BaseVO {



    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 智能体名称
     */
    @Schema(description = "智能体名称")
    private String agentName;

    /**
     * 智能体图标
     */
    @Schema(description = "智能体图标")
    private String agentLogo;

    /**
     * agentKey
     */
    @Schema(description = "智能体昵称")
    private String agentNick;

    /**
     * 智能体描述
     */
    @Schema(description = "智能体描述")
    private String agentDesc;

    /**
     * 智能体空间
     */
    @Schema(description = "智能体空间")
    private String agentSpace;

    /**
     * 智能体回复逻辑
     */
    @Schema(description = "智能体回复逻辑")
    private String agentPrompt;

    /**
     * agent地址
     */
    @Schema(description = "agent地址")
    private String agentUrl;

    /**
     * 智能体ID
     */
    @Schema(description = "智能体ID")
    private String agentKey;

    /**
     * 智能体ID
     */
    @SchemaEnum(value = AgentFlagEnum.class, description = "智能体ID")
    private Integer agentFlag;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}