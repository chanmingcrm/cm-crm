package com.platform.mesh.crm.biz.modules.crm.oninvoice.service;

import com.platform.mesh.app.api.modules.app.service.IAppService;
import com.platform.mesh.crm.biz.modules.crm.oninvoice.domain.po.CrmOnInvoice;



/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 客户关系发票回执信息
 * @author 蝉鸣
 */
public interface ICrmOnInvoiceService extends IAppService<CrmOnInvoice> {

    /**
     * 功能描述:
     * 〈修改订单开票金额〉
     * @param orderId orderId
     * @author 蝉鸣
     */
    void updateInvoiceMoney(Long orderId);

}