package com.platform.mesh.crm.biz.modules.crm.onpayment.service;

import com.platform.mesh.app.api.modules.app.service.IAppService;
import com.platform.mesh.crm.biz.modules.crm.onpayment.domain.po.CrmOnPayment;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 客户关系款项记录信息
 * @author 蝉鸣
 */
public interface ICrmOnPaymentService extends IAppService<CrmOnPayment> {

    /**
     * 功能描述:
     * 〈修改回款核销状态〉
     * @param paymentId paymentId
     * @param verifyStatus verifyStatus
     * @author 蝉鸣
     */
    void updatePaymentStatus(Long paymentId, Integer verifyStatus);

}