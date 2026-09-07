package com.platform.mesh.ai.biz.modules.ai.prompttemp.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description AI提示词模板DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AI提示词模板DTO")
public class AiPromptTempDTO extends BaseDTO {


    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 提示词模板名称
     */
    @Schema(description = "提示词模板名称")
    private String tempName;

    /**
     * 提示词分类
     */
    @Schema(description = "提示词分类")
    private Integer tempFlag;

    /**
     * 提示词模板内容
     */
    @Schema(description = "提示词模板内容")
    private String tempContent;


}