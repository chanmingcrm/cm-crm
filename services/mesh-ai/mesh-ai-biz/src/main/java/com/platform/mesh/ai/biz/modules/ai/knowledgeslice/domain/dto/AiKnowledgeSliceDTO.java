package com.platform.mesh.ai.biz.modules.ai.knowledgeslice.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description AI知识库分片DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AI知识库分片DTO")
public class AiKnowledgeSliceDTO extends BaseDTO {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 知识库ID
     */
    @Schema(description = "知识库ID")
    private Long knowledgeId;

    /**
     * 文档ID
     */
    @Schema(description = "文档ID")
    private Long knowledgeDocId;

    /**
     * 文档内容
     */
    @Schema(description = "文档内容")
    private String content;


}