package com.platform.mesh.ai.biz.job.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description AI文章生成任务配置
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AI文章生成任务配置")
public class AiArticleJobConfigDTO extends BaseDTO {

    /**
     * 主题
     */
    @Schema(description = "主题")
    private String topic;

    /**
     * 生成数量
     */
    @Schema(description = "生成数量")
    private Integer count;

}
