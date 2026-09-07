package com.platform.mesh.ai.biz.modules.ai.model.domain.vo;

import java.math.BigDecimal;

import com.platform.mesh.ai.biz.soa.model.enums.ModelFlagEnum;
import com.platform.mesh.ai.biz.soa.model.enums.ModelTypeEnum;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description AI模型VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AI模型VO")
public class AiModelVO extends BaseVO {


    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 模型名称
     */
    @Schema(description = "模型名称")
    private String modelName;

    /**
     * 模型标识
     */
    @SchemaEnum(value = ModelFlagEnum.class, description = "模型标识")
    private Integer modelFlag;

    /**
     * 模型类别
     */
    @SchemaEnum(value = ModelTypeEnum.class, description = "模型类别")
    private Integer modelType;

    /**
     * 模型描述
     */
    private String modelDesc;

    /**
     * 模型基础地址
     */
    @Schema(description = "模型基础地址")
    private String baseUrl;

    /**
     * 模型密钥
     */
    @Schema(description = "模型密钥")
    private String apiKey;

    /**
     * 随机性
     */
    @Schema(description = "随机性")
    private BigDecimal temperature;

    /**
     * 最大token数
     */
    @Schema(description = "最大token数")
    private Integer maxTokens;

    /**
     * 核采样阈值
     */
    @Schema(description = "核采样阈值")
    private BigDecimal topP;

    /**
     * 惩罚重复出现的
     */
    @Schema(description = "惩罚重复出现的")
    private BigDecimal frequencyPenalty;

    /**
     * 惩罚新出现的
     */
    @Schema(description = "惩罚新出现的")
    private BigDecimal presencePenalty;

    /**
     * 设置停止词
     */
    @Schema(description = "设置停止词")
    private String stopKey;

    /**
     * 是否启用流式响应
     */
    @Schema(description = "是否启用流式响应")
    private Integer withStream;

}