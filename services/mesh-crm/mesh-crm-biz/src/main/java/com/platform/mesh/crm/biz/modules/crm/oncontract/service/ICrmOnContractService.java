package com.platform.mesh.crm.biz.modules.crm.oncontract.service;

import com.platform.mesh.app.api.modules.app.service.IAppService;
import com.platform.mesh.crm.api.modules.fms.base.domain.bo.MoneyBO;
import com.platform.mesh.crm.biz.modules.crm.oncontract.domain.po.CrmOnContract;
import com.platform.mesh.crm.biz.modules.crm.precustomer.enums.ConfirmFlagEnum;

import java.math.BigDecimal;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 客户关系合同签订信息
 * @author 蝉鸣
 */
public interface ICrmOnContractService extends IAppService<CrmOnContract> {

    /**
     * 功能描述:
     * 〈更新合同合计，折扣，实际〉
     * @param contractId contractId
     * @param moneyBO moneyBO
     * @author 蝉鸣
     */
    void updateReceivableMoney(Long contractId, MoneyBO moneyBO);

    /**
     * 功能描述:
     * 〈订单发票金额同步合同发票金额〉
     * @param contractId contractId
     * @param realMoney realMoney
     * @author 蝉鸣
     */
    void updateReceivedMoney(Long contractId, BigDecimal realMoney);

    /**
     * 功能描述:
     * 〈订单发票金额同步合同发票金额〉
     * @param contractId contractId
     * @param invoiceMoney invoiceMoney
     * @author 蝉鸣
     */
    void updateInvoiceMoney(Long contractId, BigDecimal invoiceMoney);

    /**
     * 功能描述:
     * 〈查询客户下的总金额〉
     * @param customerId customerId
     * @author 蝉鸣
     */
    BigDecimal getTotalMoneyByCustomerId(Long customerId);

    /**
     * 功能描述:
     * 〈查询客户下的总金额〉
     * @param customerId customerId
     * @author 蝉鸣
     */
    void setTotalMoneyByCustomerId(Long customerId, ConfirmFlagEnum confirmFlagEnum, BigDecimal totalMoney);

}