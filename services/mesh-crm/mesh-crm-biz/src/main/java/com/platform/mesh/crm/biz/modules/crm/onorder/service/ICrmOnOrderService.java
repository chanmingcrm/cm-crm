package com.platform.mesh.crm.biz.modules.crm.onorder.service;

import com.platform.mesh.app.api.modules.app.service.IAppService;
import com.platform.mesh.crm.biz.modules.crm.onorder.domain.po.CrmOnOrder;

import java.math.BigDecimal;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 客户关系订单信息
 * @author 蝉鸣
 */
public interface ICrmOnOrderService extends IAppService<CrmOnOrder> {

    /**
     * 功能描述:
     * 〈同步订单金额〉
     * @param orderId orderId
     * @param realMoney realMoney
     * @author 蝉鸣
     */
    void updatePaymentMoney(Long orderId, BigDecimal realMoney);

    /**
     * 功能描述:
     * 〈同步发票金额〉
     * @param orderId orderId
     * @param realMoney realMoney
     * @author 蝉鸣
     */
    void updateInvoiceMoney(Long orderId, BigDecimal realMoney);

    /**
     * 功能描述:
     * 〈同步合同金额〉
     * @param contractId contractId
     * @author 蝉鸣
     */
    void updateReceivedMoney(Long contractId);
}