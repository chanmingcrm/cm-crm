package com.platform.mesh.crm.api.modules.fms.payable.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description 财务应付VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="财务应付BO")
public class FmsPayableBO extends BaseBO {


    /**
     * 供应商ID
     */
    @Schema(description = "供应商ID")
    private Long supplierId;

    /**
     * 合同ID
     */
    @Schema(description = "合同ID")
    private Long contractId;

    /**
     * 订单ID
     */
    @Schema(description = "订单ID")
    private Long orderId;

    /**
     * 应付金额
     */
    @Schema(description = "应付金额")
    private BigDecimal payableMoney = BigDecimal.ZERO;

    /**
     * 已付金额
     */
    @Schema(description = "已付金额")
    private BigDecimal paidMoney = BigDecimal.ZERO;

    /**
     * 未付金额
     */
    @Schema(description = "未付金额")
    private BigDecimal balanceMoney = BigDecimal.ZERO;

    /**
     * 应付日期
     */
    @Schema(description = "应付日期")
    private LocalDateTime payableTime;

}