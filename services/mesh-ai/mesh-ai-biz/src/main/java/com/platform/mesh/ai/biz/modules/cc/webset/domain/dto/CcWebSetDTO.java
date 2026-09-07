package com.platform.mesh.ai.biz.modules.cc.webset.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 页面配置DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="页面配置DTO")
public class CcWebSetDTO extends BaseDTO {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 页面描述
     */
    @Schema(description = "页面描述")
    private String webDesc;

    /**
     * 页面脚本
     */
    @Schema(description = "页面脚本")
    private String webScript;

    /**
     * 官网咨询引导配置
     */
    @Valid
    @Schema(description = "官网咨询引导配置")
    private CcConsultationGuideDTO consultationGuide;

}
