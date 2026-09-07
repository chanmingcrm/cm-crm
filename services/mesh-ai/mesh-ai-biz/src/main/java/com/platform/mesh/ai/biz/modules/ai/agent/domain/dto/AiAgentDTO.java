package com.platform.mesh.ai.biz.modules.ai.agent.domain.dto;

import com.platform.mesh.ai.biz.soa.agent.enums.AgentFlagEnum;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description AiAgentDTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AiAgentDTO")
public class AiAgentDTO extends BaseDTO {


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
     * 智能体密钥
     */
    @Schema(description = "智能体密钥")
    private String agentSecret;

    /**
     * 智能体ID
     */
    @SchemaEnum(value = AgentFlagEnum.class, description = "智能体ID")
    private Integer agentFlag;

    /**
     * 知识库ID
     */
    @Schema(description = "知识库ID")
    private List<Long> klIds;

}