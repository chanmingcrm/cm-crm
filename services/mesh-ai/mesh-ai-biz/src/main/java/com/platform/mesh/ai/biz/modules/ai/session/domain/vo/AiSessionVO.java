package com.platform.mesh.ai.biz.modules.ai.session.domain.vo;

import com.platform.mesh.ai.biz.soa.model.enums.ModelFlagEnum;
import com.platform.mesh.ai.biz.soa.model.enums.ModelTypeEnum;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description AI会话VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AI会话VO")
public class AiSessionVO extends BaseVO {


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
     * 会话名称
     */
    @Schema(description = "会话名称")
    private String modelName;

    /**
     * 会话标识
     */
    @SchemaEnum(value = ModelFlagEnum.class, description = "会话标识")
    private Integer modelFlag;

    /**
     * 会话类别
     */
    @SchemaEnum(value = ModelTypeEnum.class, description = "会话类别")
    private Integer modelType;

}