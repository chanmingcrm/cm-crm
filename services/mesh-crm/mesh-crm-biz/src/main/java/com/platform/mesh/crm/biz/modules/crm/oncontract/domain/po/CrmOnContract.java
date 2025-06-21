package com.platform.mesh.crm.biz.modules.crm.oncontract.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description 客户关系合同签订DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "crm_on_contract", autoResultMap = true)
public class CrmOnContract extends AppPO {

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 商机ID
     */
    private Long businessId;

    /**
     * 订单ID
     */
    private Long orderId;

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

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

}