package com.platform.mesh.crm.biz.modules.crm.onpayment.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @description 客户关系款项记录BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客户关系款项记录BO")
public class PaymentSumBO extends BaseBO {

    /**
     * 客户金额合计
     */
    @Schema(description = "客户金额合计")
    private BigDecimal customerMoney = BigDecimal.ZERO;

    /**
     * 合同金额合计
     */
    @Schema(description = "合同金额合计")
    private BigDecimal contractMoney = BigDecimal.ZERO;

    /**
     * 订单金额合计
     */
    @Schema(description = "订单金额合计")
    private BigDecimal orderMoney = BigDecimal.ZERO;
}