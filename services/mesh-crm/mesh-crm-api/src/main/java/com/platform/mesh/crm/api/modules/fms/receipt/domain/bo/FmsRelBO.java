package com.platform.mesh.crm.api.modules.fms.receipt.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description 财务BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="财务BO")
public class FmsRelBO extends BaseBO {

    /**
     * 合同金额
     */
    @Schema(description = "合同金额")
    private BigDecimal contractMoney;

    /**
     * 订单金额
     */
    @Schema(description = "订单金额")
    private BigDecimal orderMoney;

}