package com.platform.mesh.crm.api.modules.fms.receivable.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description 财务应收BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="财务应收BO")
public class FmsReceivableBO extends BaseBO {

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private Long customerId;

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
     * 应收金额
     */
    @Schema(description = "应收金额")
    private BigDecimal dueMoney = BigDecimal.ZERO;

    /**
     * 已收金额
     */
    @Schema(description = "已收金额")
    private BigDecimal receivedMoney = BigDecimal.ZERO;

    /**
     * 未收金额
     */
    @Schema(description = "未收金额")
    private BigDecimal balanceMoney = BigDecimal.ZERO;

    /**
     * 应收时间
     */
    @Schema(description = "应收时间")
    private LocalDateTime dueTime;
}