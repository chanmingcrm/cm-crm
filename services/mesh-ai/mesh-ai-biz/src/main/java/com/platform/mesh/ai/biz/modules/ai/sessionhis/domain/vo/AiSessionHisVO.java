package com.platform.mesh.ai.biz.modules.ai.sessionhis.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description AI会话历史VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AI会话历史VO")
public class AiSessionHisVO extends BaseVO {


    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 会话ID
     */
    @Schema(description = "会话ID")
    private Long sessionId;

    /**
     * 会话历史名称
     */
    @Schema(description = "会话历史名称")
    private String modelName;

    /**
     * 会话历史标识
     */
    @Schema(description = "会话历史标识")
    private Integer modelFlag;

    /**
     * 会话历史类别
     */
    @Schema(description = "会话历史类别")
    private Integer modelType;

    /**
     * 对话角色
     */
    @Schema(description = "对话角色")
    private String requireRole;

    /**
     * 请求提示词
     */
    @Schema(description = "请求提示词")
    private String requirePrompt;

    /**
     * 请求参数
     */
    @Schema(description = "请求参数")
    private String requireParams;

    /**
     * 响应码
     */
    @Schema(description = "响应码")
    private String responseCode;

    /**
     * 响应内容
     */
    @Schema(description = "响应内容")
    private String responseMsg;

}