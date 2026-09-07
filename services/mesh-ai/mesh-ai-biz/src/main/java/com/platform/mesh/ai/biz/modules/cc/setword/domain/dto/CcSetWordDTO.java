package com.platform.mesh.ai.biz.modules.cc.setword.domain.dto;

import com.platform.mesh.ai.biz.modules.cc.setword.enums.WordFlagEnum;
import com.platform.mesh.ai.biz.modules.cc.setword.enums.WordRateEnum;
import com.platform.mesh.ai.biz.modules.cc.setword.enums.WordRuleEnum;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 提示语关系DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="提示语关系DTO")
public class CcSetWordDTO extends BaseDTO {


    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 提示语类型
     */
    @SchemaEnum(value = WordFlagEnum.class, description = "提示语类型")
    private Integer wordFlag;

    /**
     * 提示语频率
     */
    @SchemaEnum(value = WordRateEnum.class, description = "提示语频率")
    private Integer wordRate;

    /**
     * 提示语间隔
     */
    @Schema(description = "提示语间隔")
    private Integer wordInterval;

    /**
     * 提示语规则
     */
    @SchemaEnum(value = WordRuleEnum.class, description = "提示语规则")
    private Integer wordRule;

    /**
     * 提示语内容
     */
    @Schema(description = "提示语内容")
    private String wordContent;

}
