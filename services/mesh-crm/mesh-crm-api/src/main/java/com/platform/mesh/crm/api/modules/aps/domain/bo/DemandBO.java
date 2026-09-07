package com.platform.mesh.crm.api.modules.aps.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @description 需求库存BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "需求库存BO")
public class DemandBO extends BaseBO {

    /**
     * 产品ID
     */
    @Schema(description = "产品ID")
    private Long productId;

    /**
     * 产品名称
     */
    @Schema(description = "产品名称")
    private String productName;

    /**
     * 产品大类ID
     */
    @Schema(description = "产品大类ID")
    private Long categoryId;

    /**
     * 产品大类名称
     */
    @Schema(description = "产品大类名称")
    private String categoryName;

    /**
     * 需求数量
     */
    @Schema(description = "需求数量")
    private BigDecimal demandNum;

    /**
     * 库存数量
     */
    @Schema(description = "库存数量")
    private BigDecimal stockNum;


}
