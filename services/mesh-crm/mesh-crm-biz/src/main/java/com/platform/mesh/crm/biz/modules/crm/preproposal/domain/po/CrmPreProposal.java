package com.platform.mesh.crm.biz.modules.crm.preproposal.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @description 客户关系提案报价DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "crm_pre_proposal", autoResultMap = true)
public class CrmPreProposal extends AppPO {


    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 商机ID
     */
    private Long businessId;

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

}