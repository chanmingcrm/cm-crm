package com.platform.mesh.crm.biz.modules.bi.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description BI统计DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="BI统计DTO")
public class ModuleBiDTO extends BiDTO {

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;


}