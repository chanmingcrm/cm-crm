package com.platform.mesh.crm.biz.modules.crm.onorder.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description 客户关系订单DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "crm_on_order", autoResultMap = true)
public class CrmOnOrder extends AppPO {


    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 商机ID
     */
    private Long businessId;

    /**
     * 合同ID
     */
    private Long contractId;

    /**
     * 总计金额
     */
    private BigDecimal totalMoney = BigDecimal.ZERO;

    /**
     * 折扣金额
     */
    private BigDecimal discountMoney = BigDecimal.ZERO;

    /**
     * 实际金额
     */
    private BigDecimal realMoney = BigDecimal.ZERO;

    /**
     * 成本金额
     */
    private BigDecimal costMoney = BigDecimal.ZERO;

    /**
     * 已收金额
     */
    private BigDecimal receivedMoney = BigDecimal.ZERO;

    /**
     * 未收金额
     */
    private BigDecimal unreceivedMoney = BigDecimal.ZERO;

    /**
     * 发票金额
     */
    private BigDecimal invoiceMoney = BigDecimal.ZERO;

    /**
     * 利润金额
     */
    private BigDecimal profitMoney = BigDecimal.ZERO;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 最新的流程实例ID
     */
    private Long instProcessId;

    /**
     * 最新的流程实例审批状态
     */
    private Integer processPass;
}