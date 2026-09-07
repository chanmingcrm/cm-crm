package com.platform.mesh.ai.biz.modules.ai.knowledgedoc.domain.dto;

import com.platform.mesh.ai.biz.modules.ai.knowledgedoc.enums.ContentSourceEnum;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description AI知识库附件DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AI知识库附件DTO")
public class AiKnowledgeDocDTO extends BaseDTO {

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
     * 存储文件ID
     */
    @Schema(description = "存储文件ID")
    private Long docId;

    /**
     * 文档来源
     */
    @SchemaEnum(value = ContentSourceEnum.class, description = "文档来源")
    private Integer contentSource;

    /**
     * 文档名称
     */
    @Schema(description = "文档名称")
    private String docName;

    /**
     * 文档内容
     */
    @Schema(description = "文档内容")
    private String content;


}