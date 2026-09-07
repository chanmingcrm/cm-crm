package com.platform.mesh.ai.biz.modules.ai.knowledgeslice.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description AI知识库分片VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AI知识库分片VO")
public class AiKnowledgeSliceVO extends BaseVO {


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
     * 向量ID
     */
    @Schema(description = "向量ID")
    private String vectorId;

    /**
     * 向量索引
     */
    @Schema(description = "向量索引")
    private String vectorIndex;

    /**
     * 文档内容
     */
    @Schema(description = "文档内容")
    private String content;

}