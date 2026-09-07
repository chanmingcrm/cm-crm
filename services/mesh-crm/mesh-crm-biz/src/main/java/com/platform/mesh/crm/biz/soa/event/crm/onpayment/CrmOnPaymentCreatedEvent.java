package com.platform.mesh.crm.biz.soa.event.crm.onpayment;

import com.platform.mesh.crm.biz.modules.crm.onpayment.domain.po.CrmOnPayment;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * @description 合同创建完成事件
 * @author 蝉鸣
 */
@Getter
public class CrmOnPaymentCreatedEvent extends ApplicationEvent {

    private final CrmOnPayment crmOnPayment;

    private final Map<String, Object> docData;

    public CrmOnPaymentCreatedEvent(CrmOnPayment crmOnPayment, Map<String, Object> docData) {
        super(crmOnPayment);
        this.crmOnPayment = crmOnPayment;
        this.docData = docData;
    }

}
