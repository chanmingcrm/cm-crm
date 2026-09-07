package com.platform.mesh.crm.api.modules.fms.base.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @description 金额BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="金额BO")
public class MoneyBO extends BaseBO {

    /**
     * 总计金额
     */
    @Schema(description = "总计金额")
    private BigDecimal totalMoney = BigDecimal.ZERO;

    /**
     * 折扣金额
     */
    @Schema(description = "折扣金额")
    private BigDecimal discountMoney = BigDecimal.ZERO;

    /**
     * 实际金额
     */
    @Schema(description = "实际金额")
    private BigDecimal realMoney = BigDecimal.ZERO;

    /**
     * 成本金额
     */
    @Schema(description = "成本金额")
    private BigDecimal costMoney = BigDecimal.ZERO;

    /**
     * 销售金额
     */
    @Schema(description = "销售金额")
    private BigDecimal saleMoney = BigDecimal.ZERO;

    /**
     * 利润金额
     */
    @Schema(description = "利润金额")
    private BigDecimal profitMoney = BigDecimal.ZERO;

}