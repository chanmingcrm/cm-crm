package com.platform.mesh.crm.biz.soa.event.crm.onpayment;

import com.platform.mesh.crm.biz.modules.crm.onpayment.domain.po.CrmOnPayment;
import com.platform.mesh.crm.biz.modules.crm.onpayment.enums.PaymentVerifyStatusEnum;
import com.platform.mesh.crm.biz.modules.crm.onpayment.service.ICrmOnPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @description CRM回款事件监听
 * @author 蝉鸣
 */
@Component
public class CrmOnPaymentEventListener {

    @Autowired
    private ICrmOnPaymentService crmOnPaymentService;

    /**
     * 功能描述:
     * 〈款项创建成功后更新回款核销状态〉
     * @param event event
     * @author 蝉鸣
     */
    @EventListener(CrmOnPaymentCreatedEvent.class)
    public void updatePaymentStatus(CrmOnPaymentCreatedEvent event) {
        CrmOnPayment dataPO = event.getCrmOnPayment();
        crmOnPaymentService.updatePaymentStatus(dataPO.getId(), PaymentVerifyStatusEnum.VALID.getValue());
    }
}
