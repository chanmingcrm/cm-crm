package com.platform.mesh.ai.biz.modules.ai.knowledge.domain.dto;

import com.platform.mesh.ai.biz.soa.store.enums.StoreFlagEnum;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description AI知识库DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AI知识库DTO")
public class AiKnowledgeDTO extends BaseDTO {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 模型ID
     */
    @Schema(description = "模型ID")
    private Long modelId;

    /**
     * 向量库存储类型
     */
    @SchemaEnum(value = StoreFlagEnum.class, description = "向量库存储类型")
    private Integer storeFlag;

    /**
     * 知识库名称
     */
    @Schema(description = "知识库名称")
    private String knowledgeName;

    /**
     * 欢迎语
     */
    @Schema(description = "欢迎语")
    private String knowledgeWelcome;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String knowledgeDesc;

    /**
     * 知识库排序
     */
    @Schema(description = "知识库排序")
    private Integer knowledgeSort;

    /**
     * 是否公开知识库（1 是 2否）
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "主键ID")
    private Integer knowledgeShare;

}