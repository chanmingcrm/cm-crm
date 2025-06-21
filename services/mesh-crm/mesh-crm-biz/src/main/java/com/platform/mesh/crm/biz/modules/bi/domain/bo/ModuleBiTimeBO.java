package com.platform.mesh.crm.biz.modules.bi.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description BI统计BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="BI统计BO")
public class ModuleBiTimeBO extends BiTimeBO {


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

}