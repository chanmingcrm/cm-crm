package com.platform.mesh.ai.biz.chat.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @description AI生成图片DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AI生成图片DTO")
public class AiImageDTO extends BaseDTO {


    /**
     * 模型编号
     */
    @Schema(description = "模型编号")
    private Long modelId;

    /**
     * 提示词
     */
    @Schema(description = "提示词")
    private String prompt;

    /**
     * 图片高度
     */
    @Schema(description = "图片高度")
    private Integer height;

    /**
     * 图片宽度
     */
    @Schema(description = "图片宽度")
    private Integer width;

    /**
     * 绘制参数
     */
    @Schema(description = "绘制参数")
    private Map<String, String> options;

}