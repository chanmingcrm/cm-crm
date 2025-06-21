package com.platform.mesh.crm.biz.modules.crm.oninvoice.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @description 客户关系发票回执DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "crm_on_invoice", autoResultMap = true)
public class CrmOnInvoice extends AppPO {


    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 合同ID
     */
    private Long contractId;

    /**
     * 总计金额
     */
    private BigDecimal totalMoney;

    /**
     * 折扣金额
     */
    private BigDecimal discountMoney;

    /**
     * 实际金额
     */
    private BigDecimal realMoney;
}